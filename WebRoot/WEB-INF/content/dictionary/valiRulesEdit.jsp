<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="ftitle">校验规则信息</div>
<form id="valiRulesForm" method="post">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr style="display:none">
			<th class="applytd">ID：</th>
			<td class="applytdnotcolor" colspan="3">
				<input name="valiRules.id" value="${valiRules.id}" id="valiRules.id">
			</td>
		</tr>
		<tr>
		    <th class="applytd">规则名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input name="valiRules.name" id="valiRules.name" value="${valiRules.name}" class="easyui-textbox" data-options="prompt:'输入名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	
	    <c:choose>
	      <c:when test="${valiRules.rules!=null}">
	      <tr>
			<th class="applytd">存储过程名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input readonly="readonly"  name="valiRules.rules" id="valiRules.rules" value="${valiRules.rules}" class="easyui-textbox" data-options="prompt:'输入存储过程名称...',required:true" style="width:220px;height:32px">
	        </td>
	    </tr>
	      </c:when>
	      <c:otherwise>
		<tr>
			<th class="applytd">存储过程名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="valiRules.rules" id="valiRules.rules" value="${valiRules.rules}" class="easyui-textbox" data-options="prompt:'输入存储过程名称...',required:true" style="width:220px;height:32px">
	        </td>
	    </tr>
	    </c:otherwise>
	    </c:choose>
		<tr>
	        <th class="applytd">描述：</th>
	        <td class="applytdnotcolor" colspan="3">
	        	<input name="valiRules.descrption" id="valiRules.descrption" class="easyui-textbox" data-options="multiline:true,prompt:'输入描述...',validType:['length[0,100]']" style="width:220px;height:64px" value="${valiRules.descrption}"</textarea>
	        </td>
		</tr>
		<tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="ValiRules.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>
