package com.platform.application.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class AbstractDto implements java.io.Serializable {
	/**
	 * 北京时区.
	 */
	public static final String TIMEZONE_BEIJING = "GMT+8";

	/**
	 * 当前页数.
	 */
	private int page;

	/**
	 * 查询行数.
	 */
	private int rows;

	/**
	 * 接口登录账号.
	 */
	private String loginName;

	/**
	 * 接口登录密码.
	 */
	private String password;

	/**
	 * 获取当前页数.
	 *
	 * @return 当前页数
	 */
	@JsonIgnore
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页数.
	 *
	 * @param pageVal 当前页数
	 */
	@JsonProperty
	public void setPage(final int pageVal) {
		this.page = pageVal;
	}

	/**
	 * 获取查询行数.
	 *
	 * @return 查询行数
	 */
	@JsonIgnore
	public int getRows() {
		return rows;
	}

	/**
	 * 设置查询行数.
	 *
	 * @param rowsVal 查询行数
	 */
	@JsonProperty
	public void setRows(final int rowsVal) {
		this.rows = rowsVal;
	}

	/**
	 * 获取接口登录账号.
	 *
	 * @return 接口登录账号
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置接口登录账号.
	 *
	 * @param loginNameVal 接口登录账号
	 */
	public void setLoginName(final String loginNameVal) {
		this.loginName = loginNameVal;
	}

	/**
	 * 获取接口登录密码.
	 *
	 * @return 接口登录密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置接口登录密码.
	 *
	 * @param passwordVal 接口登录密码
	 */
	public void setPassword(final String passwordVal) {
		this.password = passwordVal;
	}
}
