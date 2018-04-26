
package com.platform.application.rateindex.action;

import java.text.DecimalFormat;

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

import com.platform.application.rateindex.domain.RateLevel;
import com.platform.application.rateindex.service.RateLevelService;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
@Controller
public class RateLevelAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(RateLevelAction.class);
	private RateLevelService rateLevelService;
     
	
	@Autowired
	public void setNoticeService(RateLevelService rateLevelService) {
		this.rateLevelService = rateLevelService;
	}
	
	@InitBinder("rateLevel")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("rateLevel.");
    }
	/*
	 * 跳转到信息等级管理
	 */
	@RequestMapping("initRateLevelManage.action")
	public String initRateLevalManage() {
		return "rateindex/rateLevelList";
	}
	
	/*
	 * 信用等级json
	 */
	@RequestMapping(value = "getRateLevelJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String getRateLevelJSON() {
	   String json = RESULT_EMPTY_DEFAULT;
	   try{
		   RateLevel level = new RateLevel();
		   json=rateLevelService.getRateLevelJson(level,this.getPageObj());
		   if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.info("load role JSON:" + json);
	   }catch (Exception e) {
			e.printStackTrace();
		}
	   return json;
	}
	/*
	 * 打开新增信用等级
	 * @return 
	 * createRateLeval
	 */
	
	@RequestMapping("createRateLevel.action")
	public String createRateLeval(){
		return "rateindex/rateLevelAdd";
	}
	
	/*
	 * 保存信用等级
	 */
	@RequestMapping(value = "saveRateLevel.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String  save (RateLevel rl,HttpServletRequest request){
		String msg = "true;保存信息成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			String personId= person.getPersonId();
			String deptId = person.getDepartment().getDeptId();
			boolean isNew = StringUtil.isEmpty(rl.getId());
			if(isNew) {//新建
				rateLevelService.txCreateRateLevel(rl,personId,deptId,request);
			} else {//更新
				rateLevelService.txUpdateRateLevel(rl,deptId,personId);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg = "false;信息提交失败：" + e.getMessage();
		}
		return msg;
	}
	/*
	 * 编辑信用等级
	 */
	@RequestMapping(value ="editRateLevel.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String edit(RateLevel rl, Model model){
		try {
			rl = (RateLevel) rateLevelService.load(rl.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		DecimalFormat nf = new DecimalFormat( "0");
		
//		this.getSession().setAttribute("minNum",  nf.format(rl.getMinNum()));
//		this.getSession().setAttribute("maxNum",  nf.format(rl.getMaxNum()));
		model.addAttribute(rl);
		return "rateindex/rateLevelEdit";
	}
	/*
	 * 查看信用等级
	 */
	@RequestMapping(value ="queryRateLevel.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String query(RateLevel rl, Model model){
		try {
			rl = (RateLevel) rateLevelService.load(rl.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		DecimalFormat nf = new DecimalFormat( "0");
//		this.getSession().setAttribute("minNum",  nf.format(rl.getMinNum()));
//		this.getSession().setAttribute("maxNum",  nf.format(rl.getMaxNum()));
		model.addAttribute(rl);
		return "rateindex/rateLevelQuery";
	}
	
	/*
	 * 删除信用等级
	 */
	
	@RequestMapping(value = "deleteRateLevel.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String delete(RateLevel rl){
		String msg="true;信息等级删除成功！";
		try {
			rateLevelService.txdeleteRatelevel(rl);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;公告信息删除失败：" + e.getMessage();
		}
		return msg;
	}
	
	
	
	
	
	
}
