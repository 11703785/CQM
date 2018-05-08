package com.platform.application.sysmanage.area.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.area.TdAreaDto;
import com.platform.application.sysmanage.area.bean.TdArea;
import com.platform.application.sysmanage.area.service.TdAreaService;
import com.platform.application.sysmanage.service.AbstractService;
/**
 * 辖区管理服务类.
 *
 */
@Service
@Transactional
public class TdAreaServiceImpl extends AbstractService implements TdAreaService {

	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = Logger.getLogger(TdAreaServiceImpl.class);

	@Override
	public TdAreaDto persist(final TdArea tdArea) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始新增辖区[" + tdArea + "]");
		}
		try {
			sessionFactory.getCurrentSession().persist(tdArea);;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增辖区成功[" + tdArea + "]");
			}
			return tdArea.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("新增辖区失败 [" + tdArea + "]:" + re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public TdAreaDto update(final TdAreaDto areaDto) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始修改辖区信息[" + areaDto + "]");
		}
		try {
			final Session session = sessionFactory.getCurrentSession();
			final TdArea area = (TdArea) session.load(TdArea.class, areaDto.getAreaId());
			area.setName(areaDto.getName());
			area.setUpArea(areaDto.getUpArea());
			area.setLevels(areaDto.getLevels());
			area.setDescription(areaDto.getDescription());
			session.update(area);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("修改辖区信息成功[" + area + "]");
			}
			return area.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("修改辖区信息失败[" + areaDto + "]:" + re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public TdAreaDto delete(final TdArea tdArea) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("正在删除 " + tdArea.toString());
		}
		try {
			Session session = sessionFactory.getCurrentSession();
			session.clear();
			session.delete(tdArea);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("删除成功 " + tdArea.toString());
			}
			return tdArea.convertDto();
		} catch (final RuntimeException re) {
			LOGGER.error("删除失败 " + tdArea.toString(), re);
			throw re;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TdAreaDto findById(final String areaId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始获取辖区信息,机构代码[" + areaId + "]");
		}
		try {
			TdAreaDto dto = null;
			final TdArea instance = (TdArea) sessionFactory.getCurrentSession().get(TdArea.class, areaId);
			if (instance != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获取辖区信息成功[" + instance.toString() + "]");
				}
				dto = instance.convertDto();
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获取辖区信息失败,不存在辖区代码为[" + areaId + "]的辖区信息");
				}
			}
			return dto;
		} catch (final RuntimeException re) {
			LOGGER.error("获取辖区信息失败，辖区代码[" + areaId + "]" + re.getMessage(), re);
			throw re;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<TdAreaDto> findByDto(final TdAreaDto dto) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("开始查询辖区信息,条件[" + dto + "]");
		}
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TdArea.class);
			if(StringUtils.isNotBlank(dto.getAreaId())){
				criteria.add(Restrictions.eq("areaId", dto.getAreaId()));
			}
			if(StringUtils.isNotBlank(dto.getName())){
				criteria.add(Restrictions.eq("name", dto.getName()));
			}
			if(StringUtils.isNotBlank(dto.getUpArea())){
				criteria.add(Restrictions.eq("upArea", dto.getUpArea()));
			}
			if(StringUtils.isNotBlank(dto.getLevels())){
				criteria.add(Restrictions.eq("levels", dto.getLevels()));
			}
			if(StringUtils.isNotBlank(dto.getDescription())){
				criteria.add(Restrictions.eq("description", dto.getDescription()));
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
			final List<TdArea> results = criteria.setFirstResult((page - 1) * rows).setMaxResults(rows)
					.addOrder(Order.asc("areaId")).list();
			final List<TdAreaDto> dtos = new ArrayList<TdAreaDto>();
			for (final TdArea u : results) {
				dtos.add(u.convertDto());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("查询辖区信息成功,条件[" + results + "]");
			}
			return new PageResponse<TdAreaDto>(true, count, page, rows, dtos);
		} catch (final RuntimeException re) {
			LOGGER.error("查询辖区信息失败,条件[" + dto + "]:" + re.getMessage(), re);
			return new PageResponse<TdAreaDto>(false, "查询辖区信息失败!");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getTree(final String areaId) {
		try{
			final Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TdArea.class);
			final StringBuilder jsonBuf = new StringBuilder();
			if(StringUtils.isBlank(areaId)){
				criteria.add(Restrictions.eq("upArea", ""));
				final List<TdArea> results = criteria.addOrder(Order.asc("areaId")).list();
				if (results.size() > 0) {
					final int length = results.size();
					int idx = 0;
					jsonBuf.append("[");
					for (final TdArea area : results) {
						jsonBuf.append("{\"id\":\"")
						.append(area.getAreaId())
						.append("\",\"text\":\"")
						.append(area.getName())
						.append("[")
						.append(area.getAreaId())
						.append("]\",\"state\":\"closed\"");
						if (++idx == length) {
							jsonBuf.append("}]");
						} else {
							jsonBuf.append("},");
						}
					}
				}
			} else {
				criteria.add(Restrictions.eq("upArea", areaId));
				final List<TdArea> results = criteria.addOrder(Order.asc("areaId")).list();
				if (results.size() > 0) {
					final int length = results.size();
					int idx = 0;
					jsonBuf.append("[");
					for (final TdArea area : results) {
						jsonBuf.append("{\"id\":\"")
						.append(area.getAreaId())
						.append("\",\"text\":\"")
						.append(area.getName())
						.append("[")
						.append(area.getAreaId())
						.append("]\",\"state\":\"closed\"");
						if (++idx == length) {
							jsonBuf.append("}]");
						} else {
							jsonBuf.append("},");
						}
					}
				}
			}
			return jsonBuf.toString();
		} catch(Exception ex){
			LOGGER.error("根据辖区代码[" + areaId + "]获取辖区树失败,失败原因:[" + ex.getMessage() + "]" , ex);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String getUpTree(final String areaId) {
		final StringBuilder buf = new StringBuilder();
		final TdArea entity = (TdArea) sessionFactory.getCurrentSession().get(TdArea.class, "62100");
		buf.append("[{\"id\":\"").append(entity.getAreaId())
		.append("\",\"text\":\"")
		.append(entity.getName())
		.append("[").append(entity.getAreaId()).append("]\"");
		// 查询上级辖区为orgCode的机构
		this.getChildren("62100", buf);
		buf.append("}]");
		return buf.toString();
	}

	/**
	 * 逐级获取辖区树.
	 *
	 * @param upOrg   上级辖区代码
	 * @param buf     辖区树字符串缓存
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	private void getChildren(final String upareaId, final StringBuilder buf) {
		final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TdArea.class);
		criteria.add(Restrictions.eq("upArea", upareaId));
		final List<TdArea> results = criteria.addOrder(Order.asc("areaId")).list();
		if (results.size() > 0) {
			buf.append(",\"children\":[");
			final int length = results.size();
			int idx = 0;
			for (final TdArea orgEntity : results) {
				buf.append("{\"id\":\"").append(orgEntity.getAreaId())
				.append("\",\"text\":\"")
				.append(orgEntity.getName())
				.append("[").append(orgEntity.getAreaId())
				.append("]\"");
				if (!results.contains(upareaId)) {
					buf.append(",\"state\":\"closed\"");
				}
				getChildren(orgEntity.getAreaId(), buf);
				if (++idx == length) {
					buf.append("}]");
				} else {
					buf.append("},");
				}
			}
		}
	}


	@Override
	public boolean isBranchOrSelf(final String areaId){
		List list = sessionFactory.getCurrentSession().createCriteria(TdArea.class)
				.add(Restrictions.eq("upArea", areaId)).list();
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
}
