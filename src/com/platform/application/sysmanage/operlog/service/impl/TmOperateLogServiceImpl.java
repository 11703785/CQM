package com.platform.application.sysmanage.operlog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.dto.PageResponse;
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
	public TmOperateLogDto persist(final TmOperateLog operLog) {
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

	@Override
	@Transactional(readOnly = true)
	public TmOperateLogDto findById(final Long id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始获取日志信息,日志编号[" + id + "]");
		}
		try {
			TmOperateLogDto dto = null;
			final TmOperateLog instance = (TmOperateLog) sessionFactory.getCurrentSession().get(TmOperateLog.class, id);
			if (instance != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获取日志信息成功[" + instance.toString() + "]");
				}
				dto = instance.convertDto();
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获取日志信息失败,不存在日志编号为[" + id + "]的日志信息");
				}
			}
			return dto;
		} catch (final RuntimeException re) {
			LOGGER.error("获取日志信息失败，日志编号[" + id + "]" + re.getMessage(), re);
			throw re;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<TmOperateLogDto> findByDto(final TmOperateLogDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始查询辖区信息,条件[" + dto + "]");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TmOperateLog.class);
			if(StringUtils.isNotBlank(dto.getUserId())){
				criteria.add(Restrictions.eq("userId", dto.getUserId()));
			}
			if(StringUtils.isNotBlank(dto.getUserName())){
				criteria.add(Restrictions.eq("userName", dto.getUserName()));
			}
			if(StringUtils.isNotBlank(dto.getOprOrgCode())){
				criteria.add(Restrictions.eq("oprOrgCode", dto.getOprOrgCode()));
			}
			if(StringUtils.isNotBlank(dto.getLoginIp())){
				criteria.add(Restrictions.eq("loginIp", dto.getLoginIp()));
			}
			if(StringUtils.isNotBlank(dto.getOrgName())){
				criteria.add(Restrictions.eq("orgName", dto.getOrgName()));
			}
			if(dto.getQueryStartTime() != null){
				criteria.add(Restrictions.ge("oprTime", dto.getQueryStartTime()));
			}
			if(dto.getQueryEndTime() != null){
				criteria.add(Restrictions.lt("oprTime", dto.getQueryEndTime()));
			}
			final int count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			int page = dto.getPage();
			if (page < 1) {
				page = 1;
			}
			int rows = dto.getRows();
			if (rows < 1) {
				rows = this.pageSize;
			}
			criteria.setProjection(null);
			final List<TmOperateLog> results = criteria.setFirstResult((page - 1) * rows).setMaxResults(rows)
					.addOrder(Order.desc("id")).list();
			final List<TmOperateLogDto> dtos = new ArrayList<TmOperateLogDto>();
			for (final TmOperateLog u : results) {
				dtos.add(u.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询日志信息成功,条件[" + results + "]");
			}
			return new PageResponse<TmOperateLogDto>(true, count, page, rows, dtos);
		} catch (final RuntimeException re) {
			LOGGER.error("查询用户信息失败,条件[" + dto + "]:" + re.getMessage(), re);
			return new PageResponse<TmOperateLogDto>(false, "查询日志信息失败!");
		}
	}

}
