package com.platform.application.rateindex.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 模板对应指标及权重 实体
 * @author jdw
 *
 */
@Entity
@Table(name = "rate_templateIndex")
public class RateTempIndex implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;			//主键id
	private RateTemplate temp;	//关联模板
	//private RateIndex rateIndex;//关联指标
	
	private String indexId;		//关联的指标id
	private String indexName;	//关联的指标名称
	private String indexLevels;	//关联的指标级别
	private String indexValues;	//关联的指标取值
	private String indexvType;	//关联的指标取值类型 0：字符类型 1：数值类型
	private Integer indexOrder;	//指标序号
	private String parentIndexId;//父指标id
	
	private Double weight;		//权重值
	
	private String scoreType;	//评分标准类型 0：直等评分 1：范围评分
	
	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RateTemplate.class)
	@JoinColumn(name="tempId")
	public RateTemplate getTemp() {
		return temp;
	}
	public void setTemp(RateTemplate temp) {
		this.temp = temp;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getIndexLevels() {
		return indexLevels;
	}
	public void setIndexLevels(String indexLevels) {
		this.indexLevels = indexLevels;
	}
	public String getIndexValues() {
		return indexValues;
	}
	public void setIndexValues(String indexValues) {
		this.indexValues = indexValues;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getParentIndexId() {
		return parentIndexId;
	}
	public void setParentIndexId(String parentIndexId) {
		this.parentIndexId = parentIndexId;
	}
	public String getScoreType() {
		return scoreType;
	}
	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
	public String getIndexvType() {
		return indexvType;
	}
	public void setIndexvType(String indexvType) {
		this.indexvType = indexvType;
	}
	public Integer getIndexOrder() {
		return indexOrder;
	}
	public void setIndexOrder(Integer indexOrder) {
		this.indexOrder = indexOrder;
	}

}
