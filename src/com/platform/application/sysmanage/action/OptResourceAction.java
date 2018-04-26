package com.platform.application.sysmanage.action;

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

import com.platform.application.sysmanage.domain.OptResource;
import com.platform.application.sysmanage.service.OptResourceService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.action.BaseAction;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[OptResourceAction.java]操作资源维护功能相关操作
 */

@Controller
public class OptResourceAction extends BaseAction {

	private static final long serialVersionUID = 3302335885080538351L;
	
	private static final Logger logger = Logger.getLogger(OptResourceAction.class);
	private OptResourceService optResourceService = null;
	@Autowired
	public void setOptResourceService(OptResourceService optResourceService) {
		this.optResourceService = optResourceService;
	}
	
	@InitBinder("optResource")    
    public void initBinder(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("optResource.");
    } 
	
	/*@Actions({
		@Action(value="mainOptResource",results={@Result(name="success",location="sysmanage/optResourceMain.jsp")}),
		@Action(value="mainAuthorize",results={@Result(name="success",location="sysmanage/authorizeMain.jsp")})
	})*/
	@RequestMapping("mainOptResource.action")
	public String redirect() {
		return "sysmanage/optResourceMain";
	}
	
	/**
	 * 资源树加载
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "loadOptResourceJson.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadOptResourceJson(String id){
		String json = RESULT_EMPTY_DEFAULT;
		try {
			//String pid = this.getRequest().getParameter("id");
//			if(id==null){
//				id="-1";
//			}
			json = optResourceService.getResourcesJson(id);
			if(StringUtil.isEmpty(json)) 
				json = RESULT_EMPTY_DEFAULT;
			logger.debug("load OptResourceJSON:" + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 资源详情
	 * @param optRes
	 * @param model
	 * @return
	 */
	@RequestMapping("viewOptResource.action")
	public String view(OptResource optResource, Model model){
		String id=optResource.getResId();
		try {
			if(StringUtil.isNotBlank(optResource.getResId())){
				if(!"-1".equals(optResource.getResId())){
					List<OptResource> list=optResourceService.getOptByID(id);
					optResource=list.get(0);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(optResource);
		return "sysmanage/optResourceView";
	}
	
	/**
	 * 创建资源
	 * @param optResource
	 * @param model
	 * @return
	 */
	@RequestMapping("createOptResource.action")
	public String create(OptResource optResource, Model model){
		try {
			if(optResource!=null && optResource.getParent()!=null && StringUtil.isNotBlank(optResource.getParent().getResId())){
				OptResource parent = (OptResource) optResourceService.load(optResource.getParent().getResId());
				optResource = new OptResource();
				optResource.setParent(parent);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(optResource);
		return "sysmanage/optResourceEdit";
	}
	
	/**
	 * 编辑资源
	 * @param optResource
	 * @param model
	 * @return
	 */
	@RequestMapping("editOptResource.action")
	public String edit(OptResource optResource, Model model){
		try {
			if(StringUtil.isNotBlank(optResource.getResId())){
				optResource = (OptResource) optResourceService.load(optResource.getResId());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		model.addAttribute(optResource);
		return "sysmanage/optResourceEdit";
	}
	
	/**
	 * 保存资源
	 * @param optResource
	 * @return
	 */
	@RequestMapping(value = "saveOptResource.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String save(OptResource optResource) {
		String msg = "true;保存操作资源成功！";
		try {
			boolean isNew = StringUtil.isEmpty(optResource.getResId());
			if(isNew) {//新建
				optResourceService.txCreateOptResource(optResource);
			} else {//更新
				optResourceService.txUpdateOptResource(optResource);
			}
		}catch (Exception e) {
			e.printStackTrace();
			msg = "false;操作资源提交失败：" + e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 删除资源
	 * @param optResource
	 * @return
	 */
	@RequestMapping(value = "deleteOptResource.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String delete(OptResource optResource) {
		String msg = "true;操作资源删除成功！";
		try {
			optResourceService.txDeleteOptResource(optResource);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "false;操作资源删除失败：" + e.getMessage();
		}
		return msg;
	}
}
