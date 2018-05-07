package com.platform.application.sysmanage.operlog.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.sysmanage.area.service.impl.TdAreaServiceImpl;
import com.platform.application.sysmanage.operlog.TmOperateLogDto;
import com.platform.application.sysmanage.operlog.bean.TmOperateLog;
import com.platform.application.sysmanage.operlog.service.TmOperateLogService;
import com.platform.application.sysmanage.service.AbstractService;

/**
 * 系统操作日志服务类.
 *
 */
@Service
@Transactional
public class TmOperateLogServiceImpl extends AbstractService implements TmOperateLogService {

	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = Logger.getLogger(TdAreaServiceImpl.class);

	@Override
	public TmOperateLogDto persist(final TmOperateLog operLog) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始日志信息[" + operLog + "]");
		}
		try {
			sessionFactory.getCurrentSession().persist(operLog);;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增日志信息成功[" + operLog + "]");
			}
			return operLog.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("新增日志信息失败 [" + operLog + "]:" + re.getMessage(), re);
			throw re;
		}
	}

}
