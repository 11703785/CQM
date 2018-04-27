package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.Ehcache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks the status of a bootstrap request.
 *
 * @author Eric Dalquist
 * @version $Revision$
 */
public class BootstrapRequest {
    /**
     * Possible states of a BootstrapRequest.
     */
    public enum BootstrapStatus {
        /**
         * There is no pending request to a peer for bootstrap.
         */
        UNSENT,
        /**
         * There is a pending request to a peer for bootstrap.
         */
        REQUESTED,
        /**
         * The peer responded that it could not fulfill the bootstrap request.
         */
        INCOMPLETE,
        /**
         * The peer responded that it fulfilled the bootstrap request.
         */
        COMPLETE;
    }

    /**
     * A synchronization aid .
     */
    private volatile CountDownLatch boostrapCompleteLatch
            = new CountDownLatch(1);
    /**
     * A {@code long} value that may be updated atomically.
     */
    private final AtomicLong replicated = new AtomicLong();
    /**
     * An interface for Ehcache.
     */
    private final Ehcache cache;
    /**
     * Is asynchronous.
     */
    private final boolean asynchronous;
    /**
     * chunk Size.
     */
    private final int chunkSize;
    /**
     * Tracks the status of a bootstrap request.
     */
    private volatile BootstrapStatus bootstrapStatus = BootstrapStatus.UNSENT;

    /**
     * Create a new bootstrap request for the specified cache.
     *
     * @param cacheVal        Ehcache
     * @param asynchronousVal asynchronous
     * @param chunkSizeVal    chunkSize
     */
    public BootstrapRequest(final Ehcache cacheVal,
                            final boolean asynchronousVal,
                            final int chunkSizeVal) {
        this.cache = cacheVal;
        this.asynchronous = asynchronousVal;
        this.chunkSize = chunkSizeVal;
    }

    /**
     * @return The current status of the bootstrap request
     */
    public BootstrapStatus getBootstrapStatus() {
        return this.bootstrapStatus;
    }

    /**
     * @param bootstrapStatusVal The current status of the bootstrap request
     */
    public void setBootstrapStatus(
            final BootstrapStatus bootstrapStatusVal) {
        if (bootstrapStatusVal == null) {
            throw new IllegalArgumentException(
                    "BootstrapStatus cannot be null");
        }
        this.bootstrapStatus = bootstrapStatusVal;
    }

    /**
     * @return If the bootstrap request should be handled asynchronously
     */
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    /**
     * @return The maximum serialized size of the elements
     * to request from a remote cache peer during bootstrap.
     */
    public int getChunkSize() {
        return this.chunkSize;
    }

    /**
     * Reset the replicationCount and waitForBootstrap
     * latch to their initial states.
     */
    public void reset() {
        this.boostrapCompleteLatch = new CountDownLatch(1);
        this.replicated.set(0);
        this.bootstrapStatus = BootstrapStatus.UNSENT;
    }

    /**
     * Signal that bootstrapping is complete.
     *
     * @param status BootstrapStatus
     */
    public void boostrapComplete(final BootstrapStatus status) {
        this.bootstrapStatus = status;
        this.boostrapCompleteLatch.countDown();
    }

    /**
     * Waits for the receiver to signal that the current bootstrap
     * request is complete.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the {@code timeout} argument
     * @return {@code true} if the count reached zero and {@code false}
     * if the waiting time elapsed before the count reached zero
     * @throws InterruptedException if the current thread is interrupted
     *                              while waiting
     */
    public boolean waitForBoostrap(
            final long timeout, final TimeUnit unit)
            throws InterruptedException {
        return this.boostrapCompleteLatch.await(timeout, unit);
    }

    /**
     * Count a received bootstrap replication.
     */
    public void countReplication() {
        this.replicated.incrementAndGet();
    }

    /**
     * @return The number of bootstrap replication responses received
     */
    public long getReplicationCount() {
        return this.replicated.get();
    }

    /**
     * @return The cache that is being bootstrapped
     */
    public Ehcache getCache() {
        return this.cache;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BootstrapRequest [cache=" + this.cache.getName()
                + ", bootstrapStatus=" + this.bootstrapStatus
                + ", boostrapCompleteLatch="
                + this.boostrapCompleteLatch.getCount()
                + ", replicated=" + this.replicated
                + ", asynchronous=" + this.asynchronous
                + ", chunkSize=" + this.chunkSize + "]";
    }


}
