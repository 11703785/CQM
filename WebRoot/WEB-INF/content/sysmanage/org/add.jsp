<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-north" data-options="region:'north'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="org_af" class="easyui-form" data-options="url:'org',method:'post'">
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>机构代码:</td>
                        <td  class="firstval">
                            <input class="easyui-textbox" type="text" name="orgCode"
                                   data-options="required:true,validType:['username','stringLength[1,14]'],invalidMessage:'机构代码只能包含数字、字母、_、-且长度不超过14'"/>
                        <td>机构名称:</td>
                        <td  class="secondval">
                            <input class="easyui-textbox" type="text" name="orgName"
                                   data-options="required:true,validType:['name','stringLength[1,80]'],invalidMessage:'机构名称只能包含英文、数字、中文或.特殊符号且长度不超过80'"/>
                        </td>
                    </tr>
                    <tr>
                    	<td>上级机构:</td>
                        <td>
                            <select class="easyui-combotree" name="upOrg" id="upOrg" 
                                    data-options="url:'org/orgtree',required:true,panelWidth:450,onBeforeExpand:function(node){orgTreeExpand(this,node)}">
                            </select>
                        </td>
                        <td>机构类型:</td>
                        <td><input class="easyui-combobox" name="orgType"
                                   data-options="required:true,editable:false,panelHeight:'auto',valueField: 'key',textField: 'value',data:appDicMap[appDicKey.orgType]"
                                   value="01"/>
                        </td>
                    </tr>
                    <tr>
                        <td>机构状态:</td>
                        <td><input class="easyui-combobox" name="status"
                                   data-options="required:true,editable:false,panelHeight:'auto',valueField: 'key',textField: 'value',data:appDicMap[appDicKey.orgStatus]"
                                   value="0"/>
                        </td>
                        <td>所属辖区:</td>
                        <td><select class="easyui-combotree" name="areaCode" id="areaCode" 
                                    data-options="url:'area/areatree',required:true,panelWidth:300,onBeforeExpand:function(node){areaTreeExpand(this,node)}">
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<td>个人机构代码:</td>
                        <td>
                            <input class="easyui-textbox" type="text" name="pcOrgCode"
                                   data-options="required:true,validType:['username','stringLength[1,14]'],invalidMessage:'机构代码只能包含数字、字母、_、-且长度不超过14'"/>
                            <input name="orgRoles" type="hidden" id="orgroles_orgad"/></td>
                        <td>企业机构代码:</td>
                        <td>
                            <input class="easyui-textbox" type="text" name="ecOrgCode"
                                   data-options="required:true,validType:['username','stringLength[1,14]'],invalidMessage:'机构代码只能包含数字、字母、_、-且长度不超过14'"/>
                            <input name="orgRoles" type="hidden" id="orgroles_orgad"/></td>
                    </tr>
                    <tr>
                        <td>说明:</td>
                        <td colspan="3"><input class="easyui-textbox rjhc-table-colspan-3" type="text" name="remark"
                                               data-options="validType:['mark','stringLength[1,255]'],invalidMessage:'说明只能包含中文、字母、数字、全角符号、@和/且长度不超过255'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south',border:false">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-add'" href="javascript:void(0)"
           onclick="javascript:saveneworg()">新增</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="javascript:$('#org_newwin').window('close');">取消</a>
    </div>
</div>
<script type="text/javascript">

function saveneworg() {
    addrow("org", "机构");
}

</script>
</body>
</html>