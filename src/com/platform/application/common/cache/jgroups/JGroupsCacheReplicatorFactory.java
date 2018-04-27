package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;
import net.sf.ehcache.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Pierre Monestie (pmonestie__REMOVE__THIS__@gmail.com)
 * @author <a href="mailto:gluck@gregluck.com">Greg Luck</a>
 * @version $Id$
 */

public class JGroupsCacheReplicatorFactory extends CacheEventListenerFactory {
    /**
     * The org.slf4j.Logger interface.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(
                    JGroupsCacheReplicatorFactory.class.getName());
    /**
     * the attribute name of interval.
     */
    private static final String ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS =
            "asynchronousReplicationIntervalMillis";

    /**
     * the type of replicate:puts.
     */
    private static final String REPLICATE_PUTS = "replicatePuts";
    /**
     * the type of replicate:updates.
     */
    private static final String REPLICATE_UPDATES = "replicateUpdates";
    /**
     * the type of replicate:updates via copy.
     */
    private static final String REPLICATE_UPDATES_VIA_COPY =
            "replicateUpdatesViaCopy";
    /**
     * the type of replicate:removals.
     */
    private static final String REPLICATE_REMOVALS = "replicateRemovals";
    /**
     * the type of replicate:asynchronously.
     */
    private static final String REPLICATE_ASYNCHRONOUSLY =
            "replicateAsynchronously";

    /**
     * Empty arg constructor.
     */
    public JGroupsCacheReplicatorFactory() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheEventListener createCacheEventListener(
            final Properties properties) {
        LOG.debug("Creating JGroups CacheEventListener with configuration: {}",
                properties);
        boolean replicatePuts =
                extractBooleanProperty(properties, REPLICATE_PUTS, true);
        boolean replicateUpdates =
                extractBooleanProperty(properties, REPLICATE_UPDATES, true);
        boolean replicateUpdatesViaCopy =
                extractBooleanProperty(properties, REPLICATE_UPDATES_VIA_COPY,
                        false);
        boolean replicateRemovals =
                extractBooleanProperty(properties, REPLICATE_REMOVALS, true);
        boolean replicateAsync =
                extractBooleanProperty(properties, REPLICATE_ASYNCHRONOUSLY,
                        true);


        if (replicateAsync) {
            long asyncTime =
                    extractAsynchronousReplicationIntervalMillis(properties);

            return new JGroupsCacheReplicator(replicatePuts, replicateUpdates,
                    replicateUpdatesViaCopy, replicateRemovals, asyncTime);
        }

        return new JGroupsCacheReplicator(replicatePuts, replicateUpdates,
                replicateUpdatesViaCopy, replicateRemovals);
    }

    /**
     * Extract the {@link #ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS}
     * setting from the properties.
     *
     * @param properties properties
     * @return interval for async cache replication
     */
    protected long extractAsynchronousReplicationIntervalMillis(
            final Properties properties) {
        String parsedString = PropertyUtil.extractAndLogProperty(
                ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS, properties);
        if (parsedString == null) {
            return JGroupsCacheReplicator.DEFAULT_ASYNC_INTERVAL;
        }

        try {
            return Long.parseLong(parsedString);
        } catch (NumberFormatException e) {
            LOG.warn("Number format exception trying to set {}. "
                            + "Using the default instead."
                            + " String value was: '{}'",
                    ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS, parsedString);
            return JGroupsCacheReplicator.DEFAULT_ASYNC_INTERVAL;
        }
    }

    /**
     * Extract a Boolean out of a Property.
     *
     * @param properties   the properties
     * @param propertyName the name of the property
     * @param defaultValue the deulat value id none is found
     * @return the extracted property
     */
    protected boolean extractBooleanProperty(
            final Properties properties,
            final String propertyName,
            final boolean defaultValue) {
        String booleanCandidate =
                PropertyUtil.extractAndLogProperty(propertyName, properties);
        if (booleanCandidate != null) {
            return Boolean.parseBoolean(booleanCandidate);
        }

        return defaultValue;
    }
}
