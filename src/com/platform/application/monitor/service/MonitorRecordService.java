package com.platform.application.monitor.service;

import java.util.Date;
import java.util.List;

import com.platform.application.monitor.domain.Grsummary;
import com.platform.application.monitor.domain.Grzxcxmx;
import com.platform.application.monitor.domain.Qysummary;
import com.platform.application.monitor.domain.Qyzxcxmx;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.page.Page;

/**
 * 说明: 日志处理Service
 * @version
 * @date 2009-2-27 上午11:36:13
 * 
 */
public interface MonitorRecordService {
	/**
     * 方法说明: 分页获取所有个人数据查询
	 * @param  page 分页实体
	 * @return
	 * @throws Exception
	 */
	public String getGrzxcxmxJSON(Grzxcxmx grzxcxmx,Page page,Person person,String beginTime,String endTime,String year) throws Exception;
	/**
     * 方法说明: 分页获取所有企业数据查询
	 * @param  page 分页实体
	 * @return
	 * @throws Exception
	 */
	public String getQyzxcxmxJSON(Qyzxcxmx qyzxcxmx,Page page,Person person,String beginTime,String endTime,String year) throws Exception;
	//查询个人详情
	public Grzxcxmx getGrzxcxmx(Grzxcxmx grzxcxmx) throws Exception;
	//查询企业详情
	public Qyzxcxmx getQyzxcxmx(Qyzxcxmx qyzxcxmx) throws Exception;
	/**
     * 方法说明: 分页获取所有个人数据查询汇总
	 * @param  page 分页实体
	 * @return
	 * @throws Exception
	 */
	public String getGrsummaryJSON(Grsummary grsummary,String departmentId,Page page,Person person,Date beginTime,Date endTime) throws Exception;
	//根据id查询个人汇总详情
	public List<Grsummary> getGrsummary(int id) throws Exception;
	/**
     * 方法说明: 分页获取所有个人数据查询汇总
	 * @param  page 分页实体
	 * @return
	 * @throws Exception
	 */
	public String getQysummaryJSON(Qysummary qysummary,String departmentId,Page page,Person person,Date beginTime,Date endTime) throws Exception;
	//根据id查询个人汇总详情
	public List<Qysummary> getQysummary(int id) throws Exception;
	//定时调用存储过程为征信查询数据表注入数据
	public void summary();
	//定时调用存储过程为当前年份建立表
	public void createTable();
	//定时调用存储过程为插入Createdate
	public void insert();
	//定时调用存储过程为征信查询数据表注入数据(临时)
	public void summarytemp();
 }
