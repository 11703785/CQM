<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  response.setHeader("Pragma","No-cache");  
	response.setHeader("Cache-Control","no-cache");  
	response.setDateHeader("Expires", 0);  
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>陕西省征信查询监测系统</title>
<link rel="stylesheet" type="text/css" href="resource/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="resource/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="skin/demo.css"/>
<script type="text/javascript" src="resource/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resource/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="resource/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="icon" href="skin/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="skin/top.css" />
<link rel="stylesheet" type="text/css" href="skin/welcome.css" />
<link rel="stylesheet" type="text/css" href="skin/common.css" />
<link rel="stylesheet" type="text/css" href="skin/default.css" />
<script language="javascript" src="resource/echart/js/echarts.js"></script>
<script language="javascript" src="resource/echart/js/macarons.js"></script>
<script language="javascript" src="resource/js/common.js"></script>
<script language="javascript" src="resource/js/sysmanage.js"></script>
<script language="javascript" src="resource/js/monitor.js"></script>
<script language="javascript" src="resource/js/dictionary.js"></script>
<script type="text/javascript" src="resource/js/query.js"></script>
<style type="text/css">

.tab_content {padding-top:2px;background-color:#f5f5f5;}

</style>

<script language="JavaScript">
	/* var menuStyle = null;
	$(document).ready(function(){
		<c:forEach items="${dirMenus}" var="top">
			$('#${top.resId}').tree({
				checkbox:false,
				url:'getMenuTreeJson.action?node=${top.resId}',
				onBeforeExpand:function(node,param){
					$(this).tree('options').url="getMenuTreeJson.action?node="+node.id;
				},
				onSelect:function(node){
					var obj = $(this).tree("getRoot").id;
					if(menuStyle == null) 
						menuStyle = obj;
					if(menuStyle != obj){
						$(".tree-node-selected").removeClass("tree-node-selected");
						$(node.target).addClass("tree-node-selected");
						menuStyle = obj;
					}else{
						$(".tree-node-selected").removeClass("tree-node-selected");
						$(node.target).addClass("tree-node-selected");
					}
					$('#content').panel("refresh",node.attributes);
				}
			});
		</c:forEach>
		
	}); */
</script>
</head>
<body class="easyui-layout" scroll="no">
	<div data-options="region:'north',border:false,href:'index/head',split:false" style="background-color: #549fd6;height: 80px;overflow:hidden;z-index: 9998px"></div>
	<div data-options="region:'west',split:false,title:'菜单栏',collapsed:false" style="width:180px;padding-top:1px;">
		<div id="rjhcmean" class="easyui-accordion" fit="true" border="false">
			<%-- <c:forEach items="${dirMenus}" var="top">
				<div title="${top.resName}" icon="icon-ok" style="overflow:auto;padding:10px;background-color:#FCFCFC;">
					<ul id="${top.resId}"></ul>
			    </div>
			</c:forEach> --%>
		</div>
	
	</div>
	<div data-options="region:'center',border:true,plain:true, menu:'#rcmenu'" style="background-color:#fcfcfc;overflow:hidden;padding-left: 5px;">
		<div id="tabs_center" class="easyui-tabs" data-options="fit:true,plain:true,border:false">
			<div title="内容区" data-options="href:''" style="padding-top:2px;background-color:#fcfcfc;" id="content"></div>
		</div>
	</div>
 
	<div data-options="region:'south',split:false" style="height:20px;background-color: #549fd6;line-height: 20px;text-align: center;overflow:hidden;">
	<font color="white">北京融嘉合创科技有限公司</font>
	</div>
	
	<div id="tabsMenu" class="easyui-menu" style="width:120px;">  
	  <div name="Other">关闭其他</div>  
	  <div name="All">关闭所有</div>
	</div>
</body>
<script type="text/javascript" src="resource/js/init.js"></script>
<script>
	$(function () {
		$.ajax({
            url: 'profile/datadic',
            dataType: "JSON",
            async: false,
            cache: false,
            success: function (resp) {
                appDicMap = resp;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.messager.alert("", textStatus || errorThrown, "error");
            }
        });
	});
</script>
</html>