package com.platform.application.sysmanage.org.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.platform.application.sysmanage.org.OrgService;
import com.platform.application.sysmanage.org.bean.TmOrgRela;

/**
 * 机构关系缓存类.
 *
 */
@CacheComponent
public class TmOrgRelaCache implements ICacheProxy<Set<String>> {
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TmOrgRelaCache.class);
	/**
	 * 机构关系缓存前缀名称.
	 */
	public static final String ORG_RELATION_CACHE_NAME_PRE = "orgrelaton_all_";
	/**
	 * 机构关系根节点名称.
	 */
	private static final String ORG_ROOT_NODE = "ORG_ROOT_NODE";
	/**
	 * 机构管理服务类.
	 */
	@Autowired
	private OrgService orgService;

	@Override
	public Set<String> getCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@Cacheable(value = PROFILE_CACHE_NAME,
	key = "'" + ORG_RELATION_CACHE_NAME_PRE + "'",
	unless = "#result == null ")
	@Transactional(readOnly = true)
	public Map<String, Set<String>> getCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("获取机构上下级关系");
		}
		final List<TmOrgRela> upOrgs = orgService.selOrgAndUpOrg();
		final TreeMap<String, Set<String>> orgTree =
				new TreeMap<String, Set<String>>();
		orgTree.put(ORG_ROOT_NODE, new HashSet<String>(0));
		for (TmOrgRela rela : upOrgs) {
			if (!orgTree.containsKey(rela.getOrgCode())) {
				orgTree.put(rela.getOrgCode(), new HashSet<String>(0));
			}
			if (StringUtils.isNotBlank(rela.getUpOrg())) {
				Set<String> children = orgTree.get(rela.getUpOrg());
				if (children == null) {
					children = new HashSet<String>();
					children.add(rela.getOrgCode());
					orgTree.put(rela.getUpOrg(), children);
				} else {
					children.add(rela.getOrgCode());
				}
			} else {
				orgTree.get(ORG_ROOT_NODE).add(rela.getOrgCode());
			}
		}
		for (String orgCode : orgTree.get(ORG_ROOT_NODE)) {
			getAllSubOrg(orgCode, orgTree);
		}
		if (LOGGER.isTraceEnabled()) {
			for (Map.Entry<String, Set<String>> entry : orgTree.entrySet()) {
				LOGGER.trace("机构代码：[" + entry.getKey() + "] ["
						+ entry.getValue() + "]");
			}
		}
		return orgTree;
	}

	/**
	 * 获取所有下级机构.
	 *
	 * @param orgCode 机构代码
	 * @param treeMap 机构关系映射
	 */
	private void getAllSubOrg(final String orgCode,
			final TreeMap<String, Set<String>> treeMap) {
		Set<String> childrenSet = new HashSet<String>(0);
		childrenSet.addAll(treeMap.get(orgCode));
		for (String childCode : childrenSet) {
			getAllSubOrg(childCode, treeMap);
			treeMap.get(orgCode).addAll(treeMap.get(childCode));
		}
	}

	@Override
	public void evictCacheValue(final String key) {
		throw new RuntimeException("方法调用错误");
	}

	@Override
	@CacheEvict(value = PROFILE_CACHE_NAME,
	key = "'" + ORG_RELATION_CACHE_NAME_PRE + "'")
	public void evictCacheAllValue() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("清除机构关系");
		}
	}

	@Override
	public boolean isLoadedAll() {
		return true;
	}
}
