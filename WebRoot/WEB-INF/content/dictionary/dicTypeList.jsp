<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="dicTypeList" fit="true" class="easyui-datagrid" title="您的位置 >> 运维管理 >> 字典类型维护" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getDicTypeListJSON.action',toolbar:'#dicTypetb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'name'" width="200">字典类型</th>
		<th data-options="field:'code'" width="150">字典编码</th>
	</tr>
	</thead>
</table>
<div id="dicTypetb" style="padding:5px;height:auto">
<%--	<div id="dicList" style="margin-bottom:5px">--%>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;字典类型: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:false"></input>--%>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-search" onclick="Role.findData(1)">查询</a></a><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload" plain="true" onclick="Role.findData(2)">重置</a>--%>
<%--	</div>--%>
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="DicType.create()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="DicType.edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="DicType.deleteAll()">删除</a>
	</div>
</div>


