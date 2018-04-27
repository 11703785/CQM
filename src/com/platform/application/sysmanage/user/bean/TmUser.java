package com.platform.application.sysmanage.user.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.platform.application.sysmanage.role.TmRoleDto;
import com.platform.application.sysmanage.role.bean.TmRole;
import com.platform.application.sysmanage.user.UserDto;

/**
 * 用户信息实体类.
 *
 */
@Entity
@Table(name = "TM_USER")
@SuppressWarnings("serial")
public class TmUser implements java.io.Serializable {
	/**
	 * 用户ID.
	 */
	private String userId;

	/**
	 * 所属机构.
	 */
	private String orgCode;

	/**
	 * 用户名称.
	 */
	private String name;

	/**
	 * 用户类型.
	 */
	private String type;

	/**
	 * 用户密码.
	 */
	private String userPwd;

	/**
	 * 电话.
	 */
	private String telephone;

	/**
	 * 邮箱.
	 */
	private String email;

	/**
	 * 用户描述.
	 */
	private String userDesc;

	/**
	 * 最后登录时间.
	 */
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
	private Date createTime;

	/**
	 * 非数据库字段 页面选中的权限列表.
	 */
	private String roles;

	/**
	 * 获取用户ID.
	 *
	 * @return 用户ID
	 */
	@Id
	@Column(name = "USERID", unique = true, nullable = false)
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 用户角色集合.
	 */
	private Set<TmRole> roleEntities;

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
	@Column(name = "ORGCODE", nullable = false)
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
	@Column(name = "NAME", nullable = false)
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
	 * 获取用户类型.
	 *
	 * @return 用户类型
	 */
	@Column(name = "TYPE", nullable = false)
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
	 * 获取用户密码.
	 *
	 * @return 用户密码
	 */
	@Column(name = "USERPWD", nullable = false)
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
	@Column(name = "TELEPHONE")
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
	@Column(name = "EMAIL")
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
	@Column(name = "USERDESC")
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTLOGONTIME")
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
	@Column(name = "STATUS", nullable = false)
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
	@Column(name = "CREATOR", nullable = false)
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATTIME", nullable = false)
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
	 * 获取用户角色集合.
	 *
	 * @return 用户角色集合
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tm_userrole",
	joinColumns = {@JoinColumn(name = "userId")},
	inverseJoinColumns = {@JoinColumn(name = "rolecode")})
	public Set<TmRole> getRoleEntities() {
		return this.roleEntities;
	}

	/**
	 * 设置用户角色集合.
	 *
	 * @param roleEntitiesVal 用户角色集合
	 */
	public void setRoleEntities(final Set<TmRole> roleEntitiesVal) {
		this.roleEntities = roleEntitiesVal;
	}

	/**
	 * 添加用户角色.
	 *
	 * @param role 角色
	 */
	public void addTmRole(final TmRole role) {
		if (this.roleEntities == null) {
			this.roleEntities = new HashSet<TmRole>(0);
		}
		this.roleEntities.add(role);
	}

	/**
	 * 获取用户角色集合字符串.
	 *
	 * @return 用户角色集合字符串
	 */
	@Transient
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


	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("userId").append("='").append(getUserId()).append("' ");
		buffer.append("tmOrg").append("='").append(getOrgCode()).append("' ");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("telephone").append("='").append(getTelephone())
		.append("' ");
		buffer.append("email").append("='").append(getEmail()).append("' ");
		buffer.append("userDesc").append("='").append(getUserDesc())
		.append("' ");
		buffer.append("lastLogonTime").append("='").append(getLastLogonTime())
		.append("' ");
		buffer.append("status").append("='").append(getStatus()).append("' ");
		buffer.append("creator").append("='").append(getCreator()).append("' ");
		buffer.append("createTime").append("='").append(getCreateTime())
		.append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换交互对象.
	 *
	 * @return 系统用户交互对象
	 */
	public UserDto convertDto() {
		UserDto dto = new UserDto();
		dto.setUserId(this.userId);
		dto.setOrgCode(this.orgCode);
		dto.setName(this.name);
		dto.setType(type);
		dto.setUserPwd(this.userPwd);
		dto.setTelephone(this.telephone);
		dto.setEmail(this.email);
		dto.setUserDesc(this.userDesc);
		dto.setLastLogonTime(this.lastLogonTime);
		dto.setStatus(this.status);
		dto.setCreator(this.creator);
		dto.setCreateTime(this.createTime);
		return dto;
	}

	/**
	 * 级联转换交互对象.
	 *
	 * @return 系统用户交互对象
	 */
	public UserDto cascadeDto() {
		UserDto dto = this.convertDto();
		Set<TmRoleDto> roleSet = new HashSet<TmRoleDto>(0);
		for (TmRole role : this.roleEntities) {
			roleSet.add(role.convertDto());
		}
		dto.setTmRoles(roleSet);
		return dto;
	}
}
