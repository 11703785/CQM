package com.platform.application.sysmanage.login;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 平台登录模块控制层_菜单节点传输类.
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY,
content = JsonInclude.Include.NON_NULL)
public class MenuTreeDto implements Comparable<MenuTreeDto> {
	/**
	 * 菜单节点ID.
	 */
	private String id;
	/**
	 * 菜单节点文本.
	 */
	private String text;
	/**
	 * 菜单节点图标.
	 */
	private String iconCls;
	/**
	 * 菜单节点连接.
	 */
	private String href;
	/**
	 * 菜单节点是否使用iframe展示.
	 */
	private Boolean iframe;
	/**
	 * 菜单节点包含子节点集合.
	 */
	private Set<MenuTreeDto> children;

	/**
	 * 获取菜单节点ID.
	 *
	 * @return 菜单节点ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置菜单节点ID.
	 *
	 * @param idVal 菜单节点ID
	 */
	public void setId(final String idVal) {
		this.id = idVal;
	}

	/**
	 * 获取菜单节点文本.
	 *
	 * @return 菜单节点文本
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置菜单节点图标.
	 *
	 * @param textVal 菜单节点图标
	 */
	public void setText(final String textVal) {
		this.text = textVal;
	}

	/**
	 * 获取菜单节点图标.
	 *
	 * @return 菜单节点图标
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * 设置菜单节点图标.
	 *
	 * @param iconClsVal 菜单节点图标
	 */
	public void setIconCls(final String iconClsVal) {
		this.iconCls = iconClsVal;
	}

	/**
	 * 获取菜单节点连接.
	 *
	 * @return 菜单节点连接
	 */
	public String getHref() {
		return href;
	}

	/**
	 * 设置菜单节点连接.
	 *
	 * @param hrefVal 菜单节点连接
	 */
	public void setHref(final String hrefVal) {
		this.href = hrefVal;
	}

	/**
	 * 判读菜单节点是否使用iframe展示.
	 *
	 * @return 是否使用iframe展示
	 */
	public Boolean isIframe() {
		return iframe;
	}

	/**
	 * 设置菜单节点是否使用iframe展示.
	 *
	 * @param iframeVal 是否使用iframe展示
	 */
	public void setIframe(final Boolean iframeVal) {
		this.iframe = iframeVal;
	}

	/**
	 * 获取菜单节点包含子节点集合.
	 *
	 * @return 菜单节点包含子节点集合
	 */
	public Set<MenuTreeDto> getChildren() {
		return children;
	}

	/**
	 * 设置菜单节点包含子节点集合.
	 *
	 * @param childrenVal 菜单节点包含子节点集合
	 */
	public void setChildren(final Set<MenuTreeDto> childrenVal) {
		this.children = childrenVal;
	}

	/**
	 * 根据菜单节点ID确定顺序.
	 *
	 * @param o 菜单节点传输对象
	 * @return 比较结果
	 */
	@Override
	public int compareTo(final MenuTreeDto o) {
		if (this.getId() != null) {
			if (o.getId() != null) {
				return this.getId().compareTo(o.getId());
			} else {
				return 1;
			}
		} else {
			if (o.getId() != null) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
