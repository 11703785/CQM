<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div style="padding:5px;border:1px solid #ddd;">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="Dictionary.create(1)">创建分类</a>
</div>

<div class="easyui-panel" title="字典信息" style="width:100%;padding:10px 60px 20px 60px">
<table width="100%" cellpadding="5">
	<tr style="display:none">
		<td>字典ID：：</td>
		<td></td>
	</tr>
	<tr>
		<td>编码：</td>
		<td></td>
	</tr>
	<tr>
		<td>名称：</td>
		<td></td>
	</tr>
	<tr>
		<td>类别：</td>
		<td></td>
	</tr>
	<tr>
		<td>序号：</td>
		<td></td>
	</tr>
	<tr>
		<td>描述：</td>
		<td></td>
	</tr>
</table>
</div>