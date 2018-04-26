package com.platform.application.common.service;

import java.util.Map;

import com.platform.application.common.domain.ValiRules;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

public interface ValiRulesService extends GenericDao<ValiRules,String> {

	/**
	 * 查询列表
	 */
	public String getValiRulesListJson(Page pageObj) throws Exception;
	/**
	  * 保存
	 */
	public void txSaveValiRules(ValiRules valiRules)throws Exception;
	  /**
	   * 编辑
	   */
	 public void txEditValiRules(ValiRules valiRules)throws Exception;
	 /**
	   * 删除
	   */
	 public String txDeleteValiRules(ValiRules valiRules)throws Exception;
	 /**
	  * 校验规则map id  name
	  * */
	 public Map selValirulesMap() throws Exception;
}
