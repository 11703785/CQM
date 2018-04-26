
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="sourceOrgListTab" fit="true" class="easyui-datagrid" title="您的位置 >> 系统管理 >> 接入点管理" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getSourceOrgsJSON.action',toolbar:'#sotb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'name'" width="200">单位名称</th>
		<th data-options="field:'code'" width="150">单位编码</th>
		<th data-options="field:'area'" width="150">所属地区</th>
		<th data-options="field:'description'" width="300">描述</th>
	</tr>
	</thead>
</table>
<div id="sotb" style="padding:5px;height:auto">
	<div id="sourceOrgList" style="margin-bottom:5px">
		&nbsp;&nbsp;&nbsp;&nbsp;单位名称: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text" name="sourceOrg.name" id="sourceOrgName" data-options="required:false"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;单位编码: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text" name="sourceOrg.code" id="sourceOrgCode" data-options="required:false"></input>
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-search" onclick="DataFromManager.findData()">查询</a><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload" plain="true" onclick="DataFromManager.clear()">重置</a>
	</div>
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="DataFromManager.create()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="DataFromManager.edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="DataFromManager.deleteAll()">删除</a>
	</div>
</div>
