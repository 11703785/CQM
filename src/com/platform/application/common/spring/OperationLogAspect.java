package com.platform.application.common.spring;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.operlog.TmOperateLogDto;
import com.platform.application.sysmanage.operlog.service.TmOperateLogService;
import com.platform.application.utils.DataFormatUtils;

@Aspect
@Component
public class OperationLogAspect {
	/**
	 * 日志记录器
	 */
	private static final Log LOGGER = LogFactory.getLog(OperationLogAspect.class);
	/**
	 * 日志服务类
	 */
	@Autowired
	private TmOperateLogService operationLogService;

	/**
	 * Service层切点
	 */
	@Pointcut("@annotation(com.platform.application.common.spring.OperationServiceLog)")
	public void serviceAspect() {
	}

	/**
	 * Controller层切点
	 */
	@Pointcut("@annotation(com.platform.application.common.spring.OperationControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 *
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(final JoinPoint joinPoint) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes)
					RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			// 读取session中的用户
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			// 请求的IP
			String ip = DataFormatUtils.getIpAddr(request);
			// *========数据库日志=========*//
			TmOperateLogDto log = new TmOperateLogDto();
			log.setUserId(loginInfo.getUserId());
			log.setUserName(loginInfo.getUserName());
			log.setOprOrgCode(loginInfo.getOrgCode());
			log.setOrgName(loginInfo.getOrgName());
			log.setOprInfo(getControllerMethodDescription(joinPoint));
			log.setOprTime(new Date());
			log.setLoginIp(ip);
			operationLogService.persist(log.convertEntity());
		} catch (Exception e) {
			// 记录本地异常日志
			LOGGER.error("监测通知异常==" + e.getMessage());
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 *             ex
	 */
	@SuppressWarnings("rawtypes")
	public static String getControllerMethodDescription(
			final JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(OperationControllerLog.class).description();
					break;
				}
			}
		}
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			//				for (int i = 0; i < joinPoint.getArgs().length; i++) {
			params += ((AbstractDto)joinPoint.getArgs()[0]).getOperName();
			//				}
		}
		description += ":" + params;
		return description;
	}

}
