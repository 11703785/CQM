<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String webapp = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  response.setHeader("Pragma","No-cache");  
	response.setHeader("Cache-Control","no-cache");  
	response.setDateHeader("Expires", 0);  
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>陕西省征信查询监测系统</title>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/demo.css"/>
<script type="text/javascript" src="<%=webapp%>/resource/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="icon" href="<%=webapp%>/skin/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/top.css" />
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/welcome.css" />
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/common.css" />

<script type="text/javascript" src="<%=webapp%>/My97DatePicker/WdatePicker.js"></script>  

<script language="javascript" src="<%=webapp%>/resource/echart/js/echarts.js"></script>
<script language="javascript" src="<%=webapp%>/resource/echart/js/macarons.js"></script>


<script language="javascript" src="<%=webapp%>/resource/js/common.js"></script>

<script language="javascript" src="<%=webapp%>/resource/js/sysmanage.js"></script>

<script language="javascript" src="<%=webapp%>/resource/js/monitor.js"></script>

<script language="javascript" src="<%=webapp%>/resource/js/dictionary.js"></script>

<style type="text/css">

.tab_content {padding-top:2px;background-color:#f5f5f5;}

</style>

<script language="JavaScript">
	
	function loadContent(url){
		$('#tabs_center').panel("refresh",url);
	}
</script>
</head>
<body class="easyui-layout" scroll="no">
	<div data-options="region:'north',border:false,href:'top.action',split:false" style="background-color: #549fd6;height: 80px;overflow:hidden;z-index: 9998px"></div>

	<div data-options="region:'center',border:true,plain:true" style="background-color:#fcfcfc;overflow:hidden;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',split:false,border:false" style="overflow:hidden;height:34px;">
				<div class="easyui-panel" style="padding-left:12px;padding-top: 2px;padding-bottom: 2px;">
					<c:forEach items="${dirMenus}" var="top">
						<c:choose>
						    <c:when test="${empty top['childs']}">
								<a href='javascript:loadContent("${top['url']}")' class="easyui-linkbutton" data-options="plain:true">Home</a>
						    </c:when>
						    <c:otherwise>
								<a href="#" class="easyui-menubutton" data-options="menu:'#${top["id"]}',iconCls:'${top["ioc"]}'">${top['name']}</a>
								<div id="${top['id']}" style="width:150px;">
									<c:forEach items="${top['childs']}" var="towmenu">
										<c:choose>
										    <c:when test="${empty towmenu['childs']}">
												<div onclick='loadContent("${towmenu['url']}")' data-options="iconCls:'${towmenu["ioc"]}'">${towmenu['name']}</div>
												<div class="menu-sep"></div>
										    </c:when>
										    <c:otherwise>
											    <div>
													<span>${towmenu['name']}</span>
													<div>
														<c:forEach items="${towmenu['childs']}" var="three">
															<div data-options="iconCls:'icon-redo'">${three['name']}</div>
															<div class="menu-sep"></div>
														</c:forEach>
													</div>
												</div>
										    	<div class="menu-sep"></div>
										    
										    </c:otherwise>
										</c:choose>
									
									</c:forEach>
								</div>
						    </c:otherwise>
						</c:choose>
					</c:forEach>
				
				</div>
			</div>
			<div id="tabs_center" data-options="region:'center',fit:false,plain:true,border:false,href:'operationLog.action'" style="background-color:#fcfcfc;padding:0px;"></div>
		</div>
	
		</div>
	
	</div>
 
	<div data-options="region:'south',split:false" style="height:20px;background-color: #549fd6;line-height: 20px;text-align: center;overflow:hidden;">
	<font color="white">北京融嘉合创科技有限公司</font>
	</div>
</body>

</html>