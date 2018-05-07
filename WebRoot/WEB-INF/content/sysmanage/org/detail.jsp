<%-- <%@ page import="com.sugardt.platform.base.login.LoginInfo" %>
<%@ page import="com.sugardt.platform.base.manage.ManageModelConstants" %>--%>
<%@ page import="com.platform.application.sysmanage.org.TmOrgDto" %>
<%@ page import="com.platform.application.utils.DataFormatUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    /* LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(LoginInfo.HTTP_SESSION_LOGININFO);
    TmOrgDto orgdto = (OrgDto) request.getAttribute("orgdto");
    String roleType = (String) request.getAttribute(ManageModelConstants.PROFILE_ORG_USE_TYPE); */
    TmOrgDto orgdto = (TmOrgDto) request.getAttribute("orgdto");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-panel rjhc-detail-content">
    <div class="rjhc-panel-inner">
        <form id="org_df" class="easyui-form" method="post">
            <table class="rjhc-table-detail" cellspacing="0">
                <tr>
                    <td>机构代码:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="orgCode"
                               value="${orgdto.orgCode}" id="orgcode_orgdt"
                               data-options="required:true,validType:['stringLength[1,14]'],invalidMessage:'机构代码',missingMessage:'机构代码'"
                               disabled/>
                    </td>
                    <td>机构名称:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="orgName"
                               value="${orgdto.orgName}"
                               data-options="required:true,validType:['name','stringLength[1,80]'],invalidMessage:'机构名称只能包含英文、数字、中文或.特殊符号且长度不超过80'"/>
                    </td>
                    <td>上级机构:</td>
                    <td class="secondval">
                        <select class="easyui-combotree" name="upOrg" id="uporg_orgdt"
                                data-options="url:'org/uporgtree/${orgdto.upOrg}',value:'${orgdto.upOrg}',editable:false,required:true,panelWidth:450,onBeforeExpand:function(node){orgTreeExpand(this,node)}">
                        </select>
                    </td>
                </tr>
                <tr>
                	<td>个人机构代码:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="pcOrgCode"
                               value="${orgdto.pcOrgCode}"
                               data-options="required:true,validType:['name','stringLength[1,80]'],invalidMessage:'机构名称只能包含英文、数字、中文或.特殊符号且长度不超过80'"/>
                    </td>
                    <td>企业机构代码:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="ecOrgCode"
                               value="${orgdto.ecOrgCode}"
                               data-options="required:true,validType:['name','stringLength[1,80]'],invalidMessage:'机构名称只能包含英文、数字、中文或.特殊符号且长度不超过80'"/>
                    </td>
                    <td>机构状态:</td>
                    <td><input class="easyui-combobox" name="status" disabled
                               id="status" value="${orgdto.status}"
                               data-options="required:true,editable:false,panelHeight:44,valueField: 'key',textField: 'value',data:appDicMap[appDicKey.orgStatus]"/>
                    </td>
                </tr>
                <tr>
                	<td>机构类型:</td>
                    <td><input class="easyui-combobox" type="text" name = "orgType"
                               value="${orgdto.orgType}" data-options="required:true,editable:false,width:140,panelHeight:44,valueField: 'key',textField: 'value',data:appDicMap[appDicKey.orgType]"/></td>
                	<td>机构说明:</td>
                    <td colspan=3><input class="easyui-textbox rjhc-table-colspan-3" type="text"
                                         data-options="validType:['mark','stringLength[1,255]'],invalidMessage:'说明只能包含中文、字母、数字、全角符号、@和/且长度不超过255'"
                                         name="remark" value="${orgdto.remark}"/>
                    </td>
                </tr>
                <tr>
                	<td>所在地区</td>
                	<td><input class="easyui-combotree" name="areaCode" id="areaCode"
                               data-options="url:'area/upareatree/${orgdto.areaCode}',value:'${orgdto.areaCode}',required:true,editable:false,panelWidth:300,onBeforeExpand:function(node){areaTreeExpand(this,node)}"/>
                    </td>
                	<td>创建人:</td>
                    <td><input class="easyui-textbox" type="text"
                               value="${orgdto.creator}" data-options="required:true,width:140" disabled/></td>
                    <td>创建时间:</td>
                    <td><input class="easyui-textbox" type="text" 
                               value="<%=DataFormatUtils.getDateTime(orgdto.getCreateTime())%>"
                               data-options="required:true,width:140" disabled/></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="easyui-panel rjhc-detail-buttons">
        <%-- <% }
        }
            if ((loginInfo.haveMenuRight("900104"))
                    && StringUtils.isNotBlank(orgdto.getUpOrg()) && !loginInfo.getOrgCode().equals(orgdto.getOrgCode())) {
        %> --%>

        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           id="org_startbutton" onclick="javascript:orgstoporstart('0','${orgdto.orgCode}')">启用</a>
        <a class="easyui-linkbutton"
           data-options="iconCls:'icon-cancel'" href="javascript:void(0)" id="org_stopbutton"
           onclick="javascript:orgstoporstart('1','${orgdto.orgCode}')">停用</a>
       <%--  <%
            }
            if (loginInfo.haveMenuRight("900102")) {
        %> --%>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           onclick="javascript:updaterow('org','机构')">修改</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#org_df').form('reset');">重置</a>
        <%-- <%}%> --%>
    </div>
