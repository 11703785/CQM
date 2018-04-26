package com.platform.application.sysmanage.right;

import com.platform.application.sysmanage.right.bean.TmRight;

public class RightDto {
	/**
	 * 权限代码.
	 */
	private String rightCode;

	/**
	 * 权限名称.
	 */
	private String rightName;

	/**
	 * 权限路径.
	 */
	private String rightPath;

	/**
	 * 权限方法.
	 */
	private String rightMethod;

	/**
	 * 顶级管理员是否验证.
	 */
	private String topAdmin;

	/**
	 * 是否通过IFrame展示页面.
	 */
	private String isFrame;

	/**
	 * 多法人权限.
	 */
	private String contRight;

	/**
	 * 获取权限代码.
	 *
	 * @return 权限代码.
	 */
	public String getRightCode() {
		return rightCode;
	}

	/**
	 * 设置权限代码.
	 *
	 * @param rightCodeVal 权限代码
	 */
	public void setRightCode(final String rightCodeVal) {
		rightCode = rightCodeVal;
	}

	/**
	 * 获取权限名称.
	 *
	 * @return 权限名称.
	 */
	public String getRightName() {
		return rightName;
	}

	/**
	 * 设置权限名称.
	 *
	 * @param rightNameVal 权限名称
	 */
	public void setRightName(final String rightNameVal) {
		rightName = rightNameVal;
	}

	/**
	 * 获取权限路径.
	 *
	 * @return 权限路径.
	 */
	public String getRightPath() {
		return rightPath;
	}

	/**
	 * 设置权限路径.
	 *
	 * @param rightPathVal 权限路径
	 */
	public void setRightPath(final String rightPathVal) {
		rightPath = rightPathVal;
	}

	/**
	 * 获取权限方法.
	 *
	 * @return 权限方法.
	 */
	public String getRightMethod() {
		return rightMethod;
	}

	/**
	 * 设置权限方法.
	 *
	 * @param rightMethodVal 权限方法
	 */
	public void setRightMethod(final String rightMethodVal) {
		rightMethod = rightMethodVal;
	}

	/**
	 * 获取顶级管理员是否验证.
	 *
	 * @return 顶级管理员是否验证.
	 */
	public String getTopAdmin() {
		return topAdmin;
	}

	/**
	 * 设置顶级管理员是否验证.
	 *
	 * @param topAdminVal 顶级管理员是否验证
	 */
	public void setTopAdmin(final String topAdminVal) {
		topAdmin = topAdminVal;
	}

	/**
	 * 获取是否通过IFrame展示页面.
	 *
	 * @return 是否通过IFrame展示页面.
	 */
	public String getIsFrame() {
		return isFrame;
	}

	/**
	 * 设置是否通过IFrame展示页面.
	 *
	 * @param isFrameVal 是否通过IFrame展示页面
	 */
	public void setIsFrame(final String isFrameVal) {
		isFrame = isFrameVal;
	}

	public String getContRight() {
		return contRight;
	}

	public void setContRight(final String contRight) {
		this.contRight = contRight;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("rightCode").append("='").append(getRightCode())
		.append("' ");
		buffer.append("rightName").append("='").append(getRightName())
		.append("' ");
		buffer.append("rightPath").append("='").append(getRightPath())
		.append("' ");
		buffer.append("rightMethod").append("='").append(getRightMethod())
		.append("' ");
		buffer.append("topAdmin").append("='").append(getTopAdmin())
		.append("' ");
		buffer.append("isFrame").append("='").append(getIsFrame())
		.append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换交互对象.
	 *
	 * @return 系统权限实体类
	 */
	public TmRight convertEntity() {
		final TmRight entity = new TmRight();
		entity.setRightCode(rightCode);
		entity.setRightName(rightName);
		entity.setRightPath(rightPath);
		entity.setRightMethod(rightMethod);
		entity.setTopAdmin(topAdmin);
		entity.setIsFrame(isFrame);
		entity.setContRight(contRight);
		return entity;
	}
}
