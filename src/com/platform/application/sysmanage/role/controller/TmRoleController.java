package com.platform.application.sysmanage.role.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.right.TmRightDto;
import com.platform.application.sysmanage.right.TmRightTreeDto;
import com.platform.application.sysmanage.right.cache.TmRightCache;
import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.role.service.TmRoleService;
import com.platform.application.utils.MediaTypeUtils;

/**
 * 平台角色管理控制类.
 *
 */
@RestController
@RequestMapping(value = "/role")
public class TmRoleController {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmRoleController.class);

	@Autowired
	private TmRoleService roleService;

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 获取主界面.
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView getRoleHome() {
		return new ModelAndView("sysmanage/role/show");
	}

	/**
	 * 获取新增界面.
	 *
	 * @return 新增界面
	 */
	@RequestMapping(value = "/showadd", method = RequestMethod.GET)
	public ModelAndView getAddPage() {
		return new ModelAndView("sysmanage/role/add");
	}

	/**
	 * 新增角色.
	 *
	 * @param dto     TmRoleDto对象
	 * @param result  BindingResult
	 * @param session HttpSession
	 * @return ResultResponse<TmRoleDto>
	 */
	@RequestMapping(method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public ResultResponse<TmRoleDto> save(@RequestBody @Valid final TmRoleDto dto, final BindingResult result, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /role [" + dto + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error(result.getAllErrors());
			return new ResultResponse<TmRoleDto>(false, "新增失败，校验不通过！", result.getAllErrors());
		}
		try {
			LoginInfo loginInfo = (LoginInfo)session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (!loginInfo.isTopAdmin()) {
				throw new Exception("当前登录用户[" + loginInfo.getUserId() + "]不是超级用户，没有新增角色的权限！");
			}
			dto.setCreator(loginInfo.getUserId());
			TmRoleDto resultDto = roleService.persist(dto);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /role 新增成功[" + dto + "]");
			}
			return new ResultResponse<TmRoleDto>(true, resultDto);
		} catch (RuntimeException re) {
			LOGGER.error("POST /role 新增失败[" + dto + "]", re);
			return new ResultResponse<TmRoleDto>(false, "新增失败,无法保存角色信息！");
		} catch (Exception le) {
			LOGGER.error("POST /role 新增失败[" + dto + "]"
					+ le.getMessage(), le);
			return new ResultResponse<TmRoleDto>(false, le.getMessage());
		}
	}

	/**
	 * 获取 TmCredituser 对象集合.
	 *
	 * @param dto TmOrgRoleDto对象
	 * @return PageResponse<TmOrgRoleDto>
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public PageResponse<TmRoleDto> findByDto(@RequestBody final TmRoleDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /role/find [" + dto + "]");
		}
		PageResponse<TmRoleDto> results = roleService.findByDto(dto);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /role/find 查询成功[" + dto + "]");
		}
		return results;
	}

	/**
	 * 获取详细信息界面.
	 *
	 * @param roleCode 角色代码
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showdetail/{rolecode}", method = RequestMethod.GET)
	public ModelAndView findDetailById(@PathVariable("rolecode") final String roleCode) {
		ResultResponse<TmRoleDto> response = this.findById(roleCode);
		if (!response.isStatus()) {
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("ex", response.getError());
			return mv;
		}
		ModelAndView mv = new ModelAndView("sysmanage/role/detail");
		mv.addObject("roledto", response.getResult());
		return mv;

	}

	/**
	 * 更新角色信息.
	 *
	 * @param instance TmRoleDto对象
	 * @param result   BindingResult
	 * @param session  HttpSession
	 * @return ResultResponse<TmRoleDto>
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = {MediaTypeUtils.UTF_8})
	public ResultResponse<TmRoleDto> update(@RequestBody @Valid final TmRoleDto instance, final BindingResult result, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /role [" + instance + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error(result.getAllErrors());
			return new ResultResponse<TmRoleDto>(false, "修改失败，校验不通过！", result.getAllErrors());
		}
		try {
			LoginInfo loginInfo = (LoginInfo)session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (!loginInfo.isTopAdmin()) {
				throw new Exception("当前登录用户[" + loginInfo.getUserId() + "]不是超级用户，没有修改角色的权限！");
			}
			TmRoleDto dto = roleService.update(instance);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /role 更新成功[" + dto + "]");
			}
			return new ResultResponse<TmRoleDto>(true, dto);
		} catch (RuntimeException re) {
			LOGGER.error("PUT /role 更新失败[" + instance + "]", re);
			return new ResultResponse<TmRoleDto>(false, "更新失败！");
		} catch (Exception le) {
			LOGGER.error("PUT /role 更新失败[" + instance + "]"
					+ le.getMessage(), le);
			return new ResultResponse<TmRoleDto>(false, le.getMessage());
		}
	}

	/**
	 * 获取角色权限列表.
	 *
	 * @param rolecode 角色代码
	 * @return 角色权限列表
	 */
	@RequestMapping(value = "/showright/{rolecode}", method = RequestMethod.GET)
	public ModelAndView getRightPage(@PathVariable("rolecode") final String rolecode) {
		TmRoleDto dto = roleService.findById(rolecode, false);
		ModelAndView mv = new ModelAndView("sysmanage/role/rightinfo");
		mv.addObject("roledto", dto);
		return mv;
	}

	/**
	 * 修改角色权限分配信息.
	 *
	 * @param dto     TmRoleDto对象
	 * @param session HttpSession
	 * @return ResultResponse<TmRoleDto>
	 */
	@RequestMapping(value = "/updright", method = RequestMethod.PUT, produces = {MediaTypeUtils.UTF_8})
	public ResultResponse<TmRoleDto> updright(@RequestBody final TmRoleDto dto, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /role/updright [" + dto + "]");
		}
		try {
			LoginInfo loginInfo = (LoginInfo)session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (!loginInfo.isTopAdmin()) {
				throw new Exception("当前登录用户[" + loginInfo.getUserId() + "]不是超级用户，没有修改角色的权限！");
			}
			TmRoleDto result = roleService.updateRight(dto);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /role/updright 角色分配成功[" + result + "]");
			}
			return new ResultResponse<TmRoleDto>(true, result);
		} catch (RuntimeException re) {
			LOGGER.error("PUT /role/updright 角色分配失败[" + dto + "]", re);
			return new ResultResponse<TmRoleDto>(false, "角色分配失败！");
		} catch (Exception le) {
			LOGGER.error("PUT /role/updright 角色分配失败[" + dto + "]"
					+ le.getMessage(), le);
			return new ResultResponse<TmRoleDto>(false, le.getMessage());
		}
	}


	/**
	 * 角色启用.
	 *
	 * @param roleCode 角色代码
	 * @param session  HttpSession
	 * @return ResultResponse<TmRoleDto>
	 */
	@RequestMapping(value = "/start/{roleCode}", method = RequestMethod.PUT, produces = {MediaTypeUtils.UTF_8})
	public ResultResponse<TmRoleDto> start(@PathVariable("roleCode") final String roleCode, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /role/start [" + roleCode + "]");
		}
		try {
			LoginInfo loginInfo = (LoginInfo)session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (!loginInfo.isTopAdmin()) {
				throw new Exception("当前登录用户[" + loginInfo.getUserId() + "]不是超级用户，没有启用角色的权限！");
			}
			roleService.stopOrStartRole(roleCode, true, "");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /role/start 启用成功[" + roleCode + "]");
			}
			return new ResultResponse<TmRoleDto>(true);
		} catch (RuntimeException re) {
			LOGGER.error("PUT /role/start 启用失败[" + roleCode + "]", re);
			return new ResultResponse<TmRoleDto>(false, "启用失败,无法更新角色状态！");
		} catch (Exception le) {
			LOGGER.error("PUT /role/start 启用失败[" + roleCode + "]" + le.getMessage(), le);
			return new ResultResponse<TmRoleDto>(false, le.getMessage());
		}
	}

	/**
	 * 角色停用.
	 *
	 * @param dto     角色代码
	 * @param session HttpSession
	 * @return ResultResponse<TmRoleDto>
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.PUT, produces = {MediaTypeUtils.UTF_8})
	public ResultResponse<TmRoleDto> stop(@RequestBody final TmRoleDto dto, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /role/stop [" + dto.getRoleCode() + "]");
		}
		try {
			LoginInfo loginInfo = (LoginInfo)session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (!loginInfo.isTopAdmin()) {
				throw new Exception("当前登录用户[" + loginInfo.getUserId() + "]不是超级用户，没有停用角色的权限！");
			}
			roleService.stopOrStartRole(dto.getRoleCode(), false, ""/*dto.getStopReason()*/);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /role/stop 停用成功[" + dto.getRoleCode() + "]");
			}
			return new ResultResponse<TmRoleDto>(true);
		} catch (RuntimeException re) {
			LOGGER.error("PUT /role/stop [" + dto.getRoleCode() + "]:停用失败!", re);
			return new ResultResponse<TmRoleDto>(false, "停用失败,无法更新角色状态！");
		} catch (Exception le) {
			LOGGER.error("PUT /role/stop 停用失败[" + dto.getRoleCode() + "]" + le.getMessage(), le);
			return new ResultResponse<TmRoleDto>(false, le.getMessage());
		}
	}

	/**
	 * 获取权限树.
	 *
	 * @param roleCode 角色代码
	 * @return List<RightTreeDto>
	 */
	@RequestMapping(value = "/tree/{rolecode}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public List<TmRightTreeDto> selRightsByRole(
			@PathVariable("rolecode") final String roleCode) {
		TmRoleDto roledto = roleService.findById(roleCode, true);
		List<TmRightTreeDto> tree = selRightsByType(roledto.getType());
		if (roledto.getTmRights() != null) {
			for (TmRightTreeDto dto : tree) {
				if (roledto.getTmRights().contains(dto.getId())) {
					dto.setChecked(true);
				}
				if (!dto.getChildren().isEmpty()) {
					dto.setChecked(false);
					for (TmRightTreeDto sondto : dto.getChildren()) {
						if (roledto.getTmRights().contains(sondto.getId())) {
							sondto.setChecked(true);
						}
					}
				}
			}
		}
		return tree;
	}

	/**
	 * 获取当前登陆用户所在的权限树信息.
	 *
	 * @param type 权限类型
	 * @return List<RightTreeDto>
	 */
	@RequestMapping(value = "/rights/{type}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public List<TmRightTreeDto> selRightsByType(@PathVariable("type") final String type) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /role/rights[" + type + "]");
		}
		List<TmRightTreeDto> tree = new ArrayList<TmRightTreeDto>();
		Map<String, TmRightDto> maps = cacheProxyFactory.getCacheAllValue(TmRightCache.class);
		for (TmRightDto rightDto : maps.values()) {
			if(/*ManageModelConstants.DIC_USER_TYPE_ADMIN*/"0".equals(type)){
				if (!rightDto.getTopAdmin().equals(type)) {
					if(!(rightDto.getRightCode().startsWith("0106")|| rightDto.getRightCode().startsWith("0109"))){
						continue;
					}
				}
			} else {
				if (!rightDto.getTopAdmin().equals(type)) {
					continue;
				}
			}
			String right = rightDto.getRightCode();
			// 如果为父节点
			if (right.length() == 4/*ManageModelConstants.MENU_SECOND_RIGHT_LENGTH*/) {
				TmRightTreeDto dto = new TmRightTreeDto();
				dto.setId(right);
				dto.setText(rightDto.getRightName());
				List<TmRightTreeDto> sonlist = new ArrayList<TmRightTreeDto>();
				for (TmRightDto childDto : maps.values()) {
					String childRight = childDto.getRightCode();
					if (!childRight.equals(right) && childRight.startsWith(right)) {
						TmRightTreeDto sondto = new TmRightTreeDto();
						sondto.setId(childRight);
						sondto.setText(childDto.getRightName());
						sonlist.add(sondto);
					}
				}
				if (!sonlist.isEmpty()) {
					dto.setChildren(sonlist);
					dto.setState("closed");
				}
				tree.add(dto);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /role/rights 查询成功[" + type + "]");
		}
		return tree;
	}

	/**
	 * 获取 角色对象.
	 *
	 * @param roleCode 主键
	 * @return TmRoleDto 传输对象
	 */
	private ResultResponse<TmRoleDto> findById(@PathVariable("rolecode") final String roleCode) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET /role [" + roleCode + "]");
		}
		try {
			TmRoleDto dto = roleService.findById(roleCode, false);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /role [" + roleCode + "]:查询成功[" + dto + "]");
			}
			return new ResultResponse<TmRoleDto>(true, dto);
		} catch (RuntimeException re) {
			LOGGER.error("GET /role [" + roleCode + "]:查询失败!", re);
			return new ResultResponse<TmRoleDto>(false, "查询失败！");
		}

	}
}
