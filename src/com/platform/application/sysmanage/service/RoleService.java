package com.platform.application.sysmanage.service;

import java.util.List;

import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[RoleService.java]角色维护功能接口
 */
public interface RoleService extends GenericDao<Role,String> {
	
	/**
	 * <p>方法名称: txCreateRole|新建保存角色</p>
	 * @param  role  角色实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txCreateRole(Role role, Person person, String ip) throws Exception;

	/**
	 * <p>方法名称: txUpdateRole|更新保存角色</p>
	 * @param  role  角色实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txUpdateRole(Role role, Person person, String ip) throws Exception;
	
	/**
	 * <p>方法名称: txDeleteRole|删除角色</p>
	 * @param  role  角色实体vo
	 * @return 
	 * @throws Exception
	 */
	public void txDeleteRole(Role role, Person person, String ip) throws Exception;
	
	/**
	 * <p>方法名称: getRoles|获取角色分页信息</p>
	 * @param  role  角色实体vo
	 * @param  page  分页信息对象
	 * @return 
	 * @throws Exception
	 */
	public List<Role> getRoles(Role role, Page page) throws Exception;
	
	/**
	 * 角色列表
	 * @param role
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String getRolesJSON(Role role, Page page) throws Exception;
	
	/**
	 * 加载角色树
	 * @return
	 * @throws Exception
	 */
	public String getAllRoleJson() throws Exception;

	/**
	 * <p>方法名称: isRepeatRole|判断当前角色是否有重复</p>
	 * @param  role  要判断的角色实体vo
	 * @return true 重复 false 不重复
	 * @throws Exception
	 */
	public boolean isRepeatRole(Role role) throws Exception;
	/**
	 * <p>方法名称: getRoleOptResources|根据角色ID获取角色所持有的资源</p>
	 * @param  role  当前角色
	 * @return 角色所持有的资源ID串，以,分隔
	 * @throws Exception
	 */	
    public String getRoleResourcesJson(Role role)throws Exception;

	/**
	 * <p>方法名称: txSaveAuthorize|保存角色授权信息</p>
	 * @param  role  当前角色    含角色ID需重新load
	 * @param  ids   授权包含的操作资源ID串，以,分隔
	 * @throws Exception
	 */
	public void txSaveAuthorize(Role role,String ids, Person person, String ip) throws Exception;
	
	public boolean hasPerson(String id) throws Exception;

	/**
	 * 根据辖区，区分不同类型的角色
	 * @param department
	 * @return
	 * @throws Exception
	 */
	public List<Role> getAllTypeRole(Department department) throws Exception;
	//根据ID获得ROLE
	public List<Role> getRoleById(String id);
	
	
}
