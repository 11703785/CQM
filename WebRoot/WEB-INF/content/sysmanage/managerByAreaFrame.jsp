<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#deptTree').tree({
			checkbox:false,
			url:'loadManagerByAreaJson.action?node=-1',
			onBeforeExpand:function(node,param){
				$('#deptTree').tree('options').url="loadManagerByAreaJson.action?node="+node.id;
			},
			onSelect:function(node){
				$('#deptContent').panel("refresh","viewManagerByArea.action?area.id="+node.id);
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
	<div region="west" title="辖区树" split="true" style="width: 260px">
		<ul id="deptTree"></ul>
	</div>
	<div region="center" title="机构管理" id="deptContent" split="true"></div>
</div>