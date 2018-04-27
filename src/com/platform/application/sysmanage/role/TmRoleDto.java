package com.platform.application.sysmanage.role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.role.bean.TmRole;

@SuppressWarnings("serial")
public class TmRoleDto extends AbstractDto {
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
	private Date creatTime;

	/**
	 * 角色类型.
	 */
	private String type;

	/**
	 * 权限集合.
	 */
	private Set<String> tmRights = new HashSet<String>(0);

	/**
	 * 页面上选中的权限列表集合,多个权限逗号分隔.
	 */
	private String rights;

	/**
	 * 获取角色代码.
	 *
	 * @return 角色代码.
	 */
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
	public Date getCreatTime() {
		return this.creatTime;
	}

	/**
	 * 设置创建时间.
	 *
	 * @param createTimeVal 创建时间
	 */
	public void setCreatTime(final Date createTimeVal) {
		this.creatTime = createTimeVal;
	}


	/**
	 * 获取角色类型.
	 *
	 * @return 角色类型
	 */
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
	public Set<String> getTmRights() {
		return this.tmRights;
	}

	/**
	 * 设置角色包含权限集合.
	 *
	 * @param tmRightsVal 权限集合
	 */
	public void setTmRights(final Set<String> tmRightsVal) {
		this.tmRights = tmRightsVal;
	}

	/**
	 * 获取角色包含权限集合.
	 *
	 * @return 权限集合
	 */
	public String getRights() {
		return rights;
	}

	/**
	 * 设置角色包含权限集合.
	 *
	 * @param rightsVal 权限集合
	 */
	public void setRights(final String rightsVal) {
		this.rights = rightsVal;
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
		buffer.append("creator").append("='").append(getCreator()).append("' ");
		buffer.append("type").append("='").append(getType())
		.append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换实体对象.
	 *
	 * @return 平台角色实体对象
	 */
	public TmRole convertEntity() {
		final TmRole entity = new TmRole();
		entity.setRoleCode(this.roleCode);
		entity.setRoleName(this.roleName);
		entity.setRoleDesc(this.roleDesc);
		entity.setStatus(this.status);
		entity.setCreator(this.creator);
		entity.setType(this.type);
		entity.setRights(this.rights);
		return entity;
	}
}
