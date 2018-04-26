<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="depttbs" style="padding:5px;border:1px solid #ddd;">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="Organization.create(1)">建子机构</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="Organization.create(2)">修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="Organization.remove()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="Organization.downloadXML()">导出为xml</a>
</div>
<center>
	<div class="data_main">
</center>

<div>
	<table  align="center" class="applytable"   cellspacing="0" cellpadding="8" >
		<input type="hidden" id="parentorg" name="department.deptId" value="${organization.id}">
		<tr>
			<td class="applytd">所属辖区：</td>
			<td style="width:220px;height:32px" class="applytdnotcolor">${organization.parent.organizationname}</td>
			<td class="applytd">上级编码：</th>
			<td style="width:220px;height:32px" class="applytdnotcolor">${organization.parent.organizationcode}</td>
		</tr>
		<tr>
			<td class="applytd">名称：</td>
			<td style="width:220px;height:32px" class="applytdnotcolor">${organization.organizationname}</td>
			<td class="applytd">编码：</th>
			<td style="width:220px;height:32px" class="applytdnotcolor">${organization.organizationcode}</td>
		</tr>
		<tr>
			<td class="applytd">级别：</td>
			<td style="width:220px;height:32px" class="applytdnotcolor">
				<c:if test="${organization.deptType=='0'}">省厅级</c:if>
				<c:if test="${organization.deptType=='1'}">地市级</c:if>
			</td>
	 		<td class="applytd">序号：</td>
			<td style="width:220px;height:32px" class="applytdnotcolor">${organization.orgOrder}</td>
		</tr>
		<tr>	
			<td class="applytd">描述：</td>
			<td style="width:220px;height:32px" class="applytdnotcolor">${organization.description}</td>
			<td class="applytd">是否末级：</td>
			<td style="width:220px;height:32px" class="applytdnotcolor">
				<c:if test="${organization.isleaf=='0'}">否</c:if>
				<c:if test="${organization.isleaf=='1'}">是</c:if>
			</td>
		</tr>
	</table>
</div>


