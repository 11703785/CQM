package com.platform.application.sysmanage.right.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.ICacheProxy;
import com.platform.application.common.cache.annotation.CacheComponent;
import com.platform.application.sysmanage.right.TmRightDto;
import com.platform.application.sysmanage.right.TmRightService;

/**
 * 权限数据字典缓存类.
 */
@CacheComponent
public class TmRightCache implements ICacheProxy<TmRightDto> {
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TmRightCache.class);
	/**
	 * 权限缓存前缀名称.
	 */
	public static final String RIGHT_DIC_CACHE_NAME_PRE = "rightdic_";

	/**
	 * 平台权限服务类.
	 */
	@Autowired
	private TmRightService rightService;

	@Override
	public TmRightDto getCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME,
	key = "'" + RIGHT_DIC_CACHE_NAME_PRE + "'",
	unless = "#result == null ")
	@Transactional(readOnly = true)
	public Map<String, TmRightDto> getCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("读取权限字典");
		}
		try {
			List<TmRightDto> dics = rightService.findByDto(new TmRightDto());
			Map<String, TmRightDto> rightMap = new TreeMap<String, TmRightDto>();
			for (TmRightDto dto : dics) {
				rightMap.put(dto.getRightCode(), dto);
			}
			return rightMap;
		} catch (Exception e) {
			LOGGER.error("读取权限字典失败！");
		}
		return new HashMap<String, TmRightDto>();
	}

	@Override
	public void evictCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@CacheEvict(value = PROFILE_CACHE_NAME,
	key = "'" + RIGHT_DIC_CACHE_NAME_PRE + "'")
	public void evictCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("清除所有角色字典");
		}
	}

	@Override
	public boolean isLoadedAll() {
		return true;
	}
}
