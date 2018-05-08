package com.platform.application.sysmanage.operlog.service;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.operlog.TmOperateLogDto;
import com.platform.application.sysmanage.operlog.bean.TmOperateLog;

public interface TmOperateLogService {

	/**
	 * 新增日志信息.
	 * @param operLogDto 日志实体对象
	 * @return 日志传输对象
	 */
	public TmOperateLogDto persist(TmOperateLog operLogDto);

	/**
	 * 根据记录编号获取日志信息.
	 * @param id 记录id
	 * @return 日志信息
	 */
	public TmOperateLogDto findById(Long id);

	/**
	 * 查询所有辖区.
	 * @param dto
	 * @return PageResponse<TdAreaDto>
	 */
	public PageResponse<TmOperateLogDto> findByDto(TmOperateLogDto dto);
}
