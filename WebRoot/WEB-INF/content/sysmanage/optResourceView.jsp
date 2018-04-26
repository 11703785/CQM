<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div id="optResourcetb" style="padding:5px;border:1px solid #ddd;">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="OptResource.create(1)">创建根资源</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="OptResource.create(2)">创建子资源</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="OptResource.create(3)">编辑资源</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="OptResource.deleteAll()">删除资源</a>
</div>
<table class="applytable" cellspacing="0" cellpadding="8" align="center" data-options="toolbar:'#optResourcetb'">
	<tr style="display: none">
		<th class="applytd">资源ID：</th>
		<td>
			<input name="optResource.resId" id="resId" value="${optResource.resId}">
		</td>
	</tr>
	<tr>
		<th class="applytd">父资源：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.parent.resName}</td>
	</tr>
	<tr>
		<th class="applytd">名称：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.resName}</td>
	</tr>
	<tr>
		<th class="applytd">编码：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.resCode}</td>
	</tr>
	<tr>
		<th class="applytd">序号：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.resOrder}</td>
	</tr>
	<tr>
		<th class="applytd">类型：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.resType}</td>
	</tr>
	<tr>
		<th class="applytd">Action：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.actions}</td>
	</tr>
	<tr>
		<th class="applytd">链接：</th>
		<td style="width:220px;height:32px" class="applytdnotcolor">${optResource.resUrl}</td>
	</tr>
</table>