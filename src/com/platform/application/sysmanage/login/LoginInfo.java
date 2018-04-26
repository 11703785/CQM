package com.platform.application.sysmanage.login;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class LoginInfo implements Comparable<LoginInfo>, Serializable {
	/**
	 * 登录信息在Session中对应的属性名.
	 */
	public static final String HTTP_SESSION_LOGININFO = "HTTP_SESSION_LOGININFO";

	/**
	 * 用户ID.
	 */
	private String userId;

	/**
	 * 所属机构.
	 */
	private String orgCode;
	/**
	 * 所属机构名称.
	 */
	private String orgName;

	/**
	 * 用户类型.
	 */
	private String userType;

	/**
	 * 是否顶级机构.
	 */
	private boolean topOrg;

	/**
	 * 用户权限集合.
	 */
	private Set<String> rights = new HashSet<String>(0);

	/**
	 * 构造函数.
	 *
	 * @param userIdVal   用户ID
	 * @param orgCodeVal  所属机构
	 * @param userTypeVal 用户类型
	 * @param isTopOrgVal 是否顶级机构
	 * @param rightsVal   用户权限集合
	 */
	public LoginInfo(final String userIdVal,
			final String orgCodeVal,
			final String userTypeVal,
			final boolean isTopOrgVal,
			final Set<String> rightsVal) {
		this.userId = userIdVal;
		this.orgCode = orgCodeVal;
		this.userType = userTypeVal;
		this.topOrg = isTopOrgVal;
		this.rights = rightsVal;
	}

	/**
	 * 构造函数.
	 *
	 * @param userIdVal   用户ID
	 * @param orgCodeVal  所属机构
	 * @param userTypeVal 用户类型
	 * @param isTopOrgVal 是否顶级机构
	 * @param rightsVal   用户权限集合
	 */
	public LoginInfo(final String userIdVal,
			final String orgCodeVal,
			final String userTypeVal,
			final boolean isTopOrgVal,
			final String orgName) {
		this.userId = userIdVal;
		this.orgCode = orgCodeVal;
		this.userType = userTypeVal;
		this.topOrg = isTopOrgVal;
		this.orgName = orgName;
	}

	/**
	 * 获取用户ID.
	 *
	 * @return 用户ID
	 */
	public  String getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID.
	 *
	 * @param userIdVal 用户ID
	 */
	public  void setUserId(final String userIdVal) {
		this.userId = userIdVal;
	}

	/**
	 * 获取所属机构.
	 *
	 * @return 所属机构
	 */
	public  String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置所属机构.
	 *
	 * @param orgCodeVal 所属机构
	 */
	public  void setOrgCode(final String orgCodeVal) {
		this.orgCode = orgCodeVal;
	}

	/**
	 * 获取用户权限集合.
	 *
	 * @return 用户权限集合
	 */
	public  Set<String> getRights() {
		return rights;
	}

	/**
	 * 设置用户权限集合.
	 *
	 * @param rightsVal 用户权限集合
	 */
	public  void setRights(final Set<String> rightsVal) {
		this.rights = rightsVal;
	}

	/**
	 * 获取用户类型.
	 *
	 * @return 用户类型
	 */
	public  String getUserType() {
		return userType;
	}

	/**
	 * 设置用户类型.
	 *
	 * @param userTypeVal 用户类型
	 */
	public  void setUserType(final String userTypeVal) {
		this.userType = userTypeVal;
	}

	/**
	 * 获取是否顶级机构.
	 *
	 * @return 是否顶级机构
	 */
	public  boolean isTopOrg() {
		return topOrg;
	}

	/**
	 * 设置是否顶级机构.
	 *
	 * @param topOrgVal 是否顶级机构
	 */
	public  void setTopOrg(final boolean topOrgVal) {
		this.topOrg = topOrgVal;
	}

	/**
	 * 获取所属机构名称.
	 *
	 * @return 所属机构名称
	 */
	public  String getOrgName() {
		return orgName;
	}

	/**
	 * 设置所属机构名称.
	 *
	 * @param orgNameVal 所属机构名称
	 */
	public  void setOrgName(final String orgNameVal) {
		this.orgName = orgNameVal;
	}

	/**
	 * 判断是否超级管理员.
	 *
	 * @return 是否超级管理员
	 */
	public  boolean isTopAdmin() {
		if (this.topOrg && "9".equals(this.userType)) {
			return true;
		}
		return false;
	}

	@Override
	public  String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("userId").append("='").append(getUserId()).append("' ");
		buffer.append("orgCode").append("='").append(getOrgCode()).append("' ");
		buffer.append("userType").append("='").append(getUserType())
		.append("' ");
		buffer.append("topOrg").append("='").append(isTopOrg()).append("' ");
		buffer.append("rights").append("='").append(getRights()).append("' ");
		buffer.append("orgName").append("='").append(getRights()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public  int compareTo(final LoginInfo o) {
		int res = -1;
		if (StringUtils.isNotEmpty(this.userId)) {
			res = this.userId.compareTo(o.getUserId());
		}
		if (res == 0 && StringUtils.isNotEmpty(this.orgCode)) {
			res = this.orgCode.compareTo(o.getOrgCode());
		}
		if (res == 0 && StringUtils.isNotEmpty(this.userType)) {
			res = this.userType.compareTo(o.getUserType());
		}
		return res;
	}

	/**
	 * 判断用户是否有对应权限.
	 *
	 * @param rightCode 权限代码
	 * @return 是否有权限
	 */
	public  boolean haveMenuRight(final String rightCode) {
		if (this.rights.contains(rightCode)) {
			return true;
		}
		for (String code : this.rights) {
			if (code.startsWith(rightCode)) {
				return true;
			}
		}
		return false;
	}
}
