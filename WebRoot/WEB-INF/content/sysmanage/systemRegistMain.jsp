<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<div  align="center"   style="padding:5px;height:auto">
	
	<table  class="applytable" style="margin-top: 30px" cellspacing="0" cellpadding="8" align="center">
		<tr>
			<td class="applytd">机构名称：</td>
			<td class="applytdnotcolor">
				<input class="easyui-textbox" readonly="readonly"   style="width:220px;height:32px" value="${department.deptName}"/>
			</td>
			
			<td class="applytd">版本号：</td>
			<td class="applytdnotcolor">
				<input class="easyui-textbox" readonly="readonly" value="${version}" style="width:220px;height:32px" ></input>
			</td>
		</tr>
		 
		
			<tr>
				<td class="applytd">有效期:</td>
				<td class="applytdnotcolor" >
					<input class="easyui-textbox" readonly="readonly" value="${status}" style="width:220px;height:32px"></input>
				</td>
			 	<td class="applytd">剩余天数:</td>
				<td class="applytdnotcolor" >
					<input class="easyui-textbox" readonly="readonly"  value="${days}天" style="width:220px;height:32px"></input>
				</td>
			</tr>
			<tr>
			<td class="applytd">机器码:</td>
					<td class="applytdnotcolor" colspan="3">
						<input class="easyui-textbox" readonly="readonly" multiline="true" value="${machCode}" style="width:100%;height:120px">
					</td>
			</tr>
			<tr>
				<td class="applytd">输入注册码：</td>
				<td class="applytdnotcolor" colspan="3" >
					 <form id="sysRegist" style="width: 652px;">
						<input class="easyui-textbox" name="syscode" multiline="true" value="" style="width:100%;height:150px" >
					</form>
				</td>
				</tr>
				<tr>
					<td class="applytdnotcolor" colspan="4" style="text-align: center;">
						 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="sysRegist.regist()">注册</a>
					</td>
				</tr>
	</table>
</div>
 