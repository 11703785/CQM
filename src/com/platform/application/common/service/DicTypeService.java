package com.platform.application.common.service;



import java.util.List;

import com.platform.application.common.domain.DicType;
import com.platform.application.common.domain.Dictionary;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;



public interface DicTypeService extends GenericDao<DicType,String> {
	/**字典类型维护--------------------------------------------------------------------------start */
	/**
	 * 获取类型信息Json
	 * @param dicType
	 * @param pageObj
	 * @return
	 * @throws Exception
	 */
	public String getDicTypeJson(DicType dicType, Page pageObj) throws Exception;
    /**
     * 保存字典类型
     * @param dicType
     * @throws Exception
     */
	public void txCreateDicType(DicType dicType)throws Exception;
     /**
      * 编辑字典类型
      * @param dicType
      * @throws Exception
      */
	public void txUpdateDicType(DicType dicType)throws Exception;
	/**
	 * 删除字典类型
	 * @param dicType
	 * @return 
	 */
	public String txdeleteDicType(DicType dicType)throws Exception;
	/**
	 * 查询字典类型
	 * @return
	 * @throws Exception
	 */
	public List<DicType> getDicTypeNameList() throws Exception;
	/**
	 * 判断字典名字是否重复
	 * @param dictionart
	 * @return
	 */
	
	public boolean isRepeat(Dictionary dictionart)throws Exception;
	
	/**
	 * 查询证件类型
	 */
	public List<Dictionary> getIdType(String code) throws Exception;
	
	/**
	 * 获取类型信息Json
	 * @param dicType
	 * @return
	 */
	public String getDicTypesJsonSelect()throws Exception;

	/**字典类型维护----------------------------------------------------------------------------- end**/
	/**
	 * <p>方法名称: txCreateDictionary|新建保存字典信息</p>
	 * @param  dic  对象实体
	 * @return 
	 * @throws Exception
	 *//*
	public void txCreateDictionary(Dictionary dic, Person person, String ip) throws Exception;

	*//**
	 * <p>方法名称: txUpdateDictionary|更新保存字典信息</p>
	 * @param  dic  对象实体
	 * @return 
	 * @throws Exception
	 *//*
	public void txUpdateDictionary(Dictionary dic, Person person, String ip) throws Exception;
	
	*//**
	 * <p>方法名称: deleteDictionary|删除字典信息</p>
	 * @param  id  对象主键
	 * @return 
	 * @throws Exception
	 *//*
	public void txDeleteDictionary(String id, Person person, String ip) throws Exception;
	*//**
	 * <p>方法名称: txSwitchDictionary|开关字典项</p>
	 * @param  id  对象主键
	 * @return 
	 * @throws Exception
	 *//*
	public void txSwitchDictionary(String id, Person person, String ip) throws Exception;
	*//**
	 * <p>方法名称: getDictionarysJson|根据父ID获取</p>
	 * @param  dic  对象实体
	 * @return 
	 * @throws Exception
	 *//*
	public String getDictionarysJson(String pid) throws Exception;
	*//**
	 * <p>方法名称: getChildren|根据父节点ID获取所有子节点列表</p>
	 * @param  pid  父节点ID
	 * @return 
	 * @throws Exception
	 *//*
	public List getChildren(String pid);
	*//**
	 * <p>方法名称: getAllRootNode|获取所有根节点(字典分类)列表</p>
	 * @return 
	 * @throws Exception
	 *//*
	public List getAllRootNode() throws Exception;
	
	*//**
	* <p>方法名称: verifyDictionary|描述:保存提交的时候校验字典信息是否合法 </p>
	* @param  dic 字典对象
	*//*
	public void verifyDictionary(Dictionary dic,boolean isNew) throws Exception;
	
	*//**
	 * 判断当前字典下是否有子节点
	 * @param dicId   当前字典ID
	 * @return true 有 false 没有
	 *//*
	public boolean hasChildren(String dicId) throws Exception;
	
	*//**
	 * 通过字典分类编码查询子COMBOTREE树列表
	 * @param id 
	 * @return
	 *//*
	public Document BuildXMLDoc(String di)throws Exception ;
*/
	

	


	


  
}
