package com.platform.application.sysmanage.operlog;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.operlog.bean.TmOperateLog;

@SuppressWarnings("serial")
public class TmOperateLogDto extends AbstractDto implements Serializable {
	/**
	 * 编号.
	 */
	private Long id;

	/**
	 * 用户标识.
	 */
	private String userId;

	/**
	 * 用户名称.
	 */
	private String userName;

	/**
	 * 操作内容.
	 */
	private String oprInfo;

	/**
	 * 操作时间.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIMEZONE_BEIJING)
	private Date oprTime;

	/**
	 * 所在机构.
	 */
	private String oprOrgCode;

	/**
	 * 机构名称.
	 */
	private String orgName;


	/**
	 * 操作人员IP.
	 */
	private String loginIp;

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
	 * 获取编号.
	 *
	 * @return 编号
	 */
	public Long getId() {
		return this.id;
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
	 * 获取用户标识.
	 *
	 * @return 用户标识
	 */
	public String getUserId() {
		return this.userId;
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
	 * 获取操作内容.
	 *
	 * @return 操作内容
	 */
	public String getOprInfo() {
		return this.oprInfo;
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
	 * 获取操作时间.
	 *
	 * @return 操作时间
	 */
	public Date getOprTime() {
		return this.oprTime;
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
	 * 获取所在机构.
	 *
	 * @return 所在机构
	 */
	public String getOprOrgCode() {
		return this.oprOrgCode;
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
	 * 获取操作人员IP.
	 *
	 * @return 操作人员IP
	 */
	public String getLoginIp() {
		return this.loginIp;
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
	 * 获取用户名称.
	 * @return 用户名称
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名称.
	 * @param userName 用户名称
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * 获取机构名称.
	 * @return 机构名称
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置机构名称.
	 * @param orgName 机构名称
	 */
	public void setOrgName(final String orgName) {
		this.orgName = orgName;
	}

	public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(final Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public Date getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(final Date queryEndTime) {
		this.queryEndTime = queryEndTime;
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
	public TmOperateLog convertEntity() {
		final TmOperateLog entity = new TmOperateLog();
		entity.setId(this.id);
		entity.setUserId(this.userId);
		entity.setUserName(userName);
		entity.setOprInfo(this.oprInfo);
		entity.setOprTime(this.oprTime);
		entity.setOprOrgCode(this.oprOrgCode);
		entity.setOrgName(orgName);
		entity.setLoginIp(this.loginIp);
		return entity;
	}
}
