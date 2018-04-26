package com.platform.application.rateindex.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.platform.application.rateindex.domain.RateTemplate;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

public interface RateTemplateService extends GenericDao<RateTemplate,String> {

	/**
	 * 获取评级模板信息json
	 * @param rateTemplate
	 * @param person 
	 * @param pageObj
	 * @return
	 * @throws Exception
	 */
	public String getRateTemplateJson(RateTemplate rateTemplate, Person person, Page page) throws Exception;

	/**
	 * 加载指标树
	 * @param id
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public String loadRateIndex(String id, Person person) throws Exception;
    /**
     * 保存指标模板
     * @param rateTemplate
     * @param personId
     * @param orgId
     * @param areaids 
     * @throws Exception
     */
	public void txCreateRateTemplate(RateTemplate rateTemplate,String personId, String orgId, String areaids)throws Exception;
    /**
     * 编辑指标模板
     * @param rateTemplate
     * @param orgId
     * @param personId
     * @param areaids 
     * @throws Exception
     */
	public void txUpdateRateTemplate(RateTemplate rateTemplate, String orgId,String personId, String areaids)throws Exception;
    /**
     * 删除模板
     * @param rateTemplate
     */
	public void txDeleteRateTemplate(RateTemplate rateTemplate)throws Exception;
    /**
     * 启用模板
     * @param ids
     */
	public void txEnableRateTemplate(String ids)throws Exception;

	/**
	 * 加载已经分配模板的指标树
	 * @param id
	 * @param tempid
	 * @return
	 * @throws Exception
	 */
	public String loadRateTempIndexJson(String id, String tempid)throws Exception;
	
	public LinkedHashMap getReportRateInde(String id)throws Exception;

	/**
	 * 获取所有地区
	 * @return
	 * @throws Exception
	 */
	public List getAllAreaList() throws Exception;
	
	/**
	 * 获取指标div拼接串
	 * @param rateTemplate
	 * @return
	 * @throws Exception
	 */
	public String getRateIndexDivStr(RateTemplate rateTemplate) throws Exception;

}
