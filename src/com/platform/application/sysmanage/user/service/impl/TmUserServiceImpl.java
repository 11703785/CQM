package com.platform.application.sysmanage.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.sysmanage.org.OrgDto;
import com.platform.application.sysmanage.org.cache.TmOrgCache;
import com.platform.application.sysmanage.role.bean.TmRole;
import com.platform.application.sysmanage.service.AbstractService;
import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.bean.TmUser;
import com.platform.application.sysmanage.user.service.TmUserService;

@Service
@Transactional
public class TmUserServiceImpl extends AbstractService implements TmUserService {

	private static final Logger LOGGER = Logger.getLogger(TmUserServiceImpl.class);

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	@Value("${INIT_PWD}")
	private String defaultPwd;
	/**
	 * 保存用户信息
	 * @param tranInst 用户实体类对象
	 * @return 保存后交互对象
	 */
	@Override
	public UserDto persist(final TmUser tranInst) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始新增用户[" + tranInst + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			// 权限不为空,保存权限
			if (tranInst.getRoles() != null && StringUtils.isNotBlank(tranInst.getRoles())) {
				final String[] roleIds = tranInst.getRoles().split(",");
				for (final String roleCode : roleIds) {
					final TmRole role = (TmRole) session.load(TmRole.class, roleCode);
					tranInst.addTmRole(role);
				}
			}
			tranInst.setUserPwd(encoder.encode(defaultPwd));
			tranInst.setCreateTime(new Date(System.currentTimeMillis()));
			session.persist(tranInst);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增用户成功[" + tranInst + "]");
			}
			return tranInst.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("新增用户失败 [" + tranInst + "]:" + re.getMessage(), re);
			throw re;
		}
	}
	/**
	 * 更新用户信息
	 * @param tranInst 用户实体类对象
	 * @return 更新后交互对象
	 */
	@Override
	@Transactional
	public UserDto update(final TmUser tranInst) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在修改 " + tranInst.toString());
		}
		try {
			sessionFactory.getCurrentSession().clear();
			sessionFactory.getCurrentSession().update(tranInst);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改成功 " + tranInst.toString());
			}
			return tranInst.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("修改失败 " + tranInst.toString(), re);
			throw re;
		}
	}
	/**
	 * 删除用户信息
	 * @param tranInst 用户实体类对象
	 * @return 删除后交互对象
	 */
	@Override
	@Transactional
	public UserDto delete(final TmUser tranInst) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在删除 " + tranInst.toString());
		}
		try {
			sessionFactory.getCurrentSession().delete(tranInst);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("删除成功 " + tranInst.toString());
			}
			return tranInst.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("删除失败 " + tranInst.toString(), re);
			throw re;
		}
	}
	/**
	 * 根据条件查找用户信息
	 * @param instance
	 * @return
	 */
	@Override
	@Transactional
	public List<UserDto> findFingerInfo(final UserDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在根据条件:" + dto.toString() + "查询用户信息");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TmUser.class);
			addCondition(criteria, dto);
			final List<TmUser> list = criteria.list();
			List<UserDto> result = new ArrayList<UserDto>();
			if (list != null && list.size() > 0) {
				for (TmUser temp : list) {
					result.add(temp.convertDto());
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询成功");
			}
			return result;
		} catch (final RuntimeException re) {
			LOGGER.error("查询失败 ", re);
			throw re;
		}
	}

	/**
	 * 通过用户ID获取用户信息,不进行级联获取
	 * @param userId 用户标识
	 * @return 用户交互对象
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDto findById(final String userId) {
		return this.findById(userId, false);
	}

	/**
	 * 通过用户ID获取用户信息
	 * @param userId 用户标识
	 * @return 用户交互对象
	 */
	@Override
	public UserDto findById(final String userId, final boolean cascade) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始获取用户信息,用户标识[" + userId + "]");
		}
		try {
			UserDto dto = null;
			final TmUser instance = (TmUser) sessionFactory.getCurrentSession().get(TmUser.class, userId);
			if (instance != null) {
				if (cascade) {
					dto = instance.cascadeDto();
				} else {
					dto = instance.convertDto();
				}
				final OrgDto org = cacheProxyFactory.getCacheValue(TmOrgCache.class, instance.getOrgCode());
				dto.setOrgName(org.getOrgName());
			}
			if (LOGGER.isDebugEnabled()) {
				if (instance == null) {
					LOGGER.debug("获取用户信息失败， 不存在用户标识为[" + userId + "]的用户信息");
				} else {
					LOGGER.debug("获取用户信息成功[" + instance.toString() + "]");
				}
			}
			return dto;
		} catch (final RuntimeException re) {
			LOGGER.error("获取用户[" + userId + "]信息失败," + re.getMessage(), re);
			throw re;
		}
	}
	/**
	 * 修改用户拥有的角色信息.
	 *
	 * @param dto
	 *            用户交互实体对象
	 * @return 修改后用户交互实体对象
	 */
	@Override
	@SuppressWarnings("unused")
	public UserDto updateRole(final UserDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始修改用户拥有的角色信息[" + dto + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TmUser user = (TmUser) session.load(TmUser.class, dto.getUserId());
			// 清除用户角色信息
			user.getRoleEntities().clear();
			if (StringUtils.isNotBlank(dto.getRoles())) {
				// 跟新用户角色信息
				final Set<TmRole> roleEntities = user.getRoleEntities();
				final String[] roles = dto.getRoles().split(",");
				for (final String roleCode : roles) {
					// 查找角色相关联的权限信息
					final TmRole roleEntity = (TmRole) session.load(TmRole.class, roleCode);
					roleEntities.add(roleEntity);
				}
			}
			session.update(user);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改用户拥有的角色信息成功[" + user + "]");
			}
			return user.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("修改用户拥有的角色信息失败[" + dto + "]:" + re.getMessage(), re);
			throw re;
		}
	}
	/**
	 * 生成查询条件
	 * @param criteria
	 * @param dto 用户信息传输类
	 * @return
	 */
	private Criteria addCondition(final Criteria criteria, final UserDto dto) {
		if(StringUtils.isNotBlank(dto.getUserId())){
			criteria.add(Restrictions.eq("userId", dto.getUserId()));
		}
		if(StringUtils.isNotBlank(dto.getOrgCode())){
			criteria.add(Restrictions.eq("orgCode", dto.getOrgCode()));
		}
		if(StringUtils.isNotBlank(dto.getName())){
			criteria.add(Restrictions.eq("name", dto.getName()));
		}
		if(StringUtils.isNotBlank(dto.getTelephone())){
			criteria.add(Restrictions.eq("telephone", dto.getTelephone()));
		}
		if(StringUtils.isNotBlank(dto.getEmail())){
			criteria.add(Restrictions.eq("email", dto.getEmail()));
		}
		if(StringUtils.isNotBlank(dto.getUserDesc())){
			criteria.add(Restrictions.eq("userDesc", dto.getUserDesc()));
		}
		if(dto.getLastLogonTime()!=null){
			criteria.add(Restrictions.eq("lastLogonTime", dto.getLastLogonTime()));
		}
		if(StringUtils.isNotBlank(dto.getStatus())){
			criteria.add(Restrictions.eq("status", dto.getStatus()));
		}
		if(StringUtils.isNotBlank(dto.getCreator())){
			criteria.add(Restrictions.eq("creator", dto.getCreator()));
		}
		if(dto.getCreateTime()!=null){
			criteria.add(Restrictions.eq("createTime", dto.getCreateTime()));
		}
		if(StringUtils.isNotBlank(dto.getRoles())){
			criteria.add(Restrictions.eq("roles", dto.getRoles()));
		}
		return criteria;
	}
}
