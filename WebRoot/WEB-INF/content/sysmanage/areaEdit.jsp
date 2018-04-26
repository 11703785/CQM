
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="ftitle">辖区信息</div>
<form id="areaForm" method="post">

	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		 <input name="area.parent.id" value="${area.parent.id}" id="parentId" hidden="true">
	     <input name="area.id" value="${area.id}" id="areaId" hidden="true">
	     <input value="${area.levels}" id="levels" hidden="true">
		<tr >
			<th class="applytd">所属辖区：</th>
			<td class="applytdnotcolor" colspan="3">
				${area.parent.name}
			</td>
		</tr>
		<tr>
		    <th class="applytd">辖区名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input name="area.name" id="areaName" value="${area.name}" class="easyui-textbox" data-options="prompt:'输入名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	
		<tr>
			<th class="applytd">辖区编码：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="area.code" id="areaCode" value="${area.code}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	    
	    	<tr>
			<th class="applytd">辖区级别：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                       <select id="areaLevels" class="form-control" name="area.levels" >
                       <c:choose>
                        <c:when test="${area.levels=='0'}">
                        <option value="0" selected="selected">地/市</option>
	                  	<option value="1">区/县</option>
	                  	<option value="2">乡/镇 </option>
	                	<option value="3">村/居委会</option>
                        </c:when>
                        
                          <c:when test="${area.levels=='1'}">
                        <option value="0" >地/市</option>
	                  	<option value="1" selected="selected">区/县</option>
	                  	<option value="2">乡/镇 </option>
	                	<option value="3">村/居委会</option>
                        </c:when>
                        
                          <c:when test="${area.levels=='2'}">
                        <option value="0" >地/市</option>
	                  	<option value="1">区/县</option>
	                  	<option value="2" selected="selected">乡/镇 </option>
	                	<option value="3">村/居委会</option>
                        </c:when>
                        
                          <c:when test="${area.levels=='3'}">
                        <option value="0" >地/市</option>
	                  	<option value="1">区/县</option>
	                  	<option value="2">乡/镇 </option>
	                	<option value="3" selected="selected">村/居委会</option>
                        </c:when>
                        <c:otherwise>
                  
                         <option >请选择</option>
                        <option value="0" >地/市</option>
	                  	<option value="1">区/县</option>
	                  	<option value="2">乡/镇 </option>
	                	<option value="3" >村/居委会</option>
                        
                        </c:otherwise>
                        
                       
                       </c:choose>
                 
	                	
	                  </select>	       
	                   </td>
	    </tr>
	    
		<tr>
	        <th class="applytd">辖区描述：</th>
	        <td class="applytdnotcolor" colspan="3">
	        	<input name="area.description" id="areaDescription" class="easyui-textbox" data-options="multiline:true,prompt:'输入描述...',validType:['length[0,100]']" style="width:220px;height:64px" value="${area.description}"</textarea>
	        </td>
		</tr>
		<tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Area.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>
