package com.platform.application.rateindex.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 等级类别实体
 * @author jdw
 *
 */
@Entity
@Table(name = "rate_level")
public class RateLevel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;			//主键id
	private String name;		//等级类别
	private String formulas;	//范围
	private String description;	//描述
	
	private String personId;	//创建人
	private String orgId;		//创建部门
	private Date createTime;	//创建时间
	private Integer levelOrder;		//序号
	
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getFormulas() {
		return formulas;
	}
	public void setFormulas(String formulas) {
		this.formulas = formulas;
	}
	public Integer getLevelOrder() {
		return levelOrder;
	}
	public void setLevelOrder(Integer levelOrder) {
		this.levelOrder = levelOrder;
	}
	
}
