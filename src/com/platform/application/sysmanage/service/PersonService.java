package com.platform.application.sysmanage.service;

import java.util.List;

import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[PersonService.java]人员信息维护功能接口
 */
public interface PersonService extends GenericDao<Person,String>{
	
	/**
	 * <p>方法名称: createPerson|新建人员信息</p>
	 * @param  person  人员信息实体vo
	 * @return 
	 * @throws Exception
	 */
	public void createPerson(Person person) throws Exception;

	/**
	 * <p>方法名称: txSavePerson|保存人员信息</p>
	 * @param  person  人员信息实体vo
	 * @param  roles   人员关联角色的ID串，以,分隔
	 * @throws Exception
	 */
	public void txSavePerson(Person person,String roles,Person curPerson,String ip) throws Exception;
	
	/**
	 * <p>方法名称: txDeletePerson|批量删除人员信息</p>
	 * @param  ids  人员对象ID串
	 * @throws Exception
	 */
	public void txDeletePerson(String ids,Person curPerson,String ip) throws Exception;
	/**
	 * <p>方法名称: txResetPassword|批量重置用户密码</p>
	 * @param  ids  人员对象ID串
	 * @throws Exception
	 */
	public void txResetPassword(String ids,Person curPerson,String ip) throws Exception;
	
	/**
	 * 分页获取当前机构下的用户JSON串
	 * @param person
	 * @param page
	 * @param
	 * @return
	 */
	public String getPersonsJSON(Person person, Page page) throws Exception;

	/**
	 * 方法说明: 判断当前用户是否有重复
	 * @param person 要判断的用户对象
	 * @return
	 */
	public void isRepeat(Person person) throws Exception;
	public Person validateLoginUser(Person person) throws Exception;
	
	//根据ID获得用户
	public List<Person> getPersonById(String id);
	
	
	//根据部门ID获得用户
	public List<Person> getPersonByDept(String deptId) throws Exception;
	
}