package com.platform.application.sysmanage.right.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.platform.application.sysmanage.right.TmRightDto;

@Entity
@Table(name = "TM_RIGHT")
@SuppressWarnings("serial")
public class TmRight implements Serializable{
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
	 * 获取权限代码.
	 *
	 * @return 权限代码.
	 */
	@Id
	@Column(name = "RIGHTCODE", unique = true, nullable = false)
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
	@Column(name = "RIGHTNAME", nullable = false)
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
	@Column(name = "RIGHTPATH", nullable = false)
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
	@Column(name = "RIGHTMETHOD", nullable = false)
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
	@Column(name = "TOPADMIN", nullable = false)
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
	@Column(name = "ISFRAME")
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
	 * 转换传输对象.
	 *
	 * @return TmRightDto
	 */
	public TmRightDto convertDto() {
		final TmRightDto dto = new TmRightDto();
		dto.setRightCode(rightCode);
		dto.setRightName(rightName);
		dto.setRightPath(rightPath);
		dto.setRightMethod(rightMethod);
		dto.setTopAdmin(topAdmin);
		dto.setIsFrame(isFrame);
		return dto;
	}
}
