package com.platform.application.sysmanage.operlog.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.platform.application.sysmanage.operlog.TmOperateLogDto;

/**
 * 操作日志实体类.
 *
 */
@Entity
@Table(name = "TM_OPERATELOG")
@SuppressWarnings("serial")
public class TmOperateLog implements java.io.Serializable {

	/**
	 * 编号.
	 */
	private Long id;

	/**
	 * 操作人员IP.
	 */
	private String loginIp;

	/**
	 * 操作内容.
	 */
	private String oprInfo;

	/**
	 * 所在机构.
	 */
	private String oprOrgCode;

	/**
	 * 操作时间.
	 */
	private Date oprTime;

	/**
	 * 机构名称.
	 */
	private String orgName;

	/**
	 * 用户标识.
	 */
	private String userId;


	/**
	 * 用户名称.
	 */
	private String userName;

	/**
	 * 获取编号.
	 *
	 * @return 编号
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "com.platform.application.common.hibernate.AutoIdentifierGenerator")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	/**
	 * 获取操作人员IP.
	 *
	 * @return 操作人员IP
	 */
	@Column(name = "LOGINIP", nullable = false)
	public String getLoginIp() {
		return this.loginIp;
	}

	/**
	 * 获取操作内容.
	 *
	 * @return 操作内容
	 */
	@Column(name = "OPRINFO", nullable = false)
	public String getOprInfo() {
		return this.oprInfo;
	}

	/**
	 * 获取所在机构.
	 *
	 * @return 所在机构
	 */
	@Column(name = "OPRORGCODE", nullable = false)
	public String getOprOrgCode() {
		return this.oprOrgCode;
	}

	/**
	 * 获取操作时间.
	 *
	 * @return 操作时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPRTIME", nullable = false)
	public Date getOprTime() {
		return this.oprTime;
	}

	/**
	 * 获取机构名称.
	 * @return 机构名称
	 */
	@Column(name = "ORGNAME")
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 获取用户标识.
	 *
	 * @return 用户标识
	 */
	@Column(name = "USERID", nullable = false)
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 获取用户名称.
	 * @return 用户名称
	 */
	@Column(name = "USERNAME")
	public String getUserName() {
		return userName;
	}


	/**
	 * 设置编号.
	 *
	 * @param idVal 编号
	 */
	public void setId(final Long idVal) {
		this.id = idVal;
	}

	/**
	 * 设置操作人员IP.
	 *
	 * @param loginIpVal 操作人员IP
	 */
	public void setLoginIp(final String loginIpVal) {
		this.loginIp = loginIpVal;
	}

	/**
	 * 设置操作内容.
	 *
	 * @param oprInfoVal 操作内容
	 */
	public void setOprInfo(final String oprInfoVal) {
		this.oprInfo = oprInfoVal;
	}

	/**
	 * 设置所在机构.
	 *
	 * @param oprOrgCodeVal 所在机构
	 */
	public void setOprOrgCode(final String oprOrgCodeVal) {
		this.oprOrgCode = oprOrgCodeVal;
	}

	/**
	 * 设置操作时间.
	 *
	 * @param oprTimeVal 操作时间
	 */
	public void setOprTime(final Date oprTimeVal) {
		this.oprTime = oprTimeVal;
	}

	/**
	 * 设置机构名称.
	 * @param orgName 机构名称
	 */
	public void setOrgName(final String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 设置用户标识.
	 *
	 * @param userIdVal 用户标识
	 */
	public void setUserId(final String userIdVal) {
		this.userId = userIdVal;
	}

	/**
	 * 设置用户名称.
	 * @param userName 用户名称
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("id").append("='").append(getId()).append("' ");
		buffer.append("userId").append("='").append(getUserId()).append("' ");
		buffer.append("userName").append("='").append(getUserName()).append("' ");
		buffer.append("oprInfo").append("='").append(getOprInfo()).append("' ");
		buffer.append("oprTime").append("='").append(getOprTime()).append("' ");
		buffer.append("oprOrgCode").append("='").append(getOprOrgCode()).append("' ");
		buffer.append("orgName").append("='").append(getOrgName()).append("' ");
		buffer.append("loginIp").append("='").append(getLoginIp()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换交互对象.
	 *
	 * @return 操作日志传输对象
	 */
	public TmOperateLogDto convertDto() {
		final TmOperateLogDto dto = new TmOperateLogDto();
		dto.setId(this.id);
		dto.setUserId(this.userId);
		dto.setUserName(userName);
		dto.setOprInfo(this.oprInfo);
		dto.setOprTime(this.oprTime);
		dto.setOprOrgCode(this.oprOrgCode);
		dto.setOrgName(orgName);
		dto.setLoginIp(this.loginIp);
		return dto;
	}
}
