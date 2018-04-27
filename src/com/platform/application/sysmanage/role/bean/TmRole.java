package com.platform.application.sysmanage.role.bean;

import java.io.Serializable;
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

import com.platform.application.sysmanage.right.bean.TmRight;
import com.platform.application.sysmanage.role.TmRoleDto;

/**
 * 平台角色管理实体类.
 */
@Entity
@Table(name = "TM_ROLE")
@SuppressWarnings("serial")
public class TmRole implements Serializable {
	/**
	 * 角色代码.
	 */
	private String roleCode;

	/**
	 * 角色名称.
	 */
	private String roleName;

	/**
	 * 角色描述.
	 */
	private String roleDesc;

	/**
	 * 状态.
	 */
	private String status;

	/**
	 * 启用时间.
	 */
	private Date startTime;

	/**
	 * 停用时间.
	 */
	private Date stopTime;

	/**
	 * 创建人.
	 */
	private String creator;

	/**
	 * 创建时间.
	 */
	private Date createTime;

	/**
	 * 非数据库字段,页面上选中的权限列表集合,多个权限逗号分隔.
	 */
	private String rights;

	/**
	 * 角色类型.
	 */
	private String type;
	/**
	 * 权限集合.
	 */
	private Set<TmRight> rightEntities;

	/**
	 * 获取角色代码.
	 *
	 * @return 角色代码.
	 */
	@Id
	@Column(name = "ROLECODE", unique = true, nullable = false)
	public String getRoleCode() {
		return this.roleCode;
	}

	/**
	 * 设置角色代码.
	 *
	 * @param roleCodeVal 角色代码
	 */
	public void setRoleCode(final String roleCodeVal) {
		this.roleCode = roleCodeVal;
	}

	/**
	 * 获取角色名称.
	 *
	 * @return 角色名称
	 */
	@Column(name = "ROLENAME", nullable = false)
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * 设置角色名称.
	 *
	 * @param roleNameVal 角色名称
	 */
	public void setRoleName(final String roleNameVal) {
		this.roleName = roleNameVal;
	}

	/**
	 * 获取角色描述.
	 *
	 * @return 角色描述
	 */
	@Column(name = "ROLEDESC")
	public String getRoleDesc() {
		return this.roleDesc;
	}

	/**
	 * 设置角色描述.
	 *
	 * @param roleDescVal 角色描述
	 */
	public void setRoleDesc(final String roleDescVal) {
		this.roleDesc = roleDescVal;
	}

	/**
	 * 获取角色状态.
	 *
	 * @return 角色状态
	 */
	@Column(name = "STATUS", nullable = false)
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置角色状态.
	 *
	 * @param statusVal 角色状态
	 */
	public void setStatus(final String statusVal) {
		this.status = statusVal;
	}

	/**
	 * 获取启用时间.
	 *
	 * @return 启用时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME")
	public Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置启用时间.
	 *
	 * @param startTimeVal 启用时间
	 */
	public void setStartTime(final Date startTimeVal) {
		this.startTime = startTimeVal;
	}

	/**
	 * 获取停用时间.
	 *
	 * @return 停用时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STOPTIME")
	public Date getStopTime() {
		return this.stopTime;
	}

	/**
	 * 设置停用时间.
	 *
	 * @param stopTimeVal 停用时间
	 */
	public void setStopTime(final Date stopTimeVal) {
		this.stopTime = stopTimeVal;
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
	 * 获取角色类型.
	 *
	 * @return 角色类型
	 */
	@Column(name = "TYPE", nullable = false)
	public String getType() {
		return type;
	}

	/**
	 * 设置角色类型.
	 *
	 * @param typeVal 角色类型
	 */
	public void setType(final String typeVal) {
		this.type = typeVal;
	}

	/**
	 * 获取角色包含权限集合.
	 *
	 * @return 权限集合
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tm_roleright",
	joinColumns = {@JoinColumn(name = "roleCode")},
	inverseJoinColumns = {@JoinColumn(name = "rightcode")})
	public Set<TmRight> getRightEntities() {
		return this.rightEntities;
	}

	/**
	 * 设置角色包含权限集合.
	 *
	 * @param rightEntitiesVal 权限集合
	 */
	public void setRightEntities(final Set<TmRight> rightEntitiesVal) {
		this.rightEntities = rightEntitiesVal;
	}

	/**
	 * 添加权限到角色.
	 *
	 * @param right 权限实体对象
	 */
	public void addTmRight(final TmRight right) {
		if (this.rightEntities == null) {
			this.rightEntities = new HashSet<TmRight>();
		}
		this.rightEntities.add(right);
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("roleCode").append("='").append(getRoleCode())
		.append("' ");
		buffer.append("roleName").append("='").append(getRoleName())
		.append("' ");
		buffer.append("roleDesc").append("='").append(getRoleDesc())
		.append("' ");
		buffer.append("status").append("='").append(getStatus()).append("' ");
		buffer.append("startTime").append("='").append(getStartTime())
		.append("' ");
		buffer.append("stopTime").append("='").append(getStopTime())
		.append("' ");
		buffer.append("creator").append("='").append(getCreator()).append("' ");
		buffer.append("createTime").append("='").append(getCreateTime())
		.append("' ");
		buffer.append("type").append("='").append(getType())
		.append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换交互对象.
	 *
	 * @return 平台角色交互对象
	 */
	public TmRoleDto convertDto() {
		TmRoleDto dto = new TmRoleDto();
		dto.setRoleCode(this.roleCode);
		dto.setRoleName(this.roleName);
		dto.setRoleDesc(this.roleDesc);
		dto.setStatus(this.status);
		dto.setStartTime(this.startTime);
		dto.setStopTime(this.stopTime);
		dto.setCreator(this.creator);
		dto.setCreatTime(this.createTime);
		dto.setType(this.type);
		dto.setRights(this.rights);
		return dto;
	}

	/**
	 * 级联转换交互对象.
	 *
	 * @return 平台角色交互对象
	 */
	public TmRoleDto cascadeDto() {
		TmRoleDto dto = convertDto();
		Set<String> rightSet = new HashSet<String>(0);
		for (TmRight right : rightEntities) {
			rightSet.add(right.getRightCode());
		}
		dto.setTmRights(rightSet);
		return dto;
	}

	/**
	 * 获取权限列表集合.
	 *
	 * @return 权限列表集合
	 */
	@Transient
	public String getRights() {
		return rights;
	}

	/**
	 * 设置权限列表集合.
	 *
	 * @param rightsVal 权限列表集合
	 */
	public void setRights(final String rightsVal) {
		this.rights = rightsVal;
	}
}
