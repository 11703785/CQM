package com.platform.application.rateindex.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.rateindex.domain.RateIndex;
import com.platform.application.rateindex.service.RateIndexService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;
@Controller
public class RateIndexAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(RateIndexAction.class);
	private RateIndexService  rateIndexService;
	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	@Autowired
	public void setRateIndexService(RateIndexService rateIndexService) {
		this.rateIndexService = rateIndexService;
	}
	@InitBinder("rateIndex")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("rateIndex.");
    }
	/**
	 * 跳转页面
	 */
	@RequestMapping("initRateIndexManage.action")
	public String redirect(){
		return "rateindex/rateIndexFrame";
	}
	
	/**
	 * 指标树
	 */
	@RequestMapping(value = "loadRateIndexJson.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadRateIndexJson(String id){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			Person person = (Person) this.getCurrentUser();
			//person = (Person)personService.load(person.getPersonId())	;
			json = rateIndexService.loadRateIndex(id, person);
			if(StringUtil.isEmpty(json)) 
				json = RESULT_EMPTY_DEFAULT;
			logger.debug("load rateIndexJSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查看页面
	 */
	@RequestMapping("listRateIndex.action")
	public String listRateIndex(RateIndex rateIndex, Model model){
	   // rateIndex= (RateIndex) rateIndexService.load(rateIndex.getId());
		List lists = new ArrayList();
	    String sql = "from RateIndexOptions o where o.rateIndex.id='"+rateIndex.getId()+"'";
		lists = rateIndexService.find(sql);
		model.addAttribute(rateIndex);
		if(lists.size() > 0){
			model.addAttribute("ls",lists.size());
		}

		return "rateindex/rateIndexList";
	}
	
	
	/**
	 * 跳转到创建父节点页面
	 */
	@RequestMapping("createIndex.action")
	public String createIndex(String id, Model model) {
		RateIndex rateIndex = new RateIndex();
		try {
			if(!StringUtil.isBlank(id)&&!id.equals("-1")){
				RateIndex parent = (RateIndex) rateIndexService.load(id);
				rateIndex.setParent(parent);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(rateIndex);
		return "rateindex/rateIndexCreate";
	}
	/**
	 * 保存指标显示
	 */
	@RequestMapping(value = "saveRateIndex.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveRateIndex(String id,HttpServletRequest request){
		String msg="true;保存成功！";
		RateIndex rateIndex = new  RateIndex();
		try {
			rateIndex = (RateIndex) rateIndexService.load(id);
			Person person = (Person)this.getCurrentUser();
			rateIndexService.txSaveRateIndex(rateIndex,person,request);
		} catch (Exception e) {
			e.printStackTrace();
			msg="false;保存失败:"+e.getMessage();
		}
		return msg;
	}
	/**
	 * 校验指标名称是否相同
	 */
	private boolean isEqualName(String name){
		boolean flag = true;
		List list = new ArrayList();
		try {
			list = rateIndexService.getNames(name);
			if(list.size() > 0){
				flag = false;
			}else{
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 删除指标
	 */
	@RequestMapping(value = "deleteRateIndex.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteRateIndex(String id){
		String msg = "true;删除成功！";
		boolean flag = true;
		try {
			flag = isDelete(id);
			if(flag){
				rateIndexService.txDeleteAll(id);
			}else{
				msg = "false;删除失败,请查看该指标下是否存在评分标准或子指标！";
			}
		} catch (Exception e) {
			msg = "false;删除失败！";
		}
		return msg;
	}
	
	/**
	 * 校验是否可以删除当前指标
	 */
	private boolean isDelete(String id){
		boolean flag = true;
		List lists = new ArrayList();//是否存在指标选项的集合
		List list = new ArrayList();//是否存在下级的集合
		try {
			lists = rateIndexService.getRateIndexOptios(id);
			list = rateIndexService.getRateIndexNext(id);
			if(lists.size()>0){
				flag = false;
			}else if(list.size()>0){
				flag = false;
			}else{
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 编辑指标
	 */
	@RequestMapping("editRateIndex.action")
	public String editRateIndex(String id,Model model){
		RateIndex rateIndex = new RateIndex();
		try {
			rateIndex = (RateIndex) rateIndexService.load(id);
			List<RateIndex> rateIndexs=rateIndexService.findRateIndex(id);
			 model.addAttribute(rateIndex);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		return "rateindex/rateIndexEdit";
	}
	/**
	 * 编辑指标保存
	 */
	@RequestMapping(value = "saveRateIndexEdit.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveRateIndexEdit(RateIndex rateIndex,String type) throws Exception{
		String msg="true;保存成功！";
		type = this.getRequest().getParameter("types");
		try {
			if(StringUtils.isNotBlank(rateIndex.getId())){
				rateIndexService.txUpdate(rateIndex,type);
			}
		} catch (Exception e) {
			msg="false;保存失败:"+e.getMessage();
		}
		return msg;
	}
	/**
	 * 查看指标
	 */
	@RequestMapping("viewRateIndex.action")
	public String view(RateIndex rateIndex, Model model) {
		try {
			if (rateIndex != null) {
				if (StringUtil.isNotEmpty(rateIndex.getId())&&!rateIndex.getId().equals("-1")) {
					rateIndex = (RateIndex) rateIndexService.load(rateIndex.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(rateIndex);
		return "rateindex/rateIndexView";
	}
}
