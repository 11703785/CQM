package com.platform.application.sysmanage.right;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.sysmanage.right.bean.TmRight;
import com.platform.application.sysmanage.service.AbstractService;

/**
 * 平台权限服务类.
 *
 */
@Service
@Transactional
public class TmRightService extends AbstractService {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TmRightService.class);

	/**
	 * 根据条件查询系统权限信息.
	 *
	 * @param dto 系统权限交互对象
	 * @return 查询结果
	 * @throws SystemFailureException 系统处理失败异常
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TmRightDto> findByDto(final TmRightDto dto) throws Exception {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("开始查询系统权限,条件[" + dto.toString() + "]");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(TmRight.class);
			if (StringUtils.isNotBlank(dto.getRightName())) {
				criteria.add(Restrictions.eq("rightName", dto.getRightName()));
			}
			if (StringUtils.isNotBlank(dto.getTopAdmin())) {
				criteria.add(Restrictions.eq("topAdmin", dto.getTopAdmin()));
			}
			final List<TmRight> results = criteria.addOrder(Order.asc("rightCode")).list();
			List<TmRightDto> dtos = new ArrayList<TmRightDto>();
			for (TmRight u : results) {
				dtos.add(u.convertDto());
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("查询系统权限成功[" + results.toString() + "]");
			}
			return dtos;
		} catch (RuntimeException re) {
			LOGGER.error("查询系统权限失败,条件[" + dto.toString() + "]:"
					+ re.getMessage(), re);
			throw new Exception(re.getMessage(), re);
		}
	}
}

