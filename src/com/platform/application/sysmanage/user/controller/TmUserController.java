package com.platform.application.sysmanage.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("/user")
public class TmUserController {

	/**
	 * 获取主界面.
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView show(){
		return new ModelAndView("");
	}

	/**
	 * 展示新增界面.
	 *
	 * @return 新增界面
	 */
	@RequestMapping(value = "/showadd", method = RequestMethod.GET)
	public ModelAndView getUserAdd() {
		final ModelAndView mv = new ModelAndView("manage/user/add");

		return mv;
	}
}
