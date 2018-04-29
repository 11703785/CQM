<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String a = (String) request.getAttribute("a");
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>征信查询管理系统</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 5px; border: none">
			<form id="archive_af" class="easyui-form" method="post"
				enctype="multipart/form-data" action='archive'
				data-options="url:'archive',method:'post'">
				<!-- 隐藏字段:机构、角色、-->
				<input type="hidden" id="roles" name="roles" />
				<table cellspacing="4" width="100%" class="rjhc-table-panel">
					<tr>
						<td>
							<fieldset style="border: 1px solid #95B8E7;">
								<legend>用户信息</legend>
								<table cellspacing="4" width="100%" class="rjhc-table-panel">
									<tr>
										<td>机构部门:</td>
										<td><select class="easyui-combotree " name="orgCode"
											id="_orgCode"
											data-options="width:150,required:true,url:'org/userOrgTree',panelWidth:300">
										</select></td>
									</tr>
									<tr>
										<td>登录名:</td>
										<td class="firstval"><input class="easyui-textbox "
											type="text" name="userId"
											data-options="width:150,required:true,validType:['stringLength[1,20]'],missingMessage:'登录名为必填项'" />
										</td>
									</tr>
									<tr>
										<td>用户名:</td>
										<td class="firstval"><input class="easyui-textbox "
											type="text" name="userName"
											data-options="width:150,required:true,validType:['stringLength[1,20]'],missingMessage:'用户名为必填项'" />
										</td>
									</tr>
									<tr>
										<td>电子邮件:</td>
										<td class="firstval"><input class="easyui-textbox "
											type="text" name="email"
											data-options="width:150,validType:['stringLength[1,20]']" />
										</td>
									</tr>
									<tr>
										<td>电话号码:</td>
										<td class="firstval"><input class="easyui-textbox "
											type="text" name="telephone"
											data-options="width:150,validType:['stringLength[1,20]']" />
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="rjhc-panel-center" data-options="region:'center'">
			<div class="easyui-panel rjhc-panel-inner"
				data-options="title:'可分配角色列表',fit:true">
				<ul id="userroletree"></ul>
			</div>
		</div>
		<div class="rjhc-panel-south" data-options="region:'south'">
			<a class="easyui-linkbutton " data-options="iconCls:'icon-add'"
				href="javascript:void(0)" onclick="javascript:save('user','用户')">新增</a>
			<a class="easyui-linkbutton " data-options="iconCls:'icon-cancel'"
				href="javascript:void(0)"
				onclick="javascript:$('#user_addwin').window('close');">取消</a>
		</div>
	</div>
	<script type="text/javascript">
	// 初始化角色列表
	$(function(){
	    $('#userroletree').tree({
	        checkbox: true,
	        url: 'org/orgroletree/',
	        cascadeCheck: false
	    });
	});
	</script>
</body>
</html>