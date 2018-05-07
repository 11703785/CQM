<%@ page import="com.platform.application.sysmanage.login.LoginInfo" %>
<%@ page import="com.platform.application.sysmanage.user.UserDto" %>
<%@ page import="com.platform.application.utils.DataFormatUtils" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
    UserDto userdto = (UserDto) request.getAttribute("userdto");
    String sysType = (String) request.getAttribute("sysType");
    //超级用户不能进行操作，自己不能对自己进行操作。
    //除了超级用户外不能对本机构用户管理员进行操作。
    boolean operFlag = (!"9".equals(userdto.getType()) && !loginInfo.getUserId().equals(userdto.getUserId()))
            && !("0".equals(userdto.getType()) & loginInfo.getOrgCode().equals(userdto.getOrgCode()) && !loginInfo.isTopAdmin());
    boolean updateFlag = loginInfo.haveMenuRight("900202")
            && ((!loginInfo.getUserId().equals(userdto.getUserId()) && !("0".equals(userdto.getType()) && loginInfo.getOrgCode().equals(userdto.getOrgCode())) && !"9".equals(userdto.getType()))
            || (loginInfo.isTopAdmin()));

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-panel rjhc-detail-content">
    <div class="rjhc-panel-inner">
        <form id="user_df" class="easyui-form" method="post">
            <input type="hidden" name="stops" id="stops"/>
            <table class="rjhc-table-detail" cellspacing="0">
                <tr>
                    <td>用户ID:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="userId" value="${userdto.userId}"
                               data-options="disabled:true"/></td>
                    <td>用户类型:</td>
                    <td class="firstval">
                        <select class="easyui-combobox " name="type"
                                data-options="value:'${userdto.type}',disabled:true,required:true,editable:false,panelHeight:50,missingMessage:'用户类型必填项',valueField: 'key',textField:'value',data:appDicMap[appDicKey.userType]">
                        </select>
                    </td>
                    <td>所属机构:</td>
                    <td class="secondval">
                        <select class="easyui-combotree" name="orgCode"
                                data-options="disabled:true,required:true,url:'org/uporgtree/${userdto.orgCode}',value:'${userdto.orgCode}',panelWidth:450,onBeforeExpand:function(node){orgTreeExpand(this,node)}">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>用户名称:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" value="${userdto.name}"
                               data-options="disabled:false,required:true,validType:['anc','stringLength[1,80]'],missingMessage:'用户名称必填项'"/>
                    </td>
                    <td>用户状态:</td>
                    <td>
                        <select class="easyui-combobox " name="status"
                                data-options="value:'${userdto.status}',disabled:true,required:true,editable:false,panelHeight:50,missingMessage:'用户类型必填项',valueField: 'key',textField:'value',data:appDicMap[appDicKey.userStatus]">
                        </select>
                    </td>
                    <td>电话:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="telephone"
                               value="${userdto.telephone}"
                               data-options="disabled:false,validType:['tel','length[1,25]']"/>
                    </td>
                </tr>
                <tr>
                	<td>邮箱:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="email"
                               value="${userdto.email}"
                               data-options="validType:['email','length[1,80]']"/>
                    </td>
                    <td>用户描述:</td>
                    <td colspan="3">
                        <input class="easyui-textbox rjhc-table-colspan-3" type="text" name="userDesc"
                               value="${userdto.userDesc}" data-options="disabled:false"/>
                    </td>
                </tr>
                <tr>
                	<td>创建人:</td>
                    <td><input class="easyui-textbox" type="text" name="creator"
                               value="${userdto.creator}" data-options="disabled:true"/></td>
                    <td>创建时间:</td>
                    <td><input class="easyui-textbox" type="text" name="createTime"
                               value="<%=DataFormatUtils.getDateTime(userdto.getCreateTime())%>"
                               data-options="required:true,disabled:true"/></td>
                    <td>最后登录时间:</td>
                    <td><input class="easyui-textbox" type="text"
                               name="lastLogonTime" value="<%=DataFormatUtils.getDateTime(userdto.getLastLogonTime())%>"
                               data-options="disabled:true"/></td>
                </tr>
            </table>
        </form>
    </div>
    <%-- <% if (updateFlag || operFlag) {
    %> --%>
    <div class="easyui-panel rjhc-detail-buttons">
        <%-- <% if ((loginInfo.haveMenuRight("900208")) && operFlag) {
        %> --%>
        <a id="giverole" class="easyui-linkbutton"
           data-options="iconCls:'icon-save'" href="javascript:void(0)"
           onclick="javascript:userrole()">角色分配</a>
        <%-- <% }
            if ((loginInfo.haveMenuRight("900206")) && "2".equals(userdto.getStatus()) && operFlag) {
        %> --%>
        <a id="unlock" class="easyui-linkbutton"
           data-options="iconCls:'icon-redo'" href="javascript:void(0)"
           onclick="javascript:unlock('user','用户')">解锁</a>
        <%-- <% }
            if ((loginInfo.haveMenuRight("900204")) && "0".equals(userdto.getStatus()) && operFlag) {
        %> --%>
        <a id="stop" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="stop('user','用户')">停用</a>
        <%-- <% }
            if ((loginInfo.haveMenuRight("900204")) && "1".equals(userdto.getStatus()) && operFlag) {
        %> --%>
        <a id="start" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" href="javascript:void(0)"
           onclick="start('user','用户')">启用</a>
        <%-- <% }
            if ((loginInfo.haveMenuRight("900205")) && operFlag) {
        %> --%>
        <a id="resetpwd" class="easyui-linkbutton"
           data-options="iconCls:'icon-redo'" href="javascript:void(0)"
           onclick="resetpwd('user','用户')">重置密码</a>
        <%-- <% }
            if (updateFlag) {%> --%>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
           href="javascript:void(0)"
           onclick="javascript:updaterow('user','用户');">修改</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#user_df').form('reset');">重置</a>
        <%-- <%
            }
        %> --%>
    </div>
   <%--  <%
        }
    %> --%>
