package com.platform.application.operation.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.platform.application.sysmanage.domain.Area;

/**
 * 信息来源单位
 */
@Entity
@Table(name = "sys_sourceorg")
public class SourceOrg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String id;                 //ID
	private String name;               //单位名称
	private String code;               //企业编码
	private String personalCode;       //个人编码
	private Integer orgOrder;          //单位序号
	private String description;        //单位描述
	private Area area;				   //所属地区
	private String ip;                 //IP地址
	
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
	public Integer getOrgOrder() {
		return orgOrder;
	}
	public void setOrgOrder(Integer orgOrder) {
		this.orgOrder = orgOrder;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Area.class)
	@JoinColumn(name = "areaid")
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getPersonalCode() {
		return personalCode;
	}
	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}
	
	
	
}