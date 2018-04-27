package com.platform.application.sysmanage.user.service;

import java.util.List;

import com.platform.application.sysmanage.user.UserDto;
import com.platform.application.sysmanage.user.bean.TmUser;

public interface TmUserService {
	/**
	 * 保存用户信息
	 * @param tmUser 用户实体类对象
	 * @return 保存后交互对象
	 */
	public UserDto persist(TmUser tmUser);
	/**
	 * 更新用户信息
	 * @param tranInst 用户实体类对象
	 * @return 更新后交互对象
	 */
	public UserDto update(final TmUser tranInst);
	/**
	 * 删除用户信息
	 * @param tranInst 用户实体类对象
	 * @return 删除后交互对象
	 */
	public UserDto delete(final TmUser tranInst);
	/**
	 * 根据条件查找用户信息
	 * @param instance
	 * @return
	 */
	public List<UserDto> findFingerInfo(final UserDto dto);
	/**
	 * 通过用户ID获取用户信息,不进行级联获取
	 * @param userId 用户标识
	 * @return 用户交互对象
	 */
	public UserDto findById(final String userId);
	/**
	 * 根据用户表示查找用户信息
	 * @param userId 用户标识
	 * @param cascade 是否级联
	 * @return 用户交互对象
	 */
	public UserDto findById(final String userId, final boolean cascade);
	/**
	 * 修改用户拥有的角色信息.
	 *
	 * @param dto
	 *            用户交互实体对象
	 * @return 修改后用户交互实体对象
	 */
	public UserDto updateRole(final UserDto dto);
}