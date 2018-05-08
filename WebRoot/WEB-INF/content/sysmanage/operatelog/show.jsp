<%@ page import="com.platform.application.sysmanage.login.LoginInfo" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
%>
<div class="easyui-layout" id="operatelog" style="width: 100%; height: 100%">
	<div data-options="region:'center'" title="日志管理" id="operatelogContent" split="true">
	    <div id="operatelog_tb" style="padding:0px;height:auto">
	        <div>
	            <%-- <%
	                if (loginInfo.haveMenuRight("910101") || loginInfo.isTopAdmin()) {
	            %> --%>
	            <a class="easyui-linkbutton" iconCls="icon-search" plain="true"
	               onclick="javascript:$('#operatelog_querywin').window('open');">查询</a>
	           <%--  <% } %> --%>
	        </div>
	    </div>
	    <table id="operatelog_dg"
	           style="width:100%;height:100%;min-height:260px;">
	        <thead>
	        <tr>
	        	<th data-options="field:'id',width:70,align:'center'" width="6%">记录编号</th>		
	            <th data-options="field:'userId',width:80,align:'center'" width="10%">用户标识</th>
	            <th data-options="field:'userName',width:80,align:'center'" width="10%">用户名称</th>
	            <th data-options="field:'oprOrgCode',width:140,align:'center'" width="10%">所在机构</th>
	            <th data-options="field:'orgName',width:80,align:'center'" width="14%">机构名称</th>
	            <th data-options="field:'oprInfo',width:150,align:'left'" width="24%">操作内容</th>
	            <th data-options="field:'oprTime',width:140,align:'center'" width="12%">操作时间</th>
	            <th data-options="field:'loginIp',width:100,align:'center'" width="10%">操作人员IP</th>
	        </tr>
	        </thead>
	    </table>
	    <div id="operatelog_querywin" class="easyui-dialog" title="查询"
	         data-options="modal:true,closable:false,closed:true,iconCls:'icon-search',draggable:false,href:'operatelog/showquery'"
	         style="width:540px;height:210px;"></div>
	</div>
</div>
<script type="text/javascript">
    creategrid("operatelog", "id");
</script>
</body>
</html>
