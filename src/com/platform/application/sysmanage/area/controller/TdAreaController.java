package com.platform.application.sysmanage.area.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.platform.application.sysmanage.area.TdAreaDto;
import com.platform.application.sysmanage.area.cache.TdAreaCache;
import com.platform.application.sysmanage.area.service.TdAreaService;
import com.platform.application.utils.MediaTypeUtils;

/**
 * 辖区管理控制类.
 */
@RestController
@RequestMapping("/area")
public class TdAreaController {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TdAreaController.class);

	@Autowired
	private TdAreaService areaService;

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 获取主界面.
	 *
	 * @return 主界面
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView getUserHome() {
		ModelAndView mav = new ModelAndView("sysmanage/area/show");
		return mav;
	}

	/**
	 * 展示新增界面.
	 *
	 * @return 新增界面
	 */
	@RequestMapping(value = "/showadd", method = RequestMethod.GET)
	public ModelAndView getUserAdd() {
		final ModelAndView mv = new ModelAndView("sysmanage/area/add");
		return mv;
	}

	/**
	 * 获取详细信息界面.
	 *
	 * @param userId
	 *            主键
	 * @return 详细信息界面
	 */
	@RequestMapping(value = "/showdetail/{areaId}", method = RequestMethod.GET)
	public ModelAndView getDetailPage(@PathVariable("areaId") final String areaId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET /area/showdetail [" + areaId + "]");
		}
		TdAreaDto areaDto = areaService.findById(areaId);
		final ResultResponse<TdAreaDto> response = new ResultResponse<TdAreaDto>(true, areaDto);
		ModelAndView mv;
		if (response.isStatus()) {
			mv = new ModelAndView("sysmanage/area/detail");
			mv.addObject("areaDto", response.getResult());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /area/showdetail 成功 [" + areaId + "]");
			}
		} else {
			mv = new ModelAndView("error");
			mv.addObject("ex", response.getError());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET /area/showdetail 失败 [" + areaId + "][" + response.getError() + "]");
			}
		}
		return mv;
	}

	/**
	 * 新增辖区
	 * @param areaDto 辖区交互对象
	 * @param result BindingResult
	 * @param session HttpSession
	 * @return ResultResponse<UserDto>
	 */
	@RequestMapping(method = RequestMethod.POST, produces = { MediaTypeUtils.UTF_8 })
	@OperationControllerLog(description = "新增辖区", type = OperationType.ADD)
	public ResultResponse<TdAreaDto> save(@RequestBody @Valid final TdAreaDto areaDto, final BindingResult result,
			final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /area [" + areaDto + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error("POST /area [" + areaDto + "]:新增失败!" + result.getAllErrors());
			return new ResultResponse<TdAreaDto>(false, "新增失败，校验不通过！", result.getAllErrors());
		}
		try {
			TdAreaDto dto = areaService.persist(areaDto.convertEntity());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /area 新增成功[" + dto + "]");
			}
			return new ResultResponse<TdAreaDto>(true, dto);
		} catch (final Exception re) {
			LOGGER.error("POST /area [" + areaDto + "]:新增失败!", re);
			return new ResultResponse<TdAreaDto>(false, "新增失败！");
		}
	}

	/**
	 * 修改辖区
	 * @param areaDto 辖区交互对象
	 * @param result BindingResult
	 * @param session HttpSession
	 * @return ResultResponse<UserDto>
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = { MediaTypeUtils.UTF_8 })
	@OperationControllerLog(description = "修改辖区", type = OperationType.UPDATE)
	public ResultResponse<TdAreaDto> update(@RequestBody @Valid final TdAreaDto areaDto, final BindingResult result) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /area [" + areaDto + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error(result.getAllErrors());
			return new ResultResponse<TdAreaDto>(false, "修改失败，校验不通过！", result.getAllErrors());
		}
		try {
			final TdAreaDto dto = areaService.update(areaDto);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /area 更新成功[" + dto + "]");
			}
			return new ResultResponse<TdAreaDto>(true, dto);
		} catch (final RuntimeException re) {
			LOGGER.error("PUT /area [" + areaDto + "]:更新失败!", re);
			return new ResultResponse<TdAreaDto>(false, "更新失败！");
		} catch (final Exception e) {
			LOGGER.error("PUT /area [" + areaDto + "]:更新失败!", e);
			return new ResultResponse<TdAreaDto>(false, e.getMessage());
		}
	}

	/**
	 * 根据id删除记录.
	 * @param areaId 辖区编号
	 * @return
	 */
	@RequestMapping(value = "/{areaId}", method = RequestMethod.DELETE, produces = { MediaTypeUtils.UTF_8 })
	public ResultResponse<TdAreaDto> delete(@PathVariable("areaId") final String areaId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DELETE /area [" + areaId + "]");
		}
		try {
			TdAreaDto areaDto = cacheProxyFactory.getCacheValue(TdAreaCache.class, areaId);
			boolean self = areaService.isBranchOrSelf(areaDto.getAreaId());
			if(self){
				return new ResultResponse<TdAreaDto>(false, "存在下级辖区不可删除！");
			}
			final TdAreaDto dto = areaService.delete(areaDto.convertEntity());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /area 更新成功[" + dto + "]");
			}
			return new ResultResponse<TdAreaDto>(true, dto);
		} catch (final RuntimeException re) {
			LOGGER.error("DELETE /area [" + areaId + "]:删除失败!", re);
			return new ResultResponse<TdAreaDto>(false, "删除失败！");
		} catch (final Exception e) {
			LOGGER.error("DELETE /area [" + areaId + "]:删除失败!", e);
			return new ResultResponse<TdAreaDto>(false, e.getMessage());
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
	public PageResponse<TdAreaDto> findByDto(@RequestBody final TdAreaDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /area/find [" + dto + "]");
		}
		try {
			final PageResponse<TdAreaDto> results = areaService.findByDto(dto);
			if (LOGGER.isDebugEnabled()) {
				if (results.isStatus()) {
					LOGGER.debug("POST /area/find 查询成功[" + dto + "]");
				} else {
					LOGGER.debug("POST /area/find 查询失败[" + results.getError() + "]");
				}
			}
			return results;
		} catch (final Exception re) {
			LOGGER.error("POST /area/find [" + dto + "]:查询失败!", re);
			return new PageResponse<TdAreaDto>(false, re.getMessage());
		}
	}

	/**
	 * 根据辖区代码获取辖区信息.
	 * @param areaId 辖区代码
	 * @return ResultResponse<TdAreaDto>
	 */
	@RequestMapping(value = "findArea/{areaId}", method = RequestMethod.GET, produces = {MediaTypeUtils.UTF_8})
	public ResultResponse<TdAreaDto> findByAreaId(@PathVariable("areaId") final String areaId){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("GET /area/findArea 开始根据辖区代码["+ areaId +"]获取辖区信息");
		}
		TdAreaDto areaDto = cacheProxyFactory.getCacheValue(TdAreaCache.class, areaId);
		return new ResultResponse<TdAreaDto>(true, areaDto);
	}

	/**
	 * 获取辖区树的根.
	 *
	 * @param session  当前用户登录信息
	 * @param response 结果响应对象
	 */
	@RequestMapping(value = "/areatree", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public void getAreaTree(final HttpServletResponse response) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST /area/areatree 开始获取辖区树的根及其下级节点");
		}
		response.setCharacterEncoding("UTF-8");
		String json = areaService.getTree(null);
		PrintWriter pt = null;
		try {
			pt = response.getWriter();
			pt.write(json);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("POST /area/areatree 获取辖区树的根及下级节点成功[" + json + "]");
			}
		} catch (IOException e) {
			LOGGER.error("POST /area/areatree 开始获辖区树的根及下级节点:" + e.getMessage(), e);
		} finally {
			if (null != pt) {
				pt.close();
			}
		}
	}

	/**
	 * 根据辖区代码获取辖区树的下级节点.
	 *
	 * @param orgCode  机构代码
	 * @param session  当前登录用户信息
	 * @param response 结果响应对象
	 */
	@RequestMapping(value = "/areatree/{areaId}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public void getOrgTree(@PathVariable("areaId") final String areaId, final HttpServletResponse response) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST /area/areatree 开始获取辖区树的下级节点[" + areaId + "]");
		}
		response.setCharacterEncoding("UTF-8");
		String json = areaService.getTree(areaId);
		if (StringUtils.isBlank(json)) {
			json = "[]";
		}
		PrintWriter pt = null;
		try {
			pt = response.getWriter();
			pt.write(json);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("POST /area/areatree 获取辖区树的下级节点成功[" + json + "]");
			}
		} catch (IOException e) {
			LOGGER.trace("POST /area/areatree 获取辖区树的下级节点失败[" + areaId + "]:" + e.getMessage(), e);
		} finally {
			if (null != pt) {
				pt.close();
			}
		}
	}

	/**
	 * 根据辖区代码获取可见辖区树,并展开到目标辖区代码.
	 *
	 * @param orgCode  目标辖区代码
	 * @param session  当前登录用户信息
	 * @param response 结果响应对象
	 */
	@RequestMapping(value = "/upareatree/{areaId}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public void upOrgTree(@PathVariable("areaId") final String areaId, final HttpServletResponse response) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST /area/upareatree 开始获取登录用户可见辖区树[" + areaId + "]");
		}
		response.setCharacterEncoding("UTF-8");
		String json = areaService.getUpTree(areaId);
		if (StringUtils.isBlank(json)) {
			json = "[]";
		}
		PrintWriter pt = null;
		try {
			pt = response.getWriter();
			pt.write(json);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("POST /area/upareatree 获取登录用户可见辖区树成功[" + json + "]");
			}
		} catch (IOException e) {
			LOGGER.trace("POST /area/upareatree 获取登录用户可见辖区树失败[" + areaId + "]:" + e.getMessage(), e);
		} finally {
			if (null != pt) {
				pt.close();
			}
		}
	}
}
