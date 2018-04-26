package com.platform.application.common.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_dictype")
public class DicType implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String code;
	private List<Dictionary> dictionarys;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@OneToMany(targetEntity = Dictionary.class, fetch = FetchType.LAZY)
	@JoinColumn(name="typeid")
	@OrderBy("code asc")
	public List<Dictionary> getDictionarys() {
		return dictionarys;
	}
	public void setDictionarys(List<Dictionary> dictionarys) {
		this.dictionarys = dictionarys;
	}
	
}
