package com.platform.application.common.action;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.common.domain.DicType;
import com.platform.application.common.domain.Dictionary;
import com.platform.application.common.service.DicTypeService;
import com.platform.application.common.service.DictionaryService;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
import com.platform.framework.core.page.Page;
@Controller
public class DictionaryAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(DictionaryAction.class);
	private DictionaryService  dictionaryService = null;
	private DicTypeService dicTypeService = null; 
	
	@Autowired
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	@Autowired
	public void setDicTypeService(DicTypeService dicTypeService) {
		this.dicTypeService = dicTypeService;
	}
	@InitBinder("dictionary")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("dictionary.");
    }
	@InitBinder("dicType")    
	public void initBinder1(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("dicType.");
	}
	/** 字典信息维护 ------------------------------------------------  start  **/
	/*
	 * 加载字典维护List
	 */
	@RequestMapping("initDictionaryManage.action")
	public String initDictionaryManage(){
		return "dictionary/dictionaryFrame";
	}
	
	@RequestMapping(value ="loadDicTypeTreeJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadDicTypeTreeJson(String id){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			json = dictionaryService.getDicTypesJson(id);
			if(StringUtil.isEmpty(json)) {
			   json = RESULT_EMPTY_DEFAULT;
			}
			logger.debug("load DepartmentJSON:" + json);
			//this.sendJSON(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("listDictionary.action")
	public String listDictionary(DicType dicType, Model model){
		try {
			model.addAttribute(dicType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dictionary/dictionaryManageList";
	}
	/*
	 * 获取字典信息列表
	 */
	@RequestMapping(value ="getDictionaryManageListJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDictionaryManageListJSON(DicType dicType){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Page page=this.getPageObj();
			json=dictionaryService.getDictionaryJson(dicType,page);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping("createDictionary.action")
	public String create(Model model){
		return "dictionary/dictionaryManageCreate";
	}
	
	@RequestMapping(value = "loadDicType.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String loadDicType(){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			json = dicTypeService.getDicTypesJsonSelect();
			if(StringUtil.isEmpty(json)) 
				json = RESULT_EMPTY_DEFAULT;
			logger.debug("load OptResourceJSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
   /*
    * 保存字典信息
    */
	@RequestMapping(value = "saveDictionary.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String  save (Dictionary dictionary, DicType dicType){
		String msg = "true;保存信息成功！";
		try {
			boolean isNew = StringUtil.isEmpty(dictionary.getId());
			if(isNew) {//新增
				dictionaryService.txCreateDictionary(dictionary,dicType);
			} else {//更新
				dictionaryService.txUpdateDictionary(dictionary,dicType);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg = "false;" + e.getMessage();
		}
		return msg;
	}
	/*
	 * 查看字典信息
	 */
	@RequestMapping(value ="queryDictionary.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String query(Dictionary dictionary, Model model){
		try {
			dictionary = (Dictionary) dictionaryService.load(dictionary.getId());
			DicType  dicType =(DicType) dicTypeService.load(dictionary.getDicType().getId());
			model.addAttribute(dicType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		model.addAttribute(dictionary);
		return "dictionary/dictionaryManageQuery";
	}
	/*
	 * 编辑字典信息
	 */
	@RequestMapping(value ="editDictionary.action")
	public String edit(Dictionary dictionary, Model model){
		try {
			dictionary = (Dictionary) dictionaryService.load(dictionary.getId());
			DicType dicType =(DicType) dicTypeService.load(dictionary.getDicType().getId());
			model.addAttribute(dicType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute(dictionary);
		return "dictionary/dictionaryManageCreate";
	}
	/*
	 * 删除信用等级
	 */
	@RequestMapping(value = "deleteDictionary.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String delete(Dictionary dictionary){
		String msg="true;信息等级删除成功！";
		try {
			dictionaryService.txdeleteDictionary(dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;公告信息删除失败：" + e.getMessage();
		}
		return msg;
	}
	/** 字典信息维护 ------------------------------------------------------  end **/
	
	/** 字典类型维护 ------------------------------------------------------  start **/
	/*
	 * 加载字典类型页面
	 */
	@RequestMapping("initDicType.action")
	public String initDicType(){
		return "dictionary/dicTypeList";
	}
	
	/*
	 * 获取字典信息列表
	 */
	@RequestMapping(value ="getDicTypeListJSON.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDicTypeListJSON(DicType dicType){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Page page=this.getPageObj();
			//DicType dicType = new DicType();
			json=dicTypeService.getDicTypeJson(dicType,page);
			if (!(StringUtil.isNotBlank(json))) {
				json = RESULT_EMPTY_DEFAULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	/*
	 * 跳转新增页面
	 */
	
	@RequestMapping("createDicType.action")
	public String dicCreate(){
		return "dictionary/dicTypeEdit";
	}
	
	/*
	 * 保存字典类型
	 */
	@RequestMapping(value = "saveDicType.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String  dicSave (DicType dicType){
		String msg = "true;保存信息成功！";
		try {
			boolean isNew = StringUtil.isEmpty(dicType.getId());
			if(isNew) {//新增
				dicTypeService.txCreateDicType(dicType);
			} else {//更新
				dicTypeService.txUpdateDicType(dicType);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg = "false;信息提交失败：" + e.getMessage();
		}
		return msg;
	}
	/*
	 * 查看字典类型
	 */
	@RequestMapping(value ="queryDicType.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String dicQuery(DicType dicType, Model model){
		try {
			dicType = (DicType) dicTypeService.load(dicType.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		model.addAttribute(dicType);
		return "dictionary/dicTypeQuery";
	}
	/*
	 * 编辑字典类型
	 */
	@RequestMapping("editDicType.action")
	public String dicEdit(DicType dicType, Model model){
		try {
			dicType = (DicType) dicTypeService.load(dicType.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute(dicType);
		return "dictionary/dicTypeEdit";
	}
	/*
	 * 删除字典类型
	 */
	@RequestMapping(value = "deleteDicType.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String dicDelete(DicType dicType){
		String msg="true;字典类型删除成功！";
		try {
			msg = dicTypeService.txdeleteDicType(dicType);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;字典类型删除失败：" + e.getMessage();
		}
		return msg;
	}
	
	/** 字典类型维护  ------------------------------------------------------  end **/
	
}