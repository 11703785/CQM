package com.platform.framework.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.ContextLoader;

import com.platform.framework.common.util.CharacterEncoding;
import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.page.Page;
import com.platform.framework.core.page.PageFactory;

/**
 * 基本Action基类
 */

public class BaseAction{

	private static final long serialVersionUID = -2703855456551334899L;
	private Page pageObj;
	private String machCode;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext servletContext;
	private HttpSession session;
	//private PageContext pageContext;
	protected static final String EXT_SUCCESS = "success";
	protected static final String EXT_MESSAGE = "msg";
	/**
	 * 字典下拉列表返回空串
	 */
	protected static final String RESULT_COMBOBOX_EMPTY_DEFAULT = "[]";
	/**
	 * 记录默认的返回空串
	 */
	//protected static final String RESULT_EMPTY_DEFAULT = "{\"results\":0,\"rows\":[]}";
	/**
	 * 记录默认的返回空串--datatables 专用 json格式--jdw 20170110
	 */
	protected static final String RESULT_EMPTY_DEFAULT = "{\"recordsFiltered\":0,\"data\":[]}";
	/**
	 * 信息反馈持有容器
	 */
	protected Map messageHolder = new LinkedHashMap();

	public BaseAction(){
		messageHolder.put(EXT_SUCCESS, Boolean.TRUE);
	}
	
	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		
		this.request = request;
		this.response = response;
		this.pageObj = PageFactory.getPage(request);
		
		this.session = request.getSession();
//		this.machCode = SysInfo.getInfo();
		this.servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		
	}

	public Page getPageObj(){
		return this.pageObj;
	}
	
	public HttpServletRequest getRequest(){
		return request;
	}
	
	public HttpServletResponse getResponse(){
		response.setContentType(CharacterEncoding.TEXT_HTML_UTF8);
		return response;
	}

	public ServletContext getServletContext(){
		return servletContext;
	}

	public HttpSession getSession(){
		return session;
	}
	
	public String getMachCode() {
		return machCode;
	}

//	public PageContext getPageContext(){
//		return pageContext;
//	}
//	
//	public void setPageContext(PageContext pageContext){
//		this.pageContext = pageContext;
//	}

	/**
	 * 向客户端输出正确/错误JSON格式信息
	 * @param writer 输出流
	 * @param isErr 是否有错误
	 * @param errMsg 错误信息
	 * @throws IOException
	 */
	protected void sendJSON(Object obj){
		String json = "";
		if(obj!=null)
			json = obj.toString();
		Class cla = obj.getClass();
		PrintWriter pw = null;
		try{
			pw = this.getResponse().getWriter();
			if(StringUtil.equals(cla.getName(), java.lang.String.class.getName())){
				json = JsonUtil.fromString((String) obj);
			}
			if(StringUtil.equals(cla.getName(), java.util.LinkedHashMap.class
				.getName())){
				json = JsonUtil.fromObject((Map) obj);
			}
			if(StringUtil.equals(cla.getName(), "[Ljava.lang.Object;")){
				json = JsonUtil.fromObject((Object[]) obj);
			}
			this.getResponse().setContentType("text/html;charset=utf-8");
			pw.write(json);
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}

	/**
	 * 向客户端输出信息
	 * @param writer 输出流
	 * @param isErr 是否有错误
	 * @param errMsg 错误信息
	 * @throws IOException
	 */
	protected void sendMessage(String str){
		try{
			this.getResponse().getWriter().write(str);
			this.getResponse().getWriter().flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 发送错误消息
	 * @param
	 * @param success
	 * @param msg
	 */
	protected void sendMessage(boolean success, String msg){
		if(!success){
			messageHolder.put(EXT_SUCCESS, Boolean.FALSE);
		}
		messageHolder.put(EXT_MESSAGE, msg);
		this.sendJSON(messageHolder);
	}

	protected Object getCurrentUser(){
		return session.getAttribute(CurValues.USER);
	}

	protected Map getResMap(){
		return (Map) session.getAttribute(CurValues.RESOURCEMAP);
	}
	
	protected Map getActionMap(){
		return (Map) session.getAttribute(CurValues.ActionMAP);
	}

}
