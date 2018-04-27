package com.platform.application.common.cache;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 缓存代理工厂类.
 */
public class CacheProxyFactory implements ApplicationContextAware {
	/**
	 * Spring 应用上下文环境.
	 */
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(final ApplicationContext appContext)
			throws BeansException {
		this.applicationContext = appContext;
	}

	/**
	 * 根据缓存类型返回缓存代理实现.
	 *
	 * @param requiredType 缓存类型
	 * @param <T>          缓存代理类型
	 * @return 缓存代理实现
	 */
	private <T> ICacheProxy<T> getCacheProxy(
			final Class<? extends ICacheProxy<T>> requiredType) {
		return this.applicationContext.getBean(requiredType);
	}

	/**
	 * 获取缓存值.
	 *
	 * @param requiredType 缓存类型
	 * @param key          缓存键
	 * @param <T>          缓存代理类型
	 * @return 缓存键对应值
	 */
	public <T> T getCacheValue(
			final Class<? extends ICacheProxy<T>> requiredType,
					final String key) {
		ICacheProxy<T> proxy = this.getCacheProxy(requiredType);
		if (proxy.isLoadedAll()) {
			return proxy.getCacheAllValue().get(key);
		} else {
			return this.getCacheProxy(requiredType).getCacheValue(key);
		}
	}

	/**
	 * 获取此缓存类型下的所有缓存值.
	 *
	 * @param requiredType 缓存类型
	 * @param <T>          缓存代理类型
	 * @return 所有缓存值
	 */
	public <T> Map<String, T> getCacheAllValue(
			final Class<? extends ICacheProxy<T>> requiredType) {
		return this.getCacheProxy(requiredType).getCacheAllValue();
	}

	/**
	 * 清除缓存类型下键对对应缓存项.
	 *
	 * @param requiredType 缓存类型
	 * @param key          缓存键
	 * @param <T>          缓存代理类型
	 */
	public <T> void evictCacheValue(
			final Class<? extends ICacheProxy<T>> requiredType,
					final String key) {
		this.getCacheProxy(requiredType).evictCacheValue(key);
	}

	/**
	 * 清除缓存类型下所有缓存项.
	 *
	 * @param requiredType 缓存类型
	 * @param <T>          缓存代理类型
	 */
	public <T> void evictCacheAllValue(
			final Class<? extends ICacheProxy<T>> requiredType) {
		this.getCacheProxy(requiredType).evictCacheAllValue();
	}

}
