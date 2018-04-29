package com.platform.application.sysmanage.datadic.cache;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.ICacheProxy;
import com.platform.application.common.cache.annotation.CacheComponent;
import com.platform.application.sysmanage.datadic.TdDataDicDto;
import com.platform.application.sysmanage.datadic.TdDataDicService;

/**
 * 系统数据字典缓存实现
 */
@CacheComponent
public class TdDataDicCache implements ICacheProxy<Set<TdDataDicDto>> {
	/**
	 * 数据字典缓存前缀名称.
	 */
	public static final String DATADIC_CACHE_NAME_PRE = "datadic_";
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TdDataDicCache.class);
	/**
	 * 系统数据字典服务类.
	 */
	@Autowired
	private TdDataDicService dataDicService;

	@Override
	public Set<TdDataDicDto> getCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME,
	key = "'" + DATADIC_CACHE_NAME_PRE + "'",
	unless = "#result == null ")
	@Transactional(readOnly = true)
	public Map<String, Set<TdDataDicDto>> getCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("读取所有数据字典");
		}
		Map<String, Set<TdDataDicDto>> dics = dataDicService.findAllDic();
		if (dics.size() < 1) {
			return null;
		}
		return dics;
	}

	@Override
	public void evictCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	/**
	 * 清除数据字典缓存.
	 */
	@CacheEvict(value = PROFILE_CACHE_NAME,
			key = "'" + DATADIC_CACHE_NAME_PRE + "'")
	@Override
	public void evictCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("清除数据字典");
		}
	}

	@Override
	public boolean isLoadedAll() {
		return true;
	}
}
