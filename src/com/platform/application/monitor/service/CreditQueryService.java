package com.platform.application.monitor.service;

import java.util.Map;

import java.util.List;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.monitor.domain.Grzxcxmx;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;

public interface CreditQueryService extends GenericDao<Grzxcxmx,String>{
	//机构查询统计
	public Map<String,String> departmentCount(String beginTime,String endTime, String queryOrgName,Person person,String level) throws Exception;
	//辖区查询统计
	public Map<String,String> areaCount(String beginTime,String endTime,String area,Person peron,String level)throws Exception;
	//查询走势图
	public Map<String,List> yearCount(String year1,String year2,Person person,String type,String time,List times,String dept,String area,String level)throws Exception;
    //用户查询统计
	public Map<String,String> personCount(String beginTime,String endTime, String queryOrgName,Person person,String level) throws Exception;
    /**
     * 查询
     * @param grreason 
     * @return
     * @throws Exception
     */
    public Map<String,String> resorceCount(String beginTime,String endTime, String grreason,Person person)throws Exception;
    
    //获得所有查询原因列表
    public List<Dictionary> getAllReason()throws Exception;
    //SQL查询
    public List findSQL(final String sql);

	
}
