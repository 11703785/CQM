package com.platform.application.sysmanage.right;

import java.io.Serializable;
import java.util.List;

/**
 * 权限树交互对象.
 *
 */
@SuppressWarnings("serial")
public class TmRightTreeDto implements Serializable {
	/**
	 * 权限代码.
	 */
	private String id;

	/**
	 * 权限名称.
	 */
	private String text;
	/**
	 * 子节点.
	 */
	private List<TmRightTreeDto> children;
	/**
	 * 图标样式.
	 */
	private String iconCls;
	/**
	 * 是否勾选状态.
	 */
	private Boolean checked = false;
	/**
	 * 是否展开(open,closed).
	 */
	private String state = "open";

	/**
	 * 返回权限代码.
	 *
	 * @return 权限代码
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置权限代码.
	 *
	 * @param idVal 权限代码
	 */
	public void setId(final String idVal) {
		this.id = idVal;
	}

	/**
	 * 返回权限名称.
	 *
	 * @return 权限名称
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置权限名称.
	 *
	 * @param textVal 权限名称
	 */
	public void setText(final String textVal) {
		this.text = textVal;
	}

	/**
	 * 返回权限子节点.
	 *
	 * @return 权限子节点
	 */
	public List<TmRightTreeDto> getChildren() {
		return children;
	}

	/**
	 * 设置权限子节点.
	 *
	 * @param childrenVal 权限子节点
	 */
	public void setChildren(final List<TmRightTreeDto> childrenVal) {
		this.children = childrenVal;
	}

	/**
	 * 返回权限图标样式.
	 *
	 * @return 权限图标样式
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * 设置权限图标样式.
	 *
	 * @param iconClsVal 权限图标样式
	 */
	public void setIconCls(final String iconClsVal) {
		this.iconCls = iconClsVal;
	}

	/**
	 * 返回权限是否勾选状态.
	 *
	 * @return 权限是否勾选状态
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * 设置是否勾选状态.
	 *
	 * @param checkedVal 是否勾选状态
	 */
	public void setChecked(final Boolean checkedVal) {
		this.checked = checkedVal;
	}

	/**
	 * 返回是否展开.
	 *
	 * @return 是否展开
	 */
	public String getState() {
		return state;
	}

	/**
	 * 设置是否展开.
	 *
	 * @param stateVal 是否展开
	 */
	public void setState(final String stateVal) {
		this.state = stateVal;
	}

}
