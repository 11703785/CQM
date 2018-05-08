package com.platform.application.sysmanage.org;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.org.bean.TmOrg;

@SuppressWarnings("serial")
public class TmOrgDto extends AbstractDto {
	/**
	 * 机构代码.
	 */
	@NotBlank(message = "机构代码不允许为空")
	@Size(max = 14, message = "机构代码长度不能超过14")
	private String orgCode;

	/**
	 * 机构名称.
	 */
	@NotBlank(message = "机构名称不允许为空")
	private String orgName;

	/**
	 * 上级机构.
	 */
	@Size(max = 14, message = "上级机构长度不能超过14")
	private String upOrg;

	/**
	 * 个人机构代码.
	 */
	private String pcOrgCode;

	/**
	 * 企业机构代码.
	 */
	private String ecOrgCode;

	/**
	 * 机构状态.
	 */
	private String status;

	/**
	 * 机构类型.
	 */
	private String orgType;

	/**
	 * 所在地区.
	 */
	private String areaCode;

	/**
	 * 说明.
	 */
	@Size(max = 255, message = "说明长度不能超过255")
	private String remark;

	/**
	 * 创建用户.
	 */
	private String creator;

	/**
	 * 创建时间.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIMEZONE_BEIJING)
	private Date createTime;

	/**
	 * 创建起始日.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startCreateTime;

	/**
	 * 创建结束日.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endCreateTime;

	/**
	 * 机构角色集合代码拼接字符串.
	 */
	private String orgRoles;

	/**
	 * 机构角色集合.
	 */
	private Set<String> tmRoles = new HashSet<String>(0);

	/**
	 * 获取机构代码.
	 *
	 * @return 机构代码.
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * 设置机构代码.
	 *
	 * @param orgCodeVal 机构代码
	 */
	public void setOrgCode(final String orgCodeVal) {
		this.orgCode = orgCodeVal;
	}

	/**
	 * 获取机构名称.
	 *
	 * @return 机构名称.
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * 设置机构名称.
	 *
	 * @param orgNameVal 机构名称
	 */
	public void setOrgName(final String orgNameVal) {
		setOperName(orgNameVal + "[" + getOrgCode() + "]");
		this.orgName = orgNameVal;
	}

	/**
	 * 获取上级机构.
	 *
	 * @return 上级机构.
	 */
	public String getUpOrg() {
		return this.upOrg;
	}

	/**
	 * 设置上级机构.
	 *
	 * @param upOrgVal 上级机构
	 */
	public void setUpOrg(final String upOrgVal) {
		this.upOrg = upOrgVal;
	}

	/**
	 * 获取个人机构代码.
	 *
	 * @return 个人机构代码.
	 */
	public String getPcOrgCode() {
		return this.pcOrgCode;
	}

	/**
	 * 设置个人机构代码.
	 *
	 * @param pcOrgCodeVal 个人机构代码
	 */
	public void setPcOrgCode(final String pcOrgCodeVal) {
		this.pcOrgCode = pcOrgCodeVal;
	}

	/**
	 * 获取企业机构代码.
	 *
	 * @return 企业机构代码.
	 */
	public String getEcOrgCode() {
		return this.ecOrgCode;
	}

	/**
	 * 设置企业机构代码.
	 *
	 * @param ecOrgCodeVal 企业机构代码
	 */
	public void setEcOrgCode(final String ecOrgCodeVal) {
		this.ecOrgCode = ecOrgCodeVal;
	}

	/**
	 * 获取机构状态.
	 *
	 * @return 机构状态.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置机构状态.
	 *
	 * @param statusVal 机构状态
	 */
	public void setStatus(final String statusVal) {
		this.status = statusVal;
	}

