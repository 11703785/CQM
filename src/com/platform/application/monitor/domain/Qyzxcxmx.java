package com.platform.application.monitor.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.platform.application.common.domain.DicType;
import com.platform.application.sysmanage.domain.Person;
/**
 * 企业征信
 */

public class Qyzxcxmx implements Serializable{

	
		private static final long serialVersionUID = 1L;
		
		
		private String queryTime;                 //查询时间
		private String isQueried;                 //是否查得
		private String queryAuthTime;             //查询授权时间
		private String queryComputerIP;           //查询机IP
		private String upOrgCode;                 //机构代码
		private String queryOrgNo;                //用户所属机构 
		private String queryOrgName;              //查询网点名称
		private String queryUserSysName;          //查询用户系统名
		private String queryUserName;             //查询用户真实姓名
		private String companyName;               //被查询单位名称
		private String zzCode;                    //企业编码
		private String deptName;                  //部门名称
        private String queryReason;               //查询原因
		public String getQueryReason() {
			return queryReason;
		}
		public void setQueryReason(String queryReason) {
			this.queryReason = queryReason;
		}
		public String getUpOrgCode() {
			return upOrgCode;
		}
		public void setUpOrgCode(String upOrgCode) {
			this.upOrgCode = upOrgCode;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getZzCode() {
			return zzCode;
		}
		public void setZzCode(String zzCode) {
			this.zzCode = zzCode;
		}
		public String getDeptName() {
			return deptName;
		}
		public void setDeptName(String deptName) {
			this.deptName = deptName;
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
		public String getQueryUserSysName() {
			return queryUserSysName;
		}
		public void setQueryUserSysName(String queryUserSysName) {
			this.queryUserSysName = queryUserSysName;
		}
		public String getQueryUserName() {
			return queryUserName;
		}
		public void setQueryUserName(String queryUserName) {
			this.queryUserName = queryUserName;
		}
		
		public String getQueryTime() {
			return queryTime;
		}
		public void setQueryTime(String queryTime) {
			this.queryTime = queryTime;
		}
	
		public String getIsQueried() {
			return isQueried;
		}
		public void setIsQueried(String isQueried) {
			this.isQueried = isQueried;
		}
		public String getQueryAuthTime() {
			return queryAuthTime;
		}
		public void setQueryAuthTime(String queryAuthTime) {
			this.queryAuthTime = queryAuthTime;
		}
		public String getQueryComputerIP() {
			return queryComputerIP;
		}
		public void setQueryComputerIP(String queryComputerIP) {
			this.queryComputerIP = queryComputerIP;
		}
	
		
		
	}

