package com.platform.application.operation.action;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.operation.service.DataFromManagerService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;

/**
 * <p>版权所有:(C)2017 rjhc</p> 
 * @作者：fengfu
 * @日期：2017-04-10 下午4:29:35 
 * @描述：[DataFromManagerAction.java]接入点维护功能相关操作
 */
@Controller
public class DataFromManagerAction extends BaseAction{

	private static final long serialVersionUID = 8429409871595811853L;
	private static final Logger logger = Logger.getLogger(DataFromManagerAction.class);
	
	private DataFromManagerService dataFromService;
	private PersonService personService = null;
	private AreaService areaService;
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Autowired
	public void setDataFromService(DataFromManagerService dataFromService) {
		this.dataFromService = dataFromService;
	}
	
	@InitBinder("sourceOrg")    
    public void initBinder1(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("sourceOrg.");
	} 
	
	private String[] ids = null;

	/**
	 * 跳转到接入点列表页面
	 * @return
	 */
	@RequestMapping("dataFromManager.action")
	public String list() {
		return "operation/dataFromManageList";
	}
	
	/**
	 * 加载列表json
	 * @return
	 */
	@RequestMapping(value = "getSourceOrgsJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSourceOrgsJSON(SourceOrg sourceOrg) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Page page=this.getPageObj();
			Person person = (Person)this.getCurrentUser();
			String id=person.getPersonId();
			List<Person> list = personService.getPersonById(id);
			if(list.size()>0){
			person=list.get(0);
			}
			json = dataFromService.getSourceOrgsJSON(person,sourceOrg,page);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load sourceOrg JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 打开新建界面
	 * @return 
	 */
	@RequestMapping("createSourceOrg.action")
	public String create(Person person,Model model){
		try {
			Area area=null;
			person=(Person) this.getCurrentUser();
			if(!person.getPersonId().equals("root")){
			Department deparment=person.getDepartment();
			 area=deparment.getArea();
			} 
			List<Area> listAreas = areaService.getAreaList();
			//this.getRequest().setAttribute("list", listAreas);
			model.addAttribute("list", listAreas);
			model.addAttribute("area", area);
			model.addAttribute("person", person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "operation/dataFromManagerInsert";
	}

	/**
	 * 打开编辑界面
	 * @return 
	 */
	@RequestMapping("editSourceOrg.action")
	public String edit(SourceOrg sourceOrg, Model model){
		try {
			sourceOrg = (SourceOrg) dataFromService.load(sourceOrg.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		model.addAttribute(sourceOrg);
		return "operation/dataFromManagerInsert";
	}
	
	/**
	 * 保存或更新
	 * @return 
	 */
	@RequestMapping(value = "saveSourceOrg.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(SourceOrg sourceOrg) {
		String msg = "true;保存接入点成功！";
		try {
			Person person = (Person)this.getCurrentUser();
	     	String areaId = this.getRequest().getParameter("areaId");
			List<Person>  list=personService.getPersonById(person.getPersonId());
			person=list.get(0);
			String ip = this.getRequest().getRemoteAddr();
			boolean isNew = StringUtil.isEmpty(sourceOrg.getId());
			if(isNew) {//新建
				dataFromService.txCreateSourceOrg(sourceOrg, person, ip,areaId);
				
			} else {//更新
				dataFromService.txUpdateSourceOrg(sourceOrg, person, ip,areaId);
			}
			//this.sendMessage("true;保存接入点成功！");
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;接入点信息提交失败：" + e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 删除
	 * @return 
	 */
	@RequestMapping(value = "deleteSourceOrg.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String delete(SourceOrg sourceOrg) {
		String msg = "true;删除成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			String ip = this.getRequest().getRemoteAddr();
			dataFromService.txDeleteSourceOrg(sourceOrg, person, ip);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;删除失败：" + e.getMessage();
		}
		return msg;
	}
	
}
