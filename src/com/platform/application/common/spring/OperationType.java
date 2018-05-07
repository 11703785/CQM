package com.platform.application.common.spring;

/**
 * 操作类型枚举.
 *
 */
public enum OperationType {
	/**
	 *操作类型枚举 oper
	 */
	ADD("add"), DELETE("delete"), UPDATE("update"), QUERY("query");
	/**
	 * 操作类型
	 */
	private String type;
	/**
	 *
	 * @param tp type
	 */
	OperationType(final String tp) {
		this.type = tp;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
