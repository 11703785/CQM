<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<script type="text/javascript">
	$(document).ready(function(){
	$('#authorizeTree').tree({
			checkbox:true,
			cascadeCheck:true,
			url:'loadOptResourceJson.action',
			onBeforeExpand:function(node,param){
				$('#authorizeTree').tree('options').url="loadOptResourceJson.action?node="+node.id;
			},
			onLoadSuccess:function(){
				var node = $('#authorizeTree').tree('getRoot');
				$('#authorizeTree').tree('expandAll',node.target);
			},
			onCheck: function (node, checked) {
				if (checked) {
		        var childNode = $("#authorizeTree").tree('getChildren', node.target);
                   if (childNode.length > 0) {
                        for (var i = 0; i < childNode.length; i++) {
                           $("#authorizeTree").tree('check', childNode[i].target);
                        }
                    };
                }
				 else  {
                   var childNode = $("#authorizeTree").tree('getChildren', node.target);
                    if (childNode.length > 0) {
                        for (var i = 0; i < childNode.length; i++) {
                            $("#authorizeTree").tree('uncheck', childNode[i].target);
                        }
                    };
                   
                 
                }
            }
		
		});
		$('#roleTree').tree({
			checkbox:false,
			url:'getAllRoleJSON.action',
			onSelect:function(node){
				cancelTreeChecked($('#authorizeTree'));
				$.post('getRoleOptResources.action',{'role.roleId':node.id},function(result){
					if(result!=""){
						var rArr = result.split(",");
						$.each(rArr, function(key, val){
							var node = $('#authorizeTree').tree('find',val);
							if($('#authorizeTree').tree('isLeaf',node.target))
								$('#authorizeTree').tree('check',node.target);
						});
					}
                 });
			}
		});
	});
	

</script>
<div class="easyui-layout" id="authorize" style="width:100%;height:100%">
	<div region="west" title="角色列表" split="true" style="width:200px">
		<ul id="roleTree"></ul>
	</div>
	<div region="center" title="操作资源树" split="true">
		<div style="padding:5px;border:1px solid #ddd;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="Role.submitAuthorize()">保存授权</a>
		</div>
		<ul id="authorizeTree"></ul>
	</div>
</div>



