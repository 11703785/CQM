<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#areaTree').tree({
			checkbox:false,
			url:'loadArea.action',
			onBeforeExpand:function(node,param){
			
				$('#areaTree').tree('options').url="loadArea.action?node="+node.id;
			},
			onSelect:function(node){
					
				$('#areaContent').panel("refresh","viewArea.action?area.id="+node.id);
			},
			onLoadSuccess:function(){
					
				var sNode = $('#areaTree').tree('getSelected');
				if(sNode==null){
					
					var node = $('#areaTree').tree('getRoot');
					$('#areaTree').tree('select',node.target);
					$('#areaTree').tree('expand',node.target);
				}
			}
		});
	});
</script>
<div class="easyui-layout" id="area" style="width:100%;height:100%">
	<div region="west" title="辖区树" split="true" style="width: 260px">
		<ul id="areaTree"></ul>
	</div>
	<div region="center" title="辖区管理" id="areaContent" split="true"></div>
</div>