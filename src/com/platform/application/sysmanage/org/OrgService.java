package com.platform.application.sysmanage.org;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.org.bean.TmOrg;
import com.platform.application.sysmanage.org.bean.TmOrgRela;
import com.platform.application.sysmanage.org.cache.TmOrgCache;
import com.platform.application.sysmanage.org.cache.TmOrgRelaCache;
import com.platform.application.sysmanage.service.AbstractService;
import com.platform.application.utils.CacheConverterUtils;


/**
 * 机构管理服务类.
 *
 */
@Service
@Transactional
public class OrgService extends AbstractService {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(OrgService.class);
	/**
	 * 系统缓存代理类.
	 */
	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 新增机构.
	 *
	 * @param entity 机构实体对象
	 * @return 机构传输对象
	 * @throws Exception 新增机构逻辑校验错误
	 */
	public OrgDto persist(final OrgDto entity) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在保存 [" + entity.toString() + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			String error = "";
			if (StringUtils.isBlank(entity.getUpOrg())) {
				error = "新增机构上级机构不能为空！";
			}
			if (StringUtils.isBlank(entity.getUpOrg()) && cacheProxyFactory.getCacheValue(TmOrgCache.class,
					entity.getUpOrg()) == null) {
				error = "新增机构上级机构不存在！";
			}
			if (StringUtils.isBlank(entity.getUpOrg())
					&& cacheProxyFactory.getCacheValue(TmOrgCache.class,
							entity.getOrgCode()) != null) {
				error = "新增机构代码已存在!";
			}
			if (StringUtils.isNotBlank(error)) {
				throw new Exception(error);
			}
			entity.setCreateTime(new Date());
			final TmOrg org = entity.convertEntity();
			session.persist(org);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("保存成功 [" + entity.toString() + "]");
			}
			cacheProxyFactory.evictCacheAllValue(TmOrgRelaCache.class);
			return entity;
		} catch (final RuntimeException re) {
			LOGGER.error("保存失败 [" + entity.toString() + "]", re);
			throw re;
		}
	}

	/**
	 * 修改机构.
	 *
	 * @param dto    机构传输对象.
	 * @param isSelf 是否为所辖机构
	 * @return 修改机构传输对象
	 * @throws Exception 修改机构逻辑校验错误
	 */
	public OrgDto update(final OrgDto dto, final boolean isSelf) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在修改 [" + dto.toString() + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TmOrg TmOrg = (TmOrg) session.load(TmOrg.class, dto.getOrgCode());
			if (!isSelf) {
				if (this.isBranchOrSelf(dto.getUpOrg(), dto.getOrgCode())) {
					throw new Exception("不能将上级机构更新为本机构及其所辖机构！");
				}
				TmOrg.setUpOrg(dto.getUpOrg());
			}
			TmOrg.setAreaCode(dto.getAreaCode());
			TmOrg.setOrgName(dto.getOrgName());
			TmOrg.setRemark(dto.getRemark());
			session.update(TmOrg);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改成功[" + TmOrg.toString() + "]");
			}
			cacheProxyFactory.evictCacheValue(TmOrgCache.class, dto.getOrgCode());
			cacheProxyFactory.evictCacheAllValue(TmOrgRelaCache.class);
			return TmOrg.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("修改失败 [" + dto.toString() + "]", re);
			throw re;
		}
	}

	/**
	 * 通过主键获取 TmOrg.
	 *
	 * @param id 主键
	 * @return TmOrg
	 */
	@Transactional(readOnly = true)
	public OrgDto findById(final String id) {
		return this.findById(id, false);
	}

	/**
	 * 通过机构代码获取机构信息.
	 *
	 * @param orgCode 机构代码
	 * @param cascade 是否级联查询
	 * @return 机构信息
	 */
	@Transactional(readOnly = true)
	public OrgDto findById(final String orgCode,
			final boolean cascade) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始获取机构信息,机构代码[" + orgCode + "]");
		}
		try {
			OrgDto dto = null;
			final TmOrg instance = (TmOrg) sessionFactory
					.getCurrentSession().get(TmOrg.class, orgCode);
			if (instance != null) {
				if (cascade) {
					dto = instance.cascadeDto();
				} else {
					dto = instance.convertDto();
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获取机构信息成功[" + instance.toString() + "]");
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获取机构信息失败,不存在机构代码为["
							+ orgCode + "]的机构信息");
				}
			}
			return dto;
		} catch (final RuntimeException re) {
			LOGGER.error("获取机构信息失败，机构代码["
					+ orgCode + "]" + re.getMessage(), re);
			throw re;
		}
	}

	/**
	 * 根据实例查询数据.
	 *
	 * @param dto          机构传输对象
	 * @param loginOrgCode 登录机构代码
	 * @return 查询结果
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public PageResponse<OrgDto> findByDto(final OrgDto dto,
			final String loginOrgCode) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询  TmOrg 条件 " + dto.toString());
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(TmOrg.class);
			if (StringUtils.isNotBlank(dto.getUpOrg())) {
				criteria.add(Restrictions.eq("upOrg", dto.getUpOrg()));
			}
			if (StringUtils.isNotBlank(dto.getOrgName())) {
				criteria.add(Restrictions.like("orgName",
						"%" + dto.getOrgName() + "%"));
			}
			if (StringUtils.isNotBlank(dto.getOrgCode())) {
				criteria.add(Restrictions.like("orgCode", "%" +dto.getOrgCode() + "%"));
			}
			if (StringUtils.isNotBlank(dto.getPcOrgCode())) {
				criteria.add(Restrictions.eq("pcOrgCode", dto.getPcOrgCode()));
			}
			if (StringUtils.isNotBlank(dto.getEcOrgCode())) {
				criteria.add(Restrictions.eq("ecOrgCode", dto.getEcOrgCode()));
			}
			if (StringUtils.isNotBlank(dto.getStatus())) {
				criteria.add(Restrictions.eq("status", dto.getStatus()));
			}
			if (StringUtils.isNotBlank(dto.getAreaCode())) {
				criteria.add(Restrictions.eq("areaCode", dto.getAreaCode()));
			}
			if (StringUtils.isNotBlank(dto.getRemark())) {
				criteria.add(Restrictions.eq("remark", dto.getRemark()));
			}
			if (StringUtils.isNotBlank(dto.getCreator())) {
				criteria.add(Restrictions.eq("creator", dto.getCreator()));
			}
			if (dto.getCreateTime() != null) {
				criteria.add(Restrictions.eq("createTime",
						dto.getCreateTime()));
			}
			if (dto.getStartCreateTime() != null) {
				criteria.add(Restrictions.ge("createTime",
						dto.getStartCreateTime()));
			}
			if (dto.getEndCreateTime() != null) {
				criteria.add(Restrictions.le("createTime",
						dto.getEndCreateTime()));
			}
			if (StringUtils.isNotBlank(loginOrgCode)) {
				criteria.add(Restrictions.in("orgCode",
						CacheConverterUtils.getBranchAndSelf(
								cacheProxyFactory, loginOrgCode)));
			}
			final int total = ((Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult()).intValue();
			int page = dto.getPage();
			if (page < 1) {
				page = 1;
			}
			int rows = dto.getRows();
			if (rows < 1) {
				rows = this.pageSize;
			}
			criteria.setProjection(null);
			final List<TmOrg> results = criteria
					.setFirstResult((page - 1) * rows).setMaxResults(rows)
					.addOrder(Order.desc("createTime")).list();
			final List<OrgDto> dtos = new ArrayList<OrgDto>();
			for (final TmOrg u : results) {
				dtos.add(u.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询成功 " + results.toString());
			}
			return new PageResponse<OrgDto>(true, total, page, this.pageSize,
					dtos);
		} catch (final RuntimeException re) {
			LOGGER.error("查询失败 条件 " + dto.toString(), re);
			return new PageResponse<OrgDto>(false, "查询失败!");
		}
	}

	/**
	 * 停用机构.
	 *
	 * @param orgCode 被执行操作的机构的机构代码
	 * @param status  对机构进行操作状态
	 * @param isLow   是否对orgCode的下级机构进行操作
	 * @return 停用结果对象
	 * @throws LogicFailureException 停用机构逻辑校验错误
	 */
	public OrgDto stop(final String orgCode, final String status, final boolean isLow) throws Exception {
		/*final Session session = sessionFactory.getCurrentSession();
		final TmOrg TmOrg = (TmOrg) session
				.load(TmOrg.class, orgCode);
		// 判断将要进行的操作
		if (ManageModelConstants.DIC_ORG_STATUS_STOP.equals(status)) {
			// 判断机构是否已经是停用状态
			if (ManageModelConstants.DIC_ORG_STATUS_STOP
					.equals(TmOrg.getStatus())) {
				throw new LogicFailureException("用用户已经是停用状态");
			}
			// 设置并跟新机构的停用时间
			TmOrg.setRecordStopTime(new Date());
			TmOrg.setRecordStartTime(null);
		} else {
			// 判断机构是否已经是启用状态
			if (ManageModelConstants.DIC_ORG_STATUS_START
					.equals(TmOrg.getStatus())) {
				throw new LogicFailureException("用用户已经是启用状态");
			}
			// 设置并跟新机构的启用时间
			TmOrg.setRecordStartTime(new Date());
			TmOrg.setRecordStopTime(null);
		}
		// 设置并更新机构状态
		TmOrg.setStatus(status);
		session.update(TmOrg);
		// 判断是否对orgcode的子机构进行操作
		final Set<String> children = cacheProxyFactory.getCacheValue(
				OrgRelaCache.class, orgCode);
		if (isLow && children != null && children.size() > 0) {
			final String hql = "update TmOrg org set org.status=:status,"
					+ "org.recordStopTime=:recordStopTime,"
					+ "org.recordStartTime=:recordStartTime "
					+ "where org.orgCode in (:orgList)";
			final Query query = session.createQuery(hql);
			query.setString("status", status);
			query.setParameterList("orgList", children);
			if (ManageModelConstants.DIC_ORG_STATUS_STOP.equals(status)) {
				query.setTimestamp("recordStopTime", new Date());
				query.setTimestamp("recordStartTime", null);
			} else {
				query.setTimestamp("recordStartTime", new Date());
				query.setTimestamp("recordStopTime", null);
			}
			query.executeUpdate();
		}
		cacheProxyFactory.evictCacheValue(OrgCache.class, orgCode);
		cacheProxyFactory.evictCacheAllValue(OrgRelaCache.class);
		return TmOrg.convertDto();*/
		return null;
	}


	/**
	 * 空字符串转换.
	 *
	 * @param in 待转换字符串
	 * @return 转换后字符串
	 */
	public String translate(final String in) {
		if (null != in && !"".equals(in)) {
			return in;
		}
		return "\t";
	}

	/**
	 * 获取所有的机构及上级机构信息.
	 *
	 * @return 上级机构信息
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TmOrgRela> selOrgAndUpOrg() {
		try {
			return sessionFactory.getCurrentSession().createCriteria(TmOrgRela.class).list();
		} catch (final HibernateException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取机构树.
	 *
	 * @param orgCodeValue 机构代码,如果为空则默认当前登录用户所在机构代码
	 * @param loginInfo    当前用户登录信息
	 * @return 机构树字符串
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String getTree(final String orgCodeValue,
			final LoginInfo loginInfo) {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria criteria = session.createCriteria(TmOrg.class);
		criteria.add(Restrictions.eq("status", "0"));
		String orgCode = orgCodeValue;
		final StringBuilder jsonBuf = new StringBuilder();
		if (StringUtils.isBlank(orgCode)) {
			orgCode = loginInfo.getOrgCode();
			final TmOrg entity = (TmOrg) session.get(
					TmOrg.class, orgCode);
			jsonBuf.append("[{\"id\":\"")
			.append(entity.getOrgCode())
			.append("\",\"text\":\"")
			.append(entity.getOrgName())
			.append("[")
			.append(entity.getOrgCode())
			.append("]\"");
			// 查询上级机构为orgCode的机构
			criteria.add(Restrictions.eq("upOrg", orgCode));
			final List<TmOrg> results = criteria
					.addOrder(Order.desc("orgCode")).list();
			if (results.size() > 0) {
				final int length = results.size();
				int idx = 0;
				jsonBuf.append(",\"children\":[");
				for (final TmOrg TmOrg : results) {
					jsonBuf.append("{\"id\":\"")
					.append(TmOrg.getOrgCode())
					.append("\",\"text\":\"")
					.append(TmOrg.getOrgName())
					.append("[")
					.append(TmOrg.getOrgCode())
					.append("]\",\"state\":\"closed\"");
					if (++idx == length) {
						jsonBuf.append("}]");
					} else {
						jsonBuf.append("},");
					}
				}
			}
			jsonBuf.append("}]");
		} else {
			criteria.add(Restrictions.eq("upOrg", orgCode));
			final List<TmOrg> results = criteria
					.addOrder(Order.desc("orgCode")).list();
			if (results.size() > 0) {
				final int length = results.size();
				int idx = 0;
				jsonBuf.append("[");
				for (final TmOrg TmOrg : results) {
					jsonBuf.append("{\"id\":\"")
					.append(TmOrg.getOrgCode())
					.append("\",\"text\":\"")
					.append(TmOrg.getOrgName())
					.append("[")
					.append(TmOrg.getOrgCode())
					.append("]\",\"state\":\"closed\"");
					if (++idx == length) {
						jsonBuf.append("}]");
					} else {
						jsonBuf.append("},");
					}
				}
			}
		}
		return jsonBuf.toString();
	}

	/**
	 * 获取登录用户可见机构树.
	 *
	 * @param orgCode   目标机构代码
	 * @param loginInfo 登录用户信息
	 * @return 机构树字符串
	 */
	@Transactional(readOnly = true)
	public String getUpTree(final String orgCode,
			final LoginInfo loginInfo) {
		final StringBuilder buf = new StringBuilder();
		final String topOrg = loginInfo.getOrgCode();
		final TmOrg entity = (TmOrg) sessionFactory.getCurrentSession()
				.get(TmOrg.class, topOrg);
		buf.append("[{\"id\":\"").append(entity.getOrgCode())
		.append("\",\"text\":\"")
		.append(entity.getOrgName())
		.append("[").append(entity.getOrgCode()).append("]\"");
		// 查询上级机构为orgCode的机构
		this.getChildren(topOrg, orgCode, buf);
		buf.append("}]");
		return buf.toString();
	}

	/**
	 * 逐级获取机构树.
	 *
	 * @param upOrg   上级机构代码
	 * @param orgCode 目标机构代码
	 * @param buf     机构树字符串缓存
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	private void getChildren(final String upOrg,
			final String orgCode,
			final StringBuilder buf) {
		final Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(TmOrg.class);
		criteria.add(Restrictions.eq("upOrg", upOrg));
		final List<TmOrg> results = criteria
				.addOrder(Order.desc("orgCode")).list();
		if (results.size() > 0) {
			buf.append(",\"children\":[");
			final int length = results.size();
			int idx = 0;
			for (final TmOrg TmOrg : results) {
				buf.append("{\"id\":\"").append(TmOrg.getOrgCode())
				.append("\",\"text\":\"")
				.append(TmOrg.getOrgName())
				.append("[").append(TmOrg.getOrgCode())
				.append("]\"");
				if (!cacheProxyFactory.getCacheValue(TmOrgRelaCache.class,
						TmOrg.getOrgCode()).contains(orgCode)) {
					buf.append(",\"state\":\"closed\"");
				}
				getChildren(TmOrg.getOrgCode(), orgCode, buf);
				if (++idx == length) {
					buf.append("}]");
				} else {
					buf.append("},");
				}
			}
		}
	}

	/**
	 * 判断机构[orgcode]是否隶属(包含自己)于[uporg].
	 *
	 * @param orgCode 机构代码
	 * @param upOrg   上级机构
	 * @return 是否隶属机构
	 */
	public boolean isBranchOrSelf(final String orgCode,
			final String upOrg) {
		if (StringUtils.equals(orgCode, upOrg)) {
			return true;
		}
		final Set<String> children = cacheProxyFactory.getCacheValue(TmOrgRelaCache.class, upOrg);
		if (children != null && children.contains(orgCode)) {
			return true;
		}
		return false;
	}

}
