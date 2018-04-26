package com.platform.application.monitor.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "qysummary")
public class Qysummary implements Serializable {
  private int id;                    //id
  private String upOrgCode;          //机构编码
  private String queryOrgNo;         //机构名称
  private String queryOrgName;       //网点名称
  private String queryUserSysname;   //查询用户系统名
  private String queryUserName;      //查询用户真是姓名
  private Date queryTime;            //查询时间
  private int total;

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUpOrgCode() {
	return upOrgCode;
}
public void setUpOrgCode(String upOrgCode) {
	this.upOrgCode = upOrgCode;
}
public String getQueryOrgNo() {
	return queryOrgNo;
}
public void setQueryOrgNo(String queryOrgNo) {
	this.queryOrgNo = queryOrgNo;
}
public String getQueryOrgName() {
	return queryOrgName;
}
public void setQueryOrgName(String queryOrgName) {
	this.queryOrgName = queryOrgName;
}
public String getQueryUserSysname() {
	return queryUserSysname;
}
public void setQueryUserSysname(String queryUserSysname) {
	this.queryUserSysname = queryUserSysname;
}
public String getQueryUserName() {
	return queryUserName;
}
public void setQueryUserName(String queryUserName) {
	this.queryUserName = queryUserName;
}
public Date getQueryTime() {
	return queryTime;
}
public void setQueryTime(Date queryTime) {
	this.queryTime = queryTime;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}                 //当天查询次数

  
}
