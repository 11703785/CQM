package com.platform.application.sysmanage.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.right.bean.TmRight;
import com.platform.application.sysmanage.role.bean.TmRole;
import com.platform.application.sysmanage.role.cache.TmRoleCache;
import com.platform.application.sysmanage.service.AbstractService;

/**
 * 平台角色服务类.
 *
 */
@Service
@Transactional
public class TmRoleService extends AbstractService {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmRoleService.class);

	/**
	 * 平台缓存服务类.
	 */
	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 新增平台角色信息.
	 *
	 * @param dto 平台角色交互对象
	 * @return 保存后平台角色交互对象
	 * @throws Exception 逻辑处理失败
	 */
	public TmRoleDto persist(final TmRoleDto dto) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始保存平台角色[" + dto + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			// 判断角色是否存在
			if (cacheProxyFactory.getCacheValue(
					TmRoleCache.class, dto.getRoleCode()) != null) {
				throw new Exception("角色代码 [ " + dto.getRoleCode() + " ] 已经存在！");
			}
			final TmRole role = dto.convertEntity();
			role.setCreateTime(new Date());
			if (StringUtils.isNotBlank(dto.getRights())) {
				final String[] rights = dto.getRights().split(",");
				for (final String rightCode : rights) {
					if (StringUtils.isNotBlank(rightCode)) {
						final TmRight TmRight = (TmRight)session.load(TmRight.class, rightCode);
						role.addTmRight(TmRight);
					}
				}
			}
			session.persist(role);
			//            cacheProxyFactory.evictAllOrgRightsDic();
			cacheProxyFactory.evictCacheAllValue(TmRoleCache.class);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("保存平台角色成功[" + role.toString() + "]");
			}
			return role.convertDto();
		} catch (final RuntimeException e) {
			LOGGER.error("保存平台角色失败[" + dto.toString() + "]"
					+ e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 修改平台角色信息.
	 *
	 * @param dto 平台角色交互对象
	 * @return 修改后平台角色交互对象
	 */
	public TmRoleDto update(final TmRoleDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始修改平台角色信息[" + dto.toString() + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TmRole entity = (TmRole)
					session.get(TmRole.class, dto.getRoleCode());
			entity.setRoleName(dto.getRoleName());
			entity.setRoleDesc(dto.getRoleDesc());
			session.update(entity);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改平台角色信息成功[" + entity.toString() + "]");
			}
			return entity.convertDto();
		} catch (final RuntimeException e) {
			LOGGER.error("修改平台角色信息失败[" + dto.toString() + "]"
					+ e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 修改平台角色权限信息.
	 *
	 * @param dto 平台角色交互对象
	 * @return 修改后平台角色交互对象
	 */
	public TmRoleDto updateRight(final TmRoleDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始修改平台角色权限信息[" + dto.toString() + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TmRole entity = (TmRole)
					session.get(TmRole.class, dto.getRoleCode());
			entity.getRightEntities().clear();
			if (StringUtils.isNotBlank(dto.getRights())) {
				final String[] rights = dto.getRights().split(",");
				for (final String right : rights) {
					if (StringUtils.isNotBlank(right)) {
						final TmRight TmRight = (TmRight)session.load(TmRight.class, right);
						entity.addTmRight(TmRight);
					}
				}
			}
			session.update(entity);
			//            cacheProxyFactory.evictAllOrgRightsDic();
			cacheProxyFactory.evictCacheAllValue(TmRoleCache.class);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改平台角色权限信息成功[" + entity.toString() + "]");
			}
			return entity.convertDto();
		} catch (final RuntimeException e) {
			LOGGER.error("修改平台角色权限信息失败[" + dto.toString() + "]:"
					+ e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 通过角色代码获取平台角色信息.
	 *
	 * @param roleCode 角色代码
	 * @param cascade  是否级联
	 * @return 平台角色交互对象
	 */
	@Transactional(readOnly = true)
	public TmRoleDto findById(final String roleCode, final boolean cascade) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始获取平台角色信息,角色代码[" + roleCode + "]");
		}
		try {
			final TmRole instance = (TmRole)
					sessionFactory.getCurrentSession()
					.load(TmRole.class, roleCode);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取平台角色信息成功[" + instance + "]");

			}
			if (instance != null) {
				if (cascade) {
					return instance.cascadeDto();
				} else {
					return instance.convertDto();
				}
			}
			return null;
		} catch (final RuntimeException re) {
			LOGGER.error("获取平台角色信息失败,角色代码[" + roleCode + "]:"
					+ re.getMessage(), re);
			throw re;
		}
	}

	/**
	 * 根据条件查询平台角色列表.
	 *
	 * @param dto 平台角色交互对象
	 * @return 平台角色列表
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<TmRoleDto> findRolesByDto(final TmRoleDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始查询平台角色列表,条件[" + dto + "]");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(TmRole.class);
			if (StringUtils.isNotBlank(dto.getRoleCode())) {
				criteria.add(Restrictions.eq("roleCode", dto.getRoleCode()));
			}
			if (StringUtils.isNotBlank(dto.getRoleName())) {
				criteria.add(Restrictions.like("roleName",
						"%" + dto.getRoleName() + "%"));
			}
			if (StringUtils.isNotBlank(dto.getStatus())) {
				criteria.add(Restrictions.eq("status", dto.getStatus()));
			}
			if (StringUtils.isNotBlank(dto.getCreator())) {
				criteria.add(Restrictions.eq("creator", dto.getCreator()));
			}
			if (StringUtils.isNotBlank(dto.getType())) {
				criteria.add(Restrictions.eq("type", dto.getType()));
			}
			final List<TmRole> results = criteria.list();
			final List<TmRoleDto> dtos = new ArrayList<TmRoleDto>();
			for (final TmRole role : results) {
				dtos.add(role.cascadeDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询平台角色列表条件成功[" + results + "]");
			}
			return dtos;
		} catch (final RuntimeException re) {
			LOGGER.error("查询平台角色列表失败,条件[" + dto + "]:"
					+ re.getMessage(), re);
			throw re;
		}
	}

	/**
	 * 根据条件分页查询平台角色.
	 *
	 * @param dto 平台角色交互对象
	 * @return 平台角色查询结果
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public PageResponse<TmRoleDto> findByDto(final TmRoleDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始查询平台角色列表,条件[" + dto + "]");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(TmRole.class);
			if (StringUtils.isNotBlank(dto.getRoleCode())) {
				criteria.add(Restrictions.like("roleCode", "%" +dto.getRoleCode() + "%"));
			}
			if (StringUtils.isNotBlank(dto.getRoleName())) {
				criteria.add(Restrictions.like("roleName",
						"%" + dto.getRoleName() + "%"));
			}
			if (StringUtils.isNotBlank(dto.getStatus())) {
				criteria.add(Restrictions.eq("status", dto.getStatus()));
			}
			if (StringUtils.isNotBlank(dto.getCreator())) {
				criteria.add(Restrictions.eq("creator", dto.getCreator()));
			}
			if (StringUtils.isNotBlank(dto.getType())) {
				criteria.add(Restrictions.eq("type", dto.getType()));
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
			final List<TmRole> results = criteria
					.setFirstResult((page - 1) * rows)
					.setMaxResults(rows).list();
			final List<TmRoleDto> dtos = new ArrayList<TmRoleDto>();
			for (final TmRole role : results) {
				dtos.add(role.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询平台角色列表条件成功[" + results + "]");
			}
			return new PageResponse<TmRoleDto>(true, total, page, rows, dtos);
		} catch (final RuntimeException re) {
			LOGGER.error("查询平台角色列表失败,条件[" + dto + "]:"
					+ re.getMessage(), re);
			return new PageResponse<TmRoleDto>(false, "查询平台角色信息失败!");
		}
	}

	/**
	 * 启用/停用平台角色.
	 *
	 * @param roleCode   角色代码
	 * @param isStart    启停标识
	 * @param stopReason 停用原因
	 */
	/*public void stopOrStartRole(
			final String roleCode,
			final boolean isStart,
			final String stopReason) {
		if (LOGGER.isDebugEnabled()) {
			if (isStart) {
				LOGGER.debug("开始启用角色[" + roleCode + "]");
			} else {
				LOGGER.debug("开始停用角色[" + roleCode + "][" + stopReason + "]");
			}
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TmRole entity = (TmRole)session.get(TmRole.class, roleCode);
			entity.setStopReason(stopReason);
			if (isStart) {
				entity.setStatus(ManageModelConstants.DIC_ROLE_STATUS_START);
				entity.setStartTime(new Date());
				entity.setStopTime(null);
			} else {
				entity.setStatus(ManageModelConstants.DIC_ROLE_STATUS_STOP);
				entity.setStartTime(null);
				entity.setStopTime(new Date());
			}
			session.update(entity);
			//            cacheProxyFactory.evictAllOrgRightsDic();
			cacheProxyFactory.evictCacheAllValue(RoleCache.class);
			if (LOGGER.isDebugEnabled()) {
				if (isStart) {
					LOGGER.debug("启用角色成功[" + roleCode + "]");
				} else {
					LOGGER.debug("停用角色成功[" + roleCode + "]["
							+ stopReason + "]");
				}
			}
		} catch (final RuntimeException e) {
			if (isStart) {
				LOGGER.error("启用角色失败[" + roleCode + "]:"
						+ e.getMessage(), e);
			} else {
				LOGGER.error("停用角色失败[" + roleCode + "][" + stopReason + "]:"
						+ e.getMessage(), e);
			}
			throw e;
		}
	}*/

}
