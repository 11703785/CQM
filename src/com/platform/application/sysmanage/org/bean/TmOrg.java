package com.platform.application.sysmanage.org.bean;

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

import com.platform.application.sysmanage.org.OrgDto;
import com.platform.application.sysmanage.role.bean.TmRole;

@Entity
@Table(name = "TM_ORG")
@SuppressWarnings("serial")
public class TmOrg implements Serializable {
	/**
	 * 机构代码.
	 */
	private String orgCode;

	/**
	 * 机构名称.
	 */
	private String orgName;

	/**
	 * 上级机构.
	 */
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
	 * 所在地区.
	 */
	private String areaCode;

	/**
	 * 说明.
	 */
	private String remark;


	/**
	 * 创建用户.
	 */
	private String creator;

	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 机构包含角色.
	 */
	private Set<TmRole> roleEntities;

	/**
	 * 获取机构代码.
	 *
	 * @return 机构代码
	 */
	@Id
	@Column(name = "ORGCODE", unique = true, nullable = false)
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
	 * @return 机构名称
	 */
	@Column(name = "ORGNAME", nullable = false)
	public String getOrgName() {
		return this.orgName;
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
	 * 获取上级机构.
	 *
	 * @return 上级机构
	 */
	@Column(name = "UPORG")
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
	 * @return 个人机构代码
	 */
	@Column(name = "PERORGCODE")
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
	 * @return 企业机构代码
	 */
	@Column(name = "ENTORGCODE")
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
	 * @return 机构状态
	 */
	@Column(name = "STATUS", nullable = false)
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
	 * 获取所在地区.
	 *
	 * @return 所在地区
	 */
	@Column(name = "AREACODE")
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
	 * @return 说明
	 */
	@Column(name = "REMARK")
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
	 * @return 创建用户
	 */
	@Column(name = "CREATOR", nullable = false)
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
	 * 获取机构包含角色.
	 *
	 * @return 机构包含角色
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tm_orgrole",
	joinColumns = {@JoinColumn(name = "orgCode")},
	inverseJoinColumns = {@JoinColumn(name = "rolecode")})
	public Set<TmRole> getRoleEntities() {
		return this.roleEntities;
	}

	/**
	 * 设置机构包含角色.
	 *
	 * @param roleEntitiesVal 机构包含角色
	 */
	public void setRoleEntities(final Set<TmRole> roleEntitiesVal) {
		this.roleEntities = roleEntitiesVal;
	}

	/**
	 * 添加机构角色.
	 *
	 * @param role 角色
	 */
	public void addTmRole(final TmRole role) {
		if (this.roleEntities == null) {
			this.roleEntities = new HashSet<TmRole>(0);
		}
		this.roleEntities.add(role);
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
	 * 转换交互对象.
	 *
	 * @return 机构交互对象
	 */
	public OrgDto convertDto() {
		final OrgDto dto = new OrgDto();
		dto.setOrgCode(this.orgCode);
		dto.setOrgName(this.orgName);
		dto.setUpOrg(this.upOrg);
		dto.setPcOrgCode(this.pcOrgCode);
		dto.setEcOrgCode(this.ecOrgCode);
		dto.setStatus(this.status);
		dto.setAreaCode(this.areaCode);
		dto.setRemark(this.remark);
		dto.setCreator(this.creator);
		dto.setCreateTime(this.createTime);
		return dto;
	}

	/**
	 * 级联转换交互对象.
	 *
	 * @return 机构交互对象
	 */
	public OrgDto cascadeDto() {
		final OrgDto dto = convertDto();
		final Set<String> roles = new HashSet<String>(0);
		for (final TmRole role : this.roleEntities) {
			roles.add(role.getRoleCode());
		}
		dto.setTmRoles(roles);
		return dto;
	}
}
