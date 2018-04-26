<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!--表单信息区域-->
<div class="data_main">
<div class="ftitle">详情信息</div>
	<table class="applytable" cellspacing="0" cellpadding="8" align="center">
		
		<tr>
			<th class="applytd">机构代码：</th>
			<td class="applytdnotcolor" style="width:100px;height:32px">${grzxcxmx.upOrgCode}</td>
			<th class="applytd">用户所属机构：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryOrgNo}</td>
		</tr>
	
		<tr>
			<th class="applytd">查询网点名称：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryOrgName}</td>
			<th class="applytd">查询时间：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryTime}</td>
		</tr>
		<tr>
			<th class="applytd">查询用户系统名：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryUserSysName}</td>
			<th class="applytd">查询用户真实姓名：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryUserName}</td>
		</tr>
		<tr>
			<th class="applytd">被查询人姓名：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queriedUserName}</td>
			<th class="applytd">证件类型：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.certType}</td>
		</tr>
		<tr>
			<th class="applytd">证件号码：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.certNo}</td>
			<th class="applytd">查询原因：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryReason}</td>
		</tr>
		<tr>
			<th class="applytd">查询版本：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryFormatName}</td>
			<th class="applytd">是否查得：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.isQueried}</td>
		</tr>
		<tr>
			<th class="applytd">查询授权时间：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryAuthTime}</td>
			<th class="applytd">查询机IP：</th>
			<td class="applytdnotcolor" style="width:220px;height:32px">${grzxcxmx.queryComputerIP}</td>
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