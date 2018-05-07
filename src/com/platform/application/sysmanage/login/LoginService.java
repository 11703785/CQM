package com.platform.application.sysmanage.login;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.common.dto.ResultResponse;
import com.platform.application.sysmanage.org.TmOrgDto;
import com.platform.application.sysmanage.org.cache.TmOrgCache;
import com.platform.application.sysmanage.right.TmRightDto;
import com.platform.application.sysmanage.right.cache.TmRightCache;
import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.role.cache.TmRoleCache;
import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.service.TmUserService;

@Service
public class LoginService {

	private static final Logger LOGGER = Logger.getLogger(LoginService.class);

	@Autowired
	private TmUserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 用户登录.
	 * @param userId 用户id
	 * @param userPwd 用户密码
	 * @return ResultResponse<LoginInfo>
	 */
	public ResultResponse<LoginInfo> login(final String userId, final String userPwd) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("用户[" + userId + "]开始登录系统");
		}
		LoginInfo login = null;
		String err = "";
		try {
			final UserDto user = userService.findById(userId, true);
			boolean isChangPwd = false;
			if (user == null) {
				err = "用户或密码错误！";
			} else {
				final boolean flag = encoder.matches(userPwd, user.getUserPwd());
				if(!flag){
					err = "用户或密码错误！";
				}
			}
			if (err.length() > 0) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("用户[" + userId + "]登录系统失败[" + err + "]");
				}
				return new ResultResponse<LoginInfo>(false, err);
			}
			TmOrgDto orgDto = cacheProxyFactory.getCacheValue(TmOrgCache.class, user.getOrgCode());
			if(orgDto != null){
				if(StringUtils.equals("0", orgDto.getStatus())){
					login = new LoginInfo(user.getUserId(), user.getOrgCode(), user.getType(),
							StringUtils.isEmpty(orgDto.getUpOrg()), user.getOrgName(), user.getName());
					login.setRights(getUserRights(user, orgDto));
				}
			} else {
				err = "用户所在机构不存在!";
			}
			/*final OrgDto org = cacheProxyFactory.getCacheValue(OrgCache.class,
					user.getOrgCode());
			top = GetManagerCode.getManagerCode(org.getOrgCode());
			if (StringUtils.isBlank(top)
					&& StringUtils.isNotBlank(org.getUpOrg())) {
				return new ResultResponse<LoginInfo>(false, "错误:用户没有法人机构！");
			}
			if (StringUtils.isNotBlank(top)) {
				final OrgDto instance = cacheProxyFactory
						.getCacheValue(OrgCache.class, top);
				if ("1".equals(instance.getStatus())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("用户[" + userId + "]登录系统失败[ 用户所在法人机构已经停用]");
					}
					return new ResultResponse<LoginInfo>(false,
							"用户所在法人机构已经停用！");
				}
			}
			if ("1".equals(org.getStatus())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("用户[" + userId + "]登录系统失败[ 用户所在机构已经停用]");
				}
				return new ResultResponse<LoginInfo>(false, "用户所在机构已经停用！");
			}
			if ("0".equals(org.getStatus())) {
				login = new LoginInfo(user.getUserId(), user.getOrgCode(),
						user.getType(), StringUtils.isEmpty(org.getUpOrg()),
						top, user.getOrgName());
				ThreadLocalUtils.setCurrentLogInfo(login);
				login.setRights(getUserRights(user, org));
				// 首次登陆，强制修改密码
				if (user.getLastLogonTime() == null) {
					isChangPwd = true;
					// 不是首次登陆，判断多久没有登录过
				} else {
					final int days = getDaysBetween(new Date(),
							user.getLastLogonTime());
					final String validDays = CacheConverterUtils
							.getProfileValue(cacheProxyFactory,
									ManageModelConstants.PROFILE_MODIFY_PWD_INTERVAL);
					final int valid = Integer.parseInt(validDays);
					if (days >= valid) {
						isChangPwd = true;
					}
					if (!isChangPwd) {
						// 判断多久没有修改过密码
						if (user.getPwdChangeDate() != null) {
							final int modifyPwdDays = getDaysBetween(new Date(),
									user.getPwdChangeDate());
							if (modifyPwdDays >= valid) {
								isChangPwd = true;
							}
						}
					}
				}
				if (!isChangPwd) {
					userService.updateLogonTime(user.getUserId());
				}
			}*/
			final ResultResponse<LoginInfo> response = new ResultResponse<LoginInfo>(
					true, login);
			if (isChangPwd) {
				response.setStatusCode("0");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("用户[" + userId + "]完成登录系统");
			}
			return new ResultResponse<LoginInfo>(
					true, login);
		} catch (final RuntimeException re) {
			LOGGER.error("用户登录失败 [" + userId + "]", re);
			return new ResultResponse<LoginInfo>(false, "用户登录失败!");
		}
	}

	/**
	 * 获取用户权限集合.
	 *
	 * @param user 用户信息
	 * @param org  机构信息
	 * @return 权限集合
	 */
	private Set<String> getUserRights(final UserDto user, final TmOrgDto org) {
		Set<String> orgRights = null;
		// 系统配置用户权限，不需要权限列表。
		if ("9".equals(user.getType()) && StringUtils.isEmpty(org.getUpOrg())) {
			return getTopRights();
		}
		final Map<String, TmRoleDto> roleDic = cacheProxyFactory.getCacheAllValue(TmRoleCache.class);
		// 用户管理员
		if ("0".equals(user.getType())) {
			orgRights = new TreeSet<String>();
			for (final TmRoleDto role : user.getTmRoles()) {
				final TmRoleDto roleDto = roleDic.get(role.getRoleCode());
				if ("0".equals(roleDto.getType())
						&& "0".equals(roleDto.getStatus())) {
					for (final String right : roleDto.getTmRights()) {
						if (!orgRights.contains(right)) {
							orgRights.add(right);
						}
					}
				}
			}
			return orgRights;
		}
		// 普通用户
		final Set<String> userRights = new HashSet<String>(0);
		for (final TmRoleDto role : user.getTmRoles()) {
			// 角色未停用
			if ("0".equals(role.getStatus()) && "1".equals(role.getType())) {
				final TmRoleDto roleDto = roleDic.get(role.getRoleCode());
				for (final String right : roleDto.getTmRights()) {
					if (!userRights.contains(right)) {
						userRights.add(right);
					}
				}
			}
		}
		return userRights;
	}

	/**
	 * 获取超级管理员用户权限集合.
	 *
	 * @return 超级管理员用户权限集合
	 */
	private Set<String> getTopRights() {
		final Set<String> orgRights = new TreeSet<String>();
		final Map<String, TmRightDto> rightDtoMap =
				cacheProxyFactory.getCacheAllValue(TmRightCache.class);
		for (final TmRightDto dto : rightDtoMap.values()) {
			if (!"1".equals(dto.getTopAdmin())) {
				orgRights.add(dto.getRightCode());
			}
		}
		return orgRights;
	}
}
