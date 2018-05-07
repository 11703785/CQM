<%@ page import="com.platform.application.sysmanage.login.LoginInfo" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
%>
<div class="easyui-layout" style="width: 100%;height:100%">
	<div data-options="region:'center'" title="角色管理" id="roleContent" split="true">
	    <div id="role_tb" style="padding:0px;height:auto">
	        <div>
	            <%
	                if (loginInfo.isTopAdmin()) {
	            %>
	            <a class="easyui-linkbutton" iconCls="icon-add" plain="true"
	               onclick="javascript:$('#role_newwin').window('open');">新增</a>
	            <%
	                }
	            %>
	            <a class="easyui-linkbutton" iconCls="icon-search" plain="true"
	               onclick="javascript:$('#role_querywin').window('open')">查询</a>
	            <div style="display: inline-block;padding: 2px 0px 0px 10px">
	                <input id="role_search_input" class="easyui-searchbox" style="width:300px"
	                       data-options="searcher:dosearchrole,prompt:'请输入检索条件...',menu:'#role_search_menu'"/>
	
	                <div id="role_search_menu" style="width:120px">
	                    <div data-options="name:'roleCode'">角色代码</div>
	                    <div data-options="name:'roleName'">角色名称</div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <table id="role_dg" style="width:100%;height:100%;min-height:260px;">
	        <thead>
	        <tr>
	            <th data-options="field:'roleCode',width:80,halign:'center',align:'center'" width="16%">角色代码</th>
	            <th data-options="field:'roleName',width:100,halign:'center',align:'center'" width="20%">角色名称</th>
	            <th data-options="field:'status',width:50,halign:'center',align:'center',formatter:translate" width="15%">
	                状态
	            </th>
	            <th data-options="field:'creator',width:100,halign:'center',align:'center'" width="19%">创建人</th>
	            <th data-options="field:'creatTime',width:100,halign:'center',align:'center'" width="19%">创建时间</th>
	        </tr>
	        </thead>
	    </table>

	    <div id="role_newwin" class="easyui-dialog" title="新增角色"
	         data-options="modal:true,closable:false,closed:true,iconCls:'icon-add',draggable:false,cache:false,href:'role/showadd',onClose:function(){$('#role_af').form('reset');}"
	         style="width:530px;height:550px;"></div>
	    <div id="role_querywin" class="easyui-dialog" title="查询"
	         data-options="modal:true,closable:false,closed:true,iconCls:'icon-search',draggable:false,cache:true,href:'role/showquery'"
	         style="width:522px;height:200px;"></div>
	    <div id="role_downwin" class="easyui-dialog" title="导出"
	         data-options="modal:true,closable:false,closed:true,iconCls:'icon-save',draggable:false,cache:false,href:'role/showdownwin',onClose:function(){$('roleform').form('reset');}"
	         style="width:522px;height:200px;"></div>
	</div>
</div>
<script type="text/javascript">
    $(function () {
        creategrid("role", "roleCode");
    });
    function dosearchrole(value, name) {
        var formObj = $("#role_qf");
        if (formObj != null) {
            formObj.form('clear');
        }
        var o = {};
        if (value != "") {
            o[name] = value;
        }
        $("#role_dg").datagrid("load", o);
    }
    function translate(value) {
        if (value == 0) {
            return "<span style=\"color:green;\">启用</span>";
        } else {
            return "<span style=\"color:red;\">停用</span>";
        }
    }
</script>
</body>
</html>
