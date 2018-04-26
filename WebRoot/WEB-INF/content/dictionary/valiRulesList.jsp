<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String webapp = request.getContextPath(); %>

<table id="valiRulesList" fit="true" class="easyui-datagrid" title="您的位置 >> 运维管理>> 校验规则维护" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getValiRulesListJson.action',toolbar:'#roletb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'name'" width="200">规则名称</th>
		<th data-options="field:'rules'" width="150">存储过程名称</th>
		<th data-options="field:'description'" width="300">描述</th>
	</tr>
	</thead>
</table>
<div id="roletb" style="padding:5px;height:auto">
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="ValiRules.create()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="ValiRules.edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="ValiRules.deleteAll()">删除</a>
	</div>
</div>