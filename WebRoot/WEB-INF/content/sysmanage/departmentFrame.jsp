<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#deptTree').tree({
			checkbox:false,
			url:'loadDeptJson.action?node=-1',
			onBeforeExpand:function(node,param){
				$('#deptTree').tree('options').url="loadDeptJson.action?node="+node.id;
			},
			onSelect:function(node){
				$('#deptContent').panel("refresh","viewDepartment.action?department.deptId="+node.id);
			},
			onLoadSuccess:function(){
				var sNode = $('#deptTree').tree('getSelected');
				if(sNode==null){
					var node = $('#deptTree').tree('getRoot');
					$('#deptTree').tree('select',node.target);
					$('#deptTree').tree('expand',node.target);
				}
			}
		});
	});
</script>
<div class="easyui-layout" id="dept" style="width:100%;height:100%">
	<div region="west" title="机构树" split="true" style="width: 260px">
		<ul id="deptTree"></ul>
	</div>
	<div region="center" title="机构管理" id="deptContent" split="true"></div>
</div>