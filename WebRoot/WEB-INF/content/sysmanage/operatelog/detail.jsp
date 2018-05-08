<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-panel rjhc-detail-content">
    <div class="rjhc-panel-inner">
        <form id="operatelog_df" class="easyui-form" method="post">
            <table class="rjhc-table-detail" cellspacing="0">
                <tr>
                    <td>编号:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="id" disabled="disabled"
                               value="${operlogDto.id}"/>
                    </td>
                    <td>用户标识:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="userId" disabled="disabled"
                               value="${operlogDto.userId}"/>
                    </td>
                    <td>用户名称:</td>
                    <td class="secondval">
                        <input class="easyui-textbox" type="text" name="oprType" disabled="disabled" 
							   value="${operlogDto.userName}"/>
                    </td>
                </tr>
                <tr>
                	<td>所在机构:</td>
                    <td><input class="easyui-textbox" type="text" name="oprOrgCode" disabled="disabled"
                               value="${operlogDto.oprOrgCode}"/>
                    </td>
                    <td>机构名称:</td>
                    <td><input class="easyui-textbox" type="text" name="oprInfoType" disabled="disabled"
                               value="${operlogDto.orgName}"/>
                    </td>
                    <td>操作内容:</td>
                    <td><input class="easyui-textbox" type="text" name="oprInfo" disabled="disabled"
                               value="${operlogDto.oprInfo}"/>
                    </td>
                </tr>
                <tr>
                    <td>操作时间:</td>
                    <td><input class="easyui-textbox" type="text" name="oprTime" disabled="disabled"
                               value="${operlogDto.oprTime}"/>
                    </td>
                    <td>操作人员IP:</td>
                    <td><input class="easyui-textbox" type="text" name="loginIp" disabled="disabled"
                               value="${operlogDto.loginIp}"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
</script>
</body>
</html>
