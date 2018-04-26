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
<title>金融精准扶贫信息前置系统</title>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/demo.css"/>
<script type="text/javascript" src="<%=webapp%>/resource/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/top.css" />
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/welcome.css" />
<link rel="stylesheet" type="text/css" href="<%=webapp%>/skin/common.css" />

<script language="javascript" src="<%=webapp%>/resource/js/sysmanage.js"></script>
<script language="javascript" src="<%=webapp%>/resource/js/dictionary.js"></script>
<script language="javascript" src="<%=webapp%>/resource/js/taskexecute.js"></script>
<script language="javascript" src="<%=webapp%>/resource/js/taskmanage.js"></script>
<script language="javascript" src="<%=webapp%>/resource/js/common.js"></script>
<script language="javascript" src="<%=webapp%>/resource/js/exetract.js"></script>

<link type="text/css" href="<%=webapp%>/resource/upload/themes/default/default.css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8" src="<%=webapp%>/resource/upload/kindeditor-all.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=webapp%>/resource/upload/lang/zh_CN.js"></script>

<link rel="stylesheet" href="<%=webapp%>/highcharts/themes/default/default.css" />
<script type="text/javascript" src="<%=webapp%>/resource/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/highcharts/modules/exporting.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/highcharts/themes/grid.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/highcharts/highcharts-more.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/highcharts/highcharts-3d.js"></script>

<style type="text/css">

.tab_content {padding-top:2px;background-color:#f5f5f5;}

</style>
<script language="JavaScript">

	$(document).ready(function(){
	 var flag= ${flag};
	 if(flag=='2'){//已过期
		 $.messager.alert("操作提示", "系统当前已过期,请重新注册！！！");
	 }else if(flag == '3'){//已注册机器和登陆机器不一致
	 	$.messager.alert("操作提示","已注册机器和登陆机器不一致，请重新注册！");
	 }
	});
</script>
</head>
 
<body>
	<div  align="center"   style="padding:5px;height:auto">
	
		<table  class="applytable" style="margin-top: 30px" cellspacing="0" cellpadding="8" align="center">
			<tr>
				<td class="applytd">机构名称：</td>
				<td class="applytdnotcolor">
					<input class="easyui-textbox" readonly="readonly"   style="width:220px;height:32px"   value="${department.deptName}"/>
				</td>
				
				<td class="applytd">版本号：</td>
				<td class="applytdnotcolor">
					<input class="easyui-textbox" readonly="readonly" value="${version}"    style="width:220px;height:32px" ></input>
				</td>
			</tr>
			<c:if test="${flag eq '2'}">
				<tr>
					<td class="applytd">有效期:</td>
					<td class="applytdnotcolor" colspan="3">
						<input class="easyui-textbox" readonly="readonly" value="已过期" style="width:220px;height:32px"></input>
					</td>
				 	 
				</tr>
				<tr>
					<td class="applytd">机器码:</td>
					<td class="applytdnotcolor" colspan="3">
						<input class="easyui-textbox" multiline="true" value="${machCode}" style="width:100%;height:120px">
					</td>
				 	 
				</tr>
				<tr>
				<td class="applytd">输入注册码：</td>
				<td class="applytdnotcolor" colspan="3">
					 <form id="sysRegist" style="width: 652px;">
							<input class="easyui-textbox" name="syscode" multiline="true" value="" style="width:100%;height:150px" data-options="prompt:'输入验证码...',required:true">
					</form>
				</td>
				</tr>
				<tr>
				<td class="applytdnotcolor" colspan="4" style="text-align:center;">
					 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="sysRegist.regist()">注册</a>
				</td>
			</tr>
			</c:if>
			<c:if test="${flag eq '3'}">
				<tr>
					<td class="applytd">有效期:</td>
					<td class="applytdnotcolor" colspan="3">
						<input class="easyui-textbox" readonly="readonly" value="已过期" style="width:220px;height:32px"></input>
					</td>
				 	 
				</tr>
				<tr>
					<td class="applytd">机器码:</td>
					<td class="applytdnotcolor" colspan="3">
						<input class="easyui-textbox" multiline="true" value="${machCode}" style="width:100%;height:120px">
					</td>
				 	 
				</tr>
				<tr>

				<td class="applytd">输入注册码：</td>
				<td class="applytdnotcolor" colspan="3">
					 <form id="sysRegist" style="width: 652px;">
							<input class="easyui-textbox" name="syscode" multiline="true" value="" style="width:100%;height:150px" data-options="prompt:'输入验证码...',required:true">
					</form>
				</td>
				</tr>
				<tr>
				<td class="applytdnotcolor" colspan="4" style="text-align:center;">
					 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="sysRegist.regist()">注册</a>
				</td>
				</tr>

			</c:if>
			
		</table>
	</div>
	 
</body>
</html>