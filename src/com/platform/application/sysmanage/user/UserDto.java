package com.platform.application.sysmanage.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.user.bean.TmUser;

@SuppressWarnings("serial")
public class UserDto extends AbstractDto implements java.io.Serializable {
	/**
	 * 用户ID.
	 */
	@NotBlank(message = "用户ID不能为空")
	@Size(max = 32, message = "用户ID长度超出")
	private String userId;

	/**
	 * 所属机构.
	 */
	@NotBlank(message = "机构代码不能为空")
	@Size(max = 14, message = "机构代码长度超出")
	private String orgCode;

	/**
	 * 用户名称.
	 */
	@NotBlank(message = "用户名称不能为空")
	@Size(max = 64, message = "用户名称长度超出")
	private String name;

	/**
	 * 用户密码.
	 */
	@Size(min = 6, message = "用户密码不能少于6位")
	private String userPwd;

	/**
	 * 电话.
	 */
	private String telephone;

	/**
	 * 邮箱.
	 */
	@Email(message = "邮箱格式不符合规则")
	private String email;

	/**
	 * 用户描述.
	 */
	private String userDesc;

	/**
	 * 最后登录时间.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIMEZONE_BEIJING)
	private Date lastLogonTime;

	/**
	 * 状态.
	 */

	private String status;

	/**
	 * 创建人.
	 */
	private String creator;

	/**
	 * 创建时间.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIMEZONE_BEIJING)
	private Date createTime;

	/**
	 * 查询开始时间.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date queryStartTime;

	/**
	 * 查询结束日期.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date queryEndTime;

	/**
	 * 用户角色集合.
	 */
	private Set<TmRoleDto> tmRoles = new HashSet<TmRoleDto>(0);
	/**
	 * 用户类型.
	 */
	@NotBlank(message = "用户类型不能为空")
	private String type;

	/**
	 * 查询所用id.
	 */
	private String[] ids;
	/**
	 * 页面选中的角色.
	 */
	private String roles;
	/**
	 * 机构名称.
	 */
	private String orgName;
	/**
	 * 原密码.
	 */
	private String oldPwd;
	/**
	 * 新密码.
	 */
	private String newPwd;
	/**
	 * 确认密码.
	 */
	private String renewPwd;

	/**
	 * 获取用户ID.
	 *
	 * @return 用户ID
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置用户ID.
	 *
	 * @param userIdVal 用户ID
	 */
	public void setUserId(final String userIdVal) {
		this.userId = userIdVal;
	}

	/**
	 * 获取所属机构.
	 *
	 * @return 所属机构
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * 设置所属机构.
	 *
	 * @param orgCodeVal 所属机构
	 */
	public void setOrgCode(final String orgCodeVal) {
		this.orgCode = orgCodeVal;
	}

	/**
	 * 获取用户名称.
	 *
	 * @return 用户名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置用户名称.
	 *
	 * @param nameVal 用户名称
	 */
	public void setName(final String nameVal) {
		this.name = nameVal;
	}

	/**
	 * 获取用户密码.
	 *
	 * @return 用户密码
	 */
	public String getUserPwd() {
		return this.userPwd;
	}

	/**
	 * 设置用户密码.
	 *
	 * @param userPwdVal 用户密码
	 */
	public void setUserPwd(final String userPwdVal) {
		this.userPwd = userPwdVal;
	}

	/**
	 * 获取电话.
	 *
	 * @return 电话
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * 设置电话.
	 *
	 * @param telephoneVal 电话
	 */
	public void setTelephone(final String telephoneVal) {
		this.telephone = telephoneVal;
	}

	/**
	 * 获取邮箱.
	 *
	 * @return 邮箱
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * 设置邮箱.
	 *
	 * @param emailVal 邮箱
	 */
	public void setEmail(final String emailVal) {
		this.email = emailVal;
	}

	/**
	 * 获取用户描述.
	 *
	 * @return 用户描述
	 */
	public String getUserDesc() {
		return this.userDesc;
	}

	/**
	 * 设置用户描述.
	 *
	 * @param userDescVal 用户描述
	 */
	public void setUserDesc(final String userDescVal) {
		this.userDesc = userDescVal;
	}

	/**
	 * 获取最后登录时间.
	 *
	 * @return 最后登录时间
	 */
	public Date getLastLogonTime() {
		return this.lastLogonTime;
	}

	/**
	 * 设置状态.
	 *
	 * @param lastLogonTimeVal 最后登录时间
	 */
	public void setLastLogonTime(final Date lastLogonTimeVal) {
		this.lastLogonTime = lastLogonTimeVal;
	}

	/**
	 * 获取状态.
	 *
	 * @return 状态
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置状态.
	 *
	 * @param statusVal 状态
	 */
	public void setStatus(final String statusVal) {
		this.status = statusVal;
	}

	/**
	 * 获取创建人.
	 *
	 * @return 创建人
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * 设置创建人.
	 *
	 * @param creatorVal 创建人
	 */
	public void setCreator(final String creatorVal) {
		this.creator = creatorVal;
	}

