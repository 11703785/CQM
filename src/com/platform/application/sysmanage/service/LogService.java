package com.platform.application.sysmanage.service;

import java.util.Date;
import java.util.List;
import com.platform.application.sysmanage.domain.Log;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * 说明: 日志处理Service
 * @version
 * @date 2009-2-27 上午11:36:13
 * 
 */
public interface LogService extends GenericDao<Log,String> {

	/**
	 * 方法说明: 根据日志内容保存日志对象
	 * @date 2009-2-27 上午11:40:49
	 */
	public void txSaveLog(String deptId, String deptName, Date operTime, String loginName, String name, String ip, String operContent) throws Exception;
	
	/**
     * 方法说明: 分页获取所有日志
	 * @param  log  日志实体
	 * @param  page 分页实体
	 * @return
	 * @throws Exception
	 */
	public List getLogs(Log log,Date beginDate,Date endDate,Page page) throws Exception;
	public String getLogsJSON(Log log,Date beginDate,Date endDate,Page page) throws Exception;

}
