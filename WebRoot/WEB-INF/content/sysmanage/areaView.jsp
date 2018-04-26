
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input id="areaid" name="areaid" value="${area.id}" type="hidden" >
<input id="levels" name="levels" value="${area.levels}" type="hidden" >
<div style="padding:5px;border:1px solid #ddd;">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="Area.create(2)">新增</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="Area.create(3)">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="Area.deleteAll()">删除</a>
</div>
<div>
<table  align="center" class="applytable"   cellspacing="0" cellpadding="8" >
	<tr height="50px">
		<td class="applytd">所属辖区：</th>
		<td class="applytdnotcolor">${area.parent.name}</td>
		<th class="applytd">辖区名称：</th>
		<td class="applytdnotcolor">${area.name}</td>
		
	</tr>
	
	<tr height="50px">
		<td class="applytd">辖区编码：</td>
		<td class="applytdnotcolor">${area.code}</td>
	
		<th class="applytd">辖区级别：</th>
		<td class="applytdnotcolor">${area.levelsStr}</td>
	</tr>
	<tr height="50px">
		<td class="applytd">辖区描述：</td>
		<td colspan="3" class="applytdnotcolor">${area.description}</td>
	
	</tr>
	 
 </table>

</div>


