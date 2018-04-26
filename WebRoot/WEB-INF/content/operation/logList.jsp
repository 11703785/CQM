<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="logListTab2" fit="true" class="easyui-datagrid" title="您的位置 >> 运维管理 >> 运维日志" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getOptLogsJSON.action',toolbar:'#tb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>
	  <th data-options="field:'deptName'" width="200">机构名</th>
		<th data-options="field:'operContent'" width="300">操作内容</th>
		<th data-options="field:'ip'" width="200">IP地址</th>
		<th data-options="field:'operTime'" width="200">操作时间</th>
	</tr>
	</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	<div id="logList"  style="margin-bottom:5px">
	<div style=" text-align:center">
		&nbsp;&nbsp;&nbsp;&nbsp;机构名称: &nbsp;&nbsp;&nbsp;&nbsp; <input class="easyui-combotree" id="queryOrgName" name="queryOrgName" value="${department.name}" data-options="url:'loadDeptJson.action',method:'post'" style="width:260px;"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;IP地址: &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:120px" class="easyui-validatebox" type="text" name="optLog.ip" id="ip" onblur="vaCheckNull('ip');" data-options="required:false"/>
		&nbsp;&nbsp;&nbsp;&nbsp;操作日期: &nbsp;&nbsp;&nbsp;&nbsp;
		<input style="width:130px" class="easyui-datebox" editable="false" name="beginDate" id="logbeginDate" />
		&nbsp;——&nbsp;<input style="width:130px" class="easyui-datebox" name="endDate" editable="false" id="logendDate" />
		</div>
		<br>
		<div style=" text-align:center">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="Log.findData1(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="Log.findData1(2)">重置</a>
	  </div>
	</div>
</div>