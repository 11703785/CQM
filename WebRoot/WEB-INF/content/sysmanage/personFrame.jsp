<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#personDeptTree').tree({
			checkbox:false,
			url:'loadDeptJson.action?node=-1',
			onBeforeExpand:function(node,param){
				console.log(node.id);
				$('#personDeptTree').tree('options').url="loadDeptJson.action?node="+node.id;
			},
			onSelect:function(node){
				console.log(node.id);
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
	});
</script>
<div class="easyui-layout" id="person" style="width: 100%;height:100%">
	<div region="west" title="机构树" split="true" style="width:260px">
		<ul id="personDeptTree"></ul>
	</div>
	<div data-options="region:'center'" title="人员管理" id="personContent" split="true" ></div>
</div>