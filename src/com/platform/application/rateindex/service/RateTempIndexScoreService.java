package com.platform.application.rateindex.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.platform.application.rateindex.domain.RateTempIndexScore;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

public interface RateTempIndexScoreService extends GenericDao<RateTempIndexScore,String> {
	/**
	 * 查询结果列表
	 */
	public String loadRateIndexOptions(String ids,Person person,Page page)throws Exception;
	
	/**
	 * 查看指标选项信息
	 */
	public List<RateTempIndexScore> findRateIndexOptions(RateTempIndexScore rateTempIndexScore)throws Exception;
	
	/**
	 * 删除指标选项信息
	 */
	public void txDelete(RateTempIndexScore rateTempIndexScore)throws Exception;
	
	/**
	 * 保存评分标准
	 */
	public void txSaveRateIndexOptions(Person person, HttpServletRequest request) throws Exception;
	

	/**
	 * 获取所有的字典类型
	 * @return
	 * @throws Exception
	 */
	public List getAllDicType() throws Exception;

	/**
	 * 根据字典类型获取字典信息（拼成option串）
	 * @param typeid
	 * @return
	 * @throws Exception
	 */
	public String getDictionaryByType(String typeid) throws Exception;
}