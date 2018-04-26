package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.OptResource;
import com.platform.application.sysmanage.domain.Person;

import com.platform.application.sysmanage.domain.Role;
import com.platform.application.sysmanage.service.LogService;
import com.platform.application.sysmanage.service.OptResourceService;
import com.platform.application.sysmanage.service.RoleService;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.CollectionUtil;
import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class RoleServiceImpl extends GenericHibernateDao<Role,String> implements RoleService {
	private OptResourceService optResourceService = null ;
	@Autowired
	public void setOptResourceService(OptResourceService optResourceService) {
		this.optResourceService = optResourceService;
	}
	private LogService logService = null;
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
    //新建角色
	public void txCreateRole(Role role, Person person, String ip) throws Exception {
		if(isRepeatRole(role)){
			throw new Exception("角色名或角色编码重复，创建失败！");
		}else{
			role.setRoleId(null);
			this.save(role);
			//记录操作日志
			Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
			logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
					ip, "新建了一个角色名为"+role.getRoleName()+"的新角色！");
		}
	}
    //更新角色
	public void txUpdateRole(Role role, Person person, String ip) throws Exception {
		if(isRepeatRole(role)){
			throw new Exception("角色名或角色编码重复，更新失败！");
		}else{
			Role tmpRole = this.getRoleById(role.getRoleId()).get(0);
			String name = tmpRole.getRoleName();
			tmpRole.setRoleName(role.getRoleName());
			tmpRole.setRoleCode(role.getRoleCode());
			tmpRole.setRoleScope(role.getRoleScope());
			tmpRole.setRoleType(role.getRoleType());
			tmpRole.setDescription(role.getDescription());
			this.update(tmpRole);
			//记录操作日志
			Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
			logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
					ip, "修改角色"+name+"的基本信息！");
		}
	}
     //根据ID获得角色
     public List<Role> getRoleById(String id){
	    StringBuffer sb = new StringBuffer();
	    List paras = new ArrayList(3);
	    sb.append("from Role as role where role.roleId = ?");
	    paras.add(id);
	    return find(sb.toString(), paras);
     }
	//删除角色
	public void txDeleteRole(Role role, Person person, String ip) throws Exception {
		Role bRole = (Role)this.load(role.getRoleId());
		String roleName = bRole.getRoleName();
		
		if(this.hasPerson(role.getRoleId())){
			throw new Exception("该角色已分配给用户不允许删除！");
		}
		this.delete(bRole);
		
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
				ip, "删除了一个角色名为"+roleName+"的角色！");
	}
	
	//角色树
	public String getAllRoleJson() throws Exception {
		List<Role> roles = this.loadAll();
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode node = null;
		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);
			node = new TreeNode();
			node.setText(role.getRoleName());
			node.setId(role.getRoleId());
			node.setIsParent("false");
			node.setHasChild("false");
			list.add(node);
		}
		return JsonUtil.fromCollections(list);
	}
	
	//角色配置权限
	public void txSaveAuthorize(Role role,String ids, Person person, String ip) throws Exception {
		Role bRole = this.load(role.getRoleId());
		List<OptResource> resList = new ArrayList<OptResource>();
		if(!StringUtil.isEmpty(ids)){
			String[] resArr = ids.split(",");
			OptResource res = null;
			for (int i = 0; i < resArr.length; i++) {
				if(!resArr[i].equals("-1")){
					
					res = new OptResource();
					String z=resArr[i];
					List<OptResource> optByID = optResourceService.getOptByID(z);
					res=optByID.get(0);
					OptResource parent = res.getParent();
					if(parent!=null){
					resList.add(parent);
					}
					res.setResId(resArr[i]);
					
					resList.add(res);
					
				
				}
			}
		}
		for(int x=0;x<resList.size();x++){
			for(int y=x+1;y<resList.size();y++){
				if(resList.get(x)==resList.get(y)){
					resList.remove(y);
				}
			}
		}
		bRole.setResourceList(resList);
		this.update(bRole);
		//记录操作日志
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
				ip, "给角色"+bRole.getRoleName()+"配置了权限！");
	}
	
	//获得角色JSON
	public String getRolesJSON(Role role, Page page) throws Exception {
		List<Role> roles = this.getRoles(role,page);
		Map<String,String> map = new HashMap<String,String>();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");
		return JsonUtil.fromCollections(roles, map);
	}
	//角色条件查询
	public List<Role> getRoles(Role role, Page page) throws Exception {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("from Role as role where role.roleScope != ?");
		paras.add(CurValues.SYSROLE);
		if(StringUtil.isNotBlank(role.getRoleName())){
			sb.append(" and role.roleName like '%"+role.getRoleName()+"%'");
		}
		if(StringUtil.isNotBlank(role.getRoleCode())){
			sb.append(" and role.roleCode like '%"+role.getRoleCode()+"%'");
		}
		sb.append(" order by role.roleCode asc");
		return find(sb.toString(), paras, page);
	}
    //校验角色
	public boolean isRepeatRole(Role role) throws Exception {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("from Role as role where (role.roleName = ? or role.roleCode = ?)");
		paras.add(role.getRoleName());
		paras.add(role.getRoleCode());
		if(StringUtils.isNotBlank(role.getRoleId())){
			sb.append(" and role.roleId != ?");
			paras.add(role.getRoleId());
		}
		List list = this.find(sb.toString(), paras);
		if (list.size() > 0) return true;
		return false;
	}
	//角色是否被赋予用户
	public boolean hasPerson(String id) throws Exception {
		List para = new ArrayList(2);
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Person as person left join person.roleList role where role.roleId = ? ");
		para.add(id);
		Long num = this.getRowCount(sb.toString(),para);
		if(num.intValue()>0) return true;
		return false;
	}
    //获得所有类型的角色
	@SuppressWarnings("unchecked")
	public List<Role> getAllTypeRole(Department department) throws Exception {
		String sql=null;
		if(department.getDeptId()!=null && department.getDeptId().equals("root")){
			 sql = "select role from Role role ";
		}else{
			Area area=department.getArea();
			String areaId=department.getArea().getId();
		String str = "'"+department.getArea().getLevels()+"','2'";
		String str1 = "'"+department.getDeptType()+"','03'";
		sql = "select role from Role role where role.roleScope in ("+str+") and role.roleCode != 'root' and role.roleType in ("+str1+")";
		}
		return this.find(sql);
	}
	//获得角色所拥有的权限
	@Override
	public String getRoleResourcesJson(Role role) throws JSONException {
		List<OptResource> Children = role.getResourceList();
        String resources=null;
		for(int i=0;i<Children.size();i++){
			if(i==0){
				 resources=Children.get(i).getResId()+",";
			}else{
			if(i!=(Children.size()-1)){
	        resources+=Children.get(i).getResId()+",";
			}else if(i==(Children.size()-1)){
			     resources+=Children.get(i).getResId()+"";
			}
			}
		}	
		return resources;
	}
}
