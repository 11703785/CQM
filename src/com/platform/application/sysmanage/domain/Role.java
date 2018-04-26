package com.platform.application.sysmanage.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色实体
 */
@Entity
@Table(name = "sys_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String roleId;			        //角色ID
	private String roleName;           	    //角色名称
	private String roleCode;           	    //角色编码
	private String description;     	    //角色描述
	private String roleScope = "99";        //角色范围   0：市级角色 1：县级角色 2：通用角色
	private String roleType = "01";			//角色所属 01：人民银行 02：其他机构 03：通用角色
	private List resourceList = null;	    //角色所拥有的资源对象列表

	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleScope() {
		return roleScope;
	}
	public void setRoleScope(String roleScope) {
		this.roleScope = roleScope;
	}
	
	@Transient
	public String getRoleScopeStr(){
		String str = "";
		if("2".equals(this.getRoleScope())){
			str = "通用角色";
		}else if("0".equals(this.getRoleScope())){
			str = "市级角色";
		}else if("1".equals(this.getRoleScope())){
			str = "县级角色";
		}
		return str;
	}
	
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	@Transient
	public String getRoleTypeStr(){
		String str = "";
		if("01".equals(this.getRoleType())){
			str = "人民银行";
		}else if("02".equals(this.getRoleType())){
			str = "其他机构";
		}else if("03".equals(this.getRoleType())){
			str = "通用角色";
		}
		return str;
	}
	
	@ManyToMany(targetEntity=OptResource.class,cascade=CascadeType.REFRESH)
	@JoinTable(name="sys_role_resource",joinColumns=@JoinColumn(name="roleId"),inverseJoinColumns=@JoinColumn(name="resId"))
	public List getResourceList() {
		return resourceList;
	}
	public void setResourceList(List resourceList) {
		this.resourceList = resourceList;
	}

}
