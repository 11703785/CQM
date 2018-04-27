package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.LegacyEventMessage;

import java.io.Serializable;

/**
 * An EventMessage used for JGroups.
 *
 * @author Pierre Monestie (pmonestie[at]@gmail.com)
 * @author <a href="mailto:gluck@gregluck.com">Greg Luck</a>
 * @version $Id$
 *          EventMessage class for the JGroupsCacheReplicator.
 */
public class JGroupEventMessage extends LegacyEventMessage {

    /**
     * Request for bootstrap.
     */
    public static final int BOOTSTRAP_REQUEST = 10;

    /**
     * Reply to bootstrap.
     */
    public static final int BOOTSTRAP_RESPONSE = 11;

    /**
     * Bootstrap complete.
     */
    public static final int BOOTSTRAP_COMPLETE = 12;

    /**
     * Bootstrap could not be completed for some reason.
     */
    public static final int BOOTSTRAP_INCOMPLETE = 13;

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * the name of the cache.
     */
    private final String cacheName;
    /**
     * the asynchronous replication period for this message
     * -1 results in synchronous replication.
     */
    private final long asyncTime;

    /**
     * @param event        (PUT,REMOVE,REMOVE_ALL)
     * @param key          the serializable key of the cache element
     * @param element      The element itself. In case of a put.
     * @param cacheNameVal the name of the cache
     * @see #JGroupEventMessage(int, Serializable, Element, String, long)
     */
    public JGroupEventMessage(final int event,
                              final Serializable key,
                              final Element element,
                              final String cacheNameVal) {
        super(event, key, element);
        this.cacheName = cacheNameVal;
        this.asyncTime = -1;
    }


    /**
     * An event message for the JGroupsCacheReplicator. We keep as transient the
     * origin cache and we serialize the cacheName. That way the JgroupManager
     * will know from which cache the message came from
     *
     * @param event        (PUT,REMOVE,REMOVE_ALL)
     * @param key          the serializable key of the cache element
     * @param element      The element itself. In case of a put.
     * @param cacheNameVal the name of the cache
     * @param asyncTimeVal the asynchronous replication period for this message,
     *                     -1 results in synchronous replication
     */
    public JGroupEventMessage(final int event,
                              final Serializable key,
                              final Element element,
                              final String cacheNameVal,
                              final long asyncTimeVal) {
        super(event, key, element);
        this.cacheName = cacheNameVal;
        this.asyncTime = asyncTimeVal;
    }

    /**
     * @return If asynchronous
     */
    public boolean isAsync() {
        return this.asyncTime >= 0;
    }

    /**
     * @return The asynchronous replication delay,
     * if less than 0 no synchronous replication is used.
     */
    public long getAsyncTime() {
        return this.asyncTime;
    }

    /**
     * Returns the cache name.
     *
     * @return the cache name
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "JGroupEventMessage [event=" + getEventName(this.getEvent())
                + ", cacheName=" + this.cacheName
                + ", serializableKey=" + this.getSerializableKey()
                + ", element=" + this.getElement() + "]";
    }

    /**
     * Convert a numeric event ID to a String name.
     *
     * @param event a numeric event ID
     * @return String name
     */
    public static String getEventName(final int event) {
        final String eventName;
        switch (event) {
            case LegacyEventMessage.PUT:
                eventName = "PUT";
                break;
            case LegacyEventMessage.REMOVE:
                eventName = "REMOVE";
                break;
            case LegacyEventMessage.REMOVE_ALL:
                eventName = "REMOVE_ALL";
                break;
            case JGroupEventMessage.BOOTSTRAP_REQUEST:
                eventName = "BOOTSTRAP_REQUEST";
                break;
            case JGroupEventMessage.BOOTSTRAP_RESPONSE:
                eventName = "BOOTSTRAP_RESPONSE";
                break;
            case JGroupEventMessage.BOOTSTRAP_COMPLETE:
                eventName = "BOOTSTRAP_COMPLETE";
                break;
            case JGroupEventMessage.BOOTSTRAP_INCOMPLETE:
                eventName = "BOOTSTRAP_INCOMPLETE";
                break;
            default:
                eventName = Integer.toString(event);
                break;
        }
        return eventName;
    }
}
