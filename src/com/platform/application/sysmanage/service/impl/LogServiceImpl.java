package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.application.sysmanage.domain.Log;
import com.platform.application.sysmanage.service.LogService;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.page.Page;

@Service
public class LogServiceImpl extends GenericHibernateDao<Log,String> implements LogService {
	
	//保存日志
	public void txSaveLog(String deptId, String deptName, Date operTime, String loginName, String name, String ip, String operContent) throws Exception {
		Log log = new Log();
		log.setId(null);
		log.setDeptId(deptId);
		log.setDeptName(deptName);
		log.setOperTime(operTime);
		log.setLoginName(loginName);
		log.setName(name);
		log.setIp(ip);
		log.setOperContent(operContent);
		this.save(log);
	}
	//获得所有符合条件的日志
	public List getLogs(Log log, Date beginDate,Date endDate,Page page) throws Exception {
		List paras = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("from Log as log where 1=1");
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
		if(log!=null && StringUtil.isNotBlank(log.getDeptName())){
			sb.append(" and log.deptName like ?");
			paras.add("%"+log.getDeptName()+"%");
		}
		sb.append(" order by log.operTime desc ");
		return find(sb.toString(), paras, page);
	}
	//获得日志json
	public String getLogsJSON(Log log,Date beginDate,Date endDate,Page page) throws Exception {
		List logs = this.getLogs(log,beginDate,endDate,page);
		for(int i=0;i<logs.size();i++){
			
		}
		Map map = new HashMap();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");		
		return JsonUtil.fromCollections(logs, map);
	}

}
