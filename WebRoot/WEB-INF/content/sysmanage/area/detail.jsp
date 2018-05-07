<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<div class="easyui-panel rjhc-detail-content">
    <div class="rjhc-panel-inner">
        <form id="area_df" class="easyui-form" method="post">
            <table class="rjhc-table-detail" cellspacing="0">
                <tr>
                    <td>辖区代码:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="areaId" value="${areaDto.areaId}"
                               data-options="disabled:true"/></td>
                    <td>辖区名称:</td>
                    <td class="firstval">
                        <input class="easyui-textbox" type="text" name="name"
                               value="${areaDto.name}"
                               data-options="required:true,validType:['name','length[1,50]'],missingMessage:'辖区名称必填'"/>
                    </td>
                    <td>所属辖区:</td>
                    <td class="secondval">
                        <select class="easyui-combotree" name="upArea"
                                data-options="disabled:true,required:true,url:'area/upareatree/${areaDto.upArea}',value:'${areaDto.upArea}',panelWidth:450,onBeforeExpand:function(node){areaTreeExpand(this,node)}">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>辖区级别:</td>
                    <td>
                    	<select class="easyui-combobox" name="levels" id="levels"
                                    data-options="value:'${areaDto.levels }',required:true,editable:false,panelHeight:'auto',missingMessage:'辖区级别必填项',valueField: 'key',textField:'value',data:appDicMap[appDicKey.areaLevels]">
                        </select>
                    </td>
                    <td>辖区说明:</td>
                    <td colspan="3">
                        <input class="easyui-textbox rjhc-table-colspan-3" type="text" name="description"
                               value="${areaDto.description}" data-options="disabled:false"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="easyui-panel rjhc-detail-buttons">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
           href="javascript:void(0)"
           onclick="javascript:updaterow('area','辖区');">修改</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
           href="javascript:void(0)"
           onclick="javascript:deleterow('area','辖区');">删除</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)"
           onclick="javascript:$('#area_df').form('reset');">重置</a>
    </div>
</div>
</body>
</html>
