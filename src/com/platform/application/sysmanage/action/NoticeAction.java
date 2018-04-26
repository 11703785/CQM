package com.platform.application.sysmanage.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.sysmanage.domain.Notice;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.UserAccount;
import com.platform.application.sysmanage.service.NoticeService;
import com.platform.application.sysmanage.service.UserAccountService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

@Controller
public class NoticeAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(NoticeAction.class);
	private NoticeService noticeService;

	@Autowired
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@InitBinder("notice")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("notice.");
    }
	@RequestMapping("initInformationRelease.action")
	public String initInformationRelease() {
		return "sysmanage/noticeList";
	}
	/**
	 * 跳转更多链接
	 * */
	@RequestMapping("loadNoticeView.action")
	public String loadNoticeView() {
		return "sysmanage/noticeView";
	}
	/*
	 * 获取公告信息列表
	 */
	
	@RequestMapping(value = "getNoticesJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getNoticesJSON(Notice notice) {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person)this.getCurrentUser();
			json = noticeService.getNoticesJSON(notice,person,this.getPageObj());
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load role JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/*
	 * 打开新增公告信息
	 * @return 
	 */
	@RequestMapping("createNotice.action")
	public String createNotice(){
		return "sysmanage/noticeEdit";
	}
	
	/*
	 * 保存公告信息
	 */
	@RequestMapping(value = "saveNotice.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(Notice notice) {
		String msg = "true;保存公告信息成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			String personId=person.getPersonId();
			String deptId=person.getDepartment().getDeptId();
			boolean isNew = StringUtil.isEmpty(notice.getId());
			if(isNew) {//新建
				noticeService.txCreateNotice(notice,personId,deptId);
			} else {//更新
				noticeService.txUpdateNotice(notice,deptId,personId);
			}
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;公告信息提交失败：" + e.getMessage();
		}
		return msg;
	}
	
    /*
     * 修改公告信息
     */
	@RequestMapping(value ="editNotice.action" , method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String edit(Notice notice, Model model){
		try {
			notice = (Notice) noticeService.load(notice.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		model.addAttribute(notice);
		return "sysmanage/noticeEdit";
	}
	
	/*
	 * 删除公告信息
	 */
	@RequestMapping(value = "deleteNotice.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String delete(Notice notice) {
		String msg = "true;公告信息删除成功！";
		try {
			noticeService.txDeleteNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;公告信息删除失败：" + e.getMessage();
		}
		return msg;
	}
	
	/*
	 * 查看公告信息
	 */
	@RequestMapping(value ="queryNotice.action" , method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String query(Notice notice, Model model){
		try {
			notice = (Notice) noticeService.load(notice.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		model.addAttribute(notice);
		return "sysmanage/noticeQuery";
	}
	
	@RequestMapping("initInformationSearch.action")
	public String initInformationSearch() {
		return "sysmanage/noticeinformationList";
	}
}
