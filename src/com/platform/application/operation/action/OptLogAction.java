package com.platform.application.operation.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.operation.domain.OptLog;
import com.platform.application.operation.service.OptLogService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Log;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.LogService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;
@Controller
public class OptLogAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(OptLogAction.class);
//	private Log log = null;
//	private Date beginDate;
//	private Date endDate;
	private OptLogService logService = null;
	private PersonService personService;
	private DepartmentService departmentService;
	@Autowired
	public void setLogService(OptLogService logService) {
		this.logService = logService;
	}
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	@InitBinder("optLog")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("optLog.");
	} 
	/**
	 * 打开日志列表
	 * @return
	 */
	@RequestMapping("operationLog.action")
	public String list(){
		return "operation/logList";
	}
	//日志JSON
	@RequestMapping(value="getOptLogsJSON.action",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLogsJSON(OptLog optLog) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person) this.getCurrentUser();
			 person = (Person) personService.load(person.getPersonId());
			Page page=this.getPageObj();
			Date endDate=null;
			Date beginDate=null;
			String departmentId=this.getRequest().getParameter("queryOrgName");
			String a = this.getRequest().getParameter("beginDate");
			String b = this.getRequest().getParameter("endDate");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
             if(a!=null && !a.equals("")){
            	 beginDate=(Date) sdf.parseObject(a);
			}
			if(b!=null && !b.equals("")){
				endDate=(Date) sdf.parseObject(b);
			} 
			
			json = logService.getLogsJSON(optLog,departmentId,beginDate,endDate,page,person);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
