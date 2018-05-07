<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north'" style="padding:5px;border:0;">
        <form id="user_rf" class="easyui-form" data-options="url:'user',method:'post'">
            <input type="hidden" id="roles" name="roles"/>
            <table cellspacing="4" class="rjhc-table-panel" width="100%">
                <tr>
                    <td>用户ID:</td>
                    <td><input class="easyui-textbox" type="text" name="userId" id="userId" value="${userdto.userId }"
                               readonly="readonly"
                               data-options="width:140,validType:['length[1,30]'],invalidMessage:'用户ID',missingMessage:'用户ID'"/>
                    </td>

                    <td>用户名称:</td>
                    <td><input class="easyui-textbox" type="text" name="name" id="username" value="${userdto.name }"
                               readonly="readonly"
                               data-options="width:140,validType:['length[1,80]'],invalidMessage:'用户名称',missingMessage:'用户名称'"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center'"
         style="padding:2px 5px;border:0;">
        <div class="easyui-panel" data-options="title:'角色列表',fit:true">
            <ul id="user_rf_tree" class="easyui-tree"
                data-options="checkbox:true,url:'user/roletree/${userdto.userId}'"></ul>
        </div>
    </div>
    <div data-options="region:'south',border:false"
         style="text-align:right;padding:5px;">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-save'"
           href="javascript:void(0)"
           onclick="javascript:save('user')" style="width:80px">保存</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
           href="javascript:void(0)"
           onclick="javascript:$('#userrole_newwin_detail').window('close');"
           style="width:80px">取消</a>
    </div>
</div>
<script type="text/javascript">

    function save(model) {
        var modelName = $("#userId").val();
        var nodes = $('#user_rf_tree').tree('getChecked');
        var s = '';
        for (var i = 0; i < nodes.length; i++) {
            if (s != '') {
                s += ',';
            }
            s += nodes[i].id;
        }
        $("#roles").val(s);
        var formObj = $("#" + model + "_rf");
        if (formObj.form("validate")) {
            var form = getFormObject(formObj);
            $.ajax({
                type: "post",
                url: "user/updrole",
                data: JSON.stringify(form),
                dataType: "json",
                success: function (data) {
                    if (data.status) {
                        $.messager.alert("角色分配成功", "[" + modelName + "] 角色分配成功", "info", function () {
                            $("#user_dg").datagrid("reload");
                            $('#userrole_newwin_detail').window('close');
                        });
                    } else {
                        $.messager.alert("角色分配失败", data.error, 'error');
                    }
                },
                error: ajaxerror
            });
        }
    }

</script>
</body>
</html>