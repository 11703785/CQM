<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-north" data-options="region:'north'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="role_rf" class="easyui-form" data-options="url:'role',method:'post'">
                <input id="up_rights" name="rights" type="hidden"/>
                <input name="roleCode" type="hidden" value="${roledto.roleCode}"/>
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>角色代码:</td>
                        <td class="firstval">
                            <input class="easyui-textbox" type="text" value="${roledto.roleCode}"
                                   data-options="required:true,disabled:true"/>
                        </td>
                        <td>角色名称:</td>
                        <td class="secondval">
                            <input class="easyui-textbox" type="text" name="roleName" value="${roledto.roleName}"
                                   data-options="required:true,disabled:true"/></td>
                    </tr>
                    <tr>
                        <td>角色状态:</td>
                        <td>
                            <select class="easyui-combobox" name="status"
                                    data-options="value:'${roledto.status}',required:true,disabled:true,editable:false,panelHeight:'auto',valueField: 'key',textField:'value',data:appDicMap[appDicKey.roleStatus]">
                            </select>
                        </td>
                        <td>角色类型:</td>
                        <td>
                            <select class="easyui-combobox" name="type"
                                    data-options="value:'${roledto.type}',required:true,disabled:true,editable:false,panelHeight:'auto',valueField: 'key',textField:'value',data:appDicMap[appDicKey.roleType]">
                            </select>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-center" data-options="region:'center'">
        <div class="easyui-panel rjhc-panel-inner" data-options="title:'权限列表',fit:true">
            <ul id="role_rf_tree" class="easyui-tree"
                data-options="checkbox:true,url:'role/tree/${roledto.roleCode}'"></ul>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
           href="javascript:void(0)" onclick="javascript:addRight('role','角色');">保存</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
           href="javascript:void(0)" onclick="javascript:$('#roleright_newwin_detail').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
    function addRight(model, modelName) {
        var parentNodes = $('#role_rf_tree').tree('getChecked', 'indeterminate');
        var nodes = $('#role_rf_tree').tree('getChecked');
        var parents = '';
        for (var i = 0; i < parentNodes.length; i++) {
            if (parents != '') {
                parents += ',';
            }
            parents += parentNodes[i].id;
        }
        var s = '';
        for (var i = 0; i < nodes.length; i++) {
            if (s != '') {
                s += ',';
            }
            s += nodes[i].id;
        }
        $("#up_rights").val(parents + ',' + s);
        var formObj = $("#" + model + "_rf");
        if (formObj.form("validate")) {
            var form = getFormObject(formObj);
            $.messager.confirm("确认", "是否分配角色   ${roledto.roleCode} 权限?",
                    function (r) {
                        if (r) {
                            $.ajax({
                                type: "put",
                                url: "role/updright",
                                data: JSON.stringify(form),
                                dataType: "json",
                                success: function (data) {
                                    if (data.status) {
                                        $.messager.alert("权限分配成功", modelName
                                                + " ${roledto.roleCode} 权限分配成功", "info", function () {
                                            $('#roleright_newwin_detail').window('close');
                                            $("#role_dg").datagrid("reload");
                                        });
                                    } else {
                                        $.messager.alert("权限分配失败", data.error, 'error');
                                    }
                                },
                                error: ajaxerror
                            });
                        }
                    });
        }
    }
</script>
</body>
</html>