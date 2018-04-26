<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="padding:5px;border:1px solid #ddd">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="Dictionary.create(1)">创建字典分类</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="Dictionary.create(2)">创建字典项</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="Dictionary.create(3)">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="Dictionary.switchDic()">开关字典</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="Dictionary.deleteAll()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="Dictionary.toXML()">导出为xml</a>
</div>

<div class="easyui-panel" title="字典信息" style="width:100%;height:0%"></div>
<table class="applytable" cellspacing="0" cellpadding="8" align="center"title="字典信息">
	<tr style="display:none">
		<th class="applytd">字典ID：：</th>
		<td class="applytdnotcolor" style="width:220px;height:32px"><input name="dic.id" id="dicId" value="${dic.id}"></td>
		<th class="applytd">字典分类：</th>
		<td class="applytdnotcolor" style="width:220px;height:32px">${dic.dicType}</td>
	</tr>
	<tr>
		<th class="applytd">名称：</th>
		<td class="applytdnotcolor" style="width:220px;height:32px">${dic.name}</td>
		<th class="applytd">父字典项：</th>
		<td class="applytdnotcolor" style="width:220px;height:32px">${dic.parent.name}</td>
		</td>
	</tr>
	<tr>
		<th class="applytd">编码：</th>
		<td class="applytdnotcolor" style="width:220px;height:32px">${dic.code}</td>
		<th class="applytd">序号：</th>
		<td class="applytdnotcolor" style="width:220px;height:32px">${dic.orderNum}</td>
	</tr>
	<tr>
		<th class="applytd">状态：</th>
		<td colspan="3" class="applytdnotcolor" style="width:220px;height:32px">
			<c:if test="${dic.status=='00'}">停用</c:if>
			<c:if test="${dic.status=='01'}">启用</c:if>
		</td>
	</tr>
	<tr>
		<th class="applytd">描述：</th>
		<td colspan="3" class="applytdnotcolor" style="width:220px;height:32px">${dic.memo}</td>
	</tr>
</table>
