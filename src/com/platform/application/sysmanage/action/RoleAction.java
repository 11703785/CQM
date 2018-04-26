package com.platform.application.sysmanage.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.application.sysmanage.service.RoleService;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[RoleAction.java]角色维护功能相关操作
 */
@Controller
public class RoleAction extends BaseAction {
	
	private static final long serialVersionUID = 8429409871595811853L;
	private static final Logger logger = Logger.getLogger(RoleAction.class);
	
	private RoleService roleService = null;
	private PersonService personService = null;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@InitBinder("role")    
    public void initBinder1(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("role.");
	} 
	
	private String[] ids = null;

	/**
	 * 跳转到角色列表
	 * @return
	 */
	@RequestMapping("listRole.action")
	public String list() {
		return "sysmanage/roleList";
	}
	
	/**
	 * 加载角色列表json
	 * @return
	 */
	@RequestMapping(value = "getRolesJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRolesJSON(Role role) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Page page=this.getPageObj();
			int pageIndex = page.getPageIndex();
			int pageCount = page.getPageCount();
			int pageSize = page.getPageSize();
			json = roleService.getRolesJSON(role, page);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load role JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 打开新建角色界面
	 * @return 
	 */
	@RequestMapping("createRole.action")
	public String create(){
		return "sysmanage/roleEdit";
	}

	/**
	 * 打开编辑角色界面
	 * @return 
	 */
	@RequestMapping("editRole.action")
	public String edit(Role role, Model model){
		try {
			role = (Role) roleService.load(role.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		model.addAttribute(role);
		return "sysmanage/roleEdit";
	}
	
	/**
	 * 保存或更新角色
	 * @return 
	 */
	@RequestMapping(value = "saveRole.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(Role role) {
		String msg = "true;保存角色成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			String ip = this.getRequest().getRemoteAddr();
			boolean isNew = StringUtil.isEmpty(role.getRoleId());
			if(isNew) {//新建
				roleService.txCreateRole(role, person, ip);
			} else {//更新
				roleService.txUpdateRole(role, person, ip);
			}
			//this.sendMessage("true;保存角色成功！");
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;角色信息提交失败：" + e.getMessage();
		}
		return msg;
	}
	/**
	 * 删除角色
	 * @return 
	 */
	@RequestMapping(value = "deleteRole.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String delete(Role role) {
		String msg = "true;角色删除成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			String ip = this.getRequest().getRemoteAddr();
			roleService.txDeleteRole(role, person, ip);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;角色删除失败：" + e.getMessage();
		}
		return msg;
	}
	
	//===================================权限管理=================================
	/**
	 * 跳转到权限管理页面
	 * @return
	 */
	@RequestMapping("mainAuthorize.action")
	public String redirect() {
		return "sysmanage/authorizeMain";
	}
	
	//获得角色json
	@RequestMapping(value = "getAllRoleJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAllRoleJSON() {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			json = roleService.getAllRoleJson();
			if (StringUtil.isEmpty(json)) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load role JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * <p>方法名称: getRoleOptResources|获取角色下的资源ID串</p>
	 */
	@RequestMapping(value = "getRoleOptResources.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRoleOptResources(Role role) {
	
		 String x=null;
		try {
			if(role.getRoleId()!=null&&!role.getRoleId().equals("")){
			List<Role> rolelist= roleService.getRoleById(role.getRoleId());
			role=rolelist.get(0);
			}
	
		
		   x=roleService.getRoleResourcesJson(role);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
  		return x;
	}
	


	
	/**
	 * <p>方法名称: saveAuthorize|保存角色授权</p>
	 */
	@RequestMapping(value = "saveAuthorize.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveAuthorize(Role role, String resIds) {
		String msg = "true;角色授权信息保存成功！";
		try {
			
			Person person = (Person)this.getCurrentUser();
			String ip = this.getRequest().getRemoteAddr();
			person = (Person) personService.load(person.getPersonId());
			roleService.txSaveAuthorize(role, resIds, person, ip);
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;角色授权信息保存失败：" + e.getMessage();
		}
		return msg;
	}

}