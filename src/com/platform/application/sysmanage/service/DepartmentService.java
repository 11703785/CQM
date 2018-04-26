package com.platform.application.sysmanage.service;

import java.util.List;

import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[DepartmentService.java]机构信息维护功能接口
 */
public interface DepartmentService extends GenericDao<Department,String> {
	
	/**
	 * <p>方法名称: createDepartment|新建保存机构信息</p>
	 * @param  dept  机构对象实体
	 * @return 
	 * @throws Exception
	 */
	public void txCreateDepartment(Department dept, Person person, String ip,String areaId) throws Exception;

	/**
	 * <p>方法名称: updateDepartment|更新保存机构信息</p>
	 * @param  dept  机构对象实体
	 * @return 
	 * @throws Exception
	 */
	public void txUpdateDepartment(Department dept, Person person, String ip,String areaId) throws Exception;
	
	/**
	 * <p>方法名称: deleteDepartment|删除机构信息</p>
	 * @param  id  机构对象主键
	 * @return 
	 * @throws Exception
	 */
	public void txDeleteDepartment(String id, Person person, String ip) throws Exception;
	/**
	 * <p>方法名称: getDepartmentsJson|根据父机构ID获取</p>
	 * @param  dept  机构对象实体
	 * @return 
	 * @throws Exception
	 */
	public String getDepartmentsJson(String pid, Person person) throws Exception;
	
	public List getAllChildren(String pid);
	
	/**
	* <p>方法名称: verifyDepartment|描述:保存提交机构的时候校验部门信息是否合法 </p>
	* @param  dept 部门对象
	*/
	//public void verifyDepartment(Department dept) throws Exception;
	
	/**
	 * 判断当前部门下是否有子节点、用户
	 * @param deptId   当前机构ID
	 * @return true 有 false 没有
	 */
	public boolean hasChildren(String deptId) throws Exception;
	public boolean hasUsers(String deptId) throws Exception;
	/**
	 * 导出为xml
	 * @throws Exception
	 */
//	public Document BuildXMLDoc() throws Exception;

	/**
	 * 定时任务重新编辑注册码
	 * @throws Exception
	 */
	public void txDeptUpdateRegist()throws Exception;
	
	/**
	 * 通过当前机构所属辖区编码查询同级别的兄弟辖区编码
	 * @param curCode
	 */
	public Object[] findOtherAreaCode(String curCode);
	//根据ID查机构
	public List<Department> getDepartmentById(String id);
	//获取辖区机构
	public List<Department> getDepartmentByArea(String aid);
	//根据企业或个人编码查询机构
	public List<Department> getDepartmentByCode(String code);
	//根据级别获得机构
	public List<Department> getDepartmentByLevel(String level);
	//根据企业或个人编码查询机构所在辖区
	public List<Area> getAreaByCode(String code);
}
