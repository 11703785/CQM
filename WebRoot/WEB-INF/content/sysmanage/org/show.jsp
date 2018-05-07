<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-layout" style="width: 100%;height:100%">
	<div data-options="region:'center'" title="机构管理" id="personContent" split="true">
		<div id="org_tb" style="padding: 5px; height: auto">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="javascript:addUser()">新增</a>
			</div>
		</div>
		<table id="org_dg" style="width:100%;height:100%;min-height:260px;">
			<thead>
				<tr>
					<th data-options="field:'orgCode',halign:'center',align:'center'" width="10%">机构代码</th>
					<th data-options="field:'orgName',halign:'center',align:'center'" width="18%">机构名称</th>
					<th data-options="field:'upOrg',halign:'center',align:'center'" width="10%">上级机构</th>
					<th data-options="field:'pcOrgCode',halign:'center',align:'center'" width="10%">个人机构代码</th>
					<th data-options="field:'ecOrgCode',halign:'center',align:'center'" width="10%">企业机构代码</th>
					<th data-options="field:'status',halign:'center',align:'center',formatter:userTypeFormatter" width="8%">状态</th>
					<th data-options="field:'areaCode',halign:'center',align:'center',formatter:orgAreaFormatter" width="8%">所在地区</th>	
					<th data-options="field:'creator',halign:'center',align:'center'" width="8%">创建用户</th>	
					<th data-options="field:'createTime',halign:'center',align:'center'" width="14%">创建时间</th>				
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
	
	function orgAreaFormatter(value, row, index){
		$.ajax({
            type: 'get',
            url: 'area/findArea/' + value,
            async: false,
            success: function (data) {
	            if (data.status) {
	            	value = data.result.name;
	            }
            }, error: ajaxerror
        });
		return "<span style='color:green;'>" + value + "</span>";
	}
	
	function addUser() {
	    new rjhc.showDialog({
	        id: 'org_newwin',
	        modal: true,
	        closed: true,
	        iconCls: 'icon-add',
	        draggable: false,
	        width: 600,
	        title: "新增机构",
	        height: 260,
	        cache: false,
	        href: 'org/showadd',
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
	creategrid("org", "orgCode");
</script>