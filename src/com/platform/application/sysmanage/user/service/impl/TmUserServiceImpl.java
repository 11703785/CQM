package com.platform.application.sysmanage.user.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.role.bean.TmRole;
import com.platform.application.sysmanage.service.AbstractService;
import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.bean.TmUser;
import com.platform.application.sysmanage.user.service.TmUserService;

@Service
public class TmUserServiceImpl extends AbstractService implements TmUserService {

	private static final Logger LOGGER = Logger.getLogger(TmUserServiceImpl.class);

	@Autowired
	private PasswordEncoder encoder;

	@Value("${INIT_PWD}")
	private String defaultPwd;

	@Override
	public UserDto persist(final TmUser tranInst) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始新增用户[" + tranInst + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
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

	@Override
	public UserDto findById(final String userId, final boolean cascade) {
		return null;
	}

}
