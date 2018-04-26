<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="dictionaryManageList" fit="true" class="easyui-datagrid" title="您的位置 >> 运维管理 >> 字典信息维护" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getDictionaryManageListJSON.action',toolbar:'#dictorytb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'name'" width="200">字典名称</th>
		<th data-options="field:'code'" width="150">字典编码</th>
		<th data-options="field:'dicTypeName'" width="150">字典类型</th>
		<th data-options="field:'memo'" width="150">字典说明</th>
	</tr>
	</thead>
</table>
<div id="dictorytb" style="padding:5px;height:auto">
	<div id="roleList" style="margin-bottom:5px">
		&nbsp;&nbsp;&nbsp;&nbsp;字典类型: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text" name="dicname" id="dicname" data-options="required:false"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-search" onclick="Dictionary.findData(1)">查询</a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload" onclick="Dictionary.findData(2)">重置</a>
	</div>
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="Dictionary.create()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Dictionary.edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Dictionary.deleteAll()">删除</a>
	</div>
</div>
