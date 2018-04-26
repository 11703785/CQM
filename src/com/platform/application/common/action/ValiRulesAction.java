package com.platform.application.common.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.common.domain.ValiRules;
import com.platform.application.common.service.ValiRulesService;
import com.platform.application.sysmanage.domain.Area;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

@Controller
public class ValiRulesAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ValiRulesAction.class);
	private ValiRulesService valiRulesService;
	
	@Autowired
	public void setValiRulesService(ValiRulesService valiRulesService) {
		this.valiRulesService = valiRulesService;
	}
	
	@InitBinder("valiRules")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("valiRules.");
    }
	
	@RequestMapping("initValidateRulesManage.action")
	public String redirect(){
		return "dictionary/valiRulesList";
	}
	/**
	 * 方法说明: 加载json
	 */
	@RequestMapping(value ="getValiRulesListJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getValiRulesListJson(){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			json = valiRulesService.getValiRulesListJson(this.getPageObj());
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("createValiRules.action")
	public String createValiRules(){
		return "dictionary/valiRulesEdit";
	}
	/**
	 * 方法说明: 编辑跳转
	 */
	@RequestMapping("editValiRules.action")
	public String edit(ValiRules valiRules, Model model) {
		try {
			if(StringUtil.isNotBlank(valiRules.getId())){
				valiRules = (ValiRules) valiRulesService.load(valiRules.getId());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(valiRules);
		return "dictionary/valiRulesEdit";
	}
	/**
	 * 方法说明: 保存
	 */
	@RequestMapping(value = "saveValiRules.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveValiRules(ValiRules valiRules){
		String msg = "true;保存成功！";
		try {
			boolean isNew = StringUtil.isEmpty(valiRules.getId());
			if(isNew) {//新建
				valiRulesService.txSaveValiRules(valiRules);
			} else {//更新
				valiRulesService.txEditValiRules(valiRules);
			}
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;提交失败：" + e.getMessage();
		}
		return msg;
	}
	/**
	 * 方法说明: 查看
	 */
	@RequestMapping("viewValiRules.action")
	public String viewValiRules(ValiRules valiRules, Model model) {
		try {
			if(StringUtil.isNotBlank(valiRules.getId())){
				valiRules = (ValiRules) valiRulesService.load(valiRules.getId());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(valiRules);
		return "dictionary/valiRulesView";
	}
	/**
	 * 方法说明: 删除
	 */
	@RequestMapping(value = "deleteValiRules.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteValiRules(ValiRules valiRules) {
		String msg = "true;操作删除成功！";
		try {
			
			msg = valiRulesService.txDeleteValiRules(valiRules);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;操作删除失败：" + e.getMessage();
		}
		return msg;
	}
}
