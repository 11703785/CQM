package com.platform.application.sysmanage.service;

import java.util.List;
import com.platform.application.sysmanage.domain.OptResource;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[OptResourceService.java]操作资源信息维护功能接口
 */
public interface OptResourceService extends GenericDao<OptResource,String> {
	
	/**
	 * <p>方法名称: txCreateOptResource|新建保存操作资源信息</p>
	 * @param  optRes  操作资源对象实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txCreateOptResource(OptResource optRes) throws Exception;

	/**
	 * <p>方法名称: txUpdateOptResource|更新保存操作资源信息</p>
	 * @param  optRes  操作资源对象实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txUpdateOptResource(OptResource optRes) throws Exception;
	
	/**
	 * <p>方法名称: txDeleteOptResource|删除操作资源信息</p>
	 * @param  optRes  操作资源对象实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txDeleteOptResource(OptResource optRes) throws Exception;
	
	/**
	 * <p>方法名称: getAllRootNode|获取所有根资源节点列表</p>
	 * @return 
	 * @throws Exception
	 */
	public List getAllRootNode() throws Exception;
	
	/**
	 * <p>方法名称: getChildren|根据父节点ID获取所有子节点列表</p>
	 * @param  pid  父节点ID
	 * @return 
	 * @throws Exception
	 */
	public List getChildren(String pid) throws Exception;
	/**
	 * <p>方法名称: getResourcesJson|根据父节点ID获取所有子节点列表</p>
	 * @param  pid  父节点ID
	 * @return 
	 * @throws Exception
	 */
	public String getResourcesJson(String pid) throws Exception;
	//根据ID查询资源
	public List<OptResource> getOptByID(String id);
}