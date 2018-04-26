<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ftitle">字典类型编辑</div>
<form id="dicTypeForm" method="post">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr style="display:none">
			<th class="applytd">ID：</th>
			<td class="applytdnotcolor" colspan="3">
				<input name="dicType.id" value="${dicType.id}" id="dicType.id">
			</td>
		</tr>
		<tr>
		    <th class="applytd">字典类型名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input name="dicType.name" id="dicType.name" value="${dicType.name}" class="easyui-textbox"   onkeyup="this.value=this.value.replace(' ','')" data-options="prompt:'输入名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	
	    <c:choose >
	       <c:when test="${dicType.code!=null}">
	       <tr>
			<th class="applytd">字典类型编码：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input readonly="readonly" name="dicType.code" id="dicType.code" value="${dicType.code}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	       </c:when>
	       <c:otherwise>
		<tr>
			<th class="applytd">字典类型编码：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="dicType.code" id="dicType.code" value="${dicType.code}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	    </c:otherwise>
	    </c:choose>
		<tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="DicType.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>

