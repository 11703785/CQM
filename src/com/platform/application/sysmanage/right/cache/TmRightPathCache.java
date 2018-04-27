package com.platform.application.sysmanage.right.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
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
 * 平台访问路径权限数据字典缓存类
 */
@CacheComponent
public class TmRightPathCache implements ICacheProxy<String[]> {
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TmRightPathCache.class);
	/**
	 * 访问路径权限缓存前缀名称.
	 */
	public static final String RIGHT_PATH_CACHE_NAME_PRE = "rightpath_";
	/**
	 * 平台权限服务类.
	 */
	@Autowired
	private TmRightService rightService;

	@Override
	public String[] getCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME,
	key = "'" + RIGHT_PATH_CACHE_NAME_PRE + "'",
	unless = "#result == null ")
	@Transactional(readOnly = true)
	public Map<String, String[]> getCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("读取权限路径字典");
		}
		try {
			List<TmRightDto> dics = rightService.findByDto(new TmRightDto());
			Map<String, String[]> rightMap = new TreeMap<String, String[]>();
			for (TmRightDto dto : dics) {
				String path = dto.getRightPath();
				if (StringUtils.isNotBlank(path)) {
					if (path.contains(";")) {
						String[] paths = path.split(";");
						String[] methods = dto.getRightMethod().split(";");
						for (int i = 0; i < paths.length; i++) {
							String[] putValue = getPutValue(
									rightMap, methods[i], paths[i], dto);
							rightMap.put(methods[i] + ":" + paths[i], putValue);
						}
					} else {
						String[] putValue = getPutValue(rightMap,
								dto.getRightMethod(), dto.getRightPath(), dto);
						rightMap.put(
								dto.getRightMethod() + ":" + dto.getRightPath(),
								putValue);
					}
				}
			}
			if (LOGGER.isTraceEnabled()) {
				for (Map.Entry<String, String[]> entry : rightMap.entrySet()) {
					StringBuilder buf = new StringBuilder();
					buf.append(entry.getKey()).append("[");
					int count = 0;
					for (String rightCode : entry.getValue()) {
						if (count > 0) {
							buf.append(",");
						}
						buf.append(rightCode);
						count++;
					}
					buf.append("]");
					LOGGER.trace(buf.toString());
				}
			}
			return rightMap;
		} catch (Exception e) {
			LOGGER.error("读取权限路径字典失败！");
		}
		return new HashMap<String, String[]>();
	}

	@Override
	public void evictCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@CacheEvict(value = PROFILE_CACHE_NAME,
	key = "'" + RIGHT_PATH_CACHE_NAME_PRE + "'")
	public void evictCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("清除权限路径字典");
		}
	}

	@Override
	public boolean isLoadedAll() {
		return true;
	}

	/**
	 * 生产访问路径权限关系Map.
	 *
	 * @param rightMap 访问路径权限关系Map
	 * @param method   访问方法
	 * @param path     访问路径
	 * @param dto      权限传输对象
	 * @return 权限列表
	 */
	private String[] getPutValue(
			final Map<String, String[]> rightMap,
			final String method,
			final String path,
			final TmRightDto dto) {
		String[] value = rightMap.get(method + ":" + path);
		if (value != null) {
			String[] codes = new String[value.length + 2];
			for (int j = 0; j < value.length; j++) {
				codes[j] = value[j];
			}
			codes[value.length] = dto.getRightCode();
			codes[value.length + 1] = dto.getTopAdmin();
			return codes;
		} else {
			return new String[]{dto.getRightCode(), dto.getTopAdmin()};
		}
	}
}
