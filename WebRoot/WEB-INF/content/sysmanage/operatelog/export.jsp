<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="sugar-panel-center" data-options="region:'center'">
        <div class="easyui-panel sugar-panel-inner">
            <form id="operatelog_export" name="operatelog_export" class="easyui-form" method="post">
                <table cellspacing="0" class="sugar-table-dialog">
                    <tr>
                        <td>用户标识:</td>
                        <td class="firstval">
                            <input class="easyui-textbox" type="text" name="userId"
                                   data-options="validType:['length[1,30]'],invalidMessage:'用户标识',missingMessage:'用户标识'"/>
                        </td>
                        <td>操作类型:</td>
                        <td class="secondval">
                            <select class="easyui-combobox" name="oprType" id="oprType"
                                    data-options="editable:false,panelHeight:'auto',missingMessage:'操作类型',valueField: 'key',textField:'value',data:operatelogdic.oprInfoType">
                            </select>
                    </tr>
                    <tr>
                        <td>创建起始时间:</td>
                        <td><input class="easyui-datebox" name="queryStartTime" id="queryStartTime"
                                   id="queryStartTime"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建起始时间',missingMessage:'创建起始时间'"/>
                        </td>
                        <td>创建结束时间:</td>
                        <td><input class="easyui-datebox" name="queryEndTime" id="queryEndTime"
                                   id="queryEndTime"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建结束时间',missingMessage:'创建结束时间'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>子系统代码:</td>
                        <td><input class="easyui-textbox" type="text" name="subsystem"
                                   data-options="validType:['length[1,30]'],invalidMessage:'子系统代码',missingMessage:'子系统代码'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="sugar-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
           href="javascript:void(0)" onclick="javascript:exportoperatelogrow()">导出</a>
        <a class="easyui-linkbutton"
           data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#operatelog_export').form('reset');">重置</a>
        <a class="easyui-linkbutton"
           data-options="iconCls:'icon-cancel'"
           href="javascript:void(0)"
           onclick="javascript:$('#operatelog_exportwin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
    function exportoperatelogrow() {
        var d = new Date();
        var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
        var start = $("#queryStartTime").datebox("getValue");
        var end = $("#queryEndTime").datebox("getValue");
        if ("" != start && "" != end) {
            var result = validatorDate(start, end);
            if (result == -1) {
                $.messager.alert("错误", "创建结束日必须大于等于创建起始日", "error");
                return;
            }
        }
        if ("" != start) {
            var result = validatorDate(start, str);
            if (result == -1) {
                $.messager.alert("错误", "创建起始时间不能大于系统当前日期", "error");
                return;
            }
        }
        if ("" != end) {
            var result = validatorDate(end, str);
            if (result == -1) {
                $.messager.alert("错误", "创建结束时间不能大于系统当前日期", "error");
                return;
            }
        }
        var formObj = $("#operatelog_export");
        if (formObj.form("validate")) {
        	$.messager.show({showType:'slide', showSpeed:'600',msg:'正在导出，请稍后...',title:'导出'});
            formObj.form({
                url: 'operatelog/export',
                success: function (data) {
                    var json = JSON.parse(data);
                    if (!json.status) {
                        $.messager.alert("错误", json.error, "error");
                    }
                }
            }).submit();
            $("#operatelog_exportwin").window('close');
        }
    }
</script>
</body>
</html>