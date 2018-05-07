package com.platform.application.sysmanage.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
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
import com.platform.application.sysmanage.org.TmOrgDto;
import com.platform.application.sysmanage.org.bean.TmOrg;
import com.platform.application.sysmanage.org.bean.TmOrgRela;
import com.platform.application.sysmanage.org.cache.TmOrgCache;
import com.platform.application.sysmanage.org.cache.TmOrgRelaCache;
import com.platform.application.sysmanage.org.service.TmOrgService;
import com.platform.application.sysmanage.right.TmRightTreeDto;
import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.role.service.TmRoleService;
import com.platform.application.sysmanage.service.AbstractService;
import com.platform.application.utils.CacheConverterUtils;

/**
 * 机构服务类.
 *
 */
@Service
@Transactional
public class TmOrgSerivceImpl extends AbstractService implements TmOrgService {

	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmOrgSerivceImpl.class);

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	@Autowired
	private TmRoleService roleService;

	@Override
	public TmOrgDto findById(final String orgCode) {
		return this.findById(orgCode, false);
	}

	@Override
	public TmOrgDto persist(final TmOrgDto entity) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在保存 [" + entity.toString() + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			String error = "";
			if (StringUtils.isBlank(entity.getUpOrg())) {
				error = "新增机构上级机构不能为空！";
			}
			if (StringUtils.isBlank(entity.getUpOrg()) && cacheProxyFactory.getCacheValue(TmOrgCache.class, entity.getUpOrg()) == null) {
				error = "新增机构上级机构不存在！";
			}
			if (StringUtils.isBlank(entity.getUpOrg()) && cacheProxyFactory.getCacheValue(TmOrgCache.class, entity.getOrgCode()) != null) {
				error = "新增机构代码已存在!";
			}
			if (StringUtils.isNotBlank(error)) {
				throw new Exception(error);
			}
			entity.setCreateTime(new Date());
			//这是停用启用时间
			/*if (ManageModelConstants.DIC_ORG_STATUS_START
					.equals(entity.getStatus())) {
				entity.setRecordStartTime(entity.getCreateTime());
			} else {
				entity.setRecordStopTime(entity.getCreateTime());
			}*/
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
	@Override
	public TmOrgDto update(final TmOrgDto dto, final boolean isSelf) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在修改 [" + dto.toString() + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TmOrg org = (TmOrg) session.load(TmOrg.class, dto.getOrgCode());
			if (!isSelf) {
				if (this.isBranchOrSelf(dto.getUpOrg(), dto.getOrgCode())) {
					throw new Exception("不能将上级机构更新为本机构及其所辖机构！");
				}
				org.setUpOrg(dto.getUpOrg());
			}
			org.setAreaCode(dto.getAreaCode());
			org.setOrgName(dto.getOrgName());
			org.setPcOrgCode(dto.getPcOrgCode());
			org.setEcOrgCode(dto.getEcOrgCode());
			org.setOrgType(dto.getOrgType());
			org.setRemark(dto.getRemark());
			session.update(org);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改成功[" + org.toString() + "]");
			}
			cacheProxyFactory.evictCacheValue(TmOrgCache.class, dto.getOrgCode());
			cacheProxyFactory.evictCacheAllValue(TmOrgRelaCache.class);
			return org.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("修改失败 [" + dto.toString() + "]", re);
			throw re;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TmOrgDto findById(final String orgCode, final boolean cascade) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始获取机构信息,机构代码[" + orgCode + "]");
		}
		try {
			TmOrgDto dto = null;
			final TmOrg instance = (TmOrg) sessionFactory.getCurrentSession().get(TmOrg.class, orgCode);
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

	@Override
	@Transactional(readOnly = true)
	public PageResponse<TmOrgDto> findByDto(final TmOrgDto dto, final String loginOrgCode) {
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
				criteria.add(Restrictions.in("orgCode", CacheConverterUtils.getBranchAndSelf(cacheProxyFactory, loginOrgCode)));
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
			final List<TmOrgDto> dtos = new ArrayList<TmOrgDto>();
			for (final TmOrg u : results) {
				dtos.add(u.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询成功 " + results.toString());
			}
			return new PageResponse<TmOrgDto>(true, total, page, this.pageSize,
					dtos);
		} catch (final RuntimeException re) {
			LOGGER.error("查询失败 条件 " + dto.toString(), re);
			return new PageResponse<TmOrgDto>(false, "查询失败!");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getTree(final String orgCodeValue, final LoginInfo loginInfo) {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria criteria = session.createCriteria(TmOrg.class);
		criteria.add(Restrictions.eq("status", "0"));
		String orgCode = orgCodeValue;
		final StringBuilder jsonBuf = new StringBuilder();
		if (StringUtils.isBlank(orgCode)) {
			orgCode = loginInfo.getOrgCode();
			final TmOrg entity = (TmOrg) session.get(TmOrg.class, orgCode);
			jsonBuf.append("[{\"id\":\"")
			.append(entity.getOrgCode())
			.append("\",\"text\":\"")
			.append(entity.getOrgName())
			.append("[")
			.append(entity.getOrgCode())
			.append("]\"");
			// 查询上级机构为orgCode的机构
			criteria.add(Restrictions.eq("upOrg", orgCode));
			final List<TmOrg> results = criteria.addOrder(Order.desc("orgCode")).list();
			if (results.size() > 0) {
				final int length = results.size();
				int idx = 0;
				jsonBuf.append(",\"children\":[");
				for (final TmOrg orgEntity : results) {
					jsonBuf.append("{\"id\":\"")
					.append(orgEntity.getOrgCode())
					.append("\",\"text\":\"")
					.append(orgEntity.getOrgName())
					.append("[")
					.append(orgEntity.getOrgCode())
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
			final List<TmOrg> results = criteria.addOrder(Order.desc("orgCode")).list();
			if (results.size() > 0) {
				final int length = results.size();
				int idx = 0;
				jsonBuf.append("[");
				for (final TmOrg orgEntity : results) {
					jsonBuf.append("{\"id\":\"")
					.append(orgEntity.getOrgCode())
					.append("\",\"text\":\"")
					.append(orgEntity.getOrgName())
					.append("[")
					.append(orgEntity.getOrgCode())
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

	@Override
	@Transactional(readOnly = true)
	public String getUpTree(final String orgCode, final LoginInfo loginInfo) {
		final StringBuilder buf = new StringBuilder();
		final String topOrg = loginInfo.getOrgCode();
		final TmOrg entity = (TmOrg) sessionFactory.getCurrentSession().get(TmOrg.class, topOrg);
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
	private void getChildren(final String upOrg, final String orgCode, final StringBuilder buf) {
		final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TmOrg.class);
		criteria.add(Restrictions.eq("upOrg", upOrg));
		final List<TmOrg> results = criteria.addOrder(Order.desc("orgCode")).list();
		if (results.size() > 0) {
			buf.append(",\"children\":[");
			final int length = results.size();
			int idx = 0;
			for (final TmOrg orgEntity : results) {
				buf.append("{\"id\":\"").append(orgEntity.getOrgCode())
				.append("\",\"text\":\"")
				.append(orgEntity.getOrgName())
				.append("[").append(orgEntity.getOrgCode())
				.append("]\"");
				if (!cacheProxyFactory.getCacheValue(TmOrgRelaCache.class, orgEntity.getOrgCode()).contains(orgCode)) {
					buf.append(",\"state\":\"closed\"");
				}
				getChildren(orgEntity.getOrgCode(), orgCode, buf);
				if (++idx == length) {
					buf.append("}]");
				} else {
					buf.append("},");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TmOrgRela> selOrgAndUpOrg() {
		try {
			return sessionFactory.getCurrentSession().createCriteria(TmOrgRela.class).list();
		} catch (final HibernateException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<TmRightTreeDto> selRoleTree(final String type) {
		List<TmRightTreeDto> tree = new ArrayList<TmRightTreeDto>();
		TmRoleDto roleDto = new TmRoleDto();
		roleDto.setType(type);
		PageResponse<TmRoleDto> response = roleService.findByDto(roleDto);
		Iterator<TmRoleDto> it = response.getRows().iterator();
		while (it.hasNext()) {
			TmRightTreeDto treedto = new TmRightTreeDto();
			TmRoleDto roledto = it.next();
			treedto.setId(roledto.getRoleCode());
			treedto.setText(roledto.getRoleName());
			tree.add(treedto);
		}
		// 将权限列表排序
		Collections.sort(tree, new Comparator<TmRightTreeDto>() {
			@Override
			public int compare(final TmRightTreeDto o1, final TmRightTreeDto o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		return tree;
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
