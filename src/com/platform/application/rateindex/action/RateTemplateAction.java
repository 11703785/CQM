package com.platform.application.rateindex.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

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

import com.platform.application.rateindex.domain.RateTempIndex;
import com.platform.application.rateindex.domain.RateTempIndexScore;
import com.platform.application.rateindex.domain.RateTemplate;
import com.platform.application.rateindex.service.RateIndexService;
import com.platform.application.rateindex.service.RateTempIndexScoreService;
import com.platform.application.rateindex.service.RateTempIndexService;
import com.platform.application.rateindex.service.RateTemplateService;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

@Controller
public class RateTemplateAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(RateTemplateAction.class);
	
	private RateTemplateService rateTemplateService;
	private RateIndexService rateIndexService;
	private RateTempIndexService rateTempIndexService;
	private RateTempIndexScoreService rateTempIndexScoreService;
	

	@Autowired
	public void setRateTemplateService(RateTemplateService rateTemplateService) {
		this.rateTemplateService = rateTemplateService;
	}
	@Autowired
	public void setRateIndexService(RateIndexService rateIndexService) {
		this.rateIndexService = rateIndexService;
	}
	@Autowired
	public void setRateTempIndexService(RateTempIndexService rateTempIndexService) {
		this.rateTempIndexService = rateTempIndexService;
	}
	@Autowired
	public void setRateTempIndexScoreService(RateTempIndexScoreService rateTempIndexScoreService) {
		this.rateTempIndexScoreService = rateTempIndexScoreService;
	}
	@InitBinder("rateTemplate")    
    public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("rateTemplate.");
    }
	@InitBinder("rateTempIndexScore")    
    public void initBinder1(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("rateTempIndexScore.");
    }
	@InitBinder("rateTempIndex")    
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("rateTempIndex.");
	}
	
	/******************************* 评级模板 begin ***********************************/
	/**
	 * 加载评级模板list
	 * @return
	 */
	@RequestMapping("initRateTemplateManage.action")
	public String redirect(){
		return "rateindex/rateTemplateList";
	}
	
	/**
	 * 获取评级模板json
	 * @return
	 */
	@RequestMapping(value = "getRateTemplateJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRateTemplateJson() {
		String json = RESULT_EMPTY_DEFAULT;
		try {
			RateTemplate rateTemplate = new RateTemplate();
			Person person = (Person) this.getCurrentUser();
			json = rateTemplateService.getRateTemplateJson(rateTemplate,person,this.getPageObj());
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.info("load role JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 增加评级模板
	 */
	@RequestMapping("createRateTemplate.action")
	public String createRateTemplate(Model model) {
		try {
			//List areaList = rateTemplateService.getAllAreaList();
			//model.addAttribute("areaList", areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rateindex/rateTemplateAdd";
	}
	
	/**
	 * 保存评级模板
	 */
	@RequestMapping(value = "saveRateTemplate.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(RateTemplate rateTemplate, String areaids){
		String msg="true;保存评级模板成功";
		try {
			Person person = (Person) this.getCurrentUser();
			String personId = person.getPersonId();
			String orgId = person.getDepartment().getDeptId();
			boolean isNew = StringUtil.isEmpty(rateTemplate.getId());
			if(isNew) {//新建
				rateTemplateService.txCreateRateTemplate(rateTemplate,personId,orgId,areaids);
			} else {//更新
				rateTemplateService.txUpdateRateTemplate(rateTemplate,orgId,personId,areaids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg="false;保存评级模板失败："+e.getMessage();
		}
		return msg;
	}
	/**
	 * 编辑评级模板
	 */
	
	@RequestMapping( "editRateTemplate.action")
	public String updataRateTemplate(RateTemplate rateTemplate , Model model){
		try {
		 rateTemplate = (RateTemplate) rateTemplateService.load(rateTemplate.getId());
		 
		 //List areaList = rateTemplateService.getAllAreaList();
		 //model.addAttribute("areaList", areaList);
		 
		 model.addAttribute("areaids", rateTemplate.getAreaIds());
		 model.addAttribute(rateTemplate);
		} catch (Exception e) {
         e.printStackTrace();
		}
		return "rateindex/rateTemplateEdit";
	}
	
	/**
	 * 删除评级模板
	 * @param rateTemplate
	 * @return
	 */
	
	@RequestMapping(value = "deleteTemplate.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteAll(RateTemplate rateTemplate ){
		String msg = "true;评级模板删除成功！";
		try {
			rateTemplateService.txDeleteRateTemplate(rateTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;模板信息删除失败：" + e.getMessage();
		}
		return msg;
	}

	@RequestMapping(value = "enableRateTemplate.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String enableRateTemplate(String ids){
		String msg = "true;启用评级模板成功！";
		try {
			rateTemplateService.txEnableRateTemplate(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	
	
	/******************************* 评级模板 end ***********************************/

	/******************************* 评级模板关联指标、权重 begin ***********************************/
	/**
	 * 加载模板配置指标页面
	 * @param rateTemplate
	 * @param model
	 * @return
	 */
	@RequestMapping("loadRateTempIndexManage.action")
	public String redirects(RateTemplate rateTemplate, Model model){
		try {
			rateTemplate = (RateTemplate)rateTemplateService.load(rateTemplate.getId());
			String str = rateTemplateService.getRateIndexDivStr(rateTemplate);
			model.addAttribute(rateTemplate);
			model.addAttribute("str",str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rateindex/rateTempIndexManage";
	}
	
	/**
	 * 加载指标树
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "loadRateTempIndexJson.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadRateTempIndexJson(String id){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person) this.getCurrentUser();
			json = rateTemplateService.loadRateIndex(id, person);
			if(StringUtil.isEmpty(json)) 
				json = RESULT_EMPTY_DEFAULT;
			logger.debug("load rateIndexJSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 保存指标及权重值
	 */
	@RequestMapping(value = "saveRateTempIndex.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveRateTempIndex(String result){
		String msg="true;保存指标及权重值成功！";
		try {
			String[] ids = result.split("_");
			if(StringUtil.isNotBlank(ids[0])){
				RateTemplate temp = (RateTemplate)rateTemplateService.load(ids[0]);
				rateTempIndexService.txSaveRateTempIndex(temp,ids[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;保存指标及权重值失败："+e.getMessage();
		}
		return msg;
	}
	
	
	@RequestMapping(value = "deleteRateTempIndex.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteRateTempIndex(String tempid, String indexid){
		String msg="true;删除成功！";
		try {
			rateTempIndexService.txDeleteRateTempIndex(tempid,indexid);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;删除失败："+e.getMessage();
		}
		return msg;
	}
	/******************************* 评级模板关联指标、权重 end ***********************************/
	
	/******************************* 指标评分标准 begin ***********************************/
	/**
	 * 加载评分标准维护页面
	 * @param rateTemplate
	 * @param model
	 * @return
	 */
	@RequestMapping("loadRateTempIndexScore.action")
	public String redirectes(RateTemplate rateTemplate, Model model){
		try {
			rateTemplate = (RateTemplate)rateTemplateService.load(rateTemplate.getId());
			model.addAttribute(rateTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rateindex/rateTempIndexScoreFrame";
	}
	
	/**
	 * 指标树加载
	 * @param id
	 * @param tempid
	 * @return
	 */
	@RequestMapping(value = "loadRateTempIndexJsons.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadRateTempIndexJsons(String id,String tempid){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			json = rateTemplateService.loadRateTempIndexJson(id, tempid);
			if(StringUtil.isEmpty(json)) 
				json = RESULT_EMPTY_DEFAULT;
			logger.debug("load rateIndexJSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 跳转评分标准页面
	 */
	@RequestMapping("listRateTempScore.action")
	public String listRateIndex(String ids, Model model){
		try {
			//String str[] = ids.split(";");
			//RateIndex rateIndex= (RateIndex) rateIndexService.load(str[1]);
			//RateTemplate rateTemplate = (RateTemplate) rateTemplateService.load(str[0]);
			model.addAttribute("ids",ids);
			//model.addAttribute(rateTemplate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "rateindex/rateTempIndexScoreLists";
	}
	
	/**
	 * 评分标准列表显示
	 */
	@RequestMapping(value = "getRateIndexScoreJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRateIndexJSON(String ids){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person) this.getCurrentUser();
			json = rateTempIndexScoreService.loadRateIndexOptions(ids, person, this.getPageObj());
			if (StringUtil.isBlank(json)) {
				json = RESULT_EMPTY_DEFAULT;
			}
			logger.info("load rateIndex JSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 跳转创建指标选项
	 */
	@RequestMapping("createIndexTempScore.action")
	public String createIndexTempScore(String ids, Model model){
		try {
			List dictypeList = rateTempIndexScoreService.getAllDicType();
			model.addAttribute("dictypeList",dictypeList);
			model.addAttribute("ids",ids);
			String[] str = ids.split(";");
			RateTempIndex rateTempIndex = rateTempIndexService.queryRateTempIndex(ids);
			
			model.addAttribute("rateTempIndex", rateTempIndex);
//			rateIndexId = this.getRequest().getParameter("rateIndexId");
//			rateTemplateId = this.getRequest().getParameter("tempid");
//			
//			String sql = "from RateTempIndexScore r where r.indexId='"+rateIndexId+"'";
//			List listRateIndexOpts = rateTemplateService.find(sql);
//			model.addAttribute("listRateIndexOpts",listRateIndexOpts);
//			
//			model.addAttribute("tempid",rateTemplateId);
//			model.addAttribute("indexid",rateIndexId);
//			model.addAttribute("nums",listRateIndexOpts.size());
//			model.addAttribute("num",65+listRateIndexOpts.size());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "rateindex/rateTempIndexScoreCreate";
	}
	
	@RequestMapping(value = "getDictionaryByType.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDictionaryByType(String typeid){
		String result = "";
		try {
			result = rateTempIndexScoreService.getDictionaryByType(typeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 新增指标选项保存
	 */
	@RequestMapping(value = "saveRateTempScore.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveRateTempScore(HttpServletRequest request){
		String msg="true;保存成功！";
		try {
			Person person = (Person)this.getCurrentUser();
			rateTempIndexScoreService.txSaveRateIndexOptions(person, request);
		} catch (Exception e) {
			e.printStackTrace();
			msg="false;保存失败！";
		}
		return msg;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteRateIndexScore.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String deleteRateIndexScore(RateTempIndexScore rateTempIndexScore){
		String msg="true;指标选项删除成功！";
		try {
			rateTempIndexScoreService.txDelete(rateTempIndexScore);
		} catch (Exception e) {	
			e.printStackTrace();
			msg = "false;指标选项删除失败：" + e.getMessage();
		}
		return msg;
	}
	/******************************* 指标评分标准 end ***********************************/
	@RequestMapping("viewRateIndexReport.action")
	public String viewRateIndexReport(RateTemplate rateTemplate,Model model){
		try {
			LinkedHashMap map = rateTemplateService.getReportRateInde(rateTemplate.getId());
			rateTemplate = (RateTemplate) rateTemplateService.load(rateTemplate.getId());
			model.addAttribute("rateTemplate", rateTemplate);
			model.addAttribute("map", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rateindex/rateTempReportView";
	}
}
