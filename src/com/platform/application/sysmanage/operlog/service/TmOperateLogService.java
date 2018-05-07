package com.platform.application.sysmanage.operlog.service;

import com.platform.application.sysmanage.operlog.TmOperateLogDto;
import com.platform.application.sysmanage.operlog.bean.TmOperateLog;

public interface TmOperateLogService {

	/**
	 * 新增日志信息.
	 * @param operLogDto 日志实体对象
	 * @return 日志传输对象
	 */
	public TmOperateLogDto persist(TmOperateLog operLogDto) throws Exception;
}
