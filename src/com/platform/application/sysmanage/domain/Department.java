package com.platform.application.sysmanage.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

import com.platform.application.common.util.Constants;

/**
 * 部门
 */
@Entity
@Table(name = "sys_department")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String TYPE_01 = "01";       //人民银行
	public static final String TYPE_02 = "02";       //其他机构
	public static final String TYPE_03 = "03";       //人行下属部门

	
	private String deptId;                 //ID
	private String deptName;               //机构名称
	private String deptCode;               //机构编码（企业）
	private String personalOrgCode;        //机构编码（个人）
	private int deptOrder = 0;             //机构序号
	private String description;            //机构描述
	private String deptStatus = Constants.STATUS_OPEN;      //机构状态
	private String treeCode;               //父地区编号**ID层级字串儿，用/分隔（目前长度支持4层）
	private String deptType = TYPE_01;               //机构类型
	private String isleaf = Constants.LEAF_1;           //是否叶子节点    0 非叶子节点   1 叶子节点
	private Area area;					//关联地区
	
	private Department parent;             //父机构对象
	
	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getDeptOrder() {
		return deptOrder;
	}
	public void setDeptOrder(int deptOrder) {
		this.deptOrder = deptOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Department.class)
	@JoinColumn(name="parentId")
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}

	public String getDeptStatus() {
		return deptStatus;
	}
	public void setDeptStatus(String deptStatus) {
		this.deptStatus = deptStatus;
	}

	public String getTreeCode() {
		if(parent!=null)
			return this.parent.getDeptId()+"/"+this.deptId;
		else
			return this.deptId;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	
	@Transient
	public String getDeptTypeStr(){
		if("01".equals(this.getDeptType())){
			return "人民银行";
		}else{
			return "其他机构";
		}
	}
	
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
	
	/**
	 * 获取当前所在的机构
	 */
	@Transient
	public Department getOrg() {
		Department org = this;
		while(org!=null && org.getDeptType().equals("03")){
			org = org.getParent();
		}
		return org;
	}
	
	// 重写equals方法，通过ID判断两个对象是否一样
	public boolean equals(Object arg0){
		if(null == deptId || null == arg0){
			return false;
		}
		Department dept = (Department) arg0;
		if(this.deptId.equals(dept.getDeptId()))
			return true;
		else
			return false;
	}
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Area.class)
	@JoinColumn(name="areaId")
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	@Column(name="DEPTCODE_P")
	public String getPersonalOrgCode() {
		return personalOrgCode;
	}
	public void setPersonalOrgCode(String personalOrgCode) {
		this.personalOrgCode = personalOrgCode;
	}

}