<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String webapp = request.getContextPath(); %>


<div class="ftitle">用户信息</div>
<form id="personForm" method="post">
<!--表单信息区域-->
	<center>
		<div class="data_main">
	</center>
	<table class="applytable" cellspacing="0" cellpadding="8" style="width: 650px"  align="center">
		<tr style="display:none">
			<th>隐藏字段：</th>
			<td>
				所属部门ID：<input name="person.department.deptId" value="${person.department.deptId}">
				用户ID:<input name="person.personId" value="${person.personId}" id="person.personId">
			</td>
		</tr>
		<tr>
			<th class="applytd">所属部门：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${department.deptName}</td>
		</tr>
		<tr>
			<th class="applytd">登录名：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor" style="width:80%;height:32px">
				<input class="easyui-textbox" type="text" name="person.userAccount.loginName" id="loginName" value="${person.userAccount.loginName}" data-options="prompt:'输入登陆名...',required:true,validType:['length[1,20]']" style="width:100%;height:32px" />
			</td>
		</tr>
		<tr>
			<th class="applytd">姓名：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor" style="width:80%;height:32px">
				<input class="easyui-textbox" name="person.userAccount.userName" id="userName" value="${person.userAccount.userName}" data-options="prompt:'输入姓名...',required:true,validType:['length[1,20]']" style="width:100%;height:32px"/>
			</td>
		</tr>
		<tr>
			<th class="applytd">人员序号：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor" style="width:80%;height:32px">
				<input class="easyui-textbox" data-options="prompt:'输入人员序号...',required:true,validType:['length[1,20]']" style="width:100%;height:32px" name="person.personOrder" id="personOrder" value="${person.personOrder}"  />
			</td>
		</tr>
		<tr>
			<th class="applytd">电子邮件：&nbsp&nbsp&nbsp</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">
				<input class="easyui-textbox" data-options="prompt:'输入电子邮件...',validType:['email','length[1,20]']" style="width:100%;height:32px" name="person.email" id="personEmail" value="${person.email}"  />
			</td>
		</tr>
		<tr>
			<th class="applytd">电话号码：&nbsp&nbsp&nbsp</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">
				<input class="easyui-textbox" data-options="prompt:'输入电话号码...',validType:['length[1,10]']" style="width:100%;height:32px" name="person.mobileNumber" id="mobileNumber"  value="${person.mobileNumber}"/>
			</td>
		</tr>
		<tr>
		   <c:choose>
		     <c:when test="${ismod==0}">
			<th class="applytd">用户角色：</th>
			<td colspan="3" style="color: #7b7b7b" class="applytdnotcolor">
				<table style="width: 80%">
					<tr style="width: 80%">
						<td  width="50%">
						<table style="width: 100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="0" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
						</table>
						</td>
						<td  width="50%">
						<table style="width: 100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="1" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
						</table>
						</td>
					</tr>
				</table>
			</td>
			</c:when>
			<c:otherwise>
			   <th class="applytd">用户角色：</th>
			<td colspan="3" style="color: #7b7b7b" class="applytdnotcolor">
				<table style="width: 80%">
					<tr style="width: 80%">
						<td  width="50%">
						<table style="width: 100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="0" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
						</table>
						</td>
						<td  width="50%">
						<table style="width: 100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="1" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
							<tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
						</table>
						</td>
					</tr>
				</table>
			</td>
			</c:otherwise>
		</c:choose>
		
		</tr>
<%--		<tr>--%>
<%--			<th class="applytd">所属下载点：<font color="#FF0000"> *</font></th>--%>
<%--			<td class="applytdnotcolor" style="width:220px;height:32px" colspan="3" >--%>
<%--				<input class="easyui-combobox" id="appstore"  name="person.appstore.id" style="width:210px;height:32px"  data-options="prompt:'输入所属下载点...',validType:['length[1,50]'],valueField:'id',textField:'name',node:'remote',url:'getAppstore.action',value:'${person.appstore.name}',required:true">--%>
<%--			</td>--%>
<%--		</tr>--%>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Person.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>
<script type="text/javascript">
	if("${ids}"!=null){
		<c:forEach items="${roleList }" var="role">
			<c:forEach items="${ids }" var="id">
				if("${id}"=="${role.roleId }"){
					$('#${role.roleId}').attr("checked","checked");
				}
			</c:forEach>
		</c:forEach>
	}
</script>