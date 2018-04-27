package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.util.CacheTransactionHelper;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Handles {@link Receiver} functions around for a {@link CacheManager}.
 *
 * @author Eric Dalquist
 * @version $Revision$
 */
public class JGroupsCacheReceiver implements Receiver {
    /**
     * The org.slf4j.Logger interface.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(JGroupsCacheReceiver.class.getName());
    /**
     * The cache manager.
     */
    private final CacheManager cacheManager;
    /**
     * Manages bootstrap requests and responses.
     */
    private final JGroupsBootstrapManager bootstrapManager;

    /**
     * Create a new {@link Receiver}.
     *
     * @param cacheManagerVal     The cache manager
     * @param bootstrapManagerVal Manages bootstrap requests and responses
     */
    public JGroupsCacheReceiver(
            final CacheManager cacheManagerVal,
            final JGroupsBootstrapManager bootstrapManagerVal) {
        this.cacheManager = cacheManagerVal;
        this.bootstrapManager = bootstrapManagerVal;
    }

    @Override
    public void receive(final Message msg) {
        if (msg == null || msg.getLength() == 0) {
            LOG.warn("Recieved an empty or null Message: {}", msg);
            return;
        }

        final Object object = msg.getObject();
        if (object == null) {
            LOG.warn("Recieved a Message with a null object: {}", msg);
            return;
        }

        if (object instanceof JGroupEventMessage) {
            this.safeHandleJGroupNotification((JGroupEventMessage) object);
        } else if (object instanceof List<?>) {
            final List<?> messages = (List<?>) object;
            LOG.trace("Recieved List of {} JGroupEventMessages",
                    messages.size());
            for (final Object message : messages) {
                if (message == null) {
                    continue;
                }
                if (message instanceof JGroupEventMessage) {
                    this.safeHandleJGroupNotification(
                            (JGroupEventMessage) message);
                } else {
                    LOG.warn("Recieved message of type " + List.class
                            + " but member was of type '" + message.getClass()
                            + "' and not " + JGroupEventMessage.class
                            + ". Member ignored: " + message);
                }
            }
        } else {
            LOG.warn("Recieved message with payload of type "
                    + object.getClass() + " and not "
                    + JGroupEventMessage.class + " or List<"
                    + JGroupEventMessage.class.getSimpleName()
                    + ">. Message: " + msg + " payload " + object);
        }
    }

    /* ********** Local Methods ********** */

    /**
     * Have to do a little helper method like this to get around
     * the checkstyle cyclomatic check.
     *
     * @param message An EventMessage used for JGroups
     */
    private void safeHandleJGroupNotification(
            final JGroupEventMessage message) {
        final String cacheName = message.getCacheName();
        Ehcache cache = cacheManager.getEhcache(cacheName);
        boolean started = cache != null
                && CacheTransactionHelper.isTransactionStarted(cache);
        if (cache != null && !started) {
            CacheTransactionHelper.beginTransactionIfNeeded(cache);
        }

        try {
            this.handleJGroupNotification(message);
        } catch (Exception e) {
            LOG.error("Failed to handle message " + message, e);
        } finally {
            if (cache != null && !started) {
                CacheTransactionHelper.commitTransactionIfNeeded(cache);
            }
        }
    }

    /**
     * @param message An EventMessage used for JGroups
     */
    private void handleJGroupNotification(final JGroupEventMessage message) {
        final String cacheName = message.getCacheName();
        switch (message.getEvent()) {
            case JGroupEventMessage.BOOTSTRAP_REQUEST:
                LOG.debug("received bootstrap request: from {} for cache={}",
                        message.getSerializableKey(), cacheName);
                this.bootstrapManager.sendBootstrapResponse(message);
                break;
            case JGroupEventMessage.BOOTSTRAP_COMPLETE:
                LOG.debug("received bootstrap complete: cache={}",
                        cacheName);
                this.bootstrapManager.handleBootstrapComplete(message);
                break;
            case JGroupEventMessage.BOOTSTRAP_INCOMPLETE:
                LOG.debug("received bootstrap incomplete: cache={}",
                        cacheName);
                this.bootstrapManager.handleBootstrapIncomplete(message);
                break;
            case JGroupEventMessage.BOOTSTRAP_RESPONSE:
                final Serializable serializableKey =
                        message.getSerializableKey();
                LOG.debug("received bootstrap reply: cache={}, key={}",
                        cacheName, serializableKey);
                this.bootstrapManager.handleBootstrapResponse(message);
                break;
            default:
                this.handleEhcacheNotification(message, cacheName);
                break;
        }
    }

    /**
     * @param message   An EventMessage used for JGroups
     * @param cacheName the cache name
     */
    private void handleEhcacheNotification(
            final JGroupEventMessage message, final String cacheName) {
        final Ehcache cache = this.cacheManager.getEhcache(cacheName);
        if (cache == null) {
            LOG.warn("Received message {} for cache that does not exist: {}",
                    message, cacheName);
            return;
        }
        switch (message.getEvent()) {
            case JGroupEventMessage.REMOVE_ALL:
                LOG.debug("received remove all: cache={}", cacheName);
                cache.removeAll(true);
                break;
            case JGroupEventMessage.REMOVE:
                final Serializable serializableKey =
                        message.getSerializableKey();
                if (cache.getQuiet(serializableKey) != null) {
                    LOG.debug("received remove: cache={}, key={}",
                            cacheName, serializableKey);
                    cache.remove(serializableKey, true);
                } else if (LOG.isTraceEnabled()) {
                    LOG.trace("received remove: cache={}, key={} - Ignoring, "
                                    + "key is not in the local cache.",
                            cacheName, serializableKey);
                }
                break;
            case JGroupEventMessage.PUT:
                final Serializable sKey = message.getSerializableKey();
                LOG.debug("received put: cache={}, key={}", cacheName, sKey);
                cache.put(message.getElement(), true);
                break;
            default:
                LOG.warn("Unknown JGroupsEventMessage type recieved,"
                        + " ignoring message: " + message);
                break;
        }
    }

    /* ********** Unused ********** */

    @Override
    public void getState(final OutputStream output) {
        //Not Implemented
    }

    @Override
    public void setState(final InputStream input) {
        //Not Implemented
    }

    @Override
    public void block() {
        //Not Implemented
    }

    @Override
    public void unblock() {
        //Not Implemented
    }

    @Override
    public void suspect(final Address suspectedMbr) {
        //Not Implemented
    }

    @Override
    public void viewAccepted(final View newView) {
        //Not Implemented
    }
}
