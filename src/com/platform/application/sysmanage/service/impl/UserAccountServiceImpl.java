package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.UserAccount;
import com.platform.application.sysmanage.service.UserAccountService;
import com.platform.framework.common.util.SecurityEncode;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class UserAccountServiceImpl extends GenericHibernateDao<UserAccount,String> implements UserAccountService{
	
	private static final Logger logger = Logger.getLogger(UserAccountServiceImpl.class);

	public String txModifyPassword(UserAccount ua,String newPwd) throws Exception {
		String msg = "true;用户修改密码成功！";
		
		UserAccount bUser = this.load(ua.getUserId());
		String mPwd = SecurityEncode.EncoderByMd5(newPwd);
		if(mPwd.equals(bUser.getUserPwd())){
			msg = "false;新密码和旧密码一样！";
		}
		bUser.setUserPwd(mPwd);
		return msg;
	}
    //校验原密码输入是否正确
	@Override
	public String ckOldPwd(UserAccount us, String oldPwd) throws Exception {
        String msg="false";
        StringBuffer sb = new StringBuffer();
		sb.append("from UserAccount ua where ua.userId= ?");
		List paras = new ArrayList(1);
		paras.add(us.getUserId());
		 List<UserAccount> find = this.find(sb.toString(), paras);
	String mPwd = SecurityEncode.EncoderByMd5(oldPwd);
    String string = find.get(0).getUserPwd();
    if(mPwd.equals(string)){
    	msg="true";
    }
		return msg;
	}

}
