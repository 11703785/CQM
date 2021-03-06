<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="easyui-layout" id="person" style="width: 100%; height: 100%">
	<!-- <div region="west" title="机构树" split="true" style="width: 260px">
		<ul id="orgTree"></ul>
	</div> -->
	<div data-options="region:'center'" title="人员管理" id="personContent"
		split="true">
		<div id="user_tb" style="padding: 5px; height: auto">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="javascript:addUser()">新增</a>
				<a class="easyui-linkbutton" iconCls="icon-search" plain="true"
              		onclick="javascript:$('#user_querywin').window('open');">查询</a>
			</div>
		</div>
		<table id="user_dg"
			style="width: 100%; height: 100%; min-height: 260px;">
			<thead>
				<tr>
					<th data-options="field:'userId',halign:'center',align:'center'"
						width="8%">用户Id</th>
					<th data-options="field:'name',halign:'center',align:'center'"
						width="10%">用户名称</th>
					<th data-options="field:'orgCode',halign:'center',align:'center'"
						width="12%">所属机构</th>
					<th data-options="field:'orgName',halign:'center',align:'center'"
						width="20%">机构名称</th>
					<th
						data-options="field:'status',halign:'center',align:'center',formatter:userTypeFormatter"
						width="8%">状态</th>
					<th data-options="field:'creator',halign:'center',align:'center'"
						width="10%">创建人</th>
					<th
						data-options="field:'createTime',halign:'center',align:'center'"
						width="14%">创建时间</th>
					<th
						data-options="field:'lastLogonTime',halign:'center',align:'center'"
						width="14%">最后登录时间</th>
				</tr>
			</thead>
		</table>
		<div id="user_querywin" class="easyui-dialog" title="查询用户"
         data-options="modal:true,closed:true,iconCls:'icon-search',draggable:false,closable:false,href:'user/showquery'"
         style="width:542px;height:178px;"></div>
	</div>
</div>
<script type="text/javascript">
	function userTypeFormatter(value, row, index){
		var arr = appDicMap[appDicKey.userStatus]
	    for (var i = 0; i < arr.length; i++) {
	        if (value === arr[i].key) {
	            return "<span style='color:green;'>" + arr[i].value + "</span>";
	        }
	    }
	    return "<span style='color:green;'>" + value + "</span>";
	}
	// 初始化机构树
	$(function(){
		var orgTree = $('#orgTree').tree({
			checkbox:false,
			url:'',
			// 异步加载机构树
			onBeforeExpand:function(node){orgTreeExpand(this,node)},
			onSelect:function(node){loadgridbyparam('org','orgCode',node.id)},
			onLoadSuccess:function(){
				var sNode = $('#personDeptTree').tree('getSelected');
				if(sNode==null){
					// 选中根节点
					var node = $('#personDeptTree').tree('getRoot');
					$('#personDeptTree').tree('select',node.target);
					$('#personDeptTree').tree('expand',node.target);
					}
				}
		});
	});
	function addUser() {
		new rjhc.showDialog({
			id : 'user_newwin',
			modal : true,
			closed : true,
			iconCls : 'icon-add',
			draggable : false,
			width : 530,
			title : "新增用户",
			height : 550,
			cache : false,
			href : 'user/showadd',
		}).window('open');
	}
	creategrid("user", "userId");
</script>