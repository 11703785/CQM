package com.platform.application.operation.service;

import java.util.Date;
import java.util.List;

import com.platform.application.operation.domain.OptLog;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Log;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * 说明: 日志处理Service
 * @version
 * @date 2009-2-27 上午11:36:13
 * 
 */
public interface OptLogService extends GenericDao<Log,String> {

	/**
     * 方法说明: 分页获取所有日志
	 * @param  log  日志实体
	 * @param  page 分页实体
	 * @return
	 * @throws Exception
	 */
	public List getLogs(OptLog log,String departmentId,Date beginDate,Date endDate,Page page,Person person) throws Exception;
	//日志JSON
	public String getLogsJSON(OptLog log,String departmentId,Date beginDate,Date endDate,Page page,Person person) throws Exception;
	/**
	 * 保存上报数据日志
	 * @param org  //上报机构
	 * @param type //上报数据类型
	 * @param ip   //上报机构IP
	 * @param msg  //日志信息
	 */
	public void txSaveLog(String type, String msg,SourceOrg sourceOrg) throws Exception;

}
