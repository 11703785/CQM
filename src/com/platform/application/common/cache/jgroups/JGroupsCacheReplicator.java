package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.CacheReplicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Monestie (pmonestie[at]@gmail.com)
 * @author <a href="mailto:gluck@gregluck.com">Greg Luck</a>
 * @version $Id$
 *          <p/> This implements CacheReplicator using JGroups as underlying
 *          replication mechanism The peer provider should be of type
 *          JGroupsCacheManagerPeerProvider It is assumed that the cachepeer is
 *          a JGroupsCacheManagerPeerProvider
 */
public class JGroupsCacheReplicator implements CacheReplicator {
    /**
     * The org.slf4j.Logger interface.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(JGroupsCacheReplicator.class.getName());
    /**
     * The default interval for async cache replication.
     */
    public static final long DEFAULT_ASYNC_INTERVAL = 1000;

    /**
     * interval of asynchronous replication.
     */
    private final long asynchronousReplicationInterval;

    /**
     * Whether or not to replicate puts.
     */
    private final boolean replicatePuts;

    /**
     * Whether or not to replicate updates.
     */
    private final boolean replicateUpdates;

    /**
     * Replicate update via copying, if false via deleting.
     */
    private final boolean replicateUpdatesViaCopy;

    /**
     * Whether or not to replicate remove events.
     */
    private final boolean replicateRemovals;

    /**
     * whether or not to be alive.
     */
    private boolean alive;

    /**
     * Constructor called by factory, does synchronous replication.
     *
     * @param replicatePutsVal           whether or not to replicate puts
     * @param replicateUpdatesVal        whether or not to replicate updates
     * @param replicateUpdatesViaCopyVal whether or not to update via copying
     * @param replicateRemovalsVal       whether or not to
     *                                   replicate remove events
     */
    public JGroupsCacheReplicator(
            final boolean replicatePutsVal,
            final boolean replicateUpdatesVal,
            final boolean replicateUpdatesViaCopyVal,
            final boolean replicateRemovalsVal) {

        this(replicatePutsVal, replicateUpdatesVal, replicateUpdatesViaCopyVal,
                replicateRemovalsVal, -1);
    }

    /**
     * Constructor called by factory, does asynchronous replication.
     *
     * @param replicatePutsVal                   whether or not to
     *                                           replicate puts
     * @param replicateUpdatesVal                whether or not to
     *                                           replicate updates
     * @param replicateUpdatesViaCopyVal         whether or not to
     *                                           update via copying
     * @param replicateRemovalsVal               whether or not to
     *                                           replicate remove events
     * @param asynchronousReplicationIntervalVal interval of
     *                                           asynchronous replication
     */
    public JGroupsCacheReplicator(
            final boolean replicatePutsVal,
            final boolean replicateUpdatesVal,
            final boolean replicateUpdatesViaCopyVal,
            final boolean replicateRemovalsVal,
            final long asynchronousReplicationIntervalVal) {

        this.replicatePuts = replicatePutsVal;
        this.replicateUpdates = replicateUpdatesVal;
        this.replicateUpdatesViaCopy = replicateUpdatesViaCopyVal;
        this.replicateRemovals = replicateRemovalsVal;
        this.asynchronousReplicationInterval =
                asynchronousReplicationIntervalVal;
        this.alive = true;
    }


    @Override
    public boolean alive() {
        return this.alive;
    }

    @Override
    public boolean isReplicateUpdatesViaCopy() {
        return replicateUpdatesViaCopy;
    }

    @Override
    public boolean notAlive() {
        return !this.alive;
    }

    @Override
    public void dispose() {
        this.alive = false;
    }

    @Override
    public void notifyElementExpired(final Ehcache cache,
                                     final Element element) {
        //Ignored
    }

    @Override
    public void notifyElementPut(
            final Ehcache cache, final Element element)
            throws CacheException {
        if (notAlive() || !replicatePuts) {
            return;
        }
        replicatePutNotification(cache, element);
    }

