package com.platform.application.sysmanage.org.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机构关系实体类.
 */
@Entity
@Table(name = "TM_ORG")
@SuppressWarnings("serial")
public class TmOrgRela implements java.io.Serializable {
	/**
	 * 机构代码.
	 */
	private String orgCode;

	/**
	 * 上级机构.
	 */
	private String upOrg;

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

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("orgCode").append("='").append(getOrgCode()).append("' ");
		buffer.append("upOrg").append("='").append(getUpOrg()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}
}

