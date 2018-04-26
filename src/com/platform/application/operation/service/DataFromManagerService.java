package com.platform.application.operation.service;

import java.util.List;

import com.platform.application.sysmanage.domain.Person;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * <p>版权所有:(C)2017 rjhc</p> 
 * @作者：fengfu
 * @描述：[DataFromManagerService.java]接入点管理功能接口
 */
public interface DataFromManagerService extends GenericDao<SourceOrg,String> {
	
	/**
	 * <p>方法名称: 新建保存接入点</p>
	 * @param  SourceOrg  实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txCreateSourceOrg(SourceOrg sourceOrg, Person person, String ip,String areaId) throws Exception;

	/**
	 * <p>方法名称: 更新保存</p>
	 * @param  SourceOrg  实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txUpdateSourceOrg(SourceOrg sourceOrg, Person person, String ip,String areaId) throws Exception;
	
	/**
	 * <p>方法名称: 删除</p>
	 * @param  SourceOrg  实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txDeleteSourceOrg(SourceOrg sourceOrg, Person person, String ip) throws Exception;
	
	/**
	 * 信息列表
	 * @param person 
	 * @param sourceOrg
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String getSourceOrgsJSON(Person person, SourceOrg sourceOrg, Page page) throws Exception;
	
	//根据个人或企业编码查询接入点
	public List<SourceOrg> getSourceOrgByCode(String code);
	
}
