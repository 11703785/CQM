package com.platform.application.sysmanage.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "sys_person")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String personId;                 //人员主键ID
	private String email;                    //电子邮件
	private String mobileNumber;             //手机号码
	private int personOrder = 0;             //人员序号
	private List roleList = null;            //人员拥有的角色列表
	private Department department;           //人员所属机构
	private UserAccount userAccount = null;  //使用用户账号
	private String personName = null;    //姓名
	private String loginName = null;     //登录名
	private String roleIds = null;       //角色列表ID串，以,分隔
	private String roleNames = null;     //角色列表名称串，以,分隔
	
	/**
	 * 用户ID
	 */
	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	/**
	 *电子邮箱
	 */
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * 电话
	 */
	public String getMobileNumber(){
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	/**
	 * 序号
	 */
	public int getPersonOrder(){
		return personOrder;
	}
	public void setPersonOrder(int personOrder){
		this.personOrder = personOrder;
	}
	
	/**
	 * 角色列表
	 */
	@ManyToMany(targetEntity = Role.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_person_role",
			joinColumns = @JoinColumn(name = "personId"),
			inverseJoinColumns = @JoinColumn(name = "roleId"))
	public List getRoleList() {
		return roleList;
	}
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	/**
	 * 所属机构
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Department.class)
	@JoinColumn(name="deptId")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * 使用账号信息
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = UserAccount.class)
	@JoinColumn(name="userId")
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@Transient
    public String getPersonName() {
		return userAccount.getUserName();
	}
	@Transient
    public String getLoginName() {
		return userAccount.getLoginName();
	}
	@Transient
    public String getRoleIds() {
		StringBuffer sb = new StringBuffer();
		Role role = null;
		if(roleList!=null){
			for (int i = 0; i < roleList.size(); i++) {
				role = (Role)roleList.get(i);
				if(i==0) sb.append(role.getRoleId());
				else sb.append(","+role.getRoleId());
			}
		}
		return sb.toString();
	}
	@Transient
    public String getRoleNames() {
		StringBuffer sb = new StringBuffer();
		Role role = null;
		if(roleList!=null){
			for (int i = 0; i < roleList.size(); i++) {
				role = (Role)roleList.get(i);
				if(i==0) sb.append(role.getRoleName());
				else sb.append(","+role.getRoleName());
			}
		}
		return sb.toString();
	}
}
