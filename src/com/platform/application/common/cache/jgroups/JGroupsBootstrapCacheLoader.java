package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.distribution.RemoteCacheException;

/**
 * Loads Elements from a random Cache Peer.
 *
 * @author Greg Luck
 * @version $Id$
 */
public class JGroupsBootstrapCacheLoader implements BootstrapCacheLoader {
    /**
     * Whether to load asynchronously.
     */
    private boolean asynchronous;

    /**
     * The maximum serialized size of the elements to request
     * from a remote cache peer during bootstrap.
     */
    private int maximumChunkSizeBytes;

    /**
     * Creates a bootstrap cache loader that will work
     * with RMI based distribution.
     *
     * @param asynchronousVal  Whether to load asynchronously
     * @param maximumChunkSize The maximum serialized size
     */
    public JGroupsBootstrapCacheLoader(
            final boolean asynchronousVal,
            final int maximumChunkSize) {
        this.asynchronous = asynchronousVal;
        this.maximumChunkSizeBytes = maximumChunkSize;
    }

    /**
     * Bootstraps the cache from a random CachePeer.
     * Requests are done in chunks estimated at 5MB Serializable size.
     * This balances memory use on each end and network performance.
     *
     * @param cache Ehcache
     * @throws RemoteCacheException if anything goes wrong with the remote call
     */
    public void load(final Ehcache cache) throws RemoteCacheException {
        final JGroupsCacheManagerPeerProvider cachePeerProvider =
                JGroupsCacheManagerPeerProvider.getCachePeerProvider(cache);

        final BootstrapRequest bootstrapRequest =
                new BootstrapRequest(cache, this.asynchronous,
                        this.maximumChunkSizeBytes);
        final JGroupsBootstrapManager bootstrapManager =
                cachePeerProvider.getBootstrapManager();
        bootstrapManager.handleBootstrapRequest(bootstrapRequest);
    }

    /**
     * @return true if this bootstrap loader is asynchronous
     */
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    /**
     * Gets the maximum chunk size.
     *
     * @return the maximum chunk size
     */
    public int getMaximumChunkSizeBytes() {
        return maximumChunkSizeBytes;
    }

    /**
     * Clones this loader.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new JGroupsBootstrapCacheLoader(
                asynchronous, maximumChunkSizeBytes);
    }
}
