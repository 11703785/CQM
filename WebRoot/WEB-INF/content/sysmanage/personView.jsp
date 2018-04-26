<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!--表单信息区域-->
<div class="data_main">
<div class="ftitle">用户信息</div>
	<table class="applytable" cellspacing="0" cellpadding="8" style="width: 650px" align="center">
		<tr style="display:none">
			<th>隐藏字段：</th>
			<td class="applytdnotcolor">
				所属部门ID：<input id="departmentId" name="person.department.deptId" value="${person.department.deptId}">
				人员ID：<input id="personId" name="person.personId" value="${person.personId}">
			</td>
		</tr>
		<tr>
			<th class="applytd">所属部门：</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${person.department.deptName}</td>
		</tr>
		<tr>
			<th class="applytd">人员序号：</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${person.personOrder}</td>
		</tr>
		<tr>
			<th class="applytd">登录名：</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${person.loginName}</td>
		</tr>
		<tr>
			<th class="applytd">姓名：</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${person.personName}</td>
		</tr>
		<tr>
			<th class="applytd">电子邮箱：</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${person.email}</td>
		</tr>
		<tr>
			<th class="applytd">手机号码：</th>
			<td class="applytdnotcolor" style="width:80%;height:32px">${person.mobileNumber}</td>
		</tr>
		<tr>
		   <c:choose>
		     <c:when test="${ismod==0}">
			<th class="applytd">用户角色：</th>
			<td colspan="3" style="color: #7b7b7b" class="applytdnotcolor">
				<table style="width: 80%">
					<tr style="width: 80%">
						<td width="50%">
						<table style="width: 100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="0" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
						</table>
						</td>
						<td width="50%">
						<table style="width:100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="1" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
						</table>
						</td>
					</tr>
				</table>
			</td>
		</c:when>
		 <c:otherwise>
		 <th class="applytd">用户角色：</th>
			<td colspan="3" style="color: #7b7b7b" class="applytdnotcolor">
				<table style="width: 80%">
					<tr style="width: 80%">
						<td width="50%">
						<table style="width: 100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="0" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
						</table>
						</td>
						<td width="50%">
						<table style="width:100%;text-align: left;">
							<c:forEach items="${roleList}" var="role" begin="1" step="2">
								<tr>
									<td><input  type="checkbox" name="ids" id="${role.roleId}" value="${role.roleId}"/>${role.roleName}</td>
								</tr>
							</c:forEach>
							<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
						</table>
						</td>
					</tr>
				</table>
			</td>
		 
		 
		 
		 </c:otherwise>
		</c:choose>
		</tr>
	</table>
</div>
<script type="text/javascript">
	if("${ids}"!=null){
		<c:forEach items="${roleList }" var="role">
			<c:forEach items="${ids }" var="id">
				if("${id}"=="${role.roleId }"){
					$('#${role.roleId}').attr("checked","checked");
				}
			</c:forEach>
		</c:forEach>
	}
</script>