package com.platform.application.rateindex.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.platform.application.rateindex.domain.RateIndex;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

public interface RateIndexService extends GenericDao<RateIndex,String> {
	
	/**
	 * 查询指标类型
	 */
	public String loadRateIndex(String id,Person person) throws Exception;
	
	/**
	 * 保存指标
	 */
	public void txSaveRateIndex(RateIndex rateIndex,Person person,HttpServletRequest request) throws Exception;
	
	/**
	 * 删除指标
	 */
	public void txDeleteAll(String id)throws Exception;
	
	/**
	 * 查询指标信息
	 */
	public List findRateIndex(String id)throws Exception;
	
	/**
	 * 编辑指标信息
	 */
	public void txUpdate(RateIndex rateIndex,String types)throws Exception;
	
	/**
	 * 查询指标选项信息
	 */
	public List getRateIndexOptios(String id)throws Exception;
	
	/**
	 * 查询指标
	 */
	public List getRateIndexNext(String id)throws Exception;
	
	/**
	 * 查询指标名称集合
	 */
	public List getNames(String name)throws Exception;

}
