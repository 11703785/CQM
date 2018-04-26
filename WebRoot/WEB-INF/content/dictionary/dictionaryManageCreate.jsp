<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="ftitle">字典信息</div>
<form id="dictionaryForm" method="post">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr style="display:none">
			<th class="applytd">ID：</th>
			<td class="applytdnotcolor" colspan="3">
				<input name="dictionary.id" value="${dictionary.id}" id="dictionary.id">
			</td>
		</tr>
		<tr>
		    <th class="applytd">字典类别：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	           <input class="easyui-combotree" id="dicType.id" name="dicType.id" value="${dicType.id}" data-options="valueField:'dicType.id',textField:'dicType.name',node:'remote',url:'loadDicType.action',method:'post',required:true" style="width:400px;height:32px"></input>
	        </td>
	    </tr>
	
		<tr>
			<th class="applytd">字典名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="dictionary.name" id="dictionary.name" value="${dictionary.name}" class="easyui-textbox" data-options="prompt:'输入名称...',required:true,validType:['length[1,100]']" style="width:400px;height:32px">
	        </td>
	    </tr>
	    
		<tr>
	        <th class="applytd">字典编码：</th>
	         <td class="applytdnotcolor">
	        	<input  name="dictionary.code" id="dictionary.code" value="${dictionary.code}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:400px;height:32px">
	       	 </td>
		</tr>
		<tr>
	        <th class="applytd">字典序号：</th>
	         <td class="applytdnotcolor">
	        	<input  name="dictionary.orderNum" id="dictionary.orderNum" value="${dictionary.orderNum}" class="easyui-textbox" data-options="prompt:'输入序号...',required:true,validType:['length[1,15]']" style="width:400px;height:32px">
	       	 </td>
		</tr>
		<tr>
	        <th class="applytd">字典说明：</th>
	        <td class="applytdnotcolor" colspan="3">
	        	<textarea  name="dictionary.memo" id="dictionary.memo" class="easyui-textbox" data-options="multiline:true,prompt:'输入说明...',validType:['length[0,100]']" style="width:400px;height:64px" value="${dictionary.memo}"></textarea>
	        </td>
		</tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Dictionary.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>
