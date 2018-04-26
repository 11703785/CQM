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
 * 模板对应指标的评分标准 实体
 * @author jdw
 *
 */
@Entity
@Table(name = "rate_templateIndexScore")
public class RateTempIndexScore implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;			//主键id
	private RateTemplate temp;	//关联模板
	private String indexId;		//关联的指标id
	
	private String standName;	//评分标准名称
	private String standCode;	//评分标准编码
	
	private String standFormule;//公式
	
	private Double score;		//分值
	private Integer standOrder;	//序号
	
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
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	public String getStandCode() {
		return standCode;
	}
	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getStandFormule() {
		return standFormule;
	}
	public void setStandFormule(String standFormule) {
		this.standFormule = standFormule;
	}
	public Integer getStandOrder() {
		return standOrder;
	}
	public void setStandOrder(Integer standOrder) {
		this.standOrder = standOrder;
	}
	
}
