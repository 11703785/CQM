<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#deptmagTree').tree({
			checkbox:false,
			url:'loadDeptmagJson.action?node=-1', 
			onBeforeExpand:function(node,param){
				$('#deptmagTree').tree('options').url="loadDeptmagJson.action?node="+node.id;
			},
			onSelect:function(node){
				$('#deptmagContent').panel("refresh","vieworgmag.action?organization.id="+node.id);
				
			},
			onLoadSuccess:function(){
				var sNode = $('#deptmagTree').tree('getSelected');
				if(sNode==null){
					var node = $('#deptmagTree').tree('getRoot');
					$('#deptmagTree').tree('select',node.target);
					$('#deptmagTree').tree('expand',node.target);
				}
			}
		});
	});
</script>
<div class="easyui-layout" style="width:100%;height:100%">
	<div region="west" title="机构树" split="true" style="width: 260px">
		<ul id="deptmagTree"></ul>
	</div>
	<div region="center" title="机构管理" id="deptmagContent" split="true"></div>
</div>