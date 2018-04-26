<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!--表单信息区域-->
<div class="data_main">
<div class="ftitle">详情信息</div>
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		
		<tr>
			<th class="applytd">机构代码：</th>
			<td class="applytdnotcolor" style="width:100px;height:32px">${qyzxcxmx.upOrgCode}</td>
			<th class="applytd">用户所属机构：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryOrgNo}</td>
		</tr>
	
		<tr>
			<th class="applytd">查询网点名称：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryOrgName}</td>
			<th class="applytd">查询时间：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryTime}</td>
		</tr>
		<tr>
			<th class="applytd">查询用户系统名：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryUserSysName}</td>
			<th class="applytd">查询用户真实姓名：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryUserName}</td>
		</tr>
		<tr>
			<th class="applytd">部门名称：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.deptName}</td>
			<th class="applytd">被查询单位名称：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.companyName}</td>
		</tr>
		<tr>
			<th class="applytd">企业编码：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.zzCode}</td>
			<th class="applytd">是否查得：</th>
			<td  class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.isQueried}</td>
			
		</tr>
		
		<tr>
			<th class="applytd">查询授权时间：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryAuthTime}</td>
			<th class="applytd">查询机IP：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${qyzxcxmx.queryComputerIP}</td>
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