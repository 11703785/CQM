<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% String webapp = request.getContextPath(); %>
<div class="ftitle">字典信息</div>
<form method="post" action="saveDictionary" id="dictionaryForm">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr style="display:none">
			<td class="applytdnotcolor">字典ID：：</td>
			<td class="applytdnotcolor">
				<input name="dic.parent.id" value="${dic.parent.id}" >
				<input name="dic.id" id="dicId" value="${dic.id}">
				<input name="dic.dicType" id="dicType" value="${dic.dicType}">
			</td>
		</tr>
		<tr>
			<th class="applytd">编码：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor"><input class="easyui-textbox" name="dic.code" id="diccode" value="${dic.code}" data-options="prompt:'输入编码...',required:true,validType:['length[1,10]']" style="width:220px;height:32px"></input></td>
		</tr>
		<tr>
			<th class="applytd">名称：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor"><input class="easyui-textbox" name="dic.name" id="dicName" value="${dic.name}" data-options="prompt:'输入名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"></input></td>
		</tr>
		<tr>
			<th class="applytd">序号：<font color="#FF0000"> *</font></th>
			<td class="applytdnotcolor"><input class="easyui-textbox" name="dic.orderNum" id="dicOrderNum" value="${dic.orderNum}" data-options="prompt:'输入序号...',required:true" style="width:220px;height:32px"></input></td>
		</tr>
		<tr>
			<th class="applytd">描述：</th>
			<td class="applytdnotcolor"><input class="easyui-textbox" name="dic.memo" id="dicMemo" value="${dic.memo}" data-options="multiline:true,prompt:'输入描述...',validType:['length[0,100]']" style="width:220px;height:64px"></input></td>
		</tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Dictionary.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>
