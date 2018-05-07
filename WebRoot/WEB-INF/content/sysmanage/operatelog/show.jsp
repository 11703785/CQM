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
	            <%
	                if (loginInfo.haveMenuRight("910101") || loginInfo.isTopAdmin()) {
	            %>
	            <a class="easyui-linkbutton" iconCls="icon-search" plain="true"
	               onclick="javascript:$('#operatelog_querywin').window('open');">查询</a>
	            <% } %>
	        </div>
	    </div>
	    <table id="operatelog_dg"
	           style="width:100%;height:100%;min-height:260px;">
	        <thead>
	        <tr>
	            <th data-options="field:'userId',width:80,align:'center'" width="8%">用户标识</th>
	            <th data-options="field:'oprType',width:80,align:'center',formatter:transtype" width="8%">操作类型</th>
	            <th data-options="field:'oprStatus',width:80,align:'center',formatter:transtatus" width="8%">操作状态</th>
	            <th data-options="field:'oprOrgCode',width:140,align:'center'" width="8%">所在机构</th>
	            <th data-options="field:'oprInfoType',width:80,align:'center'" width="5%">信息类型</th>
	            <th data-options="field:'subsystem',width:100,align:'center'" width="11%">子系统代码</th>
	            <th data-options="field:'oprInfo',width:150,align:'left'" width="12%">操作内容</th>
	            <th data-options="field:'oprTime',width:140,align:'center'" width="12%">操作时间</th>
	            <th data-options="field:'loginIp',width:100,align:'center'" width="9%">操作人员IP</th>
	        </tr>
	        </thead>
	    </table>
	    <div id="operatelog_querywin" class="easyui-dialog" title="查询"
	         data-options="modal:true,closable:false,closed:true,iconCls:'icon-search',draggable:false,href:'operatelog/showquery'"
	         style="width:522px;height:238px;"></div>
	    <div id="operatelog_exportwin" class="easyui-dialog" title="导出"
	         data-options="modal:true,closable:false,closed:true,iconCls:'icon-search',draggable:false,href:'operatelog/showexport'"
	         style="width:522px;height:238px;"></div>
	</div>
</div>
<script type="text/javascript">
    var operatelogdic = {
        oprInfoType: [{
            key: '0',
            value: '系统日志'
        }, {
            key: '1',
            value: '业务日志'
        }]
    };
    function transtype(value, row, index) {
        if (value == 0) {
            return "系统日志";
        } else {
            return "业务日志";
        }
    }
    function transtatus(value, row, index) {
        if (value == 0) {
            return "操作成功";
        } else {
            return "操作失败";
        }
    }
    creategrid("operatelog", "id");
</script>
</body>
</html>
