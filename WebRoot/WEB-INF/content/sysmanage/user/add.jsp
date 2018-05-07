<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-north" data-options="region:'north'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="user_af" class="easyui-form" data-options="url:'user',method:'post'">
                <input type="hidden" id="roles" name="roles"/>
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>用户ID:</td>
                        <td class="firstval">
                            <input class="easyui-textbox " type="text" name="userId"
                                   data-options="width:140,required:true,validType:['username','stringLength[5,30]'],missingMessage:'用户ID必填项'"/>
                        </td>
                        <td>用户名称:</td>
                        <td class="secondval">
                            <input class="easyui-textbox " type="text" name="name"
                                   data-options="width:140,required:true,validType:['anc','stringLength[1,80]'],missingMessage:'用户名称必填项'"/>
                        </td>
                    </tr>
                    <tr>
                        
                        <td>所属机构:</td>
                        <td>
                            <select class="easyui-combotree " name="orgCode" id="orgCode"
                                    data-options="required:true,url:'org/orgtree',panelWidth:450,onBeforeExpand:function(node){orgTreeExpand(this,node)}">
                            </select>
                        </td>
                        <td>用户类型:</td>
                        <td>
                            <select class="easyui-combobox " name="type" id="userType"
                                    data-options="required:true,editable:false,panelHeight:'auto',missingMessage:'用户类型必填项',valueField: 'key',textField:'value',data:userType,onChange:changeorgrole">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>电话:</td>
                        <td><input class="easyui-textbox " type="text" name="telephone"
                                   data-options="width:140,validType:['tel','stringLength[1,25]']"/></td>
                        <td>邮箱:</td>
                        <td><input class="easyui-textbox " type="text" name="email"
                                   data-options="width:140,validType:['email','stringLength[1,80]']"/></td>
                    </tr>
                    <tr>
                        <td>用户描述:</td>
                        <td colspan="3">
                            <input class="easyui-textbox rjhc-table-colspan-3" type="text"
                                   name="userDesc" data-options="validType:['mark','stringLength[1,100]']"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-center" data-options="region:'center'">
        <div class="easyui-panel rjhc-panel-inner" data-options="title:'可分配角色列表',fit:true">
            <ul id="userroletree"></ul>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton " data-options="iconCls:'icon-add'" href="javascript:void(0)"
           onclick="javascript:save('user','用户')">新增</a>
        <a class="easyui-linkbutton " data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="javascript:$('#user_newwin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">
    var userType = [{
        key: '0', value: '管理员用户'
    }, {
        key: '1', value: '普通用户'
    }];
    function changeorgrole() {
    	var type = $('#userType').combobox('getValue');
        if (type != '') {
            if ($("#userroletree").find("div").length > 0) {
                $("#userroletree").empty();
            }
            $('#userroletree').tree({
                checkbox: true, url: 'org/orgroletree/' + type, cascadeCheck: false
            });
        }
    }
    function save(model, modelName) {
        var nodes = $('#userroletree').tree('getChecked');
        var s = '';
        for (var i = 0; i < nodes.length; i++) {
            if (s != '') {
                s += ',';
            }
            s += nodes[i].id;
        }
        $("#roles").val(s);
        addrow(model, modelName);
    }
</script>
</body>
</html>