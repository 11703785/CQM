package com.platform.application.sysmanage.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.UserAccount;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.application.sysmanage.service.UserAccountService;
import com.platform.framework.core.action.BaseAction;
@Controller
public class UserAccountAction extends BaseAction{
	
	private static final long serialVersionUID = 8708076823133807723L;
	private static final Logger logger = Logger.getLogger(UserAccountAction.class);
	private PersonService personService = null;
	private UserAccountService userAccountService = null;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	@Autowired
	public void setUserAccountService(UserAccountService userAccountService){
		this.userAccountService = userAccountService;
	}
	

	@RequestMapping("mainPassword.action")
	public String mainPassword(){
		return "modifyPassword";
	}
	
	/**
	 * 方法说明: 修改账户密码
	 */
	@RequestMapping(value="modifyPassword.action",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifyPassword(String newPassword){
		String msg = "true;用户修改密码成功！";
		String parameter = this.getRequest().getParameter("newPwd");
		String upwd=this.getRequest().getParameter("userPwd");
		String oldPwd=this.getRequest().getParameter("oldPwd");
		try{
			
			
			Person person = (Person) this.getCurrentUser();
			person = (Person) personService.load(person.getPersonId());
			UserAccount user = person.getUserAccount();
			 msg = userAccountService.ckOldPwd(user, oldPwd);
			 if(msg.equals("false")){
				 msg="false;原密码输入错误！";
			 }else{
			msg = userAccountService.txModifyPassword(user,parameter);
			 }
		}catch (Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			msg = "false;用户修改密码失败：" + e.getMessage();
		}
		return msg;
	}
	
}
