package com.platform.application.sysmanage.user.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.common.dto.ResultResponse;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.service.TmUserService;
import com.platform.application.utils.MediaTypeUtils;
import com.platform.framework.core.action.BaseAction;
/**
 * 平台用户管理控制类.
 *
 * @author wyl19
 * @see UserEntity
 */
@RestController
@RequestMapping("/user")
public class TmUserController extends BaseAction {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmUserController.class);
	/**
	 * 用户信息服务类.
	 */
	@Autowired
	private TmUserService tmUserService;
	/**
	 * 获取主界面.
	 *
	 * @return 主界面
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView getUserHome() {
		ModelAndView mav = new ModelAndView("sysmanage/user/show");
		//		Department department = (Department)departmentService.load(person.getDepartment().getDeptId());
		//		mav.addObject("department", department);
		return mav;
	}
	/**
	 * 展示新增界面.
	 *
	 * @return 新增界面
	 */
	@RequestMapping(value = "/showadd", method = RequestMethod.GET)
	public ModelAndView getUserAdd() {
		final ModelAndView mv = new ModelAndView("sysmanage/user/add");

		return mv;
	}

	/**
	 * 新增用户
	 * @param userDto 用户交互对象
	 * @param result BindingResult
	 * @param session HttpSession
	 * @return ResultResponse<UserDto>
	 */
	@RequestMapping(method = RequestMethod.POST, produces = { MediaTypeUtils.UTF_8 })
	public ResultResponse<UserDto> save(@RequestBody @Valid final UserDto userDto, final BindingResult result,
			final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /user [" + userDto + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error("POST /user [" + userDto + "]:新增失败!" + result.getAllErrors());
			return new ResultResponse<UserDto>(false, "新增失败，校验不通过！", result.getAllErrors());
		}
		try {
			final UserDto dto = tmUserService.persist(userDto.convertEntity());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /user 新增成功[" + dto + "]");
			}
			return new ResultResponse<UserDto>(true, dto);
		} catch (final RuntimeException re) {
			LOGGER.error("POST /user [" + userDto + "]:新增失败!", re);
			return new ResultResponse<UserDto>(false, "新增失败！");
		}
	}

	/**
	 * 分页查询列表信息.
	 *
	 * @param dto
	 *            查询条件
	 * @param session
	 *            HttpSession
	 * @return PageResponse<TmUserDto>
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST, produces = { MediaTypeUtils.UTF_8 })
	public PageResponse<UserDto> findByDto(@RequestBody final UserDto dto, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /user/find [" + dto + "]");
		}
		try {
			final LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			final PageResponse<UserDto> results = tmUserService.findByDto(dto, loginInfo);
			if (LOGGER.isDebugEnabled()) {
				if (results.isStatus()) {
					LOGGER.debug("POST /user/find 查询成功[" + dto + "]");
				} else {
					LOGGER.debug("POST /user/find 查询失败[" + results.getError() + "]");
				}
			}
			return results;
		} catch (final Exception re) {
			LOGGER.error("POST /user/find [" + dto + "]:查询失败!", re);
			return new PageResponse<UserDto>(false, re.getMessage());
		}
	}


}
