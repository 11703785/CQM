<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!--表单信息区域-->
<div class="data_main">
<table class="table_txt" cellspacing="4" cellpadding="0">
	<tr style="display:none">
		<th>隐藏字段：</th>
		<td>
			所属部门ID：<input id="departmentId" name="person.department.deptId" value="${person.department.deptId}">
			人员ID：<input id="personId" name="person.personId" value="${person.personId}">
		</td>
	</tr>
	<tr>
		<th width="20%">所属部门：</th>
		<td>${person.department.deptName}</td>
		<th width="20%">人员序号：</th>
		<td>${person.personOrder}</td>
	</tr>
	<tr>
		<th>登录名：</th>
		<td>${person.loginName}</td>
		<th>姓名：</th>
		<td>${person.personName}</td>
	</tr>
	<tr>
		<th>电子邮箱：</th>
		<th>${person.email}</td>
		<th>手机号码：</th>
		<td>${person.mobileNumber}</td>
	</tr>
	<tr>
		<th>用户角色：</th>
		<td colspan="3">${person.roleNames}</td>
	</tr>
</table>
</div>