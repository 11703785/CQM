<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String webapp = request.getContextPath(); %>
<div class="ftitle">部门信息</div>
<!--表单信息区域-->
<form id="deptForm" method="post">

<input type="hidden" id="deptId" name="parent.deptId" value="${parent.deptId}">
<input type="hidden"  name="department.deptId" value="${department.deptId}">
<table class="applytable" cellspacing="0" cellpadding="8" align="center" >

	<tr>
        <td class="applytd">所属机构：</td>
        <td class="applytdnotcolor">${parent.deptName}</td>
	</tr>
	
	<tr>
	   <td class="applytd">机构名称：<font color="#FF0000"> *</font></td>
       <td class="applytdnotcolor"><input id="departmentName" value="${department.deptName}" name="department.deptName"   class="easyui-textbox" data-options="prompt:'输入机构名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"> </td>
       
    </tr>
    
	<tr>
		<td class="applytd">机构编码(企业)：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor"><input id="departmentCode" name="department.deptCode" value="${department.deptCode}" class="easyui-textbox" data-options="prompt:'输入机构编码...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"></td>
        
    </tr>
    
    <tr>
		<td class="applytd">机构编码(个人)：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor"><input id="personalOrgCode" name="department.personalOrgCode" value="${department.personalOrgCode}" class="easyui-textbox" data-options="prompt:'输入机构编码...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"></td>
        
    </tr>
    
    <tr>
		<td class="applytd">机构类型：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor"><input id="departmentType1" name="department.deptType" value="01" type="radio" checked >人民银行</td>
        
    </tr>
    
    <tr>
    <td class="applytd"><font color="#FF0000"> </font></td>
            <td class="applytdnotcolor"><input id="departmentType2" name="department.deptType" value="02" type="radio" >其他机构</td>
    </tr>
    
    
    
    <c:choose>
	     <c:when test="${department.area.name!=null}">
	    	<tr>
			<th class="applytd">所属辖区：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="areaId" id="areaId" value="${department.area.name}" class="easyui-textbox" data-options="prompt:'输入ip...',required:true,validType:['length[1,15]']" readonly="readonly" style="width:220px;height:32px">
	        </td>
	    </tr>
	    </c:when>
	     <c:otherwise>   
         <tr>
			<th class="applytd">所属辖区：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
   <input class="easyui-combotree" id="areaId" name="areaId" value="${department.area.name}" data-options="url:'loadArea.action',method:'post',required:true" style="width:100%;"></input>
	        </td>
	    </tr>
      </c:otherwise> 
	    </c:choose>
    
    <tr>
		<td class="applytd">机构序号：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor"><input id="departmentOrder" name="department.deptOrder" value="${department.deptOrder}" class="easyui-textbox" data-options="prompt:'输入机构序号...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"></td>
           <td ></td>
   
    </tr>
    
     <tr>
		<td class="applytd">描述：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor"><input id="departmentDescription" name="department.description" value="${department.description}" class="easyui-textbox" data-options="prompt:'输入机构描述...',required:true,validType:['length[1,50]']" style="width:220px;height:32px"></td>
   
    </tr>
    
</table>
	<div style="text-align:center;padding:25px">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Department.submit()">提交</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a></tr>
	</div>
</form>