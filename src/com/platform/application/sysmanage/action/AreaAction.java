package com.platform.application.sysmanage.action;

import java.util.ArrayList;
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

import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

@Controller
public class AreaAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(AreaAction.class);
	private AreaService areaService;
	PersonService personService;
	private DepartmentService departmentService;
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}


	@InitBinder("area")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("area.");
    }
	/**
	 * 跳转到主页面辖区
	 * @return
	 */
	@RequestMapping("areaManage.action")
	public String redirect(){
		return "sysmanage/areaFrame";
	}
	/**
	 * 加载辖区列表
	 */
	@RequestMapping(value = "loadArea.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String loadArea(String id){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person)this.getCurrentUser();
			Area area = new Area();
			person = (Person)personService.load(person.getPersonId());
			if(!person.getDepartment().getDeptId().equals("root")){
				area = (Area)person.getDepartment().getArea();
				json = areaService.getArea(id,person,area);
			}else{
				json = areaService.getArea(id);//root用户
			}
			
			if(StringUtil.isEmpty(json)) 
				json = RESULT_EMPTY_DEFAULT;
			logger.debug("load OptResourceJSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查看辖区详情
	 */
	@RequestMapping("viewArea.action")
	public String view(Area area, Model model){
		try {
			if(StringUtil.isNotBlank(area.getId())){
				if(!"-1".equals(area.getId())){
					area = (Area) areaService.load(area.getId());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(area);
		return "sysmanage/areaView";
	}
	/**
	 * 保存新增辖区	
	 */
	@RequestMapping(value = "saveArea.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveArea(Area area){
		String msg=null;
		
		msg = "true;保存成功！";
		try {
			boolean isNew = StringUtil.isEmpty(area.getId());
			if(isNew) {//新建
				areaService.txSaveArea(area);
			} else {//更新
				areaService.txEditArea(area);
			}
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;提交失败：" + e.getMessage();
		}
		
		return msg;
	}
	/**
	 * 方法说明: 新建辖区
	 */
	@RequestMapping("createArea.action")
	public String createArea(Area area, Model model) {
		try {
			area =(Area) areaService.load(area.getId());
			if(area!=null && !area.getLevels().equals("3")){
				Area parent = (Area) areaService.load(area.getId());
				area = new Area();
				area.setParent(parent);
				area.setLevels("9");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(area);
		return "sysmanage/areaEdit";
	}
	

	/**
	 * 方法说明: 编辑
	 */
	@RequestMapping("editArea.action")
	public String edit(Area area, Model model) {
		try {
			if(StringUtil.isNotBlank(area.getId())){
				area = (Area) areaService.load(area.getId());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(area);
		return "sysmanage/areaEdit";
	}
	
	/**
	 * 方法说明: 删除部门
	 */
	@RequestMapping(value = "deleteArea.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteArea(Area area) {
		String msg = "true;操作删除成功！";
		try {
			boolean flag = true;
			flag = isExist(area);//校验辖区下面是否存在下级辖区
			//areaService.txLinshiUpdateAllArea();
			if(flag){
				areaService.txDeleteArea(area);
			}else{
				msg = "false;存在下级辖区不可删除！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;操作删除失败：" + e.getMessage();
		}
		return msg;
	}
	/**
	 * 校验是否存在下属辖区
	 */
	private boolean isExist(Area area){
		boolean flag = true;
		if(area != null){
			List list = new ArrayList();
			list = areaService.getParentList(area);
			if(list.size() > 0){
				flag = false;
			}else{
				flag = true;
			}
		}
		return flag;
	}
	
	
	
	/**按辖区管理机构 **/
	@RequestMapping("ManagerByArea.action")
	public String MBAmain() {
		return "sysmanage/managerByAreaFrame";
	}
	
	/**
	 * 部门树Json生成
	 */
	@RequestMapping(value = "loadManagerByAreaJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadManagerByAreaJson(String id) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person)this.getCurrentUser();
			List<Person> personById = personService.getPersonById(person.getPersonId());
			person = personById.get(0);
			if(id!=null){
			id=id.substring(0,id.indexOf("_"));
			}
			json = areaService.getManagerByAreaJson(person, id);
			if(StringUtil.isEmpty(json)) {
			   json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load DepartmentJSON:" + json);
			//this.sendJSON(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("viewManagerByArea.action")
	public String view(Model model,Area area){
	
		Department dept=null;
		String id=area.getId();
		String  x = null;
		String  y=null;
		try {
			if(StringUtil.isNotBlank(id)){
			
				 x=id.substring(id.indexOf("_"),id.length());
				 y=id.substring(0,id.indexOf("_"));
				if(x.equals("_area")){
				if(!"-1".equals(y)){
					area = (Area) areaService.load(y);
				}
				}
				if(x.equals("_dept")){
					if(!"-1".equals(y)){
						dept = (Department) departmentService.get(y);
						model.addAttribute(dept.getArea());
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		if(x.equals("_area")){
		model.addAttribute(area);
		return "sysmanage/areaView";
		}
		if(x.equals("_dept")){
			model.addAttribute(dept);
			return "sysmanage/departmentView";
			}
		return null;
	}
	

}
