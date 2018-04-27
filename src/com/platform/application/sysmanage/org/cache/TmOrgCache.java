package com.platform.application.sysmanage.org.cache;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.ICacheProxy;
import com.platform.application.common.cache.annotation.CacheComponent;
import com.platform.application.sysmanage.org.OrgDto;
import com.platform.application.sysmanage.org.OrgService;

/**
 * 机构缓存类.
 */
@CacheComponent
public class TmOrgCache implements ICacheProxy<OrgDto> {
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TmOrgCache.class);
	/**
	 * 机构缓存前缀名称.
	 */
	public static final String ORG_CACHE_NAME_PRE = "orginfo_";

	/**
	 * 机构管理服务类.
	 */
	@Autowired
	private OrgService orgService;

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME,
	key = "'" + ORG_CACHE_NAME_PRE + "'" + "+#key",
	unless = "#result == null ")
	@Transactional(readOnly = true)
	public OrgDto getCacheValue(final String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("开始获取机构信息[" + key + "]");
		}
		OrgDto dto = orgService.findById(key);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("获取机构成功[" + dto + "]");
		}
		return dto;
	}

	@Override
	public Map<String, OrgDto> getCacheAllValue() {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@CacheEvict(value = PROFILE_CACHE_NAME,
	key = "'" + ORG_CACHE_NAME_PRE + "'" + "+#key")
	public void evictCacheValue(final String key) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("清除机构信息[" + key + "]");
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