	/**
	 * 获取创建时间.
	 *
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 设置创建时间.
	 *
	 * @param createTimeVal 创建时间
	 */
	public void setCreateTime(final Date createTimeVal) {
		this.createTime = createTimeVal;
	}

	/**
	 * 获取用户类型.
	 *
	 * @return 用户类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置用户类型.
	 *
	 * @param typeVal 用户类型
	 */
	public void setType(final String typeVal) {
		this.type = typeVal;
	}

	/**
	 * 获取用户角色集合.
	 *
	 * @return 用户角色集合
	 */
	public Set<TmRoleDto> getTmRoles() {
		return this.tmRoles;
	}

	/**
	 * 设置用户角色集合.
	 *
	 * @param tmRolesVal 用户角色集合
	 */
	public void setTmRoles(final Set<TmRoleDto> tmRolesVal) {
		this.tmRoles = tmRolesVal;
	}

	/**
	 * 获取查询开始时间.
	 *
	 * @return 查询开始时间
	 */
	public Date getQueryStartTime() {
		return queryStartTime;
	}

	/**
	 * 设置查询开始时间.
	 *
	 * @param queryStartTimeVal 查询开始时间
	 */
	public void setQueryStartTime(final Date queryStartTimeVal) {
		this.queryStartTime = queryStartTimeVal;
	}

	/**
	 * 获取查询结束时间.
	 *
	 * @return 查询结束时间
	 */
	public Date getQueryEndTime() {
		return queryEndTime;
	}

	/**
	 * 设置查询结束时间.
	 *
	 * @param queryEndTimeVal 查询结束时间
	 */
	public void setQueryEndTime(final Date queryEndTimeVal) {
		this.queryEndTime = queryEndTimeVal;
	}

	/**
	 * 获取查询所用id.
	 *
	 * @return 查询所用id
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * 设置查询所用id.
	 *
	 * @param idsVal 查询所用id
	 */
	public void setIds(final String[] idsVal) {
		this.ids = idsVal;
	}

	/**
	 * 获取用户角色集合字符串.
	 *
	 * @return 用户角色集合字符串
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * 设置用户角色集合字符串.
	 *
	 * @param rolesVal 用户角色集合字符串
	 */
	public void setRoles(final String rolesVal) {
		this.roles = rolesVal;
	}

	/**
	 * 获取机构名称.
	 *
	 * @return 机构名称
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置机构名称.
	 *
	 * @param orgNameVal 机构名称
	 */
	public void setOrgName(final String orgNameVal) {
		this.orgName = orgNameVal;
	}

	/**
	 * 获取原密码.
	 *
	 * @return 原密码
	 */
	public String getOldPwd() {
		return oldPwd;
	}

	/**
	 * 设置原密码.
	 *
	 * @param oldPwdVal 原密码
	 */
	public void setOldPwd(final String oldPwdVal) {
		this.oldPwd = oldPwdVal;
	}

	/**
	 * 获取新密码.
	 *
	 * @return 新密码
	 */
	public String getNewPwd() {
		return newPwd;
	}

	/**
	 * 设置新密码.
	 *
	 * @param newPwdVal 新密码
	 */
	public void setNewPwd(final String newPwdVal) {
		this.newPwd = newPwdVal;
	}

	/**
	 * 获取确认密码.
	 *
	 * @return 确认密码
	 */
	public String getRenewPwd() {
		return renewPwd;
	}

	/**
	 * 设置确认密码.
	 *
	 * @param renewPwdVal 确认密码
	 */
	public void setRenewPwd(final String renewPwdVal) {
		this.renewPwd = renewPwdVal;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("userId").append("='").append(getUserId()).append("' ");
		buffer.append("tmOrg").append("='").append(getOrgCode()).append("' ");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("type").append("='").append(getType()).append("' ");
		buffer.append("telephone").append("='").append(getTelephone())
		.append("' ");
		buffer.append("email").append("='").append(getEmail()).append("' ");
		buffer.append("userDesc").append("='").append(getUserDesc())
		.append("' ");
		buffer.append("lastLogonTime").append("='").append(getLastLogonTime())
		.append("' ");
		buffer.append("status").append("='").append(getStatus()).append("' ");
		buffer.append("creator").append("='").append(getCreator()).append("' ");
		buffer.append("createTime").append("='").append(getCreateTime()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换实体对象.
	 *
	 * @return 系统用户实体对象
	 */
	public TmUser convertEntity() {
		TmUser entity = new TmUser();
		entity.setUserId(this.userId);
		entity.setOrgCode(this.orgCode);
		entity.setName(this.name);
		entity.setUserPwd(this.userPwd);
		entity.setType(type);
		entity.setTelephone(this.telephone);
		entity.setEmail(this.email);
		entity.setUserDesc(this.userDesc);
		entity.setLastLogonTime(this.lastLogonTime);
		entity.setStatus(this.status);
		entity.setCreator(this.creator);
		entity.setCreateTime(this.createTime);
		entity.setRoles(this.roles);
		return entity;
	}
}
