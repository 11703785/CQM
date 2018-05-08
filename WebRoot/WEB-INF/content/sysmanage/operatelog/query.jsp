<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-center" data-options="region:'center'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="operatelog_qf" class="easyui-form" method="post">
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>用户标识:</td>
                        <td class="firstval">
                            <input class="easyui-textbox" type="text" name="userId"
                                   data-options="validType:['length[1,30]'],invalidMessage:'用户标识',missingMessage:'用户标识'"/>
                        </td>
                        <td>操作人员IP:</td>
                        <td class="secondval">
                            <input class="easyui-textbox" name="loginIp" id="loginIp"
                                    data-options="validType:['length[1,30]'],invalidMessage:'操作人员IP',missingMessage:'操作人员IP'"/>
                            </td>
                    </tr>
                    <tr>
                        <td>机构名称:</td>
                        <td><input class="easyui-textbox" type="text" name="orgName"
                                   data-options="validType:['length[1,80]'],invalidMessage:'机构名称',missingMessage:'机构名称'"/>
                        </td>
                        <td>创建起始时间:</td>
                        <td><input class="easyui-datebox" name="queryStartTime" id="queryStartTime"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建起始时间',missingMessage:'创建起始时间'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>创建结束时间:</td>
                        <td><input class="easyui-datebox" name="queryEndTime" id="queryEndTime"
                                   data-options="validType:['length[1,26]'],invalidMessage:'创建结束时间',missingMessage:'创建结束时间'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
           href="javascript:void(0)" onclick="javascript:queryoperatelogrow()">查询</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#operatelog_qf').form('reset');">重置</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
           href="javascript:void(0)" onclick="javascript:$('#operatelog_querywin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
    function queryoperatelogrow() {
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
        queryrow("operatelog");
    }
</script>
</body>
</html>