package com.platform.application.sysmanage.vo;

public class TreeNodeEx extends TreeNode{
	/**
	 * code扩展时使用 根据实际赋值有实际含义
	 */
	private String code;
	private String href;
	private String hrefTarget;
	private String url;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
