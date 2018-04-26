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
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

@Controller
public class DepartmentAction extends BaseAction {

	private static final long serialVersionUID = -7762014198187045871L; 
	private static final Logger logger = Logger.getLogger(DepartmentAction.class);
	private DepartmentService departmentService = null;
	private PersonService personService;
	private AreaService  areaService;
	
	
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@InitBinder("department")
    public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("department.");
    } 
	
	@RequestMapping("listDepartment.action")
	public String redirect() {
		return "sysmanage/departmentFrame";
	}
	/**
	 * 方法说明: 新建部门
	 */
	@RequestMapping("createDepartment.action")
	public String create(Department department, Model model) {
		try {
			Person person = (Person)this.getCurrentUser();
			person = (Person)personService.load(person.getPersonId());
			Department parent = new Department();
			// 获取所属部门对象信息
			if (department != null && StringUtil.isNotEmpty(department.getDeptId())) {
				parent = (Department) departmentService.load(department.getDeptId());
			}
			List listAreas = areaService.getAreaList();
			if(!StringUtil.equals(person.getLoginName(), "root")){
				String level = person.getDepartment().getArea().getLevels();
				if(StringUtil.equals(level, "1")){
					listAreas.clear();
					listAreas.add(person.getDepartment().getArea());
				}
			}
			
			model.addAttribute("listAreas", listAreas);
			model.addAttribute("parent",parent);
			model.addAttribute("department",new Department());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sysmanage/departmentEdit";
	}

	/**
	 * 方法说明: 查看部门信息
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("viewDepartment.action")
	public String view(Department department, Model model) {
		try {
			if (department != null) {
				if (StringUtil.isNotEmpty(department.getDeptId())) {
					department = (Department) departmentService.load(department.getDeptId());
					model.addAttribute(department.getArea());

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
				model.addAttribute(department);
		return "sysmanage/departmentView";
	}
	
	/**
	 * 部门树Json生成
	 */
	@RequestMapping(value = "loadDeptJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadDeptJson(String id) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person)this.getCurrentUser();
			person = (Person)personService.load(person.getPersonId());
			json = departmentService.getDepartmentsJson(id,person);
			if(StringUtil.isEmpty(json)) {
			   json = RESULT_EMPTY_DEFAULT;
			}
			logger.info("load DepartmentJSON:" + json);
			//this.sendJSON(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	/*//**
	 * 部门树Json生成
	 *//*
	@Action(value="loadPersonDeptJson")
	public void loadPersonDeptJson() {
		try {
			String pid = this.getRequest().getParameter("node");
			String deptjson = departmentService.getDepartmentsJson(pid,(Person)this.getCurrentUser());
			if(StringUtil.isEmpty(deptjson)) deptjson = RESULT_EMPTY_DEFAULT;
			logger.debug("load DepartmentJSON:" + deptjson);
			this.sendJSON(deptjson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 方法说明: 编辑
	 */
	@RequestMapping("editDepartment.action")
	public String edit(Department department, Model model) {
		if (department != null && StringUtil.isNotEmpty(department.getDeptId())){
			try {
				Person person = (Person)this.getCurrentUser();
				person = (Person)personService.load(person.getPersonId());
				department = (Department) departmentService.load(department.getDeptId());
				List listAreas = areaService.getAreaList();
				if(!StringUtil.equals(person.getLoginName(), "root")){
				String level = person.getDepartment().getArea().getLevels();
				if(StringUtil.equals(level, "1")){
					listAreas.clear();
					listAreas.add(person.getDepartment().getArea());
				}
				}
				model.addAttribute("listAreas", listAreas);
				Department parent = department.getParent();
				model.addAttribute("parent",parent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute(department);
		return "sysmanage/departmentEdit";
	}

	/**
	 * 方法说明: 保存部门,ID为空为新建,否则为更新
	 */
	@RequestMapping(value = "saveDepartment.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(Department department ) {
		String msg = "true;保存机构信息成功！";
		try {
			
			String aname = this.getRequest().getParameter("areaId");
			Person person = (Person)this.getCurrentUser();
			String ip = this.getRequest().getRemoteAddr();
			person = (Person) personService.load(person.getPersonId());
			if(StringUtil.isEmpty(department.getDeptId())) {//新建
				departmentService.txCreateDepartment(department, person, ip,aname);
			} else {//更新
				departmentService.txUpdateDepartment(department, person, ip,aname);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;机构信息提交失败：" + e.getMessage();
		}
		return msg;
	}

	/**
	 * 方法说明: 删除部门
	 */
	@RequestMapping(value = "deleteDepartment.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String delete(Department department) {
		String msg = "true;机构删除成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			person = (Person)personService.load(person.getPersonId());
			String ip = this.getRequest().getRemoteAddr();
			departmentService.txDeleteDepartment(department.getDeptId(), person, ip);
			this.sendMessage("true;删除机构成功！");
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;删除机构失败：" + e.getMessage();
		}
		System.out.println(msg);
		return msg;
	}
	
	/**
	 * 方法说明：下载xml
	 */
	/*@Action(value = "departmentDownloadXML")
	public void departmentDownloadXML(){
		
//		BufferedInputStream is = null;
//		InputStream cfgin=null;
		ByteArrayOutputStream os=null;
		ByteArrayInputStream is=null;
		
		try {
			
			//String attid=this.getRequest().getParameter("attid");
			
			Document doc =departmentService.BuildXMLDoc();
			if(doc!=null){
		
				XMLOutputter xmlOut = new XMLOutputter();
				
				os=new ByteArrayOutputStream();
				xmlOut.output(doc, os);
				is=new ByteArrayInputStream(os.toByteArray());
				
				this.getResponse().reset();
				this.getResponse().setContentType("bin");
				HttpServletResponse response=ServletActionContext.getResponse();
				
				
				response.addHeader("content-type", "application/octet-stream");
				response.addHeader("Content-Disposition","attachment; fileName=\""
						+ URLEncoder.encode("行政区划管理.xml","UTF-8"));
				
				byte[] bytes = new byte[is.available()];
				int len = bytes.length;
				int offset = 0;
				int read = 0;
				while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
					offset += read;
					response.getOutputStream().write(bytes, 0, len);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception：" + e.getMessage());
		}finally{
			try {
				if(os!=null)
					os.close();
				if(is!=null)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	*/

	
	
	
}