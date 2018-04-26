<%--<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<div class="ftitle">修改密码</div>--%>
<%--<form id="passwordForm" method="post">--%>
<%--	<table class="table_txt" cellspacing="4" cellpadding="5">--%>
<%--		<tr class="fitem" style="display:none">--%>
<%--			<th>账户ID：</th>--%>
<%--			<td><input name="user.userId" value="${curuser.userAccount.userId}" id="userId"></td>--%>
<%--		</tr>--%>
<%--		<tr class="fitem">--%>
<%--			<th>&nbsp&nbsp&nbsp新密码：<font color="#FF0000"> *</font></th>--%>
<%--			<td><input class="easyui-textbox" onblur="UserAccount.valiNewPw()" type="password" name="newPwd" id="newPwd" data-options="required:true"style="width:220px;height:32px"></input></td>--%>
<%--		</tr>--%>
<%--		<tr>--%>
<%--				<td colspan="2" align="center"><font id="font2"></font><font id="font4" color="green"></font><input type="hidden" id="input2" name="input2" value="" /></td>--%>
<%--		    </tr>--%>
<%--		<tr class="fitem">--%>
<%--			<th>再次输入：<font color="#FF0000"> *</font></th>--%>
<%--			<td><input class="easyui-textbox" onblur="UserAccount.valiPw()" type="password" name="newPwd1" id="newPwd1" data-options="required:true"style="width:220px;height:32px"></input></td>--%>
<%--		</tr>--%>
<%--		<tr>--%>
<%--				<td align="center" colspan="2"><font id="font3"></font><font id="font5" color="green"></font><input type="hidden" id="input3" name="input3" value="" /></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td colspan="3" align="center">--%>
<%--					<div id="StrongPass"></div>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--	</table>--%>
<%--</form>--%>
<%--<div style="text-align:center;padding:10px">--%>
<%--	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="UserAccount.updatePassword()">提交</a>--%>
<%--	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>--%>
<%--</div>--%>
<%----%>


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
<form id="userForm" method="post"  >
<!--操作条区域-->

<!--信息提示区域-->
<s:component template="rocketMessage"/>

<!--表单信息区域-->
<center><div class="data_main">
<div class="date_title"></div></center>
		<table style="margin: 10px;">
			<tr>
		      
		        <td>
		        	<input type="hidden" id="user.id" name="user.id" value="${user.id }">
		        </td>
		    </tr>
		     
		     
		     <tr>
				<td>原密码&nbsp;&nbsp;&nbsp;：<font color="#FF0000"> *</font></td>
				<td><input type="password" id="oldPwd" name="oldPwd"/></td>
			</tr>
		     
			<tr>
				<td>新密码&nbsp;&nbsp;&nbsp;：<font color="#FF0000"> *</font></td>
				<td><input type="password" id="newPwd" name="newPwd" onblur="UserAccount.valiNewPw();"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font id="font2"></font><font id="font4" color="green"></font><input type="hidden" id="input2" name="input2" value="" /></td>
		    </tr>
			<tr>
				<td>再次输入：<font color="#FF0000"> *</font></td>
				<td><input type="password" id="newPwd1" name="newPwd1"  onblur="UserAccount.valiPw();"/></td>
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
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="UserAccount.updatePassword()">确定</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeDialog('common_dlg')">取消</a></tr>	
</div>
</form>
</body>
</html>
