<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String webapp = request.getContextPath(); %>

<div class="ftitle">详细信息</div>
<form method="post" action="saveOptResource" id="resourceForm">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr style="display:none">
			<th>资源ID：</th>
			<td class="applytdnotcolor">
				<input name="optResource.parent.resId" value="${optResource.parent.resId}" >
				<input name="optResource.resId" id="resId" value="${optResource.resId}">
			</td>
		</tr>
		<tr>
			<th class="applytd">父资源：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${optResource.parent.resName}</td>
		</tr>
		<tr>
			<th class="applytd">名称：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor">
			<input class="easyui-textbox" type="text" name="optResource.resName" id="resName" value="${optResource.resName}" data-options="prompt:'输入名称...',required:true,validType:['length[1,20]']" style="width:220px;height:32px"></input>
			</td>
		<tr>
		<tr>
			<th class="applytd">编码：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor">
			<input class="easyui-textbox" type="text" name="optResource.resCode" id="resCode" value="${optResource.resCode}" data-options="prompt:'输入编码...',required:true,validType:['length[1,20]']" style="width:220px;height:32px"></input>
			</td>
		<tr>
		<tr>
			<th class="applytd">序号：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor">
			<input class="easyui-textbox" type="text" name="optResource.resOrder" id="resOrder" value="${optResource.resOrder}" data-options="prompt:'输入序号...',required:true,validType:['length[1,10]']" style="width:220px;height:32px"></input>
			</td>
		<tr>
		<tr>
			<th class="applytd">类型：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor">
	     	<s:select list="#{'01':'目录','05':'菜单','10':'操作'}"  name="optResource.resType" id="resType"></s:select>	
            <select name="optResource.resType" id="resType">
              <c:if test="${optResource.resType=='01'}">
               <option value="01" selected = "selected">目录</option>
               <option value="05">菜单</option>
               <option value="10">操作</option>
               </c:if>
               <c:if test="${optResource.resType=='05'}">
               <option value="01" >目录</option>
               <option value="05" selected = "selected">菜单</option>
               <option value="10">操作</option>
               </c:if>
               <c:if test="${optResource.resType=='10'}">
               <option value="01" >目录</option>
               <option value="05">菜单</option>
               <option value="10" selected = "selected">操作</option>
               </c:if>
            </select>
			</td>
		<tr>
		<tr>
			<th class="applytd">Action：</th>
			<td class="applytdnotcolor">
			<input class="easyui-textbox" type="text" name="optResource.actions" id="actions" value="${optResource.actions}"data-options="prompt:'输入Action...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"></input>
			</td>
			<!-- <br>　　<font color="#FF0000">规则：多个Action可用,分隔填写</font> -->
		</tr>
			<tr>
			<th class="applytd">链接：</th>
			<td class="applytdnotcolor">
			<input class="easyui-textbox" type="text" name="optResource.resUrl" id="resUrl" value="${optResource.resUrl}" data-options="prompt:'输入链接...',validType:['length[1,70]']" style="width:220px;height:32px"></input>
			</td>
		<tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="OptResource.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>
