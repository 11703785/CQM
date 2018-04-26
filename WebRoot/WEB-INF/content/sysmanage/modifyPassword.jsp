<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String webapp = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title></title>
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
</head>
<form id="userForm" method="post" action="saveUserPassword" >
<!--操作条区域-->

<!--信息提示区域-->
<s:component template="rocketMessage"/>

<!--表单信息区域-->
<center><div class="data_main">
<div class="date_title"></div></center>
		<table style="margin: 10px;">
			<tr>
		        <td>
		        	用户名&nbsp;:
		        </td>
		        <td align="left">
		        	${user.name}
		        </td>
		        <td>
		        	<input type="hidden" id="user.id" name="user.id" value="${user.id }">
		        </td>
		    </tr>
			<tr>
				<td>新密码&nbsp;：<font color="#FF0000"> *</font></td>
				<td><input type="password" id="pw" value="" onblur="valiNewPw();"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font id="font2"></font><font id="font4" color="green"></font><input type="hidden" id="input2" name="input2" value="" /></td>
		    </tr>
			<tr>
				<td>再次输入：<font color="#FF0000"> *</font></td>
				<td><input type="password" id="npw" value="" onblur="valiPw();"/></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><font id="font3"></font><font id="font5" color="green"></font><input type="hidden" id="input3" name="input3" value="" /></td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<div id="StrongPass"></div>
				</td>
			</tr>
		</table>
<br/>
<div style="text-align:center;padding:5px">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subMi()">确定</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="changePaclose()">取消</a></tr>	
</div>
</form>
</body>
</html>