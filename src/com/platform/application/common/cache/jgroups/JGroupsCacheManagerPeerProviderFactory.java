package com.platform.application.common.cache.jgroups;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CacheManagerPeerProviderFactory;
import net.sf.ehcache.util.ClassLoaderUtil;
import net.sf.ehcache.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;

/**
 * @author Pierre Monestie (pmonestie__REMOVE__THIS__@gmail.com)
 * @author <a href="mailto:gluck@gregluck.com">Greg Luck</a>
 * @version $Id$
 */

public class JGroupsCacheManagerPeerProviderFactory
        extends CacheManagerPeerProviderFactory {
    /**
     * The org.slf4j.Logger interface.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(
                    JGroupsCacheManagerPeerProviderFactory.class.getName());
    /**
     * The name of channel.
     */
    private static final String CHANNEL_NAME = "channelName";
    /**
     * The name of connect.
     */
    private static final String CONNECT = "connect";
    /**
     * The name of file.
     */
    private static final String FILE = "file";

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheManagerPeerProvider createCachePeerProvider(
            final CacheManager cacheManager, final Properties properties) {
        LOG.trace("Creating JGroups CacheManagerPeerProvider for {} "
                + "with properties:\n{}", cacheManager.getName(), properties);

        final String connect = this.getProperty(CONNECT, properties);
        final String file = this.getProperty(FILE, properties);
        final String channelName = this.getProperty(CHANNEL_NAME, properties);

        final JGroupsCacheManagerPeerProvider peerProvider;
        if (file != null) {
            if (connect != null) {
                LOG.warn("Both '" + CONNECT + "' and '" + FILE
                        + "' properties set. '" + CONNECT
                        + "' will be ignored");
            }

            final ClassLoader contextClassLoader =
                    ClassLoaderUtil.class.getClassLoader();
            final URL configUrl = contextClassLoader.getResource(file);

            LOG.debug("Creating JGroups CacheManagerPeerProvider for {}"
                            + " with configuration file: {}",
                    cacheManager.getName(), configUrl);
            peerProvider = new JGroupsCacheManagerPeerProvider(
                    cacheManager, configUrl);
        } else {
            LOG.debug("Creating JGroups CacheManagerPeerProvider for {} "
                            + "with configuration:\n{}",
                    cacheManager.getName(), connect);
            peerProvider = new JGroupsCacheManagerPeerProvider(
                    cacheManager, connect);
        }

        peerProvider.setChannelName(channelName);

        return peerProvider;
    }

    /**
     * @param name       the name of property
     * @param properties properties
     * @return the value of property
     */
    private String getProperty(final String name, final Properties properties) {
        String property = PropertyUtil.extractAndLogProperty(name, properties);
        if (property != null) {
            property = property.trim();
            property = property.replaceAll(" ", "");
            if (property.equals("")) {
                property = null;
            }
        }
        return property;
    }

}
