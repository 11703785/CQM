package com.platform.application.rateindex.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.platform.application.rateindex.domain.RateLevel;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

public interface RateLevelService extends GenericDao<RateLevel,String> {
	/**
	 * 信用等级JSON
	 * @param level
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String getRateLevelJson(RateLevel level, Page page) throws Exception;
	/**
	 * 新建信用等级
	 * @param rl
	 * @param personId
	 * @param deptId
	 * @param maxNum 
	 * @param result 
	 * @param symbolsChar2 
	 * @param symbolsChar1 
	 * @param minNum 
	 */
	public void txCreateRateLevel(RateLevel rl, String personId, String deptId, HttpServletRequest request)throws Exception;
	/**
	 * 编辑信用等级
	 * @param rl
	 * @param deptId
	 * @param personId
	 */
	public void txUpdateRateLevel(RateLevel rl, String deptId, String personId)throws Exception;
	/**
	 * 删除信用等级信息
	 * @param rl
	 */
	public void txdeleteRatelevel(RateLevel rl)throws Exception ;
	
	/**
	 * 获取信用等级
	 * @return
	 * @throws Exception
	 */
	public List getAllLevelList() throws Exception;

}
