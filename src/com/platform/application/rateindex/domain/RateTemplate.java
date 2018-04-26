package com.platform.application.rateindex.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.platform.application.sysmanage.domain.Area;

/**
 * 评级模板实体
 * @author jdw
 *
 */
@Entity
@Table(name = "rate_template")
public class RateTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;			//主键id
	private String name;		//模板名称
	private String code;		//模板编号
	private String status;		//状态 1：启用 0：停用
	private String description;	//模板描述
	private String personId;	//创建人
	private String orgId;		//创建部门
	private Date createTime;	//创建时间
	
	private List areaList;		//关联地区
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getStatusStr(){
		String msg = "停用";
		if("1".equals(this.getStatus())){
			msg = "启用";
		}
		return msg;
	}
	@ManyToMany(targetEntity=Area.class,cascade=CascadeType.REFRESH)
	@JoinTable(name="rate_template_area",joinColumns=@JoinColumn(name="tempId"),inverseJoinColumns=@JoinColumn(name="areaId"))
	public List getAreaList() {
		return areaList;
	}
	public void setAreaList(List areaList) {
		this.areaList = areaList;
	}
	@Transient
	public String getAreaIds(){
		StringBuffer sb = new StringBuffer();
		Area area = null;
		if(areaList != null){
			for (int i = 0; i < areaList.size(); i++) {
				area = (Area)areaList.get(i);
				if(i==0) sb.append(area.getId());
				else sb.append(","+area.getId());
			}
		}
		return sb.toString();
	}
	@Transient
	public String getAreaNames(){
		StringBuffer sb = new StringBuffer();
		Area area = null;
		if(areaList != null){
			for(int i=0; i<areaList.size(); i++){
				area = (Area)areaList.get(i);
				if(i==0) sb.append(area.getName());
				else sb.append(","+area.getName());
			}
		}
		return sb.toString();
	}
}
