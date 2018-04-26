package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.common.util.PropertyUtil;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.application.sysmanage.domain.UserAccount;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.LogService;
import com.platform.application.sysmanage.service.PersonService;
import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.SecurityEncode;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class PersonServiceImpl extends GenericHibernateDao<Person,String> implements PersonService{

	private static final Logger logger = Logger.getLogger(PersonServiceImpl.class);
	private DepartmentService departmentService = null;
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	private LogService logService = null;
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void createPerson(Person person) throws Exception {
		if(person!=null && person.getDepartment()!=null && StringUtil.isNotEmpty(person.getDepartment().getDeptId())){
			String deptId = person.getDepartment().getDeptId();
			Department dept = (Department)departmentService.load(deptId);
			person.setDepartment(dept);
		}
	}
    //保存用户信息
	public void txSavePerson(Person person,String roles,Person curPerson,String ip) throws Exception {
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		this.isRepeat(person);
		List roleList = new ArrayList();
		if(!StringUtil.isEmpty(roles)){
			String[] rArr = roles.split(",");
			for (int i = 0; i < rArr.length; i++) {
				Role role = new Role();
				role.setRoleId(rArr[i]);
				roleList.add(role);
			}
		}
		if (StringUtil.isEmpty(person.getPersonId())){//新建用户
			UserAccount ua = person.getUserAccount();
			ua.setUserPwd(SecurityEncode.EncoderByMd5(PropertyUtil.getPropertyByKey("INIT_PWD")));
			ua.setLastModPwdTime(new Date());
			person.setPersonId(null);
			ua.setUserId(null);
			person.setRoleList(roleList);
			this.save(person);
			logService.txSaveLog(curPerson.getDepartment().getDeptId(), curPerson.getDepartment().getDeptName(), date, curPerson.getLoginName(), curPerson.getPersonName(), 
					ip, "创建了一个新用户："+person.getPersonName()+"("+person.getLoginName()+")"+"!");
		}else{//更新用户
			Person tPerson = this.load(person.getPersonId());
			tPerson.setPersonOrder(person.getPersonOrder());
			tPerson.setMobileNumber(person.getMobileNumber());
			tPerson.setEmail(person.getEmail());
			tPerson.setRoleList(roleList);
			
			UserAccount ua=person.getUserAccount();
			if(ua!=null){
				UserAccount user=tPerson.getUserAccount();
				if(StringUtil.isNotEmpty(ua.getLoginName()))
					user.setLoginName(ua.getLoginName());
				if(StringUtil.isNotEmpty(ua.getUserName()))
					user.setUserName(ua.getUserName());
				tPerson.setUserAccount(user);
			}
			
			this.update(tPerson);
			logService.txSaveLog(curPerson.getDepartment().getDeptId(), curPerson.getDepartment().getDeptName(), date, curPerson.getLoginName(), curPerson.getPersonName(), 
					ip, "修改了"+tPerson.getDepartment().getDeptName()+"中的用户："+tPerson.getPersonName()+"("+tPerson.getLoginName()+")"+"!");
		}
	}
    //删除用户
	public void txDeletePerson(String ids,Person curPerson,String ip) throws Exception {
		if(StringUtil.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			Person person = null;
			String dept = null;
			String name = null;
			Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
			for (int i = 0; i < idArr.length; i++) {
				person = this.load(idArr[i]);
				dept = person.getDepartment().getDeptName();
				name = person.getUserAccount().getUserName();
				this.delete(person);
				logService.txSaveLog(curPerson.getDepartment().getDeptId(), curPerson.getDepartment().getDeptName(), date, curPerson.getLoginName(), curPerson.getPersonName(), 
						ip, "删除了"+dept+"中的用户："+name+"!");
			}
		}
	}
	//重置用户密码
	public void txResetPassword(String ids,Person curPerson,String ip) throws Exception {
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		if(StringUtil.isNotEmpty(ids)){
			//String[] idArr = ids.split(",");
			Person person = null;
			UserAccount ua = null;
			//for (int i = 0; i < idArr.length; i++) {
				person = this.load(ids);
				ua = person.getUserAccount();
				ua.setUserPwd(SecurityEncode.EncoderByMd5(PropertyUtil.getPropertyByKey("INIT_PWD")));
			//}
				person.setUserAccount(ua);
			 logService.txSaveLog(curPerson.getDepartment().getDeptId(),  curPerson.getDepartment().getDeptName(), date,curPerson.getLoginName(),curPerson.getPersonName(), 
					ip, "重置了用户："+person.getPersonName()+"("+person.getLoginName()+")"+"的密码!");
		}
	}
	
	public List getPersons(String deptId, Page pageInfo){
		List paras = new ArrayList(2);
		StringBuffer sb = new StringBuffer();
		sb.append("from Person person where person.department.deptId=? and person.personId!=? order by person.personOrder ASC");
		paras.add(deptId);
		paras.add(CurValues.ROOT);
		return find(sb.toString(), paras, pageInfo);
	}
    //用户json 
	public String getPersonsJSON(Person person, Page page) throws Exception {
		List users = new ArrayList(1);
		if(person.getDepartment() != null){
			users = this.getPersons(person.getDepartment().getDeptId(), page);
		}
		Map<String, String> map = new HashMap();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");
		return JsonUtil.fromCollections(users, map);
	}
   //校验用户
	public void isRepeat(Person person) throws Exception {
		String query = "from Person as person where person.userAccount.loginName = ?";
		List paras = new ArrayList();
		paras.add(person.getUserAccount().getLoginName());
		if(StringUtil.isNotEmpty(person.getPersonId())){
			query += " and person.personId != ?";
			paras.add(person.getPersonId());
		}
		List list = this.find(query, paras);
		if(list.size() > 0) throw new Exception("用户登录名重复，请重新输入！");
	}
	//登录校验
	public Person validateLoginUser(Person person) throws Exception{
		StringBuffer sb = new StringBuffer();
		List paras = new ArrayList();
		sb.append("from Person as person where person.userAccount.loginName = ? and person.userAccount.userPwd = ?");
		paras.add(person.getUserAccount().getLoginName());
		String mPwd = SecurityEncode.EncoderByMd5(person.getUserAccount().getUserPwd());
		paras.add(mPwd);
		List list = this.find(sb.toString(), paras);
		if(list.size() > 0) return (Person)list.get(0);
		return null;
		
	}
    //根据ID获得用户
	@Override
	public List<Person> getPersonById(String id) {
		StringBuffer sb = new StringBuffer();
		List paras = new ArrayList();
		sb.append("from Person as person where person.personId=?");
		paras.add(id);
		return find(sb.toString(), paras);
	}
    //获得部门下用户
	@Override
	public List<Person> getPersonByDept(String deptId) throws Exception {
		List paras = new ArrayList(2);
		StringBuffer sb = new StringBuffer();
		sb.append("from Person person where person.department.deptId=? order by person.personOrder ASC");
		paras.add(deptId);
		return find(sb.toString(), paras);
	}

}