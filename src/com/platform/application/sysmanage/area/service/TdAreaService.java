package com.platform.application.sysmanage.area.service;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.area.TdAreaDto;
import com.platform.application.sysmanage.area.bean.TdArea;

public interface TdAreaService {

	/**
	 * 保存辖区信息.
	 * @param tdArea 辖区实体类对象
	 * @return 保存后交互对象
	 */
	public TdAreaDto persist(TdArea tdArea) throws Exception;

	/**
	 * 更新辖区信息.
	 * @param areaDto 辖区交互实体对象
	 * @return 更新后交互对象
	 */
	public TdAreaDto update(final TdAreaDto areaDto) throws Exception;

	/**
	 * 删除辖区信息.
	 * @param tdArea 辖区实体类对象
	 * @return 删除后交互对象
	 */
	public TdAreaDto delete(final TdArea tdArea);

	/**
	 * 查询所有辖区.
	 * @param dto
	 * @return PageResponse<TdAreaDto>
	 */
	public PageResponse<TdAreaDto> findByDto(TdAreaDto dto);

	/**
	 * 查询辖区机构树.
	 * @param areaId 辖区id
	 * @return 辖区机构树
	 */
	public String getTree(String areaId);

	/**
	 * 根据辖区代码获取辖区信息.
	 * @param areaId 辖区id
	 * @return 辖区信息
	 */
	public TdAreaDto findById(String areaId);

	/**
	 * 根据辖区代码获取可见辖区树,并展开到目标辖区代码
	 * @param areaId 辖区代码
	 * @return 辖区树字符串
	 */
	public String getUpTree(final String areaId);

	/**
	 * 查看辖区编号下是否有子辖区.
	 * @param areaId 辖区编号
	 * @return 存在/不存在
	 */
	public boolean isBranchOrSelf(final String areaId);

}
