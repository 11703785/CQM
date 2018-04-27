package com.platform.application.common.cache.jgroups;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Simplified version of a Map that wraps a weak value
 * reference concurrent Map of {@link BootstrapRequest}s.
 * It also handles allowing clients to wait for the map's
 * size to change to a specific value.
 *
 * @author Eric Dalquist
 * @version $Revision$
 */
class BootstrapRequestMap {
    /**
     * The org.slf4j.Logger interface.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(BootstrapRequestMap.class.getName());

    /**
     * A Map that tracks the status of a bootstrap request.
     */
    private final ConcurrentMap<String, Reference<BootstrapRequest>>
            bootstrapRequests =
            new ConcurrentHashMap<String, Reference<BootstrapRequest>>();
    /**
     * Request change lock Object.
     */
    private final Object requestChangeNotifier = new Object();

    /**
     * Wait for the map to change to the specified size.
     * Returns true if the map reached the size before
     * the timeout.
     *
     * @param size     wait for size
     * @param duration wait time
     * @return Is equal size
     */
    public boolean waitForMapSize(final int size, final long duration) {
        final long waitTime = Math.min(duration, 1000);
        final long start = System.currentTimeMillis();
        this.cleanBootstrapRequests();
        while (this.bootstrapRequests.size() != size
                && (System.currentTimeMillis() - start) < duration) {
            try {
                synchronized (this.requestChangeNotifier) {
                    this.requestChangeNotifier.wait(waitTime);
                }
            } catch (InterruptedException e) {
                LOG.warn("Interrupted while waiting for"
                        + " BootstrapRequestMap to empty", e);
            }

            this.cleanBootstrapRequests();
        }

        return this.bootstrapRequests.size() == size;
    }

    /**
     * @return a set view of the keys contained in this map
     * @see Map#keySet()
     */
    public Set<String> keySet() {
        this.cleanBootstrapRequests();
        return Collections.unmodifiableSet(this.bootstrapRequests.keySet());
    }

    /**
     * @return <tt>true</tt> if this map contains no key-value mappings
     * @see Map#isEmpty()
     */
    public boolean isEmpty() {
        this.cleanBootstrapRequests();
        return this.bootstrapRequests.isEmpty();
    }

    /**
     * @return the number of key-value mappings in this map
     * @see Map#size()
     */
    public int size() {
        this.cleanBootstrapRequests();
        return this.bootstrapRequests.size();
    }

    /**
     * @param cacheName        name of cache
     * @param bootstrapRequest request
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>,
     * if the implementation supports <tt>null</tt> values.)
     * @see Map#put(Object, Object)
     */
    public BootstrapRequest put(
            final String cacheName,
            final BootstrapRequest bootstrapRequest) {
        final Reference<BootstrapRequest> oldReference =
                this.bootstrapRequests.put(cacheName,
                        new WeakReference<BootstrapRequest>(bootstrapRequest));

        synchronized (this.requestChangeNotifier) {
            this.requestChangeNotifier.notifyAll();
        }

        if (oldReference != null) {
            return oldReference.get();
        }

        return null;
    }

    /**
     * @param cacheName name of cache
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @see Map#get(Object)
     */
    public BootstrapRequest get(final String cacheName) {
        final Reference<BootstrapRequest> reference =
                this.bootstrapRequests.get(cacheName);
        if (reference == null) {
            return null;
        }

        final BootstrapRequest bootstrapRequest = reference.get();
        if (bootstrapRequest == null) {
            LOG.info("BootstrapRequest for {} has been GCed, "
                    + "removing from requests map.", cacheName);

            //Remove GC'd entry
            if (this.bootstrapRequests.remove(cacheName, reference)) {
                synchronized (this.requestChangeNotifier) {
                    this.requestChangeNotifier.notifyAll();
                }
            }
            return null;
        }

        return bootstrapRequest;
    }

    /**
     * @param cacheName name of cache
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @see Map#remove(Object)
     */
    public BootstrapRequest remove(final String cacheName) {
        final Reference<BootstrapRequest> reference =
                this.bootstrapRequests.remove(cacheName);
        if (reference == null) {
            return null;
        }

        synchronized (this.requestChangeNotifier) {
            this.requestChangeNotifier.notifyAll();
        }

        return reference.get();
    }

    /**
     * Iterates over the map cleaning up
     * {@link WeakReference}s that have been GCd.
     */
    public void cleanBootstrapRequests() {
        final Iterator<Map.Entry<String, Reference<BootstrapRequest>>>
                bootstrapRequestItr =
                this.bootstrapRequests.entrySet().iterator();
        while (bootstrapRequestItr.hasNext()) {

            final Entry<String, Reference<BootstrapRequest>>
                    bootstrapRequestEntry = bootstrapRequestItr.next();

            if (bootstrapRequestEntry.getValue().get() == null) {
                LOG.info("BootstrapRequest for {} has been GCed,"
                                + " removing from requests map.",
                        bootstrapRequestEntry.getKey());
                bootstrapRequestItr.remove();

                synchronized (this.requestChangeNotifier) {
                    this.requestChangeNotifier.notifyAll();
                }
            }
        }
    }
}
