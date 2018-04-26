package com.platform.framework.common.util;

public interface CurValues {

	// session中对象的Key值
	public static final String USER = "cur_user";         //当前用户对象
	public static final String RESOURCEMAP = "resmap";    //当前用户可用的资源MAP(以资源code名称为key)
	public static final String ActionMAP = "actionmap";   //当前用户有权限执行的ActionMap，以action名称为key和value
	public static final String ANONYMOUS = "anonymous";   //匿名角色对象
	public static final String DEFAULT = "default";       //默认角色对象
	public static final String ROOT = "root";             //默认超级角色对象
	public static final String SYSROLE = "00";            //系统角色类型
	public static final String REASONTYPE = "reasontype"; //信息提示原因

	// 登录及注销相关提示信息
	public static final String LOGONMSG1 = "用户名或密码错误！";
	public static final String LOGONMSG2 = "当前用户未开通帐号！";
	public static final String LOGONMSG3 = "登录验证参数无效！";
	public static final String LOGONMSG4 = "IP地址验证失败！";
	public static final String LOGONMSG5 = "登录超时或权限不足！";
	public static final String LOGONMSG6 = "身份不合法！";
	public static final String LOGOUTMSG1 = "注销成功！";
	public static final String LOGDIFFERENT = "已注册机器与登录机器不一致!";

}
