package com.platform.application.monitor.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.monitor.service.QyQueryService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.core.action.BaseAction;

@Controller
public class QyQueryAction extends BaseAction{
	private QyQueryService qyQueryService;
	private DepartmentService deptService;
	private PersonService personService;
	@Autowired
	public void setQyQueryService(QyQueryService qyQueryService) {
		this.qyQueryService = qyQueryService;
	}
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	@Autowired
	public void setDepartmentService(DepartmentService deptService) {
		this.deptService = deptService;
	}
	//企业机构征信查询图
	@RequestMapping("qyqueryPersonal.action")
	public String queryPersonal(Model model) throws Exception{
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		String departmentId=this.getRequest().getParameter("queryOrgName");
		 String level=this.getRequest().getParameter("level");
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		 if(departmentId!=null&&departmentId.length()>=15){//去掉undefined
			 departmentId=departmentId.substring(9);			 
		 }
		Map<String,String> maps = qyQueryService.departmentCount(beginTime,endTime,departmentId,person,level);
		model.addAttribute("maps", maps);
		return "monitor/qypersonalQuery";
	}
	//企业原因查询统计图
	@RequestMapping("qyqueryReason.action")
	public String queryReason(Model model) throws Exception{
		String qyreason=this.getRequest().getParameter("qyreason");
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		Map<String,String> maps = qyQueryService.resorceCount(beginTime, endTime,qyreason);
         List<Dictionary> allReason = qyQueryService.getAllReason();
		
		model.addAttribute("reason", allReason);
		model.addAttribute("maps", maps);
		return "monitor/qyreasonQuery";
	}
	//企业年度对比
	@RequestMapping("qycontrast.action")
	public String contrast(String str,Model model) throws Exception{
		String year1 = this.getRequest().getParameter("year1");
		String year2 = this.getRequest().getParameter("year2");
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		Map<String,String> maps = qyQueryService.yearCount(year1, year2,person);
		model.addAttribute("maps", maps);
		return "monitor/qycontrastQuery";
	}
	//企业辖区查询统计图
	@RequestMapping("qyqueryArea.action")
	public String queryArea(Model model) throws Exception{
		String areaId=this.getRequest().getParameter("area");
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		 String level=this.getRequest().getParameter("level");

		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		 if(areaId!=null&&areaId.length()>=12){//去掉undefined
			 areaId=areaId.substring(9);			 
		 }
		Map<String,String> maps = qyQueryService.areaCount(beginTime, endTime, areaId, person,level);		
		model.addAttribute("maps", maps);
		return "monitor/qyareaQuery";
	}
	//企业用户查询统计图
	@RequestMapping("qyqueryPerson.action")
	public String queryPerson(Model model) throws Exception{
		String departmentId=this.getRequest().getParameter("queryOrgName");
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		 if(departmentId!=null&&departmentId.length()>=15){//去掉undefined
			 departmentId=departmentId.substring(9);			 
		 }
		Map<String,String> maps = qyQueryService.personCount(beginTime, endTime, departmentId, person);		
		model.addAttribute("maps", maps);
		return "monitor/qypersonQuery";
	}
	//企业查询统计图主页面
	@RequestMapping("qyQueryMain.action")
	public String qyMain(){
		return "monitor/qyQueryMain";
	}
}
