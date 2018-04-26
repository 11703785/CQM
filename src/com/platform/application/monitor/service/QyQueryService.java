package com.platform.application.monitor.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.monitor.domain.Qyzxcxmx;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;

public interface QyQueryService extends GenericDao<Qyzxcxmx,String> {
	//机构查询统计
	public Map<String,String> departmentCount(String beginTime,String endTime,String queryOrgName,Person person,String level) throws Exception;
	//年度对比
	public Map<String,String> yearCount(String year1,String year2,Person person)throws Exception;
    //辖区查询统计
	public Map<String,String> areaCount(String beginTime,String endTime,String areaid,Person person,String level)throws Exception;
	 //用户查询统计
	public Map<String,String> personCount(String beginTime,String endTime, String queryOrgName,Person person) throws Exception;
    /**
     * 查询
     * @return
     * @throws Exception
     */
    public Map<String,String> resorceCount(String beginTime,String endTime,String qyreason)throws Exception;
    /**
     * 导入数据
     * @param type
     * @param tablename
     * @param records
     * @param org
     * @throws Exception
     */
    //上报数据
	public void txUploadRecord(String type,String[] nameParts,File records,SourceOrg sourceOrg) throws Exception;
    //获取所有查询原因
    public List<Dictionary> getAllReason()throws Exception;


}