</div>
<script type="text/javascript">
    <%-- $(function () {
        <% if(loginInfo.getOrgCode().equals(orgdto.getOrgCode())){%>
        $("#uporg_orgdt").attr('disabled', true);
        <%}%>
        <%if(!(loginInfo.haveMenuRight("900102"))){%>
        $("#org_df input").each(function () {
            if (!$(this).attr('disabled')) {
                $(this).attr('disabled', true);
            }
        });
        $("#uporg_orgdt").attr('disabled', true);
        <%}%>
    }); --%>
    function ajaxLoading() {
        $("<div class=\"datagrid-mask\"></div>").css({
            display: "block", width: "100%", height: $(window).height()
        }).appendTo("body");
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({
            display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2
        });
    }
    function ajaxLoadEnd() {
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }
    function statusload() {
        $(this).combobox("select", "${orgdto.status }");
        if ($("#status_orgdt").val() === "0") {
            $("#org_startbutton").hide();
            $("#org_stopbutton").show();
        } else {
            $("#org_startbutton").show();
            $("#org_stopbutton").hide();
        }
    }
    function orgstoporstart(status, orgCode) {
        var temp = "您确定停用" + orgCode + "机构";
        if ("0" == status) {
            temp = "您确定启用" + orgCode + "机构";
        }
        $.messager.confirm('确认', temp, function (r) {
            if (r) {
                $.messager.confirm('确认', '您确定对下级机构沿用操作？', function (r2) {
                    var flag = false;
                    if (r2) {
                        flag = true;
                    }
                    var url = "org/stop/" + status + "/" + flag + "/" + orgCode;
                    $.ajax({
                        type: "post", url: url, cache: false, beforeSend: ajaxLoading, success: function (data) {
                            ajaxLoadEnd();
                            if (data.status) {
                                $("#org_dg").datagrid("reload");
                            } else {
                                $.messager.alert("失败", data.error, 'error');
                            }
                        }
                    });
                });
            }
        });
    }
    function openroletreewin() {
        var orgCode = $("#orgcode_orgdt").val();
        new sugar.showDialog({
            id: 'org_roletreewin',
            modal: true,
            closed: true,
            iconCls: 'icon-edit',
            draggable: false,
            width: 480,
            title: "角色",
            height: 390,
            cache: false,
            href: 'org/showrolewindow/' + orgCode
        }).window('open');
    }
</script>
</body>
</html>
