package com.platform.application.common.cache.jgroups;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;
import net.sf.ehcache.util.PropertyUtil;


/**
 * A factory to create a configured JGroupsBootstrapCacheLoader.
 *
 * @author Greg Luck
 * @version $Id$
 */
@SuppressWarnings("rawtypes")
public class JGroupsBootstrapCacheLoaderFactory
extends BootstrapCacheLoaderFactory {
	/**
	 * The org.slf4j.Logger interface.
	 */
	private static final Logger LOG =
			LoggerFactory.getLogger(
					JGroupsBootstrapCacheLoaderFactory.class.getName());

	/**
	 * The property name expected in ehcache.xml
	 * for the bootstrap asynchronously switch.
	 */
	public static final String BOOTSTRAP_ASYNCHRONOUSLY =
			"bootstrapAsynchronously";

	/**
	 * The property name expected in ehcache.xml
	 * for the maximum chunk size in bytes.
	 */
	public static final String MAXIMUM_CHUNK_SIZE_BYTES =
			"maximumChunkSizeBytes";

	/**
	 * The default maximum serialized size of the elements to request
	 * from a remote cache peer during bootstrap.
	 */
	protected static final int DEFAULT_MAXIMUM_CHUNK_SIZE_BYTES = 5000000;

	/**
	 * The highest reasonable chunk size in bytes.
	 */
	protected static final int ONE_HUNDRED_MB = 100000000;

	/**
	 * The lowest reasonable chunk size in bytes.
	 */
	protected static final int FIVE_KB = 5000;


	/**
	 * Create a <code>BootstrapCacheLoader</code>.
	 *
	 * @param properties implementation specific properties.
	 *                   These are configured as comma
	 *                   separated name value pairs in ehcache.xml
	 * @return a constructed BootstrapCacheLoader
	 */
	@Override
	public BootstrapCacheLoader createBootstrapCacheLoader(
			final Properties properties) {
		if (properties == null) {
			LOG.debug("Creating JGroups BootstrapCacheLoader"
					+ " with default configuration.");
		} else {
			LOG.debug("Creating JGroups BootstrapCacheLoader"
					+ " with configuration:\n{}", properties);
		}

		boolean bootstrapAsynchronously =
				extractAndValidateBootstrapAsynchronously(properties);
		int maximumChunkSizeBytes = extractMaximumChunkSizeBytes(properties);
		return new JGroupsBootstrapCacheLoader(
				bootstrapAsynchronously, maximumChunkSizeBytes);
	}

	/**
	 * Extracts the value of maximumChunkSizeBytes from the properties.
	 *
	 * @param properties properties
	 * @return maximumChunkSizeBytes
	 */
	protected int extractMaximumChunkSizeBytes(final Properties properties) {
		int maximumChunkSizeBytes = 0;
		String maximumChunkSizeBytesString =
				PropertyUtil.extractAndLogProperty(
						MAXIMUM_CHUNK_SIZE_BYTES, properties);
		if (maximumChunkSizeBytesString == null) {
			return DEFAULT_MAXIMUM_CHUNK_SIZE_BYTES;
		}

		int maximumChunkSizeBytesCandidate;
		try {
			maximumChunkSizeBytesCandidate =
					Integer.parseInt(maximumChunkSizeBytesString);
		} catch (NumberFormatException e) {
			LOG.warn("Number format exception trying to set chunk size to '{}'."
					+ " Using the default instead.",
					maximumChunkSizeBytesString);
			return DEFAULT_MAXIMUM_CHUNK_SIZE_BYTES;
		}

		if ((maximumChunkSizeBytesCandidate < FIVE_KB)
				|| (maximumChunkSizeBytesCandidate > ONE_HUNDRED_MB)) {
			LOG.warn("Trying to set the chunk size to an unreasonable "
					+ "number: {}. Using the default instead.",
					maximumChunkSizeBytesCandidate);
			return DEFAULT_MAXIMUM_CHUNK_SIZE_BYTES;
		}

		return maximumChunkSizeBytes;
	}


	/**
	 * Extracts the value of bootstrapAsynchronously from the properties.
	 *
	 * @param properties properties
	 * @return is asynchronously
	 */
	protected boolean extractAndValidateBootstrapAsynchronously(
			final Properties properties) {
		String bootstrapAsynchronouslyString =
				PropertyUtil.extractAndLogProperty(
						BOOTSTRAP_ASYNCHRONOUSLY, properties);
		if (bootstrapAsynchronouslyString != null) {
			return Boolean.parseBoolean(bootstrapAsynchronouslyString);
		}
		return true;
	}
}
