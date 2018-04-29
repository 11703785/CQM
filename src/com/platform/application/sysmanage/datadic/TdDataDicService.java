package com.platform.application.sysmanage.datadic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.datadic.bean.TdDataDic;
import com.platform.application.sysmanage.service.AbstractService;

/**
 * 系统数据字典服务类.
 *
 * @see TdDataDic
 */
@Service
@Transactional
public class TdDataDicService extends AbstractService {
	/**
	 * 日志记录器.
	 */
	private static final Log LOGGER = LogFactory.getLog(TdDataDicService.class);

	/**
	 * 根据查询条件查询数据字典.
	 *
	 * @param dicDto 数据字典交互对象
	 * @return 查询结果
	 */
	@SuppressWarnings("unchecked")
	public PageResponse<TdDataDicDto> findByDto(final TdDataDicDto dicDto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始查询系统数据字典,条件[" + dicDto.toString() + "]");
		}
		try {
			final Criteria criteria = sessionFactory
					.getCurrentSession().createCriteria(TdDataDic.class);
			if (StringUtils.isNotBlank(dicDto.getCode())) {
				criteria.add(Restrictions.eq("code", dicDto.getCode()));
			}
			if (StringUtils.isNotBlank(dicDto.getName())) {
				criteria.add(Restrictions.eq("name", dicDto.getName()));
			}
			if (StringUtils.isNotBlank(dicDto.getType())) {
				criteria.add(Restrictions.eq("type", dicDto.getType()));
			}
			int count = ((Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult()).intValue();
			criteria.setProjection(null);
			int pageNumber = dicDto.getPage();
			final List<TdDataDic> results = criteria
					.setFirstResult((pageNumber - 1) * this.pageSize)
					.setMaxResults(pageNumber * this.pageSize)
					.addOrder(Order.desc("id")).list();
			List<TdDataDicDto> dtos = new ArrayList<TdDataDicDto>();
			for (TdDataDic u : results) {
				dtos.add(u.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询系统数据字典成功[" + results.toString() + "]");
			}
			return new PageResponse<TdDataDicDto>(
					true, count, pageNumber, this.pageSize, dtos);
		} catch (RuntimeException re) {
			LOGGER.error("查询系统数据字典失败,条件[" + dicDto.toString()
			+ "]:" + re.getMessage(), re);
			return new PageResponse<TdDataDicDto>(false, "查询系统数据字典失败!");
		}
	}

	/**
	 * 查询所有数据字典.
	 *
	 * @return 查询结果
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Set<TdDataDicDto>> findAllDic() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始查询系统所有数据字典");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(TdDataDic.class);
			final List<TdDataDic> results =
					criteria.addOrder(Order.desc("id")).list();
			Map<String, Set<TdDataDicDto>> dicSet =
					new HashMap<String, Set<TdDataDicDto>>();
			for (TdDataDic u : results) {
				String type = u.getType();
				Set<TdDataDicDto> dtos = dicSet.get(type);
				if (dtos == null) {
					dtos = new TreeSet<TdDataDicDto>();
					dicSet.put(type, dtos);
				}
				dtos.add(u.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询系统所有数据字典成功[" + results + "]");
			}
			return dicSet;
		} catch (RuntimeException re) {
			LOGGER.error("查询系统所有数据字典失败:" + re.getMessage(), re);
			throw re;
		}
	}
}
