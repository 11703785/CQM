package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.util.NamedThreadFactory;
import org.jgroups.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages bootstrap requests and responses.
 *
 * @author Eric Dalquist
 * @version $Revision$
 */
public class JGroupsBootstrapManager {
    /**
     * The org.slf4j.Logger interface.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(JGroupsBootstrapManager.class.getName());

    /**
     * the number of threads to keep in the pool.
     */
    private static final int BOOTSTRAP_CORE_THREADS = 0;
    /**
     * the maximum number of threads to allow in the pool.
     */
    private static final int BOOTSTRAP_MAX_THREADS = 50;
    /**
     * this is the maximum time that excess idle threads
     * will wait for new tasks before terminating.
     */
    private static final int BOOTSTRAP_THREAD_TIMEOUT = 60;
    /**
     * delay in milliseconds before task is to be executed.
     * period time in milliseconds between successive task executions.
     */
    private static final long BOOTSTRAP_REQUEST_CLEANUP_INTERVAL = 60000;
    /**
     * An instance of this class is used to generate a stream of
     * pseudorandom numbers.
     */
    private static final Random BOOTSTRAP_PEER_CHOOSER = new Random();
    /**
     * the maximum time to wait in milliseconds.
     */
    private static final long BOOTSTRAP_RESPONSE_TIMEOUT = 30000;
    /**
     * Try the maximum number of times.
     */
    private static final long BOOTSTRAP_RESPONSE_TRIES = 10;
    /**
     * the total time to wait in milliseconds.
     */
    private static final long BOOTSTRAP_RESPONSE_MAX_TIMEOUT =
            BOOTSTRAP_RESPONSE_TIMEOUT * BOOTSTRAP_RESPONSE_TRIES;
    /**
     * the size of chunk.
     */
    private static final int BOOTSTRAP_CHUNK_SIZE = 100;
    /**
     * is alive.
     */
    private volatile boolean alive = true;
    /**
     * A {@code boolean} value that may be updated atomically.
     */
    private final AtomicBoolean referenceTimerScheduled =
            new AtomicBoolean(false);
    /**
     * Simplified version of a Map that wraps a weak value
     * reference concurrent Map of {@link BootstrapRequest}s.
     */
    private final BootstrapRequestMap bootstrapRequests =
            new BootstrapRequestMap();
    /**
     * A facility for threads to schedule tasks for future execution in a
     * background thread.
     */
    private Timer bootstrapRequestCleanupTimer;
    /**
     * An {@link ExecutorService} that executes each submitted task using
     * one of possibly several pooled threads, normally configured
     * using {@link Executors} factory methods.
     */
    private final ThreadPoolExecutor bootstrapThreadPool;
    /**
     * The name of cluster.
     */
    private final String clusterName;
    /**
     * The peer of cache.
     */
    private final JGroupsCachePeer cachePeer;
    /**
     * The manager of cache.
     */
    private final CacheManager cacheManager;

