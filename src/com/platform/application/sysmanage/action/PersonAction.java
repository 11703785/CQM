package com.platform.application.sysmanage.action;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.application.sysmanage.service.RoleService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;

@Controller
public class PersonAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(PersonAction.class);
	//private Person person = null;
	//private String ids = null;
	private PersonService personService;
	private DepartmentService departmentService;
	private RoleService roleService;
	@Autowired
	public void setPersonService(PersonService personService){
		this.personService = personService;
	}
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	@InitBinder("person")    
    public void initBinder1(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("person.");    
    }  
	
//	@Actions({
//		@Action(value="mainPerson",results={@Result(name="success",location="sysmanage/personFrame.jsp")}),
//		@Action(value="listPerson",results={@Result(name="success",location="sysmanage/personList.jsp")})
//	})
	/**
	 * 跳转到用户管理页面
	 * @return
	 */
	@RequestMapping("mainPerson.action")
	public String redirect() {
		return "sysmanage/personFrame";
	}
	/**
	 * 打开用户列表
	 * @return
	 */
	@RequestMapping("listPerson.action")
	public String listPerson(Person person,Model model) {
		Department department = (Department)departmentService.load(person.getDepartment().getDeptId());
		//model.addAttribute(person);
		model.addAttribute(department);
		return "sysmanage/personList";
	}
	//部门下的用户
	@RequestMapping("listPersonByDept.action")
	public void listPersonByDept(String deptId,Model model) throws IOException {
 		String json = RESULT_EMPTY_DEFAULT;
 		deptId=this.getRequest().getParameter("deptId");
 		Page page=this.getPageObj();
 		try{
 			List<Person> personByDept = personService.getPersonByDept(deptId);
 			String z="";
 			for(int i=0;i<personByDept.size();i++){
 				if(i!=(personByDept.size()-1)){
 				z+="{\"id\":\""+personByDept.get(i).getPersonName()+"\",\"text\":\""+personByDept.get(i).getPersonName()+"\"},";
 				}else{
 	 		    z+="{\"id\":\""+personByDept.get(i).getPersonName()+"\",\"text\":\""+personByDept.get(i).getPersonName()+"\"}";
 				}
 			}
 			
            if (StringUtil.isBlank(json)) {
				json = RESULT_EMPTY_DEFAULT;
			}
            json="["+z+"]";
			logger.debug("load person JSON:" + json);
 		} catch (Exception e) {
			e.printStackTrace();
		}
 		System.out.println(json);
 		this.getResponse().getWriter().write(json);
 		this.getResponse().getWriter().close();
	}
	
	//用户Json
	@RequestMapping(value = "getPersonsJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPersonsJSON(Person person){
 		String json = RESULT_EMPTY_DEFAULT;
		try {
			Page page=this.getPageObj();
			json = personService.getPersonsJSON(person, page);
			if (StringUtil.isBlank(json)) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load person JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 方法说明: 新建用户
	 */
	@RequestMapping("createPerson.action")
	public String create(Person person,Model model){
		Department department=null;
		try{
			String id=person.getDepartment().getDeptId();
			List<Department> lsit = departmentService.getDepartmentById(id);
			for(int i=0;i<lsit.size();i++){
				 department=lsit.get(i);
			}
				
			List<Role> list = roleService.getAllTypeRole(department);
			int size = list.size();
			int ismod=size%2;
			model.addAttribute("ismod",ismod);
			model.addAttribute(department);
			model.addAttribute("roleList",list);
			model.addAttribute("ids","");
		}catch (Exception e){
			logger.error(e);
		}
		return "sysmanage/personEdit";
	}

	 
	/**
	 * 方法说明: 编辑用户
	 */
	@RequestMapping("editPerson.action")
	public String edit(Person person,Model model){
		
		try{
			
			String ids = "";
			if(StringUtil.isNotEmpty(person.getPersonId())){
				person = (Person)personService.load(person.getPersonId());
				ids = person.getRoleIds();
			}
			Department department = (Department)departmentService.load(person.getDepartment().getDeptId());
			List<Role> list = roleService.getAllTypeRole(department);
			
			int size = list.size();
			int ismod=size%2;
			model.addAttribute("ismod",ismod);
			model.addAttribute("roleList",list);
			model.addAttribute(department);
			model.addAttribute(person);
			model.addAttribute("ids",ids);
		}catch (Exception e){
			logger.error(e);
		}
		return "sysmanage/personEdit";
		
	}

	/**
	 * 方法说明: 查看
	 * @param
	 * @return
	 */
	@RequestMapping("viewPerson.action")
	public String view(Person person,Model model){
try{
			
			String ids = "";
			if(StringUtil.isNotEmpty(person.getPersonId())){
				person = (Person)personService.load(person.getPersonId());
				ids = person.getRoleIds();
			}
			Department department = (Department)departmentService.load(person.getDepartment().getDeptId());
			List<Role> list = roleService.getAllTypeRole(department);
			
			int size = list.size();
			int ismod=size%2;
			model.addAttribute("ismod",ismod);
			model.addAttribute("roleList",list);
			model.addAttribute(department);
			model.addAttribute(person);
			model.addAttribute("ids",ids);
		}catch (Exception e){
			logger.error(e);
		}
	
		return "sysmanage/personView";
	}

	/**
	 * 方法说明: 保存或更新用户对象
	 */
	@RequestMapping(value = "savePerson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(Person person){
		String msg = "true;保存用户成功！";
		try{
			 String roleids=this.getRequest().getParameter("ids");
			Person curPerson = (Person)this.getCurrentUser();
			String ip = this.getRequest().getRemoteAddr();
			curPerson = (Person)personService.load(curPerson.getPersonId());
			personService.txSavePerson(person, roleids,curPerson,ip);
		}catch (Exception e){
			logger.error(e);
			msg = "false;用户信息提交失败：" + e.getMessage();
		}
		return msg;
	}

	/**
	 * 方法说明: 删除用户
	 */
	@RequestMapping(value = "deletePerson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String delete(String ids){
		String msg = "true;用户删除成功！";
		try{
			Person curPerson = (Person)this.getCurrentUser();
			String ip = this.getRequest().getRemoteAddr();
			curPerson = (Person)personService.load(curPerson.getPersonId());
			if(StringUtil.equals(ids, curPerson.getPersonId())){
				msg = "false;用户删除失败：所删除的用户与登录用户一致，无法删除";
			}else{
				personService.txDeletePerson(ids,curPerson,ip);
			}
		}catch (Exception e){
			e.printStackTrace();
			msg = "false;用户删除失败：" + e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 方法说明: 重置用户密码
	 */
	@RequestMapping(value="resetPassword.action")
	public String resetPassword(String ids){
		String msg = "true;用户重置密码成功！";
		try{
			Person curPerson = (Person)this.getCurrentUser();
			curPerson = (Person)personService.load(curPerson.getPersonId());
			String ip = this.getRequest().getRemoteAddr();
			personService.txResetPassword(ids,curPerson,ip);
			//this.sendMessage("true;用户重置密码成功！");
		}catch (Exception e){
			e.printStackTrace();
			//this.sendMessage("false;用户重置密码失败：" + e.getMessage());
			msg = "false;用户重置密码失败：" + e.getMessage();
		}
		return msg;
	}
	

}