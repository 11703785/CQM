package com.platform.application.sysmanage.area.cache;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.ICacheProxy;
import com.platform.application.common.cache.annotation.CacheComponent;
import com.platform.application.sysmanage.area.TdAreaDto;
import com.platform.application.sysmanage.area.service.TdAreaService;

/**
 * 辖区缓存类.
 */
@CacheComponent
public class TdAreaCache implements ICacheProxy<TdAreaDto> {

	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TdAreaCache.class);

	/**
	 * 辖区缓存前缀名称.
	 */
	public static final String AREA_CACHE_NAME_PRE = "areainfo_";

	/**
	 * 辖区服务类.
	 */
	@Autowired
	private TdAreaService areaService;

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME, key = "'" + AREA_CACHE_NAME_PRE + "'" + "+#key", unless = "#result == null ")
	@Transactional(readOnly = true)
	public TdAreaDto getCacheValue(final String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("开始获取辖区信息[" + key + "]");
		}
		TdAreaDto dto = areaService.findById(key);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("获取辖区成功[" + dto + "]");
		}
		return dto;
	}

	@Override
	public Map<String, TdAreaDto> getCacheAllValue() {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@CacheEvict(value = PROFILE_CACHE_NAME, key = "'" + AREA_CACHE_NAME_PRE + "'" + "+#key")
	public void evictCacheValue(final String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("清除辖区信息[" + key + "]");
		}
	}

	@Override
	public void evictCacheAllValue() {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	public boolean isLoadedAll() {
		return false;
	}

}
