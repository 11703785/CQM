package com.platform.framework.core.page;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:翻页工厂类
 * 
 */
public class PageFactory {

	/**
	 * 构造器
	 */
	private PageFactory() {
	}

	/**
	 * 方法说明:取得page实例
	 * 
	 * @param servletrequest  前台request对象
	 * @return
	 * @author
	 * @since 2008-6-30 下午10:23:01
	 */
	public static final Page getPage(HttpServletRequest servletrequest) {
		Page page = null;
		if (servletrequest.getParameter("page") != null) {
			String pageIndex = servletrequest.getParameter("page");
			int index = Integer.parseInt(pageIndex);
			String rows = servletrequest.getParameter("rows");
			int size = Integer.parseInt(rows);
//			
//			System.out.println("index="+index);
//			System.out.println("rows="+rows);
			
			page = new Page(index,size);
		}
		return page;
	}

}
