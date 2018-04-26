<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="ftitle">角色信息</div>
<form id="roleForm" method="post">
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		<tr style="display:none">
			<th class="applytd">角色ID：</th>
			<td class="applytdnotcolor" colspan="3">
				<input name="role.roleId" value="${role.roleId}" id="role.roleId">
			</td>
		</tr>
		<tr>
		    <th class="applytd">角色名称：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input name="role.roleName" id="roleName" value="${role.roleName}" class="easyui-textbox" data-options="prompt:'输入名称...',required:true,validType:['length[1,50]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	
		<tr>
			<th class="applytd">角色编码：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
	        	<input  name="role.roleCode" id="roleCode" value="${role.roleCode}" class="easyui-textbox" data-options="prompt:'输入编码...',required:true,validType:['length[1,15]']" style="width:220px;height:32px">
	        </td>
	    </tr>
	    <
	        <c:if test="${role.roleScope==0}">
	    	<tr>
			<th class="applytd">角色级别：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                       <select id="roleScope" class="form-control" name="role.roleScope" >
	                  	<option value="2">通用角色</option>
	                  	<option value="0" selected="selected">市级角色</option>
	                  	<option value="1">县级角色</option>
	                  </select>	       
	                   </td>
	    </tr>
	    </c:if>
	    <c:if test="${role.roleScope==1}">
	    	<tr>
			<th class="applytd">角色级别：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                       <select id="roleScope" class="form-control" name="role.roleScope" >
	                  	<option value="2">通用角色</option>
	                  	<option value="0" >市级角色</option>
	                  	<option value="1" selected="selected">县级角色</option>
	                  </select>	       
	                   </td>
	    </tr>
	    </c:if>
	    <c:if test="${role.roleScope==2}">
	    	<tr>
			<th class="applytd">角色级别：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                       <select id="roleScope" class="form-control" name="role.roleScope" >
	                  	<option value="2" selected="selected">通用角色</option>
	                  	<option value="0" >市级角色</option>
	                  	<option value="1">县级角色</option>
	                  </select>	       
	                   </td>
	    </tr>
	    </c:if>
	    
	     <c:if test="${role.roleScope==null}">
	    	<tr>
			<th class="applytd">角色级别：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                       <select id="roleScope" class="form-control" name="role.roleScope" >
	                  	<option value="2" selected="selected">通用角色</option>
	                  	<option value="0" >市级角色</option>
	                  	<option value="1">县级角色</option>
	                  </select>	       
	                   </td>
	    </tr>
	    </c:if>
	    
	      <c:if test="${role.roleType==01}">
	     	<tr>
			<th class="applytd">角色所属：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                        <select id="roleType" class="form-control" name="role.roleType" >
	                  	<option value="03">通用角色</option>
	                  	<option value="01" selected="selected">人民银行</option>
	                  	<option value="02">其他机构</option>
	                  </select>    
	                   </td>
	    </tr>
	    </c:if>
	    <c:if test="${role.roleType==03}">
	     	<tr>
			<th class="applytd">角色所属：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                        <select id="roleType" class="form-control" name="role.roleType" >
	                  	<option value="03" selected="selected">通用角色</option>
	                  	<option value="01" >人民银行</option>
	                  	<option value="02">其他机构</option>
	                  </select>    
	                   </td>
	    </tr>
	    </c:if>
	    <c:if test="${role.roleType==02}">
	     	<tr>
			<th class="applytd">角色所属：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                        <select id="roleType" class="form-control" name="role.roleType" >
	                  	<option value="03">通用角色</option>
	                  	<option value="01" >人民银行</option>
	                  	<option value="02" selected="selected">其他机构</option>
	                  </select>    
	                   </td>
	    </tr>
	    </c:if>
	    
	     <c:if test="${role.roleType==null}">
	     	<tr>
			<th class="applytd">角色所属：<font color="#FF0000"> *</font></th>
	        <td class="applytdnotcolor">
                        <select id="roleType" class="form-control" name="role.roleType" >
	                  	<option value="03" selected="selected">通用角色</option>
	                  	<option value="01" >人民银行</option>
	                  	<option value="02">其他机构</option>
	                  </select>    
	                   </td>
	    </tr>
	    </c:if>
	    
		<tr>
	        <th class="applytd">描述：</th>
	        <td class="applytdnotcolor" colspan="3">
	        	<input name="role.description" id="description" class="easyui-textbox" data-options="multiline:true,prompt:'输入描述...',validType:['length[0,100]']" style="width:220px;height:64px" value="${role.description}"</textarea>
	        </td>
		</tr>
		<tr>
	</table>
</form>
<div style="text-align:center;padding:25px">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="Role.submit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeDialog('common_dlg')">取消</a>
</div>