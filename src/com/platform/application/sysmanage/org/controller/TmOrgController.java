package com.platform.application.sysmanage.org.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.org.TmOrgDto;
import com.platform.application.sysmanage.org.cache.TmOrgCache;
import com.platform.application.sysmanage.org.service.TmOrgService;
import com.platform.application.sysmanage.right.TmRightTreeDto;
import com.platform.application.utils.CacheConverterUtils;
import com.platform.application.utils.MediaTypeUtils;

/**
 * 平台机构管理控制类.
 */
@RestController
@RequestMapping("/org")
public class TmOrgController {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmOrgController.class);

	@Autowired
	private TmOrgService orgService;

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 获取机构管理主界面.
	 *
	 * @return 机构管理主界面
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView getHomePage() {
		return new ModelAndView("sysmanage/org/show");
	}

	/**
	 * 获取机构新增主界面.
	 *
	 * @return 机构新增主界面
	 */
	@RequestMapping(value = "/showadd", method = RequestMethod.GET)
	public ModelAndView getAddPage() {
		return new ModelAndView("sysmanage/org/add");
	}

	/**
	 * 获取机构管理详情页面.
	 *
	 * @return 机构管理主界面
	 */
	@RequestMapping(value = "/showdetail/{orgCode}", method = RequestMethod.GET)
	public ModelAndView getDetailPage(@PathVariable("orgCode") final String orgCode) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET /org/showdetail 开始获取机构详细信息[" + orgCode + "]");
		}
		TmOrgDto orgDto = orgService.findById(orgCode);
		ResultResponse<TmOrgDto> response = new ResultResponse<TmOrgDto>(true, orgDto);
		if (!response.isStatus()) {
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("ex", response.getError());
			LOGGER.error("GET /org/showdetail 获取机构详细信息失败[" + orgCode + "]:" + response.getError());
			return mv;
		}
		ModelAndView mv = new ModelAndView("sysmanage/org/detail");
		mv.addObject("orgdto", response.getResult());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET /org/showdetail 获取机构详细信息成功[" + orgCode + "]");
		}
		return mv;
	}

	/**
	 * 获取机构查询界面.
	 *
	 * @return 机构查询界面
	 */
	@RequestMapping(value = "/showquery", method = RequestMethod.GET)
	public ModelAndView getQueryPage() {
		return new ModelAndView("sysmanage/org/query");
	}

	/**
	 * 根据机构代码和角色类型获取机构的角色信息.
	 *
	 * @param orgCode 机构代码
	 * @param type    角色类型
	 * @return 机构的角色信息列表
	 */
	@RequestMapping(value = "/orgroletree/{type}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public List<TmRightTreeDto> selRoleTree(@PathVariable("type") final String type) {
		return orgService.selRoleTree(type);
	}

	/**
	 * 新增机构.
	 *
	 * @param orgDto  机构交互对象
	 * @param result  新增机构对象校验结果
	 * @param session 用户会话信息
	 * @return 新增机构结果
	 */
	@RequestMapping(method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	@OperationControllerLog(description = "新增机构", type = OperationType.ADD)
	public ResultResponse<TmOrgDto> save(@RequestBody @Valid final TmOrgDto orgDto, final BindingResult result, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /org 开始新增机构[" + orgDto + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error("POST /org 新增机构失败[" + orgDto + "]:" + result.getAllErrors());
			return new ResultResponse<TmOrgDto>(false, "新增机构失败，校验不通过！", result.getAllErrors());
		}
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		try {
			if (!this.isBranchOrSelf(orgDto.getUpOrg(), loginInfo.getOrgCode())) {
				throw new Exception("新增机构上级机构不隶属于当前登录用户！");
			}
			orgDto.setCreator(loginInfo.getUserId());
			TmOrgDto dto = orgService.persist(orgDto);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /org 新增机构成功[" + dto + "]");
			}
			return new ResultResponse<TmOrgDto>(true, dto);
		} catch (RuntimeException re) {
			LOGGER.error("POST /org 新增机构失败[" + orgDto + "]:" + re.getMessage(), re);
			return new ResultResponse<TmOrgDto>(false, "新增机构失败！");
		} catch (Exception e) {
			LOGGER.error("POST /org 新增机构失败[" + orgDto + "]:" + e.getMessage(), e);
			return new ResultResponse<TmOrgDto>(false, e.getMessage());
		}
	}

	/**
	 * 更新机构信息.
	 *
	 * @param orgDto  机构交互对象
	 * @param result  新增机构对象校验结果
	 * @param session 用户会话信息
	 * @return 更新结果
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = {MediaTypeUtils.UTF_8})
	@OperationControllerLog(description = "修改机构", type = OperationType.UPDATE)
	public ResultResponse<TmOrgDto> update(@RequestBody @Valid final TmOrgDto orgDto, final BindingResult result, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PUT /org 开始更新机构信息[" + orgDto + "]");
		}
		if (result.hasErrors()) {
			LOGGER.error("POST /org 更新机构信息失败[" + orgDto + "]:" + result.getAllErrors());
			return new ResultResponse<TmOrgDto>(false, "更新失败，校验不通过！", result.getAllErrors());
		}
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		try {
			if (!this.isBranchOrSelf(orgDto.getOrgCode(), loginInfo.getOrgCode())) {
				throw new Exception("更新机构不隶属于当前登录用户！");
			}
			boolean isSelf = loginInfo.getOrgCode().equals(orgDto.getOrgCode());
			if (StringUtils.isBlank(orgDto.getUpOrg())) {
				TmOrgDto orgInfo = cacheProxyFactory.getCacheValue(TmOrgCache.class, orgDto.getOrgCode());
				if (StringUtils.isNotBlank(orgInfo.getUpOrg())) {
					throw new Exception("更新机构上级机构不能为空！");
				}
			} else {
				if (!this.isBranchOrSelf(orgDto.getUpOrg(), loginInfo.getOrgCode())) {
					throw new Exception("更新机构上级机构不隶属于当前登录用户！");
				}
				if (isSelf) {
					TmOrgDto orgInfo = cacheProxyFactory.getCacheValue(TmOrgCache.class, orgDto.getOrgCode());
					if (!StringUtils.equals(orgInfo.getUpOrg(), orgDto.getUpOrg())) {
						throw new Exception("不能更新当前登录用户所在机构的上级机构！");
					}
				}
			}
			TmOrgDto dto = orgService.update(orgDto, isSelf);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PUT /org 更新机构信息成功[" + dto + "]");
			}
			return new ResultResponse<TmOrgDto>(true, dto);
		} catch (RuntimeException re) {
			LOGGER.error("PUT /org 更新机构信息失败[" + orgDto + "]:" + re.getMessage(), re);
			return new ResultResponse<TmOrgDto>(false, "更新机构信息失败！");
		} catch (Exception e) {
			LOGGER.error("PUT /org 更新机构信息失败[" + orgDto + "]:" + e.getMessage(), e);
			return new ResultResponse<TmOrgDto>(false, e.getMessage());
		}
	}

	/**
	 * 查询机构信息.
	 * @param dto     机构交互对象
	 * @param session 用户会话信息
	 * @return 查询结果列表
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public PageResponse<TmOrgDto> findByDto(@RequestBody final TmOrgDto dto, final HttpSession session) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST /org/find 开始查询机构信息[" + dto + "]");
		}
		LoginInfo loginInfo = (LoginInfo) session
				.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		try {
			if (null != dto.getStartCreateTime()
					&& dto.getStartCreateTime().after(new Date())) {
				throw new Exception("创建起始日不能早于查询当日");
			}
			if (null != dto.getEndCreateTime()
					&& dto.getEndCreateTime().after(new Date())) {
				throw new Exception("创建结束日不能早于查询当日");
			}
			if (null != dto.getStartCreateTime()
					&& null != dto.getEndCreateTime()) {
				if (dto.getStartCreateTime().after(dto.getEndCreateTime())) {
					throw new Exception("创建起始日不能晚于创建结束日");
				}
			}
			if (null != dto.getEndCreateTime()) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dto.getEndCreateTime());
				calendar.add(Calendar.DATE, 1);
				dto.setEndCreateTime(calendar.getTime());
			}
			PageResponse<TmOrgDto> results = orgService.findByDto(dto, loginInfo.getOrgCode());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST /org/find 查询机构信息成功["
						+ results + "]");
			}
			return results;
		} catch (Exception re) {
			LOGGER.error("POST /org/find 查询机构信息失败["
					+ dto + "]:" + re.getMessage(), re);
			return new PageResponse<TmOrgDto>(false, re.getMessage());
		}
	}

	/**
	 * 根据登录用户所在机构的机构代码获取机构树的根及其下级节点.
	 *
	 * @param session  当前用户登录信息
	 * @param response 结果响应对象
	 */
	@RequestMapping(value = "/orgtree", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public void getOrgTree(final HttpSession session, final HttpServletResponse response) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST /org/orgtree 开始获取机构树的根及其下级节点");
		}
		response.setCharacterEncoding("UTF-8");
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		String json = orgService.getTree(null, loginInfo);
		PrintWriter pt = null;
		try {
			pt = response.getWriter();
			pt.write(json);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("POST /org/orgtree 获取机构树的根及下级节点成功[" + json + "]");
			}
		} catch (IOException e) {
			LOGGER.error("POST /org/orgtree 开始获取机构树的根及下级节点:" + e.getMessage(), e);
		} finally {
			if (null != pt) {
				pt.close();
			}
		}
	}

	/**
	 * 根据机构代码获取机构树的下级节点.
	 *
	 * @param orgCode  机构代码
	 * @param session  当前登录用户信息
	 * @param response 结果响应对象
	 */
	@RequestMapping(value = "/orgtree/{orgcode}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public void getOrgTree(@PathVariable("orgcode") final String orgCode, final HttpSession session,
			final HttpServletResponse response) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST /org/orgtree 开始获取机构树的下级节点[" + orgCode + "]");
		}
		response.setCharacterEncoding("UTF-8");
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		String json = orgService.getTree(orgCode, loginInfo);
		if (StringUtils.isBlank(json)) {
			json = "[]";
		}
		PrintWriter pt = null;
		try {
			pt = response.getWriter();
			pt.write(json);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("POST /org/orgtree 获取机构树的下级节点成功[" + json + "]");
			}
		} catch (IOException e) {
			LOGGER.trace("POST /org/orgtree 获取机构树的下级节点失败[" + orgCode + "]:" + e.getMessage(), e);
		} finally {
			if (null != pt) {
				pt.close();
			}
		}
	}

	/**
	 * 根据机构代码获取登录用户可见机构树,并展开到目标机构代码.
	 *
	 * @param orgCode  目标机构代码
	 * @param session  当前登录用户信息
	 * @param response 结果响应对象
	 */
	@RequestMapping(value = "/uporgtree/{orgcode}", method = RequestMethod.POST, produces = {MediaTypeUtils.UTF_8})
	public void upOrgTree(@PathVariable("orgcode") final String orgCode, final HttpSession session, final HttpServletResponse response) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST /org/uporgtree 开始获取登录用户可见机构树[" + orgCode + "]");
		}
		response.setCharacterEncoding("UTF-8");
		LoginInfo loginInfo = (LoginInfo) session
				.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
		String json = orgService.getUpTree(orgCode, loginInfo);
		if (StringUtils.isBlank(json)) {
			json = "[]";
		}
		PrintWriter pt = null;
		try {
			pt = response.getWriter();
			pt.write(json);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("POST /org/uporgtree 获取登录用户可见机构树成功[" + json + "]");
			}
		} catch (IOException e) {
			LOGGER.trace("POST /org/uporgtree 获取登录用户可见机构树失败[" + orgCode + "]:" + e.getMessage(), e);
		} finally {
			if (null != pt) {
				pt.close();
			}
		}
	}

	/**
	 * 判断机构[orgcode]是否隶属(包含自己)于[uporg].
	 *
	 * @param orgCode 机构代码
	 * @param upOrg   上级机构
	 * @return 是否包含
	 */
	public boolean isBranchOrSelf(final String orgCode, final String upOrg) {
		if (StringUtils.equals(orgCode, upOrg)) {
			return true;
		}
		List<String> children = CacheConverterUtils.getBranchAndSelf(cacheProxyFactory, upOrg);
		if (children != null && children.contains(orgCode)) {
			return true;
		}
		return false;
	}
}
