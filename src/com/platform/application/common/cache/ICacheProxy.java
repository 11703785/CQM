package com.platform.application.common.cache;

import java.util.Map;

/**
 * 系统缓存代理接口.
 *
 * @param <T> 缓存实现类
 */
public interface ICacheProxy<T> {

	/**
	 * 平台基础缓存名称.
	 */
	String PROFILE_CACHE_NAME = "profile";

	/**
	 * 根据键获取缓存值.
	 *
	 * @param key 缓存键
	 * @return 缓存值
	 */
	T getCacheValue(String key);

	/**
	 * 获取所有缓存项.
	 *
	 * @return 所有缓存项
	 */
	Map<String, T> getCacheAllValue();

	/**
	 * 根据键清除缓存值.
	 *
	 * @param key 缓存键
	 */
	void evictCacheValue(String key);

	/**
	 * 清除所有缓存值.
	 */
	void evictCacheAllValue();

	/**
	 * 获取是否提供所有缓存获取方式.
	 *
	 * @return 是否提供所有缓存获取方式
	 */
	boolean isLoadedAll();
}
