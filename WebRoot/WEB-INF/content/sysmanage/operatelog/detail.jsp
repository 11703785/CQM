<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-panel sugar-detail-content">
    <div class="sugar-panel-inner">
        <form id="operatelog_df" class="easyui-form" method="post">
            <table class="sugar-table-detail" cellspacing="0">
                <tr>
                    <td>编号:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="id" disabled="disabled"
                               value="${operatelogdto.id}"/>
                    </td>
                    <td>用户标识:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="userId" disabled="disabled"
                               value="${operatelogdto.userId}"/>
                    </td>
                    <td>操作类型:</td>
                    <td class="secondval">
                        <input class="easyui-textbox" type="text" name="oprType" disabled="disabled" id="oprType"/>
                    </td>
                </tr>
                <tr>
                    <td>操作内容:</td>
                    <td><input class="easyui-textbox" type="text" name="oprInfo" disabled="disabled"
                               value="${operatelogdto.oprInfo}"/>
                    </td>
                    <td>操作时间:</td>
                    <td><input class="easyui-textbox" type="text" name="oprTime" disabled="disabled"
                               value="${operatelogdto.oprTime}"/>
                    </td>
                    <td>操作状态:</td>
                    <td><input class="easyui-textbox" type="text" name="oprStatus" disabled="disabled" id="oprStatus"/>
                    </td>
                </tr>
                <tr>
                    <td>所在机构:</td>
                    <td><input class="easyui-textbox" type="text" name="oprOrgCode" disabled="disabled"
                               value="${operatelogdto.oprOrgCode}"/>
                    </td>
                    <td>信息类型:</td>
                    <td><input class="easyui-textbox" type="text" name="oprInfoType" disabled="disabled"
                               value="${operatelogdto.oprInfoType}"/>
                    </td>
                    <td>子系统代码:</td>
                    <td><input class="easyui-textbox" type="text" name="subsystem" disabled="disabled"
                               value="${operatelogdto.subsystem}"/>
                    </td>
                </tr>
                <tr>
                    <td>操作人员IP:</td>
                    <td><input class="easyui-textbox" type="text" name="loginIp" disabled="disabled"
                               value="${operatelogdto.loginIp}"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var oprType =  ${operatelogdto.oprType};
        if (oprType == '0') {
            $("#oprType").val("系统日志");
        } else {
            $("#oprType").val("业务日志");
        }
        var oprStatus = ${operatelogdto.oprStatus};
        if (oprStatus == '0') {
            $("#oprStatus").val("操作成功");
        } else {
            $("#oprStatus").val("操作失败");
        }
    });
</script>
</body>
</html>
