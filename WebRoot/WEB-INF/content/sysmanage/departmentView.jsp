<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input id="deptId" name="deptId" value="${department.deptId}" type="hidden" >
<input id="deptType" name="deptType" value="${department.deptType}" type="hidden" >
<div style="padding:5px;border:1px solid #ddd;">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="Department.create(1)">新增</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="Department.create(2)">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="Department.removes()">删除</a>
</div>
<div>
<table  align="center" class="applytable"   cellspacing="0" cellpadding="8" >
	<tr height="50px">
		<td class="applytd">所属机构:</th>
		<td class="applytdnotcolor">${department.parent.deptName}</td>
		<th class="applytd">机构名称：</th>
		<td class="applytdnotcolor">${department.deptName}</td>
		
	</tr>
	
	<tr height="50px">
		<td class="applytd">机构编码(企业)：</td>
		<td class="applytdnotcolor">${department.deptCode}</td>
	    
	    <td class="applytd">机构编码(个人)：</td>
		<td class="applytdnotcolor">${department.personalOrgCode}</td>
		
	</tr>
	<tr height="50px">
		<td class="applytd">所属辖区：</td>
		<td class="applytdnotcolor">${area.name}</td>
	
		<th class="applytd">机构类型：</th>
		<td class="applytdnotcolor">${department.deptTypeStr}</td>
	</tr>
	 
	 <tr height="50px">
		<th class="applytd">机构描述：</th>
		<td class="applytdnotcolor">${department.description}</td>
		
		<th class="applytd">机构序号：</th>
		<td class="applytdnotcolor">${department.deptOrder}</td>
	</tr>
 </table>

</div>
