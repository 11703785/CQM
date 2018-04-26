package com.platform.application.sysmanage.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 地区实体表
 * @author jdw
 *
 */
@Entity
@Table(name = "sys_area")
public class Area implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;			//主键id
	private String name;		//地区名称
	private String code;		//地区编码
	private String levels;		//地区级别 0：地市 1：区/县 2：乡/街道/镇 3：村/居委会
	private String description;	//描述
	private Integer areaOrder;	//序号

	private Area parent;		//父地区
	private String countyid;	//所属区县
	
	@Id
	//@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	//@GeneratedValue(generator="uuidGenerator")
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
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	@Transient
	public String getLevelsStr(){
		String str = "无";
		if("0".equals(this.getLevels())){
			str = "市";
		}else if("1".equals(this.getLevels())){
			str = "区/县";
		}else if("2".equals(this.getLevels())){
			str = "乡/镇/街道";
		}else if("3".equals(this.getLevels())){
			str = "村/居委会";
		}
		return str;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Area.class)
	@JoinColumn(name="parentId")
	public Area getParent() {
		return parent;
	}
	public void setParent(Area parent) {
		this.parent = parent;
	}
	public Integer getAreaOrder() {
		return areaOrder;
	}
	public void setAreaOrder(Integer areaOrder) {
		this.areaOrder = areaOrder;
	}
	public String getCountyid() {
		return countyid;
	}
	public void setCountyid(String countyid) {
		this.countyid = countyid;
	}
	
}