</div>
<script type="text/javascript">
    <%-- $(function () {
        <%if(!updateFlag){%>
        $("#user_df input").each(function () {
            if (!$(this).attr('disabled')) {
                $(this).attr('disabled', true);
            }
        });
        <%}%>
    }); --%>
    function userrole() {
        new rjhc.showDialog({
            id: 'userrole_newwin_detail',
            modal: true,
            closed: true,
            iconCls: 'icon-edit',
            draggable: false,
            width: 500,
            title: "角色分配",
            height: 500,
            cache: false,
            href: 'user/showrole/${userdto.userId}'
        }).window('open');
    }
    /* function unlock(model, modelName) {
        var form = getFormObject($("#" + model + "_df"));
        var idField = $("#" + model + "_dg").datagrid('options').idField;
        $.messager.confirm("确认", "是否解锁" + modelName + " " + eval("form." + idField) + "?", function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: "user/unlock",
                    data: JSON.stringify(form),
                    dataType: "json",
                    success: function (data) {
                        if (data.status) {
                            $.messager.alert("解锁成功", modelName + " " + eval("form." + idField) + " 解锁成功", "info", function () {
                                $("#" + model + "_dg").datagrid("reload");
                            });
                        } else {
                            $.messager.alert("解锁失败", data.error, 'error');
                        }
                    },
                    error: ajaxerror
                });
            }
        });
    } */
    function resetpwd(model, modelName) {
        var url = 'user/reset';
        var form = getFormObject($("#" + model + "_df"));
        var idField = $("#" + model + "_dg").datagrid('options').idField;
        $.messager.confirm("确认", "是否重置密码" + modelName + " " + eval("form." + idField) + "?", function (r) {
            if (r) {
                $.ajax({
                    type: "post", url: url, data: JSON.stringify(form), dataType: "json", success: function (data) {
                        if (data.status) {
                            $.messager.alert("重置密码成功", modelName + " " + eval("form." + idField) + " 重置密码成功", "info", function () {
                                $("#" + model + "_dg").datagrid("reload");
                            });
                        } else {
                            $.messager.alert("重置密码失败", data.error, 'error');
                        }
                    }, error: ajaxerror
                });
            }
        });
    }
    function stop(model, modelName) {
        $.messager.prompt('提示信息', '请输入停用原因：', function (r) {
            if (r) {
                if (r == '') {
                    $.messager.alert("停用失败", "停用原因不能为空！", 'error');
                    return;
                }
                $("#stopReason").textbox("setValue",r);
                var form = getFormObject($("#" + model + "_df"));
                var idField = $("#" + model + "_dg").datagrid('options').idField;
                $.messager.confirm("确认", "是否停用 " + modelName + " " + eval("form." + idField) + "?", function (r) {
                    if (r) {
                        $.ajax({
                            type: "post",
                            url: "user/stop",
                            data: JSON.stringify(form),
                            dataType: "json",
                            success: function (data) {
                                if (data.status) {
                                    $.messager.alert("停用成功", modelName + " " + eval("form." + idField) + " 停用成功", "info", function () {
                                        $("#" + model + "_dg").datagrid("reload");
                                    });
                                } else {
                                    $.messager.alert("停用失败", data.error, 'error');
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
    function start(model, modelName) {
        var form = getFormObject($("#" + model + "_df"));
        var idField = $("#" + model + "_dg").datagrid('options').idField;
        $.messager.confirm("确认", "是否启用" + modelName + " " + eval("form." + idField) + "?", function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: "user/start",
                    data: JSON.stringify(form),
                    dataType: "json",
                    success: function (data) {
                        if (data.status) {
                            $.messager.alert("启用成功", modelName + " " + eval("form." + idField) + " 启用成功", "info", function () {
                                $("#" + model + "_dg").datagrid("reload");
                            });
                        } else {
                            $.messager.alert("启用失败", data.error, 'error');
                        }
                    },
                    error: ajaxerror
                });
            }
        });
    }
</script>
</body>
</html>
