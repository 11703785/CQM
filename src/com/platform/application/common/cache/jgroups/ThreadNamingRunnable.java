package com.platform.application.common.cache.jgroups;

/**
 * Basic Runnable that sets the name of the thread doing the execution.
 *
 * @author Eric Dalquist
 */
public abstract class ThreadNamingRunnable implements Runnable {
    /**
     * The suffix that will be appended to the thread name
     * when the runnable is executing.
     */
    protected final String threadNameSuffix;

    /**
     * Create new ThreadNamingRunnable with the specified suffix.
     *
     * @param threadNameSuffixVal 线程名后缀
     */
    public ThreadNamingRunnable(final String threadNameSuffixVal) {
        this.threadNameSuffix = threadNameSuffixVal;
    }

    /**
     * {@inheritDoc}
     */
    public final void run() {
        final Thread currentThread = Thread.currentThread();
        final String name = currentThread.getName();
        try {
            currentThread.setName(name + this.threadNameSuffix);
            this.runInternal();
        } finally {
            currentThread.setName(name);
        }
    }

    /**
     * @see Runnable#run()
     */
    public abstract void runInternal();
}
