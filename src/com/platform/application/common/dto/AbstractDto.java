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
	 * 操作日志名称.
	 */
	private String operName;


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
	 * 获取操作日志名称.
	 *
	 * @return 接口登录账号
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * 设置操作日志名称.
	 *
	 * @param loginNameVal 操作日志名称
	 */
	public void setOperName(final String operName) {
		this.operName = operName;
	}

}
