package com.platform.application.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.sysmanage.org.cache.TmOrgRelaCache;

/**
 * 缓存工具类.
 */
public final class CacheConverterUtils {
	/**
	 * 默认构造函数.
	 */
	private CacheConverterUtils() {

	}

	/**
	 * 从缓存中获取配置参数值.
	 *
	 * @param cacheProxyFactory 平台缓存代理工厂
	 * @param code              配置参数键
	 * @return 配置参数值
	 */
	/*public static String getProfileValue(
			final CacheProxyFactory cacheProxyFactory,
			final String code) {
		return cacheProxyFactory.getCacheValue(ProfileCache.class, code);
	}*/

	/**
	 * 从缓存中获取数据字典值.
	 *
	 * @param cacheProxyFactory 平台缓存代理工厂
	 * @param type              数据字典类型
	 * @param key               数据字典键
	 * @return 数据字典值
	 */
	/*public static String getDataDicValue(
			final CacheProxyFactory cacheProxyFactory,
			final String type,
			final String key) {
		Map<String, String> dics = getDataDic(cacheProxyFactory, type);
		return dics.get(key);
	}*/

	/**
	 * 从缓存中获取数据字典集合.
	 *
	 * @param cacheProxyFactory 平台缓存代理工厂
	 * @param type              数据字典类型
	 * @return 数据字典集合
	 */
	/*public static Map<String, String> getDataDic(
			final CacheProxyFactory cacheProxyFactory,
			final String type) {
		Set<DataDicDto> dtos = cacheProxyFactory
				.getCacheValue(DataDicCache.class, type);
		if (dtos == null) {
			return null;
		} else {
			Map<String, String> dic = new HashMap<String, String>();
			for (DataDicDto dto : dtos) {
				dic.put(dto.getCode(), dto.getName());
			}
			return dic;
		}
	}*/

	/**
	 * 从缓存中获取机构及其所辖机构列表.
	 *
	 * @param cacheProxyFactory 平台缓存代理工厂
	 * @param orgCode           机构代码
	 * @return 机构及其所辖机构列表
	 */
	public static List<String> getBranchAndSelf(
			final CacheProxyFactory cacheProxyFactory,
			final String orgCode) {
		// 得到当前登陆机构以及下属机构信息
		Set<String> orgSet = cacheProxyFactory.getCacheValue(TmOrgRelaCache.class, orgCode);
		List<String> orgList = new ArrayList<String>(orgSet.size() + 1);
		orgList.add(orgCode);
		orgList.addAll(orgSet);
		return orgList;
	}
}
