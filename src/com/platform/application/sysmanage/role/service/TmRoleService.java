package com.platform.application.sysmanage.role.service;

import java.util.List;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.role.TmRoleDto;

public interface TmRoleService {

	/**
	 * 新增平台角色信息.
	 *
	 * @param dto 平台角色交互对象
	 * @return 保存后平台角色交互对象
	 * @throws Exception 逻辑处理失败
	 */
	public TmRoleDto persist(final TmRoleDto dto) throws Exception;

	/**
	 * 修改平台角色信息.
	 *
	 * @param dto 平台角色交互对象
	 * @return 修改后平台角色交互对象
	 */
	public TmRoleDto update(final TmRoleDto dto);

	/**
	 * 修改平台角色权限信息.
	 *
	 * @param dto 平台角色交互对象
	 * @return 修改后平台角色交互对象
	 */
	public TmRoleDto updateRight(final TmRoleDto dto);

	/**
	 * 通过角色代码获取平台角色信息.
	 *
	 * @param roleCode 角色代码
	 * @param cascade  是否级联
	 * @return 平台角色交互对象
	 */
	public TmRoleDto findById(final String roleCode, final boolean cascade);

	/**
	 * 根据条件查询平台角色列表.
	 *
	 * @param dto 平台角色交互对象
	 * @return 平台角色列表
	 */
	public List<TmRoleDto> findRolesByDto(final TmRoleDto dto);

	/**
	 * 根据条件分页查询平台角色.
	 *
	 * @param dto 平台角色交互对象
	 * @return 平台角色查询结果
	 */
	public PageResponse<TmRoleDto> findByDto(final TmRoleDto dto);

	/**
	 * 启用/停用平台角色.
	 *
	 * @param roleCode   角色代码
	 * @param isStart    启停标识
	 * @param stopReason 停用原因
	 */
	public void stopOrStartRole(final String roleCode, final boolean isStart, final String stopReason);
}
