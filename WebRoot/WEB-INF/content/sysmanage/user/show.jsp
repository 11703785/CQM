<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-layout" id="person" style="width: 100%;height:100%">
	<div data-options="region:'center'" title="人员管理" id="personContent" split="true">
		<input type="hidden" id="departmentId" name="person.department.deptId"
			value="${person.department.deptId}" />
		<div id="persontb" style="padding: 5px; height: auto">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="javascript:addUser()">新增</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="Person.deleteAll()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-undo" plain="true" onclick="Person.resetPassword()">重置密码</a>
			</div>
		</div>
		<table id="personListTab" fit="true" class="easyui-datagrid"
			width="auto" height="auto"
			data-options="border:false,rownumbers:true,singleSelect:true,url:'getPersonsJSON.action?person.department.deptId=${person.department.deptId}',method:'post',toolbar:'#persontb'"
			idField="personId" fitColumns="true" pagination="true">
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
	</div>
</div>
<script type="text/javascript">
function addUser() {
    new rjhc.showDialog({
        id: 'user_addwin',
        modal: true,
        closed: true,
        iconCls: 'icon-edit',
        draggable: false,
        width: 700,
        title: "新增用户",
        height: 588,
        cache: false,
        href: 'user/showadd',
    }).window('open');
}
/* 	$(document).ready(function(){
		$('#personDeptTree').tree({
			checkbox:false,
			url:'loadDeptJson.action?node=-1',
			onBeforeExpand:function(node,param){
				$('#personDeptTree').tree('options').url="loadDeptJson.action?node="+node.id;
			},
			onSelect:function(node){
				$('#personContent').panel("refresh","listPerson.action?person.department.deptId="+node.id);
			},
			onLoadSuccess:function(){
		
				var sNode = $('#personDeptTree').tree('getSelected');
				if(sNode==null){
					var node = $('#personDeptTree').tree('getRoot');
					$('#personDeptTree').tree('select',node.target);
					$('#personDeptTree').tree('expand',node.target);
				}
			}
		});
	}); */
</script>