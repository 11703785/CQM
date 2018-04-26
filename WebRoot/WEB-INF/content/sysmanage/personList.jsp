<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<input type="hidden" id="departmentId" name="person.department.deptId" value="${person.department.deptId}" />
<table id="personListTab" fit="true" class="easyui-datagrid" width="auto" height="auto" 
			data-options="border:false,rownumbers:true,singleSelect:true,url:'getPersonsJSON.action?person.department.deptId=${person.department.deptId}',method:'post',toolbar:'#persontb'" idField="personId" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'personOrder',width:150">人员序号</th>
		<th data-options="field:'loginName',width:150">登录名</th>
		<th data-options="field:'personName',width:150">姓名</th>
		<th data-options="field:'mobileNumber',width:150">电话</th>
		<th data-options="field:'email',width:200">邮箱</th>
		<th data-options="field:'personId',formatter:Person.bb,width:300">操作</th>
	</tr>
	</thead>
</table>
<div id="persontb" style="padding:5px;height:auto">
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="Person.create(1)">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Person.deleteAll()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="Person.resetPassword()">重置密码</a>
	</div>
</div>