    /**
     * Create a new bootstrap manager.
     *
     * @param clusterNameVal  name of cluster
     * @param cachePeerVal    peer of cache
     * @param cacheManagerVal manager of cache
     */
    public JGroupsBootstrapManager(final String clusterNameVal,
                                   final JGroupsCachePeer cachePeerVal,
                                   final CacheManager cacheManagerVal) {
        this.clusterName = clusterNameVal;
        this.cachePeer = cachePeerVal;
        this.cacheManager = cacheManagerVal;
        this.bootstrapThreadPool = new ThreadPoolExecutor(
                BOOTSTRAP_CORE_THREADS, BOOTSTRAP_MAX_THREADS,
                BOOTSTRAP_THREAD_TIMEOUT, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(true),
                new NamedThreadFactory(clusterNameVal + " Bootstrap"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * @param duration Wait until the bootstrap process is complete.
     * @return is complete
     */
    public boolean waitForCompleteBootstrap(final long duration) {
        return this.bootstrapRequests.waitForMapSize(0, duration);
    }

    /**
     * Shutdown resources uses by the bootstrap managed.
     */
    public void dispose() {
        this.alive = false;

        if (!this.bootstrapRequests.isEmpty()) {
            LOG.debug("Waiting for BootstrapRequests to complete");
            this.bootstrapRequests.waitForMapSize(
                    0, BOOTSTRAP_RESPONSE_TIMEOUT);
            if (!this.bootstrapRequests.isEmpty()) {
                LOG.warn("Shutting down bootstrap manager while there are "
                                + "still {} bootstrap requests pending",
                        this.bootstrapRequests.size());
            }
        }

        this.bootstrapThreadPool.shutdown();
        try {
            if (!this.bootstrapThreadPool.awaitTermination(
                    BOOTSTRAP_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS)) {
                LOG.warn("Not all bootstrap threads shutdown within "
                        + "{}ms window", BOOTSTRAP_RESPONSE_TIMEOUT);
            }
        } catch (InterruptedException e) {
            LOG.warn("Interrupted while waiting "
                    + "for bootstrap threads to complete", e);
        }

        if (this.bootstrapRequestCleanupTimer != null) {
            this.bootstrapRequestCleanupTimer.cancel();
            this.bootstrapRequestCleanupTimer.purge();
        }
    }

    /**
     * Set the maximum number of threads to use in the bootstrap pool.
     * Defaults to {@link #BOOTSTRAP_MAX_THREADS}
     *
     * @param bootstrapThreads the maximum number of threads.
     */
    public void setBootstrapThreads(final int bootstrapThreads) {
        this.bootstrapThreadPool.setMaximumPoolSize(bootstrapThreads);
    }

    /**
     * @return true if there are pending bootstrap requests
     */
    public boolean isPendingBootstrapRequests() {
        return !this.bootstrapRequests.isEmpty();
    }

    /**
     * @param bootstrapRequest Handle a bootstrap request for a cache.
     */
    public void handleBootstrapRequest(
            final BootstrapRequest bootstrapRequest) {
        if (!this.alive) {
            LOG.warn("dispose has been called, no new BootstrapRequests "
                            + "will be handled, ignoring: {}",
                    bootstrapRequest);
            return;
        }

        if (!this.referenceTimerScheduled.getAndSet(true)) {
            this.bootstrapRequestCleanupTimer =
                    new Timer(clusterName
                            + " Bootstrap Request Cleanup Thread", true);
            this.bootstrapRequestCleanupTimer
                    .schedule(new BootstrapRequestCleanerTimerTask(),
                            BOOTSTRAP_REQUEST_CLEANUP_INTERVAL,
                            BOOTSTRAP_REQUEST_CLEANUP_INTERVAL);
            LOG.debug("Scheduled BootstrapRequest Reference cleanup timer "
                    + "with {}ms period", BOOTSTRAP_REQUEST_CLEANUP_INTERVAL);
        }

        final Ehcache cache = bootstrapRequest.getCache();
        final String cacheName = cache.getName();
        final BootstrapRequest oldRequest =
                this.bootstrapRequests.put(cacheName, bootstrapRequest);
        if (oldRequest != null) {
            LOG.warn("There is already a BootstrapRequest registered "
                            + "for {} with value {}, it has been replaced "
                            + "with the current request.",
                    cacheName, oldRequest);
        }
        LOG.debug("Registered {}", bootstrapRequest);
        final BootstrapRequestRunnable bootstrapRequestRunnable =
                new BootstrapRequestRunnable(bootstrapRequest);
        final Future<?> future = this.bootstrapThreadPool
                .submit(bootstrapRequestRunnable);
        if (!bootstrapRequest.isAsynchronous()) {
            LOG.debug("Waiting up to {}ms for BootstrapRequest of {} to "
                    + "complete", BOOTSTRAP_RESPONSE_MAX_TIMEOUT, cacheName);
            try {
                future.get(BOOTSTRAP_RESPONSE_MAX_TIMEOUT,
                        TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOG.warn("Interrupted while waiting for bootstrap of "
                        + cacheName + " to complete", e);
            } catch (ExecutionException e) {
                LOG.warn("Exception thrown while bootstrapping "
                        + cacheName, e);
            } catch (TimeoutException e) {
                LOG.warn("Timed out waiting " + BOOTSTRAP_RESPONSE_MAX_TIMEOUT
                        + "ms for bootstrap of " + cacheName
                        + " to complete", e);
            }
        }
    }

    /**
     * Handles responding to a bootstrap request.
     *
     * @param message An EventMessage used for JGroups
     */
    public void sendBootstrapResponse(final JGroupEventMessage message) {
        if (!this.alive) {
            LOG.warn("dispose has been called, no new BootstrapResponses"
                    + " will be handled");
            return;
        }
        final BootstrapResponseRunnable bootstrapResponseRunnable =
                new BootstrapResponseRunnable(message);
        this.bootstrapThreadPool.submit(bootstrapResponseRunnable);
    }

    /**
     * Handle a {@link JGroupEventMessage#BOOTSTRAP_COMPLETE} message.
     *
     * @param message An EventMessage used for JGroups
     */
    public void handleBootstrapComplete(final JGroupEventMessage message) {
        final String cacheName = message.getCacheName();
        final BootstrapRequest bootstrapRequestStatus =
                this.bootstrapRequests.get(cacheName);
        if (bootstrapRequestStatus != null) {
            bootstrapRequestStatus.boostrapComplete(
                    BootstrapRequest.BootstrapStatus.COMPLETE);
        } else {
            LOG.warn("No BootstrapRequest registered for cache {}, "
                    + "the event will have no effect: {}", cacheName, message);
        }
    }

    /**
     * Handle a {@link JGroupEventMessage#BOOTSTRAP_INCOMPLETE} message.
     *
     * @param message An EventMessage used for JGroups
     */
    public void handleBootstrapIncomplete(final JGroupEventMessage message) {
        final String cacheName = message.getCacheName();
        final BootstrapRequest bootstrapRequestStatus =
                this.bootstrapRequests.get(cacheName);
        if (bootstrapRequestStatus != null) {
            bootstrapRequestStatus.boostrapComplete(
                    BootstrapRequest.BootstrapStatus.INCOMPLETE);
        } else {
            LOG.warn("No BootstrapRequest registered for cache {},"
                            + " the event will have no effect: {}",
                    cacheName, message);
        }
    }

    /**
     * Handle a {@link JGroupEventMessage#BOOTSTRAP_RESPONSE} message.
     *
     * @param message An EventMessage used for JGroups
     */
    public void handleBootstrapResponse(final JGroupEventMessage message) {
        final String cacheName = message.getCacheName();
        final BootstrapRequest bootstrapRequestStatus =
                this.bootstrapRequests.get(cacheName);
        if (bootstrapRequestStatus != null) {
            final Ehcache cache = bootstrapRequestStatus.getCache();
            cache.put(message.getElement(), true);
            bootstrapRequestStatus.countReplication();
        } else {
            LOG.warn("No BootstrapRequest registered for cache {},"
                            + " the event will have no effect: {}",
                    cacheName, message);
        }
    }

    /**
     * Task that calls {@link JGroupsCacheReceiver#cleanBootstrapRequests()}.
     */
    private final class BootstrapRequestCleanerTimerTask extends TimerTask {
        @Override
        public void run() {
            bootstrapRequests.cleanBootstrapRequests();
        }
    }

    /**
     * Runnable that handles sending a cache bootstrap request.
     */
    private final class BootstrapRequestRunnable extends ThreadNamingRunnable {
        /**
         * Tracks the status of a bootstrap request.
         */
        private final BootstrapRequest bootstrapRequest;

        /**
         * Create a  BootstrapRequestRunnable.
         *
         * @param bootstrapRequestVal Tracks the status of a bootstrap request
         */
        BootstrapRequestRunnable(final BootstrapRequest bootstrapRequestVal) {
            super(" - Request for " + bootstrapRequestVal.getCache().getName());
            this.bootstrapRequest = bootstrapRequestVal;
        }

        @Override
        public void runInternal() {
            final Ehcache cache = bootstrapRequest.getCache();
            final String cacheName = cache.getName();

            try {
                final List<Address> addresses =
                        cachePeer.getOtherGroupMembers();
                if (addresses == null || addresses.size() == 0) {
                    LOG.info("There are no other nodes in the cluster "
                            + "to bootstrap {} from", cacheName);
                    return;
                }
                final Address localAddress = cachePeer.getLocalAddress();
                LOG.debug("Loading cache {} with local address {} "
                                + "from peers: {}",
                        new Object[]{cacheName, localAddress, addresses});

                int replicationCount = 0;
                do {
                    bootstrapRequest.reset();
                    final int randomPeerNumber =
                            BOOTSTRAP_PEER_CHOOSER.nextInt(addresses.size());
                    final Address address = addresses.remove(randomPeerNumber);
                    JGroupEventMessage event =
                            new JGroupEventMessage(
                                    JGroupEventMessage.BOOTSTRAP_REQUEST,
                                    localAddress, null, cacheName);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Requesting bootstrap of {} from {}",
                                cacheName, address);
                    }
                    cachePeer.send(address, Arrays.asList(event));
                    waitForBootstrap(cacheName, address);
                    replicationCount += bootstrapRequest.getReplicationCount();
                    //Loop until the bootstrap process is complete
                    // or we've contacted all peers
                }
                while (bootstrapRequest.getBootstrapStatus()
                        != BootstrapRequest.BootstrapStatus.COMPLETE
                        && addresses.size() > 0);
                if (BootstrapRequest.BootstrapStatus.COMPLETE
                        == bootstrapRequest.getBootstrapStatus()) {
                    LOG.info("Bootstrap for cache {} is complete,"
                                    + " loaded {} elements",
                            cacheName, replicationCount);
                } else {
                    LOG.info("Bootstrap for cache {} ended with status {}, "
                                    + "loaded {} elements",
                            new Object[]{cacheName,
                                    bootstrapRequest.getBootstrapStatus(),
                                    replicationCount});
                }
            } finally {
                final BootstrapRequest removedRequest =
                        bootstrapRequests.remove(cacheName);
                if (removedRequest == null) {
                    LOG.warn("No BootstrapRequest for {} to remove", cacheName);
                    return;
                }

                LOG.debug("Removed {}", removedRequest);
            }
        }

        /**
         * Wait for the bootstrap complete message
         * or for the BOOTSTRAP_RESPONSE_MAX_TIMEOUT time to pass.
         *
         * @param cacheName name of cache
         * @param address   address of peer
         */
        protected void waitForBootstrap(final String cacheName,
                                        final Address address) {
            //Wait up to 10 minutes for the bootstrap to complete
            for (int waitTry = 1; waitTry <= BOOTSTRAP_RESPONSE_TRIES;
                 waitTry++) {
                try {
                    if (bootstrapRequest.waitForBoostrap(
                            BOOTSTRAP_RESPONSE_TIMEOUT,
                            TimeUnit.MILLISECONDS)) {
                        return;
                    }
                    LOG.debug("Bootstrap of {} did not complete in {}ms,"
                                    + " will wait {} more times.",
                            new Object[]{cacheName,
                                    BOOTSTRAP_RESPONSE_TIMEOUT * waitTry,
                                    BOOTSTRAP_RESPONSE_TRIES - waitTry});
                } catch (InterruptedException e) {
                    LOG.warn("Interrupted while waiting for bootstrap of "
                            + cacheName + " to complete", e);
                }
            }

            LOG.warn("Bootstrap of {} did not complete in {}ms,"
                            + " giving up on bootstrap request to {}.",
                    new Object[]{cacheName, BOOTSTRAP_RESPONSE_MAX_TIMEOUT,
                            address});
        }

        @Override
        public String toString() {
            return "BootstrapRequestRunnable [name=" + this.threadNameSuffix
                    + ", message=" + this.bootstrapRequest + "]";
        }
    }

    /**
     * Runnable that handles responding to a cache bootstrap request.
     */
    private final class BootstrapResponseRunnable extends ThreadNamingRunnable {
        /**
         * An EventMessage used for JGroups.
         */
        private final JGroupEventMessage message;

        /**
         * Create a  BootstrapRequestRunnable.
         *
         * @param messageVal An EventMessage used for JGroups
         */
        BootstrapResponseRunnable(final JGroupEventMessage messageVal) {
            super(" - Response for " + messageVal.getCacheName());
            this.message = messageVal;
        }

        @Override
        public void runInternal() {
            final Address requestAddress = (Address)
                    this.message.getSerializableKey();
            final String cacheName = this.message.getCacheName();
            final Ehcache cache = cacheManager.getEhcache(cacheName);
            if (cache == null) {
                LOG.warn("ignoring bootstrap request: from {} for cache {} "
                                + "which does not exist on this memeber",
                        requestAddress, cacheName);

                final JGroupEventMessage bootstrapCompleteMessage =
                        new JGroupEventMessage(
                                JGroupEventMessage.BOOTSTRAP_INCOMPLETE,
                                null, null, cacheName);
                cachePeer.send(requestAddress,
                        Arrays.asList(bootstrapCompleteMessage));

                return;
            }

            LOG.debug("servicing bootstrap request: from {} for cache={}",
                    requestAddress, cacheName);

            //Check that this CacheManager doesn't have
            // any pending bootstrap requests.
            // If it does it is still starting up and
            //cannot reasonably respond to bootstrap requests
            // from other CacheManagers
            if (bootstrapRequests.get(cacheName) != null) {
                LOG.debug("This group member is currently bootstrapping {} "
                                + "from another node and cannot respond to"
                                + " a bootstrap request for this cache. "
                                + "Notifying requester of incomplete "
                                + "bootstrap",
                        cacheName);

                final JGroupEventMessage bootstrapCompleteMessage =
                        new JGroupEventMessage(
                                JGroupEventMessage.BOOTSTRAP_INCOMPLETE,
                                null, null, cacheName);
                cachePeer.send(requestAddress,
                        Arrays.asList(bootstrapCompleteMessage));
            }


            final List<?> keys = cache.getKeys();

            //Skip any real work if the cache is empty.
            if (keys == null || keys.size() == 0) {
                LOG.debug("no keys to reply to {} to bootstrap cache {}",
                        requestAddress, cacheName);
            } else {
                final List<JGroupEventMessage> messageList =
                        new ArrayList<JGroupEventMessage>(
                                Math.min(keys.size(), BOOTSTRAP_CHUNK_SIZE));
                for (final Object key : keys) {
                    //Don't touch the cache stats for replication
                    final Element element = cache.getQuiet(key);

                    //Skip entries that have been removed or are expired
                    if (element == null || element.isExpired()) {
                        continue;
                    }

                    final JGroupEventMessage groupEventMessage =
                            new JGroupEventMessage(
                                    JGroupEventMessage.BOOTSTRAP_RESPONSE,
                                    (Serializable) key, element, cacheName);

                    messageList.add(groupEventMessage);

                    if (messageList.size() == BOOTSTRAP_CHUNK_SIZE) {
                        this.sendResponseChunk(
                                cache, requestAddress, messageList);
                        messageList.clear();
                    }

                }

                //send any remaining messages
                if (messageList.size() > 0) {
                    this.sendResponseChunk(cache, requestAddress, messageList);
                }
            }

            //Tell the requester that bootstrap is complete
            final JGroupEventMessage bootstrapCompleteMessage =
                    new JGroupEventMessage(
                            JGroupEventMessage.BOOTSTRAP_COMPLETE,
                            null, null, cacheName);
            cachePeer.send(requestAddress,
                    Arrays.asList(bootstrapCompleteMessage));
        }

        /**
         * @param cache          cache
         * @param requestAddress address
         * @param events         events
         */
        private void sendResponseChunk(final Ehcache cache,
                                       final Address requestAddress,
                                       final List<JGroupEventMessage> events) {
            LOG.debug("reply {} elements to {} to bootstrap cache {}",
                    new Object[]{events.size(), requestAddress,
                            cache.getName()});
            cachePeer.send(requestAddress, events);
        }

        @Override
        public String toString() {
            return "BootstrapResponseRunnable [name=" + this.threadNameSuffix
                    + ", message=" + this.message + "]";
        }
    }
}
