package com.platform.application.rateindex.service;

import com.platform.application.rateindex.domain.RateTempIndex;
import com.platform.application.rateindex.domain.RateTemplate;
import com.platform.framework.core.dao.GenericDao;

public interface RateTempIndexService extends GenericDao<RateTempIndex,String> {

	/**
	 * 保存指标、权重值
	 * @param temp 
	 * @param result
	 * @throws Exception
	 */
	public void txSaveRateTempIndex(RateTemplate temp, String strs) throws Exception;
	
	/**
	 * 查询指标数值
	 * @param str
	 * @return
	 */
	public RateTempIndex queryRateTempIndex(String ids);

	/**
	 * 删除指标及权重
	 * @param tempid
	 * @param indexid
	 * @throws Exception
	 */
	public void txDeleteRateTempIndex(String tempid, String indexid) throws Exception;

}