    @Override
    public void notifyElementRemoved(
            final Ehcache cache, final Element element)
            throws CacheException {
        if (notAlive() || !replicateRemovals) {
            return;
        }

        replicateRemoveNotification(cache, element);
    }

    @Override
    public void notifyElementUpdated(
            final Ehcache cache, final Element element)
            throws CacheException {
        if (notAlive() || !replicateUpdates) {
            return;
        }

        if (replicateUpdatesViaCopy) {
            replicatePutNotification(cache, element);
        } else {
            replicateRemoveNotification(cache, element);
        }

    }

    @Override
    public void notifyElementEvicted(
            final Ehcache cache, final Element element) {
        //Ignore
    }

    @Override
    public void notifyRemoveAll(final Ehcache cache) {
        if (replicateRemovals) {
            final String cacheName = cache.getName();
            LOG.debug("Remove all elements called on {}", cacheName);
            JGroupEventMessage e =
                    new JGroupEventMessage(JGroupEventMessage.REMOVE_ALL,
                            null, null, cacheName,
                            this.asynchronousReplicationInterval);
            sendNotification(cache, e);
        }
    }

    /**
     * @param cache   the cache emitting the notification
     * @param element the element just deleted, or a synthetic element
     *                with just the key set if no element was removed
     */
    private void replicatePutNotification(
            final Ehcache cache, final Element element) {
        if (!element.isKeySerializable()) {
            LOG.warn("Key {} is not Serializable and cannot be replicated.",
                    element.getObjectKey());
            return;
        }
        if (!element.isSerializable()) {
            LOG.warn("Object with key {} is not Serializable "
                            + "and cannot be updated via copy",
                    element.getObjectKey());
            return;
        }
        JGroupEventMessage e = new JGroupEventMessage(JGroupEventMessage.PUT,
                (Serializable) element.getObjectKey(), element,
                cache.getName(), this.asynchronousReplicationInterval);
        sendNotification(cache, e);
    }

    /**
     * @param cache   the cache emitting the notification
     * @param element the element just deleted, or a synthetic element
     *                with just the key set if no element was removed
     */
    private void replicateRemoveNotification(
            final Ehcache cache, final Element element) {
        if (!element.isKeySerializable()) {
            LOG.warn("Key {} is not Serializable and cannot be replicated.",
                    element.getObjectKey());
            return;
        }
        JGroupEventMessage e =
                new JGroupEventMessage(JGroupEventMessage.REMOVE,
                        (Serializable) element.getObjectKey(), null,
                        cache.getName(), this.asynchronousReplicationInterval);

        sendNotification(cache, e);
    }

    /**
     * Used to send notification to the peer. If Async this method simply add
     * the element to the replication queue. If not async, searches for the
     * cachePeer and send the Message. That way the class handles both async and
     * sync replication Sending is delegated to the peer.
     *
     * @param cache        the cache emitting the notification
     * @param eventMessage an EventMessage used for JGroups
     */
    protected void sendNotification(
            final Ehcache cache, final JGroupEventMessage eventMessage) {
        final List<CachePeer> peers = this.listRemoteCachePeers(cache);

        for (final CachePeer peer : peers) {
            try {
                peer.send(Arrays.asList(eventMessage));
            } catch (RemoteException e) {
                LOG.warn("Failed to send message '" + eventMessage
                        + "' to peer '" + peer + "'", e);
            }
        }

    }

    /**
     * Package protected List of cache peers.
     *
     * @param cache the cache emitting the notification
     * @return a list of {@link CachePeer} peers for the given cache, excluding
     * the local peer.
     */
    @SuppressWarnings("unchecked")
    private List<CachePeer> listRemoteCachePeers(final Ehcache cache) {
        final CacheManager cacheManager = cache.getCacheManager();
        final CacheManagerPeerProvider provider =
                cacheManager.getCacheManagerPeerProvider(
                        JGroupsCacheManagerPeerProvider.SCHEME_NAME);
        return provider.listRemoteCachePeers(cache);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
