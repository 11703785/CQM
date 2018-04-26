<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="roleListTab" fit="true" class="easyui-datagrid" title="您的位置 >> 系统管理 >> 角色管理" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getRolesJSON.action',toolbar:'#roletb'" idField="roleId" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'roleName'" width="200">角色名称</th>
		<th data-options="field:'roleCode'" width="150">角色编码</th>
		<th data-options="field:'description'" width="300">描述</th>
	</tr>
	</thead>
</table>
<div id="roletb" style="padding:5px;height:auto">
	<div id="roleList" style="margin-bottom:5px">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色名称: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text" name="role.roleName" id="roleName" data-options="required:false"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-search" onclick="Role.findData(1)">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="Role.findData(2)">重置</a>
     </div>
		<div style="margin-bottom:5px">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add"  onclick="Role.create()">新增</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-edit"  onclick="Role.edit()">编辑</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="Role.deleteAll()">删除</a>
	</div>
</div>