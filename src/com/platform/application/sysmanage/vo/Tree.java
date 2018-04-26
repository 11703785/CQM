package com.platform.application.sysmanage.vo;

public class Tree extends TreeNode {

	private String children;
	private boolean expanded;

	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}

	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
