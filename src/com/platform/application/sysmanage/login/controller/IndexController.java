package com.platform.application.sysmanage.login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.login.MenuTreeDto;
import com.platform.application.sysmanage.right.TmRightDto;
import com.platform.application.sysmanage.right.cache.TmRightCache;
import com.platform.application.utils.MediaTypeUtils;
import com.platform.framework.common.util.DateUtils;

@RestController
@RequestMapping("/index")
public class IndexController {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(IndexController.class);

	/**
	 * 系统缓存代理类.
	 */
	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 获取登录页面.
	 *
	 * @return 登录页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getLogin() {
		return new ModelAndView("index");
	}

	/**
	 * 获取登录页面.
	 *
	 * @return 登录页面
	 */
	@RequestMapping(value = "/head", method = RequestMethod.GET)
	public ModelAndView getHead(final HttpSession session) {
		ModelAndView mav = new ModelAndView("header");
		String curdate=DateUtils.getCurrDateStr("yyyy年MM月dd日");
		String curweek=DateUtils.getWeek();
		LoginInfo loginInfo = (LoginInfo)session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		mav.addObject("loginInfo", loginInfo);
		mav.addObject("curdate", curdate);
		mav.addObject("curweek", curweek);
		return mav;
	}
	/**
	 * 获取导航菜单数据.
	 *
	 * @param session 用户登录会话
	 * @return 导航菜单数据
	 */
	@RequestMapping(value = "/navigation", produces = {MediaTypeUtils.UTF_8})
	public String getNavigation(final HttpSession session) {
		Map<String, TmRightDto> rightDic = cacheProxyFactory.getCacheAllValue(TmRightCache.class);
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		StringBuilder buf = new StringBuilder();
		TreeSet<String> rightSet = new TreeSet<String>(loginInfo.getRights());
		Map<String, MenuTreeDto> nodes = new HashMap<String, MenuTreeDto>(0);
		for (String right : rightSet) {
			getNode(right, rightDic, nodes);
		}
		Set<MenuTreeDto> menuTree = new TreeSet<MenuTreeDto>();
		for (Map.Entry<String, MenuTreeDto> entry : nodes.entrySet()) {
			if (StringUtils.length(entry.getKey()) == 2/*ManageModelConstants.MENU_FIRST_RIGHT_LENGTH*/) {
				menuTree.add(entry.getValue());
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			buf.append(objectMapper.writeValueAsString(menuTree));
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
			buf.append("[]");
		}
		return buf.toString();
	}

	/**
	 * 获取导航菜单树模型.
	 *
	 * @param rightCode 用户拥有的权限代码
	 * @param rightDic  权限数据字典
	 * @param nodes     导航菜单节点Map
	 * @return 导航菜单节点
	 */
	private MenuTreeDto getNode(final String rightCode,
			final Map<String, TmRightDto> rightDic,
			final Map<String, MenuTreeDto> nodes) {
		MenuTreeDto node = nodes.get(rightCode);
		if (node != null) {
			return node;
		}
		TmRightDto dic = rightDic.get(rightCode);
		if (rightDic.get(rightCode) == null) {
			return null;
		}
		int length = StringUtils.length(rightCode);
		if (length == 6/*ManageModelConstants.MENU_THIRD_RIGHT_LENGTH*/) {
			getNode(StringUtils.substring(rightCode, 0, 4
					/*ManageModelConstants.MENU_SECOND_RIGHT_LENGTH*/),
					rightDic, nodes);
			return null;
		}
		node = new MenuTreeDto();
		node.setId(rightCode);
		node.setText(dic.getRightName());
		switch (length) {
		case 2 /*ManageModelConstants.MENU_FIRST_RIGHT_LENGTH*/:
			node.setIconCls("icon-folder");
			nodes.put(rightCode, node);
			break;
		case 4/*ManageModelConstants.MENU_SECOND_RIGHT_LENGTH*/:
			String upRight = StringUtils.substring(rightCode, 0, 2
					/*ManageModelConstants.MENU_FIRST_RIGHT_LENGTH*/);
			MenuTreeDto parent = getNode(upRight, rightDic, nodes);
			if (parent == null) {
				return null;
			}
			if (parent.getChildren() == null) {
				parent.setChildren(new TreeSet<MenuTreeDto>());
			}
			node.setIconCls("icon-16-" + rightCode);
			String path = dic.getRightPath();
			if (StringUtils.isNotBlank(path)) {
				node.setHref("." + StringUtils.split(path, ";")[0]);
			}
			if ("0".equals(dic.getIsFrame())) {
				node.setIframe(false);
			} else {
				node.setIframe(true);
			}
			parent.getChildren().add(node);
			nodes.put(rightCode, node);
			break;
		default:
			break;
		}
		return node;
	}
}
