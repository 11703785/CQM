package com.platform.application.sysmanage.action;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.monitor.service.MonitorRecordService;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.OptResource;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.application.sysmanage.service.impl.ResourceFactoryManager;
import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

@Controller
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = -269363570412083771L;
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private Department   department;
	private AreaService areaService;
	private MonitorRecordService monitorRecordService;
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	@Autowired
	public void setMonitorRecordService(MonitorRecordService monitorRecordService) {
		this.monitorRecordService = monitorRecordService;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	private PersonService personService = null;

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@InitBinder("person")    
    public void initBinder1(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("person.");    
    }  
	@RequestMapping("goon.action")
	public String goon(Model model){
		String remsg="index";
		String curdate=DateUtils.getCurrDateStr("yyyy年MM月dd日");
		List dirMenus = new ArrayList();
		List optMenus = new ArrayList();
		OptResource res = null;
		
		Person users = (Person)this.getCurrentUser();
		Person person = (Person)personService.load(users.getPersonId());
		
		List resList = new ArrayList(this.getResMap().values());
		//给列表排序
		Collections.sort(resList, new OptResource());
		
		
		String roleids=person.getRoleIds();
		/*if(roleids.indexOf("root")!=-1){*/
			for (int i = 0; i < resList.size(); i++) {
				res = (OptResource) resList.get(i);
				if (res.getResType().equals(OptResource.dir)) {
					dirMenus.add(res);
				}
				if (res.getResType().equals(OptResource.menu)) {
					optMenus.add(res);
				}
			}
			
			
			model.addAttribute("optMenus", optMenus);
		/*}else{
			remsg="indexuser";
			for(Object obj:resList){
				
				res = (OptResource) obj;
				OptResource parent=res.getParent();
				
				if(parent==null){
					HashMap<String, Object> node=new HashMap<String, Object>();//菜单节点
					node.put("id", res.getResId());
					node.put("name", res.getResName());
					node.put("url", res.getResUrl());
					node.put("ioc", res.getPictureclass());
					dirMenus.add(node);
					getChilds(resList,node);
					//resList.remove(obj);
				}
			}
			System.out.println(Arrays.toString(dirMenus.toArray()));
		}*/
		String curweek=DateUtils.getWeek();
		model.addAttribute("dirMenus", dirMenus);
		model.addAttribute("curdate",curdate);
		model.addAttribute("curweek",curweek);
		model.addAttribute(person);
		return remsg;
	}
	
	private void getChilds(List resList,HashMap<String, Object> node){
		String pid=(String) node.get("id");
		ArrayList childs=new ArrayList();
		for(Object obj:resList){
			OptResource res=(OptResource) obj;
			if(res.getParent()!=null && res.getParent().getResId().equals(pid)){
				HashMap<String, Object> child=new HashMap<String, Object>();//菜单节点
				child.put("id", res.getResId());
				child.put("name", res.getResName());
				child.put("url", res.getResUrl());
				child.put("ioc", res.getPictureclass());
				childs.add(child);
				//resList.remove(obj);
				getChilds(resList,child);
			}
		}
		if(childs.size()>0)
			node.put("childs", childs);
		
	}
	
	
	@RequestMapping("mainHome.action")
	public String mainHome(Model model){
		List<Object[]> nList = new ArrayList<Object[]>();
		Map LevelMap=new HashMap();
		Map maps = new HashMap();
		Person person = (Person) this.getCurrentUser();
		person = (Person) personService.load(person.getPersonId());
		if(!person.getPersonId().equals("root")){
			Area area=person.getDepartment().getArea();
			try {
				List areaList=areaService.getAreaChildList(area);
				//nList = noticeService.selNoticeData();
				
				model.addAttribute("maps", maps);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("person", person);
		}
		return "home";
	}
	
	@RequestMapping("top.action")
	public String top(Model model){
		String curdate=DateUtils.getCurrDateStr("yyyy年MM月dd日");
		String curweek=DateUtils.getWeek();
		
		Person users = (Person)this.getCurrentUser();
		Person person = (Person)personService.load(users.getPersonId());
		
		model.addAttribute("curdate",curdate);
		model.addAttribute("curweek",curweek);
		model.addAttribute(person);
		
		return "header";
	}
	
	
	
	/**
	 * 方法说明：点击首页菜单树，获取此节点下的子节点，返回JSON
	 */
	@RequestMapping(value = "getMenuTreeJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMenuTreeJson(String node) throws JSONException{
		OptResource res = null;
		String pid = node;
		List resList = new ArrayList(this.getResMap().values());
		//给列表排序
		Collections.sort(resList, new OptResource());
		JSONArray jar = new JSONArray();
		JSONObject job = null;
		for (int i = 0; i < resList.size(); i++) {
			res = (OptResource) resList.get(i);
			if(res.getParent()!=null && res.getParent().getResId().equals(pid)){
				job = new JSONObject();
				job.put("id", res.getResId());
				job.put("text", res.getResName());
				if(res.getResType().equals(OptResource.dir)) job.put("state", "closed");
				else job.put("state", "open");
				if(StringUtil.isNotEmpty(res.getResUrl())) job.put("attributes", res.getResUrl());
				jar.put(job);
			}
		}
		return jar.toString();
	}
	
	@RequestMapping(value = "login.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String login(Person person) {
		String msg="false;"+CurValues.LOGONMSG1;
		try {
			Person curUser = personService.validateLoginUser(person);
			if (curUser != null) {
				putSessionContent(curUser);
				msg="true;登录成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;登录失败："+e.getMessage();
		}
		return msg;
	}
	
	public void manualDownload(){
		try{
			String filename = "金融精准扶贫信息系统前置系统用户操作手册.doc";
			String url=this.getServletContext().getRealPath("/manual/金融精准扶贫信息系统前置系统用户操作手册.doc");
			
			//String newPath = url+"/"+filename;
			
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(url));
			this.getResponse().reset();
			this.getResponse().setContentType("bin");
			this.getResponse().addHeader("content-type", "application/x-msdownload");
			this.getResponse().addHeader("Content-Disposition","attachment; fileName=\""
					+ URLEncoder.encode(filename,"UTF-8"));
			
			byte[] bytes = new byte[is.available()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
				this.getResponse().getOutputStream().write(bytes, 0, len);
			}
			is.close();
		}catch (Exception e) {
			e.printStackTrace();
			this.sendMessage("false;文档下载失败：" + e.getMessage());
		}
	}
	
	
	private void putSessionContent(Person user) {
		// 将当前用户对象加入session
		this.getSession().setAttribute(CurValues.USER, user);
		this.getSession().setAttribute(CurValues.RESOURCEMAP, new HashMap());
		this.getSession().setAttribute(CurValues.ActionMAP, new HashMap());
		// 将用户持有的资源加入资源Map
		Role role = null;
		for (int i = 0; i < user.getRoleList().size(); i++) {
			role = (Role) user.getRoleList().get(i);
			this.putResource(this.getResMap(),role.getResourceList(), this.getActionMap());
		}
		user.setRoleList(null);
	}
	
	private void putResource(Map map, List resList, Map actionMap) {
		OptResource res = null;
		String[] arr = null;
		for (int i = 0; i < resList.size(); i++) {
			res = (OptResource) resList.get(i);
			map.put(res.getResCode(), ResourceFactoryManager.allResMap.get(res.getResCode()));
			if (StringUtil.isNotEmpty(res.getActions())){
				arr = res.getActions().split(",");
				for (int j = 0; j < arr.length; j++) {
					actionMap.put(arr[j], arr[j]);
				}
			}
		}
	}
	/**
	 * 退出登录
	 */
	@RequestMapping("logout.action")
	public String logout() {
		try {
			Person curUser = (Person) this.getSession().getAttribute(CurValues.USER);
			if (curUser != null) {
				curUser = (Person) personService.load(curUser.getPersonId());
				this.getSession().removeAttribute(CurValues.USER);
				this.getSession().removeAttribute(CurValues.RESOURCEMAP);
				this.getSession().removeAttribute(CurValues.ActionMAP);
				this.getSession().invalidate();
				logger.debug("用户 [ " + curUser.getPersonName() + " ] 注销成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}
//	@RequestMapping("resetPassword.action")
//	public String resetPassword(String ids){
//		String msg = "true;用户重置密码成功！";
//		try{
//			Person curPerson = (Person)this.getCurrentUser();
//			String ip = this.getRequest().getRemoteAddr();
//			personService.txResetPassword(ids,curPerson,ip);
//		}catch (Exception e){
//			e.printStackTrace();
//			msg = "false;用户重置密码失败：" + e.getMessage();
//		}
//		return msg;
//	}
	/**
	 * 登录
	 */
	@RequestMapping("index_anonymous.action")
	public String  loginReplace(){
		return "login";
	}
	
}
