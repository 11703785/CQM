package com.platform.application.sysmanage.profile.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.sysmanage.datadic.TdDataDicDto;
import com.platform.application.sysmanage.datadic.TdDictionaryDto;
import com.platform.application.sysmanage.datadic.cache.TdDataDicCache;
import com.platform.application.utils.MediaTypeUtils;

/**
 * 平台配置管理控制类.
 *
 */
@RestController
@RequestMapping("/profile")
public class TmProfileController {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmProfileController.class);

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 获取数据字典.
	 *
	 * @return 数据字典
	 */
	@RequestMapping(value = "/datadic", method = RequestMethod.GET, produces = {MediaTypeUtils.UTF_8})
	public Map<String, Set<TdDictionaryDto>> getDataDic() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("GET /profile/datadic 开始获取数据字典");
		}
		Map<String, Set<TdDataDicDto>> dics =
				cacheProxyFactory.getCacheAllValue(TdDataDicCache.class);
		Map<String, Set<TdDictionaryDto>> ms =
				new HashMap<String, Set<TdDictionaryDto>>();
		for (Map.Entry<String, Set<TdDataDicDto>> entry : dics.entrySet()) {
			Set<TdDictionaryDto> dic = ms.get(entry.getKey());
			if (dic == null) {
				dic = new TreeSet<TdDictionaryDto>();
				ms.put(entry.getKey(), dic);
			}
			for (TdDataDicDto dto : entry.getValue()) {
				dic.add(new TdDictionaryDto(dto.getCode(), dto.getName()));
			}
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("GET /profile/datadic 获取数据字典成功[" + ms + "]");
		}
		return ms;
	}
}
