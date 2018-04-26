package com.platform.application.sysmanage.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.platform.application.sysmanage.domain.Log;
import com.platform.application.sysmanage.service.LogService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;
@Controller
public class LogAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LogAction.class);
//	private Log log = null;
//	private Date beginDate;
//	private Date endDate;
	private LogService logService = null;
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	@InitBinder("log")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("log.");
	} 
	/**
	 * 打开日志列表
	 * @return
	 */
	@RequestMapping("listLog.action")
	public String list(){
		return "sysmanage/logList";
	}
	@RequestMapping(value="getLogsJSON.action",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLogsJSON(Log log) {
		
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Date endDate=null;
			Date beginDate=null;
			Page page=this.getPageObj();
			String a = this.getRequest().getParameter("beginDate");
			String b = this.getRequest().getParameter("endDate");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			  if(a!=null && !a.equals("")){
					
	            	 beginDate=(Date) sdf.parseObject(a);
				}
				if(b!=null && !b.equals("")){
					endDate=(Date) sdf.parseObject(b);
				} 
			json = logService.getLogsJSON(log,beginDate,endDate,page);
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
