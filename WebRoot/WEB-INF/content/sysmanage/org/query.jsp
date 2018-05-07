<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-center" data-options="region:'center'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="org_qf" class="easyui-form" method="post">
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>机构:</td>
                        <td class="firstval">
                            <select class="easyui-combotree" name="orgCode" id="orgCode_query"
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
                        <td>上级机构:</td>
                        <td class="firstval">
                            <select class="easyui-combotree" name="upOrg" id="upOrg_query"
                                    data-options="url:'org/orgtree',required:false,panelWidth:450,onBeforeExpand:function(node){orgTreeExpand(this,node)}">
                            </select>
                        </td>
                        <td>创建用户:</td>
                        <td class="secondval">
                            <input class="easyui-textbox" type="text" name="creator"
                                   data-options="validType:['username','stringLength[4,30]']"/>
                        </td>
                    </tr>
                    <tr>
                        <td>创建起始时间:</td>
                        <td><input class="easyui-datebox" name="startCreateTime" id="creattime_s_orgqu"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建起始时间',missingMessage:'创建起始时间',editable:false"/>
                        </td>
                        <td>创建结束时间:</td>
                        <td><input class="easyui-datebox" name="endCreateTime" id="creattime_e_orgqu"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建结束时间',missingMessage:'创建结束时间',editable:false"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
           onclick="javascript:doqueryroworg()">查询</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#org_qf').form('reset');$('#orgCode_query').combotree('clear');$('#upOrg_query').combotree('clear');">重置</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="javascript:$('#org_querywin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
	function doqueryroworg() {
	    var d = new Date();
	    var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
	    var start = $("#creattime_s_orgqu").datebox("getValue");
	    var end = $("#creattime_e_orgqu").datebox("getValue");
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
	    queryrow("org");
	}
</script>
</body>
</html>