<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#resourceTree').tree({
			checkbox:false,
			url:'loadOptResourceJson.action?node=-1',
			onBeforeExpand:function(node,param){
				$('#resourceTree').tree('options').url="loadOptResourceJson.action?node="+node.id;
			},
			onSelect:function(node){
				if(node.id!="-1"){
					$('#resourceContent').panel("refresh","viewOptResource.action?optResource.resId="+node.id);
				}
			},
			onLoadSuccess:function(){
			var sNode = $('#resourceTree').tree('getSelected');
				if(sNode==null){
					var node = $('#resourceTree').tree('getRoot');
					$('#resourceTree').tree('select',node.target);
					$('#resourceTree').tree('expand',node.target);
				}
			}
		});
	});
</script>
<div class="easyui-layout" id="resource" style="width:100%;height:100%">
	<div region="west" title="操作资源树" split="true" style="width:200px">
		<ul id="resourceTree"><ul>
	</div>
	<div region="center" title="操作资源信息" id="resourceContent" split="true"></div>
</div>