package com.platform.application.sysmanage.org.service;

import java.util.List;

import com.platform.application.common.dto.PageResponse;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.org.TmOrgDto;
import com.platform.application.sysmanage.org.bean.TmOrgRela;
import com.platform.application.sysmanage.right.TmRightTreeDto;

public interface TmOrgService {

	/**
	 * 新增机构信息.
	 * @param orgDto 机构实体对象
	 * @return 机构传输对象
	 */
	public TmOrgDto persist(TmOrgDto orgDto) throws Exception;

	/**
	 * 修改机构.
	 *
	 * @param dto    机构传输对象.
	 * @param isSelf 是否为所辖机构
	 * @return 修改机构传输对象
	 * @throws Exception 修改机构逻辑校验错误
	 */
	public TmOrgDto update(TmOrgDto dto, boolean isSelf) throws Exception;

	/**
	 * 通过机构代码获取机构信息.
	 *
	 * @param orgCode 机构代码
	 * @return 机构信息
	 */
	public TmOrgDto findById(final String orgCode);

	/**
	 * 通过机构代码获取机构信息.
	 *
	 * @param orgCode 机构代码
	 * @param cascade 是否级联查询
	 * @return 机构信息
	 */
	public TmOrgDto findById(final String orgCode, final boolean cascade);

	/**
	 * 根据实例查询数据.
	 *
	 * @param dto          机构传输对象
	 * @param loginOrgCode 登录机构代码
	 * @return 查询结果
	 */
	public PageResponse<TmOrgDto> findByDto(final TmOrgDto dto, final String loginOrgCode);

	/**
	 * 获取机构树.
	 *
	 * @param orgCodeValue 机构代码,如果为空则默认当前登录用户所在机构代码
	 * @param loginInfo    当前用户登录信息
	 * @return 机构树字符串
	 */
	public String getTree(String orgCode, LoginInfo loginInfo );

	/**
	 * 获取登录用户可见机构树.
	 *
	 * @param orgCode   目标机构代码
	 * @param loginInfo 登录用户信息
	 * @return 机构树字符串
	 */
	public String getUpTree(final String orgCode, final LoginInfo loginInfo);

	/**
	 * 获取所有的机构及上级机构信息.
	 *
	 * @return 上级机构信息
	 */
	public List<TmOrgRela> selOrgAndUpOrg();

	/**
	 * 获取机构角色树.
	 *
	 * @param type    角色类型
	 * @return 角色树
	 */
	public List<TmRightTreeDto> selRoleTree(final String type);

}
