package com.platform.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.StringUtil;

public class AccessInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = Logger.getLogger(AccessInterceptor.class);

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//log.info("==============执行顺序: 1、preHandle================"+handler.toString());
		
		
		String requestUri = request.getRequestURI();  
		requestUri=getAction(requestUri);
		
		//匿名用户或直接访问的路径
		if(StringUtil.isEmpty(requestUri)||requestUri.equals("login")||requestUri.endsWith("_anonymous")||requestUri.endsWith("logout")){
			return true;
			
		}else{//只有登录用户且有权限才能访问的路径
			
			//判断SESSION有效时间
			String sessionid = (String) WebUtils.getSessionId(request);
			if(sessionid==null)
				throw new RuntimeException("您长时间未操作，请重新登陆！");
			HttpSession session = request.getSession();
			if(session.getAttribute(CurValues.USER) == null)
				throw new RuntimeException("非法访问，请重新登陆！");
			
//				//系统所有访问路径
//				Map allActionMap = ResourceFactoryManager.allActionMap;
//				if(!allActionMap.containsValue(requestUri+".action"))
//					throw new RuntimeException("非法访问，无效连接！");
//				
//				//登录用户权限内的访问路径
//				Map actionMap = (Map)session.getAttribute(CurValues.ActionMAP);
//				
//				if(!actionMap.containsValue(requestUri+".action"))
//					throw new RuntimeException("非法访问，无访问权限！");
		}
//		try {
//		} catch (Exception e) {
//			
//			log.info("访问异常："+e.getMessage());
//			//跳转到login页面！  
//			request.setAttribute("LOGIN_ERROR", e.getMessage());
//			response.sendRedirect("lte_login.jsp");
//            //request.getRequestDispatcher("/lte_login.jsp").forward(request, response);  
//            return false;  
//		}
		
		return true;
	}
	
	

	/**
	 * 方法说明: 在完整的url路径中截取当前执行的Action名称
	 */
	public String getAction(String url){
		if(url.lastIndexOf(".action") != -1){
			return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".action"));
		}
		return null;
	}
	

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
