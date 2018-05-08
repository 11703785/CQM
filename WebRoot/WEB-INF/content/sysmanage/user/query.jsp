<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-center" data-options="region:'center'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="user_qf" class="easyui-form" method="post">
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                    	<td>用户名称:</td>
                        <td class="firstval">
                            <input class="easyui-textbox" type="text" name="name"
                                	    data-options="validType:['name','stringLength[4,30]']"/>
                            
                        </td>
                        <td>创建用户:</td>
                        <td class="secondval">
                            <input class="easyui-textbox" type="text" name="creator"
                                   data-options="validType:['creator','stringLength[4,30]']"/>
                        </td>
                    </tr>
                    <tr>
                        <td>机构:</td>
                        <td class="firstval">
                            <select class="easyui-combotree" name="orgName" id="userCode_query"
                                    data-options="url:'org/orgtree',required:false,panelWidth:450,onBeforeExpand:function(node){orgTreeExpand(this,node)}">
                            </select>
                        </td>
                         <td>机构状态:</td>
                        <td class="secondval">
                            <input class="easyui-combobox" name="status"
                                   data-options="required:false,editable:false,panelHeight:'auto',valueField: 'key',textField: 'value',data:appDicMap[appDicKey.orgStatus]"/>
                        </td>
                    </tr>
                    <tr>
                        <td>创建起始时间:</td>
                        <td><input class="easyui-datebox" name="queryStartTime" id="creattime_s_userqu"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建起始时间',missingMessage:'创建起始时间',editable:false"/>
                        </td>
                        <td>创建结束时间:</td>
                        <td><input class="easyui-datebox" name="queryEndTime" id="creattime_e_userqu"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建结束时间',missingMessage:'创建结束时间',editable:false"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
           onclick="javascript:doqueryrowuser()">查询</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#user_qf').form('reset');$('#userCode_query').combotree('clear');$('#upUser_query').combotree('clear');">重置</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="javascript:$('#user_querywin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
	function doqueryrowuser() {
	    var d = new Date();
	    var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
	    var start = $("#creattime_s_userqu").datebox("getValue");
	    var end = $("#creattime_e_userqu").datebox("getValue");
	    if ("" != start && "" != end) {
	        var result = validatorDate(start, end);
	        if (result == -1) {
	            $.messager.alert("错误", "创建结束日不能晚于创建起始日", "error");
	            return;
	        }
	    }
	    if ("" != start) {
	        var result = validatorDate(start, str);
	        if (result == -1) {
	            $.messager.alert("错误", "创建起始时间不能晚于今日", "error");
	            return;
	        }
	    }
	    if ("" != end) {
	        var result = validatorDate(end, str);
	        if (result == -1) {
	            $.messager.alert("错误", "创建结束时间不能晚于今日", "error");
	            return;
	        }
	    }
	    queryrow("user");
	}
</script>
</body>
</html>