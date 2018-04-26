package com.platform.application.monitor.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.monitor.service.CreditQueryService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.core.action.BaseAction;
/***
 * 征信查询  
 *
 */
@Controller
public class CreditQueryAction extends BaseAction{
	private CreditQueryService creditQueryService;
	private DepartmentService deptService;
	private PersonService personService;
	@Autowired
	public void setCreditQueryService(CreditQueryService creditQueryService) {
		this.creditQueryService = creditQueryService;
	}
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	@Autowired
	public void setDepartmentService(DepartmentService deptService) {
		this.deptService = deptService;
	}
	//个人机构征信查询统计图
	@RequestMapping("grqueryPersonal.action")
	public String queryPersonal(Model model,String departmentId) throws Exception{
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		 departmentId=this.getRequest().getParameter("queryOrgName");
		 String level=this.getRequest().getParameter("level");
		 if(departmentId!=null&&!departmentId.equals("")&&departmentId.substring(0,1).equals("u")){//去掉undefined
			 departmentId=departmentId.substring(9);			 
		 }
		
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		Map<String,String> maps = creditQueryService.departmentCount(beginTime,endTime,departmentId,person,level);
		model.addAttribute("maps", maps);
		return "monitor/personalQuery";
	}
	//个人原因查询统计图
	@RequestMapping("grqueryReason.action")
	public String queryReason(Model model) throws Exception{
		String grreason=this.getRequest().getParameter("grreason");
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		Map<String,String> maps = creditQueryService.resorceCount(beginTime, endTime,grreason,person);
		//所有查询原因
		List<Dictionary> allReason = creditQueryService.getAllReason();
		model.addAttribute("reason", allReason);
		model.addAttribute("maps", maps);
		return "monitor/grreasonQuery";
	}
	//个人征信查询走势图
	@RequestMapping("grcontrast.action")
	public String contrast(String str,Model model) throws Exception{
 		String json = RESULT_EMPTY_DEFAULT;
 		String json2 = RESULT_EMPTY_DEFAULT;
        String z="";
        String y="";
        Map<String,String> map=new HashMap<String,String>();
	    List times=new ArrayList();
	    Date start=new Date();
	    Date end=new Date();
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd" );
		 SimpleDateFormat sdfmonth =  new SimpleDateFormat( "yyyy-MM" );
		String year1 = this.getRequest().getParameter("year1");
		if(year1!=null&&year1!=""){		
			 start = sdf.parse(year1);
         }
		String year2 = this.getRequest().getParameter("year2");
		if(year2!=null&&year2!=""){		
			 end = sdf.parse(year2);
         }
		String type=this.getRequest().getParameter("type");//查询类型
		String time=this.getRequest().getParameter("time");//时间刻度
		String dept=this.getRequest().getParameter("dept");
		String area=this.getRequest().getParameter("area");
		String level=this.getRequest().getParameter("level");//查询级别
		 if(dept!=null&&!dept.equals("")&&dept.substring(0,1).equals("u")){//去掉undefined
			 dept=dept.substring(9);			 
		 }
		 if(area!=null&&!area.equals("")&&area.substring(0,1).equals("u")){//去掉undefined
			 area=area.substring(9);			 
		 }
		Calendar now = Calendar.getInstance();  
	      int year=now.get(Calendar.YEAR);
	      int month=now.get(Calendar.MONTH)+1;
	      int day=now.get(Calendar.DATE);
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		if(time==null||time.equals("0")||time.equals("")){
			if((year1==null&&year2==null)||(year1.equals("")&&year2.equals(""))){
			for(int i=1;i<day+1;i++){
		        if(i<10){
		        	 if(month<10){
		        	times.add(year+"-0"+month+"-0"+i);
		        	 }else{
				        	times.add(year+"-"+month+"-0"+i); 
		        	 }
		        }else{
		        	 if(month<10){
				        	times.add(year+"-0"+month+"-"+i);
				        	 }else{
						        	times.add(year+"-"+month+"-"+i); 
				        	 }	
		        }
			}
			model.addAttribute("time",times);
			}else if(year1!=null&&year2!=null){
			long days=(end.getTime()-start.getTime())/(24*60*60*1000);	
			   for(long i=0;i<days;i++){
				   times.add(sdf.format(new Date(start.getTime()+ (long)i * 24 * 60 * 60 * 1000)));
			   }
				model.addAttribute("time",times);
			}
		}else if(time.equals("1")){
			if((year1==null&&year2==null)||(year1.equals("")&&year2.equals(""))){
			for(int i=1;i<month+1;i++){
				if(i<10){
				times.add(year+"-0"+i);
				}else{
					times.add(year+"-"+i);
				}
			}
			model.addAttribute("time",times);
			}else if(year1!=null&&year2!=null){
				   String str1 = year1;
				   String str2 = year2;
				   Calendar bef = Calendar.getInstance();
				   Calendar aft = Calendar.getInstance();
				   bef.setTime(sdfmonth.parse(str1));
				   aft.setTime(sdfmonth.parse(str2));
				   int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
				   int month1 = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR))*12;
				   int monthresult=month1+result;//计算月份差
				Calendar rightNow = Calendar.getInstance();
				for(int i=0;i<monthresult+1;i++){
				rightNow.setTime(start);
				rightNow.add(Calendar.MONTH,i);//月份增长
				Date dt1=rightNow.getTime();
				times.add(sdfmonth.format(dt1));
				}
				model.addAttribute("time",times);
			}
		}else if(time.equals("2")){
			for(int i=2017;i<year+1;i++){
				times.add(i);
			}
			model.addAttribute("time",times);
		}
		Map<String,List> maps = creditQueryService.yearCount(year1, year2,person,type,time,times,dept,area,level);
		List keys = maps.get("keys");
		List values = maps.get("values");
		for(int i=0;i<keys.size();i++){
			if(i==keys.size()-1){
				z+="{ name:'"+keys.get(i)+"',type:\'line\', data:"+values.get(i)+"}";
			}else{
				z+="{ name:'"+keys.get(i)+"',type:\'line\', data:"+values.get(i)+"},";	
			}
		}
		for(int i=0;i<keys.size();i++){
			if(i==keys.size()-1){
				y+="'"+keys.get(i)+"'";
			}else{
				y+="'"+keys.get(i)+"',";
			}
		}
		json2="["+y+"]";
        json="["+z+"]";
        model.addAttribute("json2", json2);
        model.addAttribute("json", json);
		return "monitor/grcontrastQuery";
	}
	
	
	//个人辖区查询统计图
	@RequestMapping("grqueryArea.action")
	public String queryArea(Model model) throws Exception{
		String areaId=this.getRequest().getParameter("area");
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		 String level=this.getRequest().getParameter("level");
		 if(areaId!=null&&!areaId.equals("")&&areaId.substring(0,1).equals("u")){//去掉undefined
			 areaId=areaId.substring(9);			 
		 }
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		Map<String,String> maps = creditQueryService.areaCount(beginTime, endTime, areaId, person,level);		
		model.addAttribute("maps", maps);
		return "monitor/grareaQuery";
	}
	//个人用户查询统计图
	@RequestMapping("grqueryPerson.action")
	public String queryPerson(Model model) throws Exception{
		String departmentId=this.getRequest().getParameter("queryOrgName");
		String beginTime = this.getRequest().getParameter("beginTime");
		String endTime = this.getRequest().getParameter("endTime");
		 String level=this.getRequest().getParameter("level");
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		if(departmentId!=null&&!departmentId.equals("")&&departmentId.substring(0,1).equals("u")){//去掉undefined
			departmentId=departmentId.substring(9);			 
		 }
		Map<String,String> maps = creditQueryService.personCount(beginTime, endTime, departmentId, person,level);		
		model.addAttribute("maps", maps);
		return "monitor/grpersonQuery";
	}
	//主页面
	@RequestMapping("grQueryMain.action")
	public String qyMain(){
		return "monitor/grQueryMain";
	}
}
