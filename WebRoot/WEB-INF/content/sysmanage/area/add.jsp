<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div class="rjhc-panel-north" data-options="region:'north'">
        <div class="easyui-panel rjhc-panel-inner">
            <form id="area_af" class="easyui-form" data-options="url:'area',method:'post'">
                <table cellspacing="0" class="rjhc-table-dialog">
                    <tr>
                        <td>辖区代码:</td>
                        <td class="firstval">
                            <input class="easyui-textbox " type="text" name="areaId"
                                   data-options="width:140,required:true,validType:['username','stringLength[1,50]'],missingMessage:'辖区代码必填项'"/>
                        </td>
                        <td>辖区名称:</td>
                        <td class="secondval">
                            <input class="easyui-textbox " type="text" name="name"
                                   data-options="width:140,required:true,validType:['name','stringLength[1,50]'],missingMessage:'辖区名称必填项'"/>
                        </td>
                    </tr>
                    <tr>
                        
                        <td>所属辖区:</td>
                        <td>
                            <select class="easyui-combotree " name="upArea" id="upArea"
                                    data-options="required:true,url:'area/areatree',panelWidth:300,onBeforeExpand:function(node){areaTreeExpand(this,node)}">
                            </select>
                        </td>
                        <td>辖区级别:</td>
                        <td>
                            <select class="easyui-combobox " name="levels" id="levels"
                                    data-options="required:true,editable:false,panelHeight:'auto',missingMessage:'辖区级别必填项',valueField: 'key',textField:'value',data:appDicMap[appDicKey.areaLevels]">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>辖区说明:</td>
                        <td colspan="3">
                            <input class="easyui-textbox rjhc-table-colspan-3" type="text"
                                   name="description" data-options="validType:['mark','stringLength[1,100]']"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rjhc-panel-south" data-options="region:'south'">
        <a class="easyui-linkbutton " data-options="iconCls:'icon-add'" href="javascript:void(0)"
           onclick="javascript:addrowaddrow('area','辖区')">新增</a>
        <a class="easyui-linkbutton " data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           onclick="javascript:$('#areanewwin').window('close');">取消</a>
    </div>
</div>
</body>
</html>