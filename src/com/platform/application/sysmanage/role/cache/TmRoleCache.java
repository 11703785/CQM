package com.platform.application.sysmanage.role.cache;

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
import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.role.TmRoleService;

/**
 * 平台角色缓存类.
 */
@CacheComponent
public class TmRoleCache implements ICacheProxy<TmRoleDto> {
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TmRoleCache.class);
	/**
	 * 角色缓存前缀名称.
	 */
	public static final String ROLE_CACHE_NAME_PRE = "roleinfo_";
	/**
	 * 平台角色服务类.
	 */
	@Autowired
	private TmRoleService roleService;

	@Override
	public TmRoleDto getCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME,
	key = "'" + ROLE_CACHE_NAME_PRE + "'",
	unless = "#result == null ")
	@Transactional(readOnly = true)
	public Map<String, TmRoleDto> getCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("读取所有角色字典");
		}
		try {
			List<TmRoleDto> dics = roleService.findRolesByDto(new TmRoleDto());
			Map<String, TmRoleDto> rightMap = new TreeMap<String, TmRoleDto>();
			for (TmRoleDto dto : dics) {
				rightMap.put(dto.getRoleCode(), dto);
			}
			return rightMap;
		} catch (RuntimeException e) {
			LOGGER.error("读取所有角色字典失败！");
		}
		return new HashMap<String, TmRoleDto>();
	}

	@Override
	public void evictCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@CacheEvict(value = PROFILE_CACHE_NAME,
	key = "'" + ROLE_CACHE_NAME_PRE + "'")
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
