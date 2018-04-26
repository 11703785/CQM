package com.platform.application.sysmanage.vo;

public class ComboBoxNode {

	/**
	 * [{ id: 1, text: 'A leaf Node', desc: '描述',selected:true/false }] }]
	 */
	private String id;
	private String text;
	private String desc = null;
	private String selected = null;
	
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}