	/**
	 * 获取机构类型.
	 * @return 机构类型
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * 设置机构类型.
	 * @param orgType
	 */
	public void setOrgType(final String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 获取所在地区.
	 *
	 * @return 所在地区.
	 */
	public String getAreaCode() {
		return this.areaCode;
	}

	/**
	 * 设置所在地区.
	 *
	 * @param areaCodeVal 所在地区
	 */
	public void setAreaCode(final String areaCodeVal) {
		this.areaCode = areaCodeVal;
	}



	/**
	 * 获取说明.
	 *
	 * @return 说明.
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置说明.
	 *
	 * @param remarkVal 说明
	 */
	public void setRemark(final String remarkVal) {
		this.remark = remarkVal;
	}



	/**
	 * 获取创建用户.
	 *
	 * @return 创建用户.
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * 设置创建用户.
	 *
	 * @param creatorVal 创建用户
	 */
	public void setCreator(final String creatorVal) {
		this.creator = creatorVal;
	}

	/**
	 * 获取创建时间.
	 *
	 * @return 创建时间.
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
	 * 获取创建起始日.
	 *
	 * @return 创建起始日
	 */
	public Date getStartCreateTime() {
		return this.startCreateTime;
	}

	/**
	 * 设置创建起始日.
	 *
	 * @param startCreateTimeVal 创建起始日
	 */
	public void setStartCreateTime(final Date startCreateTimeVal) {
		this.startCreateTime = startCreateTimeVal;
	}

	/**
	 * 设置创建结束日.
	 *
	 * @return 创建结束日
	 */
	public Date getEndCreateTime() {
		return this.endCreateTime;
	}

	/**
	 * 设置创建结束日.
	 *
	 * @param endCreateTimeVal 创建结束日
	 */
	public void setEndCreateTime(final Date endCreateTimeVal) {
		this.endCreateTime = endCreateTimeVal;
	}

	/**
	 * 获取机构角色拼接字符串.
	 *
	 * @return 机构角色集合代码拼接字符串
	 */
	public String getOrgRoles() {
		return this.orgRoles;
	}

	/**
	 * 设置获取机构角色拼接字符串.
	 *
	 * @param orgRolesVal 机构角色集合代码拼接字符串
	 */
	public void setOrgRoles(final String orgRolesVal) {
		this.orgRoles = orgRolesVal;
	}

	/**
	 * 获取机构角色集合.
	 *
	 * @return 机构角色集合
	 */
	public Set<String> getTmRoles() {
		return this.tmRoles;
	}

	/**
	 * 设置机构角色集合.
	 *
	 * @param tmRolesVal 机构角色集合
	 */
	public void setTmRoles(final Set<String> tmRolesVal) {
		this.tmRoles = tmRolesVal;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("orgCode").append("='").append(getOrgCode()).append("' ");
		buffer.append("orgName").append("='").append(getOrgName()).append("' ");
		buffer.append("upOrg").append("='").append(getUpOrg()).append("' ");
		buffer.append("pcOrgCode").append("='").append(getPcOrgCode())
		.append("' ");
		buffer.append("ecOrgCode").append("='").append(getEcOrgCode())
		.append("' ");
		buffer.append("status").append("='").append(getStatus()).append("' ");
		buffer.append("orgType").append("='").append(getOrgType()).append("' ");
		buffer.append("areaCode").append("='").append(getAreaCode())
		.append("' ");
		buffer.append("remark").append("='").append(getRemark()).append("' ");
		buffer.append("creator").append("='").append(getCreator()).append("' ");
		buffer.append("createTime").append("='").append(getCreateTime())
		.append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换实体对象.
	 *
	 * @return 机构实体对象
	 */
	public TmOrg convertEntity() {
		TmOrg entity = new TmOrg();
		entity.setOrgCode(this.orgCode);
		entity.setOrgName(this.orgName);
		entity.setUpOrg(this.upOrg);
		entity.setPcOrgCode(this.pcOrgCode);
		entity.setEcOrgCode(this.ecOrgCode);
		entity.setStatus(this.status);
		entity.setOrgType(orgType);
		entity.setAreaCode(this.areaCode);
		entity.setRemark(this.remark);
		entity.setCreator(this.creator);
		entity.setCreateTime(this.createTime);
		return entity;
	}
}
