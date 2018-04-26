package com.platform.application.sysmanage.service;

import com.platform.application.sysmanage.domain.UserAccount;
import com.platform.framework.core.dao.GenericDao;

/**
 * <p>版权所有:(C)2003-2010 rjhc</p> 
 * @作者：chenwei
 * @日期：2012-03-10 下午01:44:35 
 * @描述：[UserAccountService.java]用户账号维护功能接口
 */
public interface UserAccountService extends GenericDao<UserAccount,String>{
	/**
	 * <p>方法名称: txModifyPassword|修改用户账户密码</p>
	 * @param  id    账户对象ID
	 * @throws Exception
	 */
	public String txModifyPassword(UserAccount ua,String newPwd) throws Exception;
	
	//验证原密码输入是否正确
	public String ckOldPwd(UserAccount us,String oldPwd)throws Exception;

}
