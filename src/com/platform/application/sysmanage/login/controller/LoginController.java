package com.platform.application.sysmanage.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.platform.application.common.dto.ResultResponse;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.login.LoginService;
import com.platform.application.utils.MediaTypeUtils;

@RestController
public class LoginController {

	private static final Logger LOGGER = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	/**
	 * 获取登录页面.
	 *
	 * @return ModelAndView 登录页面
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getLogin() {
		final ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	/**
	 * 用户登陆.
	 *
	 * @param userId
	 *            用户名
	 * @param userPwd
	 *            密码
	 * @param session
	 *            用户登录会话
	 * @return ResultResponse<LoginInfo>
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = {
			MediaTypeUtils.UTF_8 })
	public ResultResponse<LoginInfo> login(
			@RequestParam("userId") final String userId,
			@RequestParam("userPwd") final String userPwd,
			final HttpServletRequest request, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /login 用户[" + userId + "]登录系统");
		}
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(userPwd)) {
			LOGGER.error("POST /login 用户[" + userId + "]登录系统失败:登录标识或密码不能为空");
			return new ResultResponse<LoginInfo>(false, "登录标识或密码不能为空！");
		}
		try {
			final ResultResponse<LoginInfo> response = loginService.login(userId, userPwd);
			if (!response.isStatus()) {
				return response;
			}
			session.setAttribute(LoginInfo.HTTP_SESSION_LOGININFO,
					response.getResult());
			if (LOGGER.isDebugEnabled()) {
				if (response.isStatus()) {
					LOGGER.debug("POST /login 用户[" + userId + "]登录系统成功["
							+ response.getResult() + "]");
				} else {
					LOGGER.debug("POST /login 用户[" + userId + "]登录系统失败["
							+ response.getError() + "]");
				}
			}
			return response;
		} catch (final RuntimeException re) {
			LOGGER.error("POST /login 用户[" + userId + "]登录系统失败!", re);
			return new ResultResponse<LoginInfo>(false, "登录失败！");
		}
	}
}
