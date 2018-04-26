<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% String webapp = request.getContextPath(); %>
<s:set name="resMap" value="#session.resmap" />
<div class="ftitle">部门信息</div>
<!--表单信息区域-->
<center>
		<div class="data_main">
		<div class="date_title"></div>
</center>

<form id="orgForm" method="post">
	<input type="hidden" id="parentorg" name="parent.id" value="${parent.id}">
	<input type="hidden"  name="organization.id" value="${organization.id}">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr>
	        <td class="applytd">所属辖区：</td>
	        <td class="applytdnotcolor" name="parent.organizationname">${parent.organizationname}</td>
		    <td class="applytd">机构名称：<font color="#FF0000"> *</font></td>
	        <td class="applytdnotcolor"><input id="organizationname" name="organization.organizationname"  class="easyui-textbox" data-options="prompt:'输入机构名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"> </td> 
	       
	    </tr>
		<tr>
			<td class="applytd">序号：<font color="#FF0000"> *</font></td>
	        <td class="applytdnotcolor" ><input id="deptOrder" name="organization.orgOrder" value="${organization.orgOrder}" class="easyui-numberbox" maxlength="3" data-options="prompt:'输入序号...',required:true" missingMessage="必须填写1	`3位之间的数字" style="width:220px;height:32px"></td>
			<td class="applytd">编码：<font color="#FF0000"> *</font></td>
	        <td class="applytdnotcolor"><input id="deptCode" name="organization.organizationcode" value="${organization.organizationcode}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px"></td>
		</tr>
		<tr>
			<td class="applytd">级别：<font color="#FF0000"> *</font></td>
	        <td class="applytdnotcolor">
	       		 <s:select panelHeight="auto" class="easyui-combobox" list="#{'0':'省厅级','1':'地市级'}" name="organization.deptType" id="deptType" style="width:220px;height:32px"data-options="prompt:'输入级别...',required:true,validType:['length[0,20]']"/>
	        </td>
	        <td class="applytd">描述：</th>
	        <td class="applytdnotcolor" >
	        	<input name="organization.description" id="description" class="easyui-textbox" data-options="multiline:true,prompt:'输入描述...',validType:['length[0,100]']" style="width:220px;height:64px" value="${organization.description}"</textarea>
	        </td>
		</tr>
	</table>

<div style="text-align:center;padding:30px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Organization.submit()">提交</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a></tr>
</div>
</form>