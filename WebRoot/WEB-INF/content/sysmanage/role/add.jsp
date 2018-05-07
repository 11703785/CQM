<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-north" data-options="region:'north'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="role_af" class="easyui-form"
                  data-options="url:'role',method:'post'">
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>角色代码:</td>
                        <td class="firstval">
                            <input class="easyui-textbox" type="text" name="roleCode"
                                   data-options="required:true,validType:['num','forcelength[6]'],missingMessage:'角色代码必填'"/>
                        </td>
                        <td>角色名称:</td>
                        <td class="secondval">
                            <input class="easyui-textbox" type="text" name="roleName"
                                   data-options="required:true,validType:['anc','length[1,80]'],missingMessage:'角色名称必填'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>角色状态:</td>
                        <td><select class="easyui-combobox" name="status"
                                    data-options="value:'0',required:true,editable:false,panelHeight:'auto',valueField: 'key',textField:'value',data:appDicMap[appDicKey.roleStatus]">
                        </select></td>
                        <td>角色类型:</td>
                        <td><select class="easyui-combobox" name="type"
                                    data-options="required:true,editable:false,panelHeight:'auto',valueField: 'key',textField:'value',data:appDicMap[appDicKey.roleType],onSelect:changeRoleRights">
                        </select></td>
                    </tr>
                    <tr>
                        <td>角色描述:</td>
                        <td colspan="3"><input class="easyui-textbox rjhc-table-colspan-3" type="text" name="roleDesc"
                                               data-options="validType:['anc','stringLength[1,50]']"/></td>
                        <input id="rights" name="rights" type="hidden"/>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-center" data-options="region:'center'">
        <div class="easyui-panel rjhc-panel-inner" data-options="title:'权限列表',fit:true">
            <ul id="role_af_tree"/>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south',border:false">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
           href="javascript:void(0)" onclick="javascript:addRole('role','角色');">新增</a>
        <a class="easyui-linkbutton"
           data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="javascript:$('#role_newwin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
    function changeRoleRights(record) {
        if ($("#role_af_tree").find("div").length > 0) {
            $("#role_af_tree").empty();
        }
        $('#role_af_tree').tree({
            checkbox: true, url: 'role/rights/' + record.key
        });
    }
    function addRole(model, modelName) {
        var af_tree = $('#role_af_tree');
        var parentNodes = af_tree.tree('getChecked', 'indeterminate');
        var nodes = af_tree.tree('getChecked');
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
        if (parents != '') {
            s = parents + ',' + s;
        }
        $("#rights").val(s);
        addrow(model, modelName);
    }
</script>
</body>
</html>