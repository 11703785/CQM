package com.platform.application.common.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_rules")
public class ValiRules implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;		//主键id
	private String name;	//规则名称
	private String rules;	//正则表达式
	private String descrption; //说明
	
	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	
	
}
