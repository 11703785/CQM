package com.platform.application.sysmanage.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.common.dto.PageResponse;
import com.platform.application.common.dto.ResultResponse;
import com.platform.application.common.spring.OperationControllerLog;
import com.platform.application.common.spring.OperationType;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.org.service.TmOrgService;
import com.platform.application.sysmanage.right.TmRightTreeDto;
import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.service.TmUserService;
import com.platform.application.utils.CacheConverterUtils;
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

	@Autowired
	private TmOrgService tmOrgService;

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 获取主界面.
	 *
	 * @return 主界面
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView getUserHome() {
		ModelAndView mav = new ModelAndView("sysmanage/user/show");
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
	 * 获取详细信息界面.
	 *
	 * @param userId
	 *            主键
	 * @return 详细信息界面
	 */
	@RequestMapping(value = "/showdetail/{userId}", method = RequestMethod.GET)
	public ModelAndView getDetailPage(@PathVariable("userId") final String userId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET /user/showdetail [" + userId + "]");
		}
		UserDto userDto = tmUserService.findById(userId);
		final ResultResponse<UserDto> response = new ResultResponse<UserDto>(true, userDto);
		ModelAndView mv;
		if (response.isStatus()) {
			mv = new ModelAndView("sysmanage/user/detail");
			mv.addObject("userdto", response.getResult());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /user/showdetail 成功 [" + userId + "]");
			}
		} else {
			mv = new ModelAndView("error");
			mv.addObject("ex", response.getError());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /user/showdetail 失败 [" + userId + "][" + response.getError() + "]");
			}
		}
		return mv;
	}

	/**
	 * 获取角色信息页面.
	 *
	 * @param userId
	 *            用户ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showrole/{userId}", method = RequestMethod.GET)
	public ModelAndView getUserRole(@PathVariable("userId") final String userId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET /user/showrole [" + userId + "]");
		}
		ModelAndView mv;
		UserDto userDto = tmUserService.findById(userId);
		final ResultResponse<UserDto> response = new ResultResponse<UserDto>(true, userDto);
		if (response.isStatus()) {
			mv = new ModelAndView("sysmanage/user/userrole");
			mv.addObject("userdto", response.getResult());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /user/showrole 成功 [" + userId + "]");
			}
		} else {
			mv = new ModelAndView("error");
			mv.addObject("ex", response.getError());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /user/showrole 失败 [" + userId + "][" + response.getError() + "]");
			}
		}
		return mv;
	}

	/**
	 * 获取用户角色信息.
	 *
	 * @param userId
	 *            用户ID
	 * @param orgCode
	 *            机构代码
	 * @return List<RightTreeDto>
	 */
	@RequestMapping(value = "/roletree/{userid}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8 })
	public List<TmRightTreeDto> getRoleTree(@PathVariable("userid") final String userId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /user/roletree [" + userId + "]");
		}
		try {
			final UserDto userDto = tmUserService.findById(userId, true);
			final List<TmRightTreeDto> tree = tmOrgService.selRoleTree(userDto.getType());
			final Set<TmRoleDto> roleSet = userDto.getTmRoles();
			final Iterator<TmRoleDto> it = roleSet.iterator();
			final List<String> roles = new ArrayList<String>();
			while (it.hasNext()) {
				roles.add(it.next().getRoleCode());
			}
			for (final TmRightTreeDto roletree : tree) {
				if (roles.contains(roletree.getId())) {
					roletree.setChecked(true);
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /user/roletree 成功 [" + userId + "]");
			}
			return tree;
		} catch (final RuntimeException re) {
			LOGGER.debug("POST /user/roletree 失败 [" + userId + "]");
			return null;
		}
	}

	/**
	 * 新增用户
	 * @param userDto 用户交互对象
	 * @param result BindingResult
	 * @param session HttpSession
	 * @return ResultResponse<UserDto>
	 */
	@RequestMapping(method = RequestMethod.POST, produces = { MediaTypeUtils.UTF_8 })
	@OperationControllerLog(description = "新增用户", type = OperationType.ADD)
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
			final String userType = userDto.getType();
			final LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if ("9".equals(userType)) {
				throw new Exception("不能创建超级用户");
			}
			final List<String> childrens = CacheConverterUtils.getBranchAndSelf(cacheProxyFactory, loginInfo.getOrgCode());
			if(childrens != null && !childrens.contains(userDto.getOrgCode())){
				throw new Exception("只能为本级及下级机构创建用户");
			}
			if (loginInfo.getOrgCode().equals(userDto.getOrgCode())) {
				if ("1".equals(userType) && !loginInfo.isTopAdmin()) {
					throw new Exception("只能为所在机构创建普通用户");
				}
			}
			userDto.setStatus("0");
			userDto.setCreator(loginInfo.getUserId());
			final UserDto dto = tmUserService.persist(userDto.convertEntity());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /user 新增成功[" + dto + "]");
			}
			return new ResultResponse<UserDto>(true, dto);
		} catch (final Exception re) {
			LOGGER.error("POST /user [" + userDto + "]:新增失败!", re);
			return new ResultResponse<UserDto>(false, "新增失败！");
		}
	}

	/**
	 * 修改用户
	 * @param userDto 用户交互对象
	 * @param result BindingResult
	 * @param session HttpSession
	 * @return ResultResponse<UserDto>
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = { MediaTypeUtils.UTF_8 })
	@OperationControllerLog(description = "修改用户", type = OperationType.UPDATE)
	public ResultResponse<UserDto> update(@RequestBody @Valid final UserDto instance, final BindingResult result, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /user [" + instance + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error(result.getAllErrors());
			return new ResultResponse<UserDto>(false, "修改失败，校验不通过！", result.getAllErrors());
		}
		try {
			final LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (loginInfo.getUserId().equals(instance.getUserId()) && !loginInfo.isTopAdmin()) {
				throw new Exception("当前登陆用户不能被修改！");
			}
			final UserDto dto = tmUserService.update(instance);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /user 更新成功[" + dto + "]");
			}
			return new ResultResponse<UserDto>(true, dto);
		} catch (final RuntimeException re) {
			LOGGER.error("PUT /user [" + instance + "]:更新失败!", re);
			return new ResultResponse<UserDto>(false, "更新失败！");
		} catch (final Exception e) {
			LOGGER.error("PUT /user [" + instance + "]:更新失败!", e);
			return new ResultResponse<UserDto>(false, e.getMessage());
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
