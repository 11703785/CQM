<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#dictionaryTree').tree({
			checkbox:false,
			url:'loadDictionaryJson.action',
			onBeforeExpand:function(node,param){
				$('#dictionaryTree').tree('options').url="loadDictionaryJson.action?node="+node.id;
			},
			onClick:function(node){
				if(node.id!="-1"){
					$('#dictionaryContent').panel("refresh","viewDictionary.action?dic.id="+node.id);
				}
			},
			onLoadSuccess:function(){
				var node = $('#dictionaryTree').tree('getRoot');
				$('#dictionaryTree').tree('expand',node.target);
			}
		});
	});
</script>
<div class="easyui-layout" id="dictionary" style="width:100%;height:100%">
	<div region="west" title="字典树" split="true" style="width:200px">
		<ul id="dictionaryTree"><ul>
	</div>
	<div region="center" title="字典面板" data-options="href:'viewDictionary.action'" id="dictionaryContent" split="true"></div>
</div>