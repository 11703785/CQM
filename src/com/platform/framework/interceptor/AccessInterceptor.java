package com.platform.framework.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.application.common.cache.CacheProxyFactory;
import com.platform.application.sysmanage.login.LoginInfo;
import com.platform.application.sysmanage.right.cache.TmRightPathCache;

public class AccessInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = Logger.getLogger(AccessInterceptor.class);

	@Autowired
	private CacheProxyFactory cacheProxyFactory;

	/**
	 * 内容长度.
	 */
	private int contextLength = 0;

	/**
	 * 字符串结尾匹配排除列表.
	 */
	private final List<String> endExcludedUrls = new ArrayList<String>(0);

	/**
	 * 构造函数.
	 */
	public AccessInterceptor() {
		endExcludedUrls.add("/");
		endExcludedUrls.add("/login");
		endExcludedUrls.add("/failure");
		endExcludedUrls.add(".js");
		endExcludedUrls.add(".css");
		endExcludedUrls.add(".png");
		endExcludedUrls.add(".gif");
		endExcludedUrls.add(".ico");
		endExcludedUrls.add(".jpg");
		endExcludedUrls.add("/index");
		endExcludedUrls.add("/index/desktop");
		endExcludedUrls.add("/index/navigation");
		endExcludedUrls.add("/logout");
		endExcludedUrls.add("/index/head");
	}

	/**
	 * 获取字符串结尾匹配排除列表.
	 *
	 * @return 字符串结尾匹配排除列表
	 */
	public final List<String> getEndExcludedUrls() {
		return endExcludedUrls;
	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler) throws Exception {
		if (cacheProxyFactory == null) {
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(request.getServletContext());
			cacheProxyFactory = wac.getBean(CacheProxyFactory.class);
		}
		if (this.contextLength == 0) {
			contextLength = request.getContextPath().length();
		}
		if (!this.isExcluded(request)) {
			HttpSession session = request.getSession();
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
			if (loginInfo == null) {
				throw new Exception("您长时间未操作，请重新登陆！");
			}
			String method = request.getMethod();
			String uri = request.getRequestURI().substring(this.contextLength);
			String[] rightCode = getRightCode(method, uri);
			if (rightCode != null && StringUtils.isNotBlank(rightCode[0])) {
				for (int i = 1; i < rightCode.length; i += 2) {
					//9-公用级别权限
					if ("8".equals(rightCode[i])) {
						return true;
					}
					//2-系统配置用户权限 0-系统管理员用户权限
					if (("1".equals(rightCode[i]) || "0".equals(rightCode[i])) && loginInfo.isTopAdmin()) {
						return true;
					}
				}
				for (int i = 0; i < rightCode.length; i += 2) {
					if (loginInfo.getRights().contains(rightCode[i])) {
						return true;
					}
				}
				throw new Exception("登录用户没有 [" + cacheProxyFactory.getCacheValue(TmRightPathCache.class, rightCode[0]) + "] 权限");
			} else {
				log.error(request.getRequestURI() + "没有设置权限");
				throw new Exception("系统没有设置 [" + method + "][" + request.getRequestURI() + "] 权限");
			}
		}
		return true;
	}



	/**
	 * 方法说明: 在完整的url路径中截取当前执行的Action名称
	 */
	public String getAction(final String url){
		if(url.lastIndexOf(".action") != -1){
			return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".action"));
		}
		return null;
	}


	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 *
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */

	@Override
	public void afterCompletion(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler, final Exception ex)
					throws Exception {
	}

	/**
	 * 根据请求方法及路径获取权限代码集合.
	 *
	 * @param method Http请求方法
	 * @param uri    Http请求路径
	 * @return 权限代码集合
	 */
	private String[] getRightCode(final String method, final String uri) {
		String[] rightCode = cacheProxyFactory.getCacheValue(TmRightPathCache.class, method + ":" + uri);
		if (rightCode == null || StringUtils.isBlank(rightCode[0])) {
			rightCode = getRightCode(method, uri, 1);
		}
		return rightCode;
	}

	/**
	 * 根据请求方法及路径获取权限代码集合.
	 *
	 * @param method Http请求方法
	 * @param path   Http请求路径
	 * @param dePath 递归层次
	 * @return 权限代码集合
	 */
	private String[] getRightCode(final String method, final String path, final int dePath) {
		String uri = path;
		int nextdePath = dePath;
		int index = uri.lastIndexOf("/");
		if (index > 0) {
			uri = uri.substring(0, index);
			StringBuilder buf = new StringBuilder().append(uri);
			for (int i = 0; i < dePath; i++) {
				buf.append("/*");
			}
			String tUri = buf.toString();
			String[] rightCode = cacheProxyFactory.getCacheValue(TmRightPathCache.class, method + ":" + tUri);
			if (rightCode == null || StringUtils.isBlank(rightCode[0])) {
				rightCode = getRightCode(method, uri, ++nextdePath);
			}
			return rightCode;
		} else {
			return null;
		}
	}

	/**
	 * 判断请求是否被拦截.
	 *
	 * @param request HTTP请求
	 * @return 是否被拦截
	 */
	private boolean isExcluded(final HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		for (String url : this.getEndExcludedUrls()) {
			if (StringUtils.endsWithIgnoreCase(requestUri, url)) {
				return true;
			}
		}
		return false;
	}

}
