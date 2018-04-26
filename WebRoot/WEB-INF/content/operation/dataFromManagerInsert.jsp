<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="ftitle">接入点编辑</div>
<form id="sourceOrgForm" method="post">
	<table  class="applytable" cellspacing="0" cellpadding="8" align="center">
	 <input name="sourceOrg.id" value="${sourceOrg.id}" id="sourceOrg.id" hidden="true">
			   <input name="sourceOrg.areaId" value="${sourceOrg.area.id}" id="areaIds" hidden="true"> 
	
		<tr>
		    <th  class="applytd">单位名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input name="sourceOrg.name" id="name" value="${sourceOrg.name}" class="easyui-textbox" data-options="prompt:'输入名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	
		<tr>
			<th class="applytd">单位编码：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="sourceOrg.code" id="code" value="${sourceOrg.code}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	    
	    <tr>
			<th class="applytd">个人编码：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="sourceOrg.personalCode" id="personalCode" value="${sourceOrg.personalCode}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	    
	    
	   
         <tr>
			<th class="applytd">Ip地址：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="sourceOrg.ip" id="ip" value="${sourceOrg.ip}" class="easyui-textbox" data-options="prompt:'输入ip...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
   

 <c:choose>
	     <c:when test="${sourceOrg.area.name!=null}">
	    	<tr>
			<th class="applytd">所属辖区：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="areaId" id="areaId" value="${sourceOrg.area.name}" class="easyui-textbox" data-options="prompt:'输入ip...',required:true,validType:['length[1,15]']" readonly="readonly" style="width:220px;height:32px">
	        </td>
	    </tr>
	    </c:when>
	     <c:otherwise>   
         <tr>
			<th class="applytd">所属辖区：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
   <input class="easyui-combotree" id="areaId" name="areaId" value="${sourceOrg.area.name}" data-options="url:'loadArea.action',method:'post',required:true" style="width:100%;"></input>
	        </td>
	    </tr>
      </c:otherwise> 
	    </c:choose>

	    	
	
		<tr>
	        <th class="applytd">描述：</th>
	        <td class="applytdnotcolor" colspan="3">
	        	<input name="sourceOrg.description" id="description" class="easyui-textbox" data-options="multiline:true,prompt:'输入描述...',validType:['length[0,100]']" style="width:220px;height:64px" value="${sourceOrg.description}"</textarea>
	        </td>
		</tr>
		<tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="DataFromManager.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>