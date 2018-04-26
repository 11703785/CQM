package com.platform.application.monitor.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.platform.application.common.util.PropertyUtil;
import com.platform.application.monitor.domain.Grsummary;
import com.platform.application.monitor.domain.Grzxcxmx;
import com.platform.application.monitor.domain.Qysummary;
import com.platform.application.monitor.domain.Qyzxcxmx;
import com.platform.application.monitor.service.MonitorRecordService;
import com.platform.application.monitor.service.QyQueryService;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.operation.service.DataFromManagerService;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;

@Controller
public class MonitorRecordAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(MonitorRecordAction.class);

	private MonitorRecordService monitorRecordService = null;
	private QyQueryService qyQueryService;
	private DataFromManagerService sourceOrgService;
	private PersonService personService = null;
	@Autowired
	public void setQyQueryService(QyQueryService qyQueryService) {
		this.qyQueryService = qyQueryService;
	}
	@Autowired
	public void setMonitorRecordService(
			MonitorRecordService monitorRecordService) {
		this.monitorRecordService = monitorRecordService;
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Autowired
	public void setDataFromManagerService(DataFromManagerService sourceOrgService) {
		this.sourceOrgService = sourceOrgService;
	}
	@InitBinder("grzxcxmx")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("grzxcxmx.");
	}

	@InitBinder("qyzxcxmx")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("qyzxcxmx.");
	}
	@InitBinder("grsummary")
	public void initBinder3(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("grsummary.");
	}
	@InitBinder("qysummary")
	public void initBinder4(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("qysummary.");
	}
    //跳转个人查询页面
	@RequestMapping("monitorRecordgr.action")
	public String list() {
		return "monitor/grRecordList";
	}
   
	//获取个人查询数据的JSON
	@RequestMapping(value = "getGrzxcxmxJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getGrzxcxmxJSON(Grzxcxmx grzxcxmx) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			String year=this.getRequest().getParameter("gryear");
			String a = this.getRequest().getParameter("grbeginTime");
			String b = this.getRequest().getParameter("grendTime");
			Page page = this.getPageObj();
			Person person = (Person) this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			json = monitorRecordService.getGrzxcxmxJSON(grzxcxmx, page,person,a,b,year);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	//个人查询详情
	@RequestMapping("viewGrzxcxmx.action")
	public String view(Grzxcxmx grzxcxmx, Model model) {
		try {
			grzxcxmx = monitorRecordService.getGrzxcxmx(grzxcxmx);
			model.addAttribute(grzxcxmx);
		} catch (Exception e) {
			logger.error(e);
		}
		return "monitor/grRecordView";
	}
    //跳转企业查询页面
	@RequestMapping("monitorRecordqy.action")
	public String list2() {
		return "monitor/qyRecordList";
	}
	//获取企业查询数据的JSON
	@RequestMapping(value = "getQyzxcxmxJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getQyzxcxmxJSON(Qyzxcxmx qyzxcxmx) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Page page = this.getPageObj();
			Person person = (Person) this.getCurrentUser();
			String year=this.getRequest().getParameter("qyyear");
			String a = this.getRequest().getParameter("qybeginTime");
			String b = this.getRequest().getParameter("qyendTime");
			person = (Person) personService.load(person.getPersonId());
			json = monitorRecordService.getQyzxcxmxJSON(qyzxcxmx, page,person,a,b,year);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	//企业查询详情
	@RequestMapping("viewQyzxcxmx.action")
	public String view(Qyzxcxmx qyzxcxmx, Model model) {
		try {
			qyzxcxmx = monitorRecordService.getQyzxcxmx(qyzxcxmx);
			model.addAttribute(qyzxcxmx);
		} catch (Exception e) {
			logger.error(e);
		}
		return "monitor/qyRecordView";
	}
	
	/**
	 * 上报数据页面
	 * @param model
	 * @return
	 */
	@RequestMapping("uploadRecordUI.action")
	public String uploadRecordUI(Model model) {
		Person users = (Person)this.getCurrentUser();
		Person person = (Person)personService.load(users.getPersonId());
		//Department dept=person.getDepartment();
		model.addAttribute(person);
		return "monitor/uploadRecordUI";
	}
	/**
	 * 上报数据方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "uploadFile.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String  springUpload(HttpServletRequest request){
		String msg="true;上传成功！";
		String filePath =PropertyUtil.getPropertyByKey("TEMP_PATH");
		String path=null;
		SourceOrg sourceOrg=null;
		List<SourceOrg> sourceOrgByCode=null;
		String type = request.getParameter("type");
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		String perconalCode=person.getDepartment().getPersonalOrgCode();
		String deptCode=person.getDepartment().getDeptCode();
		//通过类型判断企业或个人上报查询接入点
		if(type.equals("2")){
		sourceOrgByCode = sourceOrgService.getSourceOrgByCode(deptCode);	
		}
		else if(type.equals("1")){
		sourceOrgByCode = sourceOrgService.getSourceOrgByCode(perconalCode);
		}
		//判断接入点是否注册
		if(sourceOrgByCode.size()>0){
			 sourceOrg=sourceOrgByCode.get(0);
			}else{
				msg="true;失败！接入点未注册，请注册后重新上报！";
           	return msg;
			}
		if(!new File(filePath).exists() && !new File(filePath).isDirectory()){
			new File(filePath).mkdirs();
		}
		try {
	         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
	        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
	        //检查form中是否有enctype="multipart/form-data"
	        if(multipartResolver.isMultipart(request))
	        {
	            //将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	           //获取multiRequest 中所有的文件名
	            Iterator iter=multiRequest.getFileNames();
	            String name = "";
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file=multiRequest.getFile(iter.next().toString());
	                boolean isTxt=file.getOriginalFilename().endsWith(".txt");
	                //boolean isXLSX=file.getOriginalFilename().endsWith(".xlsx");
	                if(isTxt==false){
	                	msg="true;失败！上传的文件不是.txt！";
	                	return msg;
	                }
	                name=file.getOriginalFilename();
	                if(file!=null)
	                {
	                    path=filePath+"\\"+name;
	                    //上传
	                   // file.transferTo(new File(path));	                    
	    				InputStream ops=file.getInputStream();	    				
	    				BufferedReader br=new BufferedReader(new InputStreamReader(ops));
	    				OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(new File(path)),"UTF-8");//确认流的输
	    				String str=null;
	    				String endLine = System.getProperty("line.separator"); // 获取换行符
	    				while ((str = br.readLine())!= null) // 判断最后一行不存在，为空结束循环
	    				{
	    				pw.write(str+endLine);//将要写入文件的内容，写入到新文件
	    				};
	    				br.close();
	    				pw.close();
	                }
	            }
	            String[] str = name.split("_");
        		qyQueryService.txUploadRecord(type,str,new File(filePath+"\\"+name),sourceOrg);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			msg="true;系统异常！"+e;
		}
        return msg; 
	}
	 //跳转个人汇总页面
	@RequestMapping("grsummary.action")
	public String list3() {
		return "monitor/grsummaryList";
	}
	
	//获取个人查询数据汇总的JSON
	@RequestMapping(value = "getGrsummaryJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getGrsummaryJSON(Grsummary grsummary) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Date endTime=null;
			Date beginTime=null;
			String departmentId=this.getRequest().getParameter("departmentId");
			String a = this.getRequest().getParameter("grsbeginTime");
			String b = this.getRequest().getParameter("grsendTime");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(a!=null && !a.equals("")){	
				beginTime=(Date) sdf.parseObject(a);
			}
			if(b!=null && !b.equals("")){
			endTime=(Date) sdf.parseObject(b);
			}
			Page page = this.getPageObj();
			Person person = (Person) this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			json = monitorRecordService.getGrsummaryJSON(grsummary,departmentId, page,person,beginTime,endTime);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	Grsummary grs;//个人汇总详情用
	 //跳转个人汇总详情页面
	@RequestMapping("grsummaryView.action")
	public String list4(Model model,Grsummary grsummary) {
		try {
		int grsId=0;
		if(grsummary!=null){
			grsId=grsummary.getId();
		}
		grs=null;
		if(grsId!=0){
				List<Grsummary> grsummary2 = monitorRecordService.getGrsummary(grsId);
				if(grsummary2.size()>0){
				  grs=grsummary2.get(0);
				}
			     }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		model.addAttribute("grsummary", grs);
		return "monitor/grsummaryView";
	}
	//个人汇总详情页面json
	@RequestMapping(value = "viewGrsummary.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getGrsummaryViewJSON(Grzxcxmx grzxcxmx) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			String a = this.getRequest().getParameter("grbeginTime");
			String b = this.getRequest().getParameter("grendTime");
			String year=this.getRequest().getParameter("gryear");
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
			Page page = this.getPageObj();
			Person person = (Person) this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			//第一次进入个人汇总详情（默认的企业名称，网点名称，时间，还有查询人）
			if(grzxcxmx.getQueryOrgName()==null&&grs!=null){
				grzxcxmx.setQueryOrgName(grs.getQueryOrgName());
			}
			if(grzxcxmx.getQueryOrgNo()==null&&grs!=null){
				grzxcxmx.setQueryOrgNo(grs.getQueryOrgNo());
			}
			if(grzxcxmx.getQueryUserName()==null&&grs!=null){
				grzxcxmx.setQueryUserName(grs.getQueryUserName());
			}
			if(grzxcxmx.getQueryTime()==null&&grs!=null){
		        String str = sdf.format(grs.getQueryTime());
				grzxcxmx.setQueryTime(str);
			}
			json = monitorRecordService.getGrzxcxmxJSON(grzxcxmx, page,person,a,b,year);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
	}
	 //跳转企业汇总页面
	@RequestMapping("qysummary.action")
	public String list4() {
		return "monitor/qysummaryList";
	}
	
	//获取企业查询数据汇总的JSON
	@RequestMapping(value = "getQysummaryJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getQysummaryJSON(Qysummary qysummary) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Date endTime=null;
			Date beginTime=null;
			String departmentId=this.getRequest().getParameter("departmentId");
			String a = this.getRequest().getParameter("qysbeginTime");
			String b = this.getRequest().getParameter("qysendTime");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(a!=null && !a.equals("")){	
				beginTime=(Date) sdf.parseObject(a);
			}
			if(b!=null && !b.equals("")){
			endTime=(Date) sdf.parseObject(b);
			}
			Page page = this.getPageObj();
			Person person = (Person) this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			json = monitorRecordService.getQysummaryJSON(qysummary,departmentId, page,person,beginTime,endTime);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	Qysummary qys;//个人汇总详情用
	 //跳转企业汇总详情页面
	@RequestMapping("qysummaryView.action")
	public String list5(Model model,Qysummary qysummary) {
		try {
		int qysId=0;
		if(qysummary!=null){
			qysId=qysummary.getId();
		}
		qys=null;
		if(qysId!=0){
				List<Qysummary> qysummary2 = monitorRecordService.getQysummary(qysId);
				if(qysummary2.size()>0){
				  qys=qysummary2.get(0);
				}
			     }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		model.addAttribute("qysummary", qys);
		return "monitor/qysummaryView";
	}
	//企业汇总详情页面json
	@RequestMapping(value = "viewQysummary.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getQysummaryViewJSON(Qyzxcxmx qyzxcxmx) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			String a = this.getRequest().getParameter("qybeginTime");
			String b = this.getRequest().getParameter("qyendTime");
			String year=this.getRequest().getParameter("qyyear");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );		
			Page page = this.getPageObj();
			Person person = (Person) this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			//第一次进入企业汇总详情（默认的企业名称，网点名称，时间，还有查询人）
			if(qyzxcxmx.getQueryOrgName()==null&&qys!=null){
				qyzxcxmx.setQueryOrgName(qys.getQueryOrgName());
			}
			if(qyzxcxmx.getQueryOrgNo()==null&&qys!=null){
				qyzxcxmx.setQueryOrgNo(qys.getQueryOrgNo());
			}
			if(qyzxcxmx.getQueryUserName()==null&&qys!=null){
				qyzxcxmx.setQueryUserName(qys.getQueryUserName());
			}
			if(qyzxcxmx.getQueryTime()==null&&qys!=null){
		        String str = sdf.format(qys.getQueryTime());
				qyzxcxmx.setQueryTime(str);
			}
			json = monitorRecordService.getQyzxcxmxJSON(qyzxcxmx, page,person,a,b,year);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load log JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
	}
}
