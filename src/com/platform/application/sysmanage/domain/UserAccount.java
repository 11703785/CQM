package com.platform.application.sysmanage.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import com.platform.application.common.util.Constants;

@Entity
@Table(name = "sys_useraccount")
public class UserAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userId;             //用户ID
	private String loginName;          //用户登录名
	private String userPwd;            //登录密码
	private String userName;           //用户姓名
	private String userStatus = Constants.STATUS_OPEN;         //用户状态
	private Date lastModPwdTime;       //最后密码修改时间

	public UserAccount() {}

	/**
	 * 用户ID
	 */
	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 用户登录名
	 */
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 密码
	 */
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * 用户姓名
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 状态
	 */
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * 最后密码修改时间
	 */
	public Date getLastModPwdTime() {
		return lastModPwdTime;
	}
	public void setLastModPwdTime(Date lastModPwdTime) {
		this.lastModPwdTime = lastModPwdTime;
	}

}