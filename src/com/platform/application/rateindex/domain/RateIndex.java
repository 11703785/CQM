package com.platform.application.rateindex.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 评级指标实体
 * @author jdw
 *
 */
@Entity
@Table(name = "rate_index")
public class RateIndex implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;					//主键id
	private String name;				//指标名称
	private String code;				//指标编码
	private String levels;				//指标等级 1 一级（根）指标【类型】 2 二级指标 并以此类推
	private String personId;			//创建人
	private String orgId;				//创建部门
	private Date createTime;			//创建时间
	
	private RateIndex parent;			//父指标
	//private RateIndexType indexType;	//关联类型
	
	//private RateIndexValues rateIndexValues;//关联取值实体
	
	private String indexValues;			//指标取值
	private String indexvtype;			//指标取值类型 0：字符类型 1：数值类型
	private Integer orderNum; 			//序号
	private String indexstatus;			//指标类型：0 通用指标 1：特色指标

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
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
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
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RateIndex.class)
	@JoinColumn(name="parentId")
	public RateIndex getParent() {
		return parent;
	}
	public void setParent(RateIndex parent) {
		this.parent = parent;
	}
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RateIndexType.class)
//	@JoinColumn(name="typeId")
//	public RateIndexType getIndexType() {
//		return indexType;
//	}
//	public void setIndexType(RateIndexType indexType) {
//		this.indexType = indexType;
//	}
//	@OneToOne(mappedBy = "rateIndex")
//	public RateIndexValues getRateIndexValues() {
//		return rateIndexValues;
//	}
//	public void setRateIndexValues(RateIndexValues rateIndexValues) {
//		this.rateIndexValues = rateIndexValues;
//	}
	public String getIndexValues() {
		return indexValues;
	}
	public void setIndexValues(String indexValues) {
		this.indexValues = indexValues;
	}
	public String getIndexvtype() {
		return indexvtype;
	}
	public void setIndexvtype(String indexvtype) {
		this.indexvtype = indexvtype;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getIndexstatus() {
		return indexstatus;
	}
	public void setIndexstatus(String indexstatus) {
		this.indexstatus = indexstatus;
	}
	
	
}
