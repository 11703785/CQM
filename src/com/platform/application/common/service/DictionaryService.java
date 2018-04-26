package com.platform.application.common.service;

import java.util.List;
import java.util.Map;

import com.platform.application.common.domain.DicType;
import com.platform.application.common.domain.Dictionary;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[DictionaryService.java]字典信息维护功能接口
 */

public interface DictionaryService extends GenericDao<Dictionary,String> {
	/**字典维护 start**/
	  /**
	   * 获取字典信息Json
	   * @param dictionart
	   * @param pageObj
	   * @return
	   */
     
	public String getDictionaryJson(DicType dicType, Page page) throws Exception;
	
	/**
	 * 保存字典信息
	 * @param dictionart
	 * @throws Exception
	 */
	public void txCreateDictionary(Dictionary dictionart,DicType dicType)throws Exception;
	/**
	 * 编辑字典信息
	 * @param dictionart
	 * @throws Exception
	 */
	public void txUpdateDictionary(Dictionary dictionart,DicType dicType) throws Exception;
	/**
	 * 删除字典信息
	 * @param dictionary
	 * @throws Exception
	 */
	
	public void txdeleteDictionary(Dictionary dictionary)throws Exception;

	/**
	 * 根据字典类型和编码，获取字典名称
	 * @param string
	 * @param valueOf
	 * @return
	 * @throws Exception
	 */
	public String getDicByLxAndCode(String string, String code) throws Exception;

	/**
	 * 获取所有字典map集合
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDictionaryMaps() throws Exception;

	public String getDicTypesJson(String id) throws Exception;
	
	
	/**
	 * 根据ID，获取字典
	 * @param string
	 * @param valueOf
	 * @return
	 * @throws Exception
	 */
	public List<Dictionary>  getDicById(String id);
	
	/**字典维护 end**/
}
