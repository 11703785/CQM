package com.platform.application.operation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.operation.domain.OptLog;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.operation.service.OptLogService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Log;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class OptLogServiceImpl extends GenericHibernateDao<Log,String> implements OptLogService {
	private DepartmentService departmentService;
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	//获取日志实体
	public List getLogs(OptLog log,String departmentId, Date beginDate,Date endDate,Page page,Person person) throws Exception {
		List paras = new ArrayList();
		StringBuffer sb = new StringBuffer();
		Department dept=new Department();
		List<Department> departmentById = departmentService.getDepartmentById(departmentId);
		if(departmentById.size()>0){
			dept=departmentById.get(0);
		}
		sb.append("from OptLog as log where 1=1");
		String deptCode=person.getDepartment().getDeptId();
		String deptCode1=dept.getDeptCode();
		String deptCode_p=dept.getPersonalOrgCode();
		if(!deptCode.equals("root")&&person!=null){
			sb.append(" and log.deptCode = ? or log.deptCode=?");
			paras.add(person.getDepartment().getDeptCode());
			paras.add(person.getDepartment().getPersonalOrgCode());
		}
		if(beginDate!=null){
			sb.append(" and log.operTime >= ?");
			paras.add(beginDate);
		}
		if(endDate!=null){
			sb.append(" and log.operTime <= ?");
			paras.add(DateUtils.modDay(endDate, 0));
		}
		if(log!=null && StringUtil.isNotBlank(log.getName())){
			sb.append(" and log.name like ?");
			paras.add("%" + log.getName() + "%");
		}

		if(log!=null && StringUtil.isNotBlank(log.getIp())){
			sb.append(" and log.ip like ?");
			paras.add("%"+log.getIp()+"%");
		}
		if(log!=null && StringUtil.isNotBlank(log.getOperContent())){
			sb.append(" and log.operContent like ?");
			paras.add("%"+log.getOperContent()+"%");
		}
		if((deptCode1!=null&&deptCode1!="")||(deptCode_p!=null&&deptCode_p!="")){
			sb.append(" and log.deptCode in (?,?)");
			paras.add(deptCode1);
			paras.add(deptCode_p);

		}
		sb.append(" order by log.operTime desc ");
		return find(sb.toString(), paras, page);
	}
	//日志JSON
	public String getLogsJSON(OptLog log,String departmentId,Date beginDate,Date endDate,Page page,Person person) throws Exception {
		List logs = this.getLogs(log,departmentId,beginDate,endDate,page,person);
		Map map = new HashMap();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");		
		return JsonUtil.fromCollections(logs, map);
	}

    //保存日志
	public void txSaveLog(String type, String str,SourceOrg sourceOrg) throws Exception {
		OptLog log = new OptLog();
		log.setId(null);
		StringBuffer msg=new StringBuffer();
		if(type!=null && !type.equals("")){
			if(type.equals("1")){
				log.setDeptCode(sourceOrg.getPersonalCode());
				msg.append("成功上报个人");
				msg.append(str);
			}else if(type.equals("2")){
				log.setDeptCode(sourceOrg.getCode());			
			msg.append("成功上报企业");
			msg.append(str);
			}
   
		}
		log.setDeptName(sourceOrg.getName());
		log.setOperTime(new Date());
		log.setIp(sourceOrg.getIp());
		log.setOperContent(msg.toString());
		this.save(log);
	}

}
