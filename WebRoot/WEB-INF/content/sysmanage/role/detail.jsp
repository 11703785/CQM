<%@ page import="com.platform.application.sysmanage.role.TmRoleDto" %>
<%@ page import="com.platform.application.utils.DataFormatUtils" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
	TmRoleDto dto = (TmRoleDto) request.getAttribute("roledto");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<style type="text/css">
    .dv-table td {
        border: 0;
    }
</style>
<div class="easyui-panel rjhc-detail-content">
    <div class="rjhc-panel-inner">
        <form id="role_df" class="easyui-form" method="post">
            <!-- <input type="hidden" name="stopReason" id="stopReason"/> -->
            <table class="rjhc-table-detail" cellspacing="0">
                <tr>
                    <td>角色代码:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="roleCode"
                               id="roleCode" value="${roledto.roleCode}"
                               data-options="disabled:true"/></td>

                    <td>角色状态:</td>
                    <td class="firstval">
                        <select class="easyui-combobox" name="status" id="status"
                                data-options="value:'${roledto.status }',required:true,disabled:true,editable:false,panelHeight:'auto',valueField: 'key',textField:'value',data:appDicMap[appDicKey.roleStatus]">
                        </select></td>
                    <td>角色类型:</td>
                    <td class="firstval">
                        <input class="easyui-combobox" name="type" id="type" value="${roledto.type}"
                               data-options="required:true,disabled:true,editable:false,panelHeight:'auto',valueField: 'key',textField:'value',data:appDicMap[appDicKey.roleType]"/></td>
                </tr>
                <tr>
                    <td>角色名称:</td>
                    <td><input class="easyui-textbox" type="text" name="roleName"
                               value="${roledto.roleName}"
                               data-options="required:true,validType:['anc','length[1,80]'],missingMessage:'角色名称必填'"/>
                    </td>
                    <td>角色描述:</td>
                    <td><input class="easyui-textbox" type="text" name="roleDesc"
                               value="${roledto.roleDesc}"
                               data-options="validType:['anc','length[1,50]']"/>
                    </td>
                    <td>创建人:</td>
                    <td><input class="easyui-textbox" type="text" name="creator"
                               value="${roledto.creator}"
                               data-options="required:true,disabled:true,width:140"/></td>
                </tr>
                <tr>
                    <td>创建时间:</td>
                    <td><input class="easyui-textbox" type="text" name="creatTime"
                               value="<%=DataFormatUtils.getDateTime(dto.getCreatTime())%>"
                               data-options="required:true,disabled:true,width:140"/></td>
                     <% if ("0".equals(dto.getStatus())) {%>
                    <td>启用时间:</td>
                    <td class="secondval">
                        <input class="easyui-textbox" type="text" name="startTime"
                               value="<%=DataFormatUtils.getDateTime(dto.getStartTime())%>"
                               data-options="required:true,disabled:true,width:140"/></td>
                    <% } else {%>
                    <td>停用时间:</td>
                    <td class="secondval">
                        <input class="easyui-textbox" type="text" name="stopTime"
                               value="<%=DataFormatUtils.getDateTime(dto.getStopTime())%>"
                               data-options="required:true,disabled:true,width:140"/></td>
                    <% }%>
                    <%-- <td>停用原因:</td>
                    <td colspan="3"><input class="easyui-textbox rjhc-table-colspan-3" type="text" name="stopReason"
                                           value="${roledto.stopReason}" data-options="disabled:true"/> --%>
                </tr>
            </table>
        </form>
    </div>
    <div class="easyui-panel rjhc-detail-buttons">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)"
           onclick="javascript:roleright()">权限分配</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           onclick="javascript:updaterow('role','角色')">修改</a>
        <% if ("0".equals(dto.getStatus())) { %>
        <a id="stop" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="stoprole()">停用</a>
        <%} else {%>
        <a id="start" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" href="javascript:void(0)"
           onclick="startrole('role','角色')">启用</a>
        <% }%>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#role_df').form('reset');">重置</a>
    </div>
</div>
<script type="text/javascript">
    function roleright() {
        var roleCode = $('#roleCode').val();
        new rjhc.showDialog({
            id: 'roleright_newwin_detail',
            modal: true,
            closed: true,
            iconCls: 'icon-edit',
            draggable: false,
            width: 522,
            title: "角色分配",
            height: 550,
            href: 'role/showright/' + roleCode,
            onLoad: function () {
                disableform("role_rf");
            }
        }).window('open');
    }
    function stoprole() {
        var status = ${roledto.status};
        if (status == '1') {
            $.messager.alert("停用失败", "该用户已经被停用！", 'error');
            return;
        }
        $.messager.prompt('提示信息', '请输入停用原因：', function (r) {
            if (r) {
                if (r == '') {
                    $.messager.alert("停用失败", "停用原因不能为空！", 'error');
                    return;
                }
                $("#stopReason").val(r);
                var form = getFormObject($("#role_df"));
                $.messager.confirm("确认", "是否停用角色 [${roledto.roleCode}] ?", function (r1) {
                    if (r1) {
                        $.ajax({
                            type: "put",
                            url: "role/stop",
                            data: JSON.stringify(form),
                            dataType: "json",
                            success: function (data) {
                                if (data.status) {
                                    $.messager.alert("停用成功", "角色 [${roledto.roleCode}] 停用成功", "info", function () {
                                        $("#role_dg").datagrid("reload");
                                    });
                                } else {
                                    responseerror(data);
                                }
                            },
                            error: ajaxerror
                        });
                    }
                });
            } else {
                if (r == '') {
                    $.messager.alert("停用失败", "停用原因不能为空！", 'error');
                    return;
                } else {
                    return;
                }
            }
        });
    }
    function startrole(model, modelName) {
        var status = ${roledto.status};
        if (status == '0') {
            $.messager.alert("启用失败", "该用户已经启用！", 'error');
            return;
        }
        $.messager.confirm("确认", "是否启用角色 [${roledto.roleCode}] ?", function (r) {
            if (r) {
                $.ajax({
                    type: "put", url: "role/start/${roledto.roleCode}", dataType: "json", success: function (data) {
                        if (data.status) {
                            $.messager.alert("启用成功", "角色 [${roledto.roleCode}] 启用成功", "info", function () {
                                $("#role_dg").datagrid("reload");
                            });
                        } else {
                            responseerror(data);
                        }
                    }, error: ajaxerror
                });
            }
        });
    }
</script>
</body>
</html>
