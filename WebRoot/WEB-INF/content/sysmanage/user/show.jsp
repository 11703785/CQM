<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-layout" id="person" style="width: 100%;height:100%">
	<div data-options="region:'center'" title="人员管理" id="personContent" split="true">
		<div id="user_tb" style="padding: 5px; height: auto">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="javascript:addUser()">新增</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="Person.deleteAll()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-undo" plain="true" onclick="Person.resetPassword()">重置密码</a>
			</div>
		</div>
		<table id="user_dg" style="width:100%;height:100%;min-height:260px;">
			<thead>
				<tr>
					<th data-options="field:'userId',halign:'center',align:'center'" width="8%">用户Id</th>
					<th data-options="field:'name',halign:'center',align:'center'" width="10%">用户名称</th>
					<th data-options="field:'orgCode',halign:'center',align:'center'" width="12%">所属机构</th>
					<th data-options="field:'orgName',halign:'center',align:'center'" width="20%">机构名称</th>
					<th data-options="field:'status',halign:'center',align:'center',formatter:userTypeFormatter" width="8%">状态</th>
					<th data-options="field:'creator',halign:'center',align:'center'" width="10%">创建人</th>	
					<th data-options="field:'createTime',halign:'center',align:'center'" width="14%">创建时间</th>	
					<th data-options="field:'lastLogonTime',halign:'center',align:'center'" width="14%">最后登录时间</th>				
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	function userTypeFormatter(value, row, index) {
	    var arr = appDicMap[appDicKey.userStatus];
	    for (var i = 0; i < arr.length; i++) {
	        if (value === arr[i].key) {
	            return "<span style='color:green;'>" + arr[i].value + "</span>";
	        }
	    }
	    return "<span style='color:green;'>" + value + "</span>";
	}
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
	creategrid("user", "userId");
</script>