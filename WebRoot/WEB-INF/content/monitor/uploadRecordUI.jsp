<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="curuser" value="#session.cur_user" />


<div class="ftitle">数据采集信息</div>
<!--表单信息区域-->
<!--表单信息区域-->
<center>
<div class="data_main"></div>
<div class="date_title"></div>
</center>
<form id="uploadForm" method="post" enctype="multipart/form-data">

<table class="applytable" cellspacing="" cellpadding="8" align="center" border="1" >
	<tr>
	   <td class="applytd">上报机构：<font color="#FF0000"> *</font></td>
       <td class="applytdnotcolor">${person.department.deptName}</td>
    </tr>
	<tr>
		<td class="applytd">机构编码：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor">${person.department.deptCode}</td>
        
    </tr>
    
		<td class="applytd">数据类型：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor">
        <select id = "type" name = "type" style="width: 100px;">
        <option value="2">企业数据上报</option>
        <option value="1">个人数据上报</option>
        </select></td>
    </tr>
     <tr>
		<td class="applytd">上传：<font color="#FF0000"> *</font></td>
        <td class="applytdnotcolor"><input type="file" id="file" name = "file"  /></td>
    </tr>
</table>
	<div style="text-align:center;padding:25px">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="uploadSubmit('1')">提交</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="uploadSubmit('2')">重置</a></tr>
	</div>
</form>

<script type="text/javascript">
function uploadSubmit(type){
	if(type == '1'){
	$('#uploadForm').form('submit',{
         url: "uploadFile.action",
         success: function(result){
             var rArr = result.split(";");
             alert(rArr[1]);
             $('#file').attr("value","");
<%--             $.messager.show({--%>
<%--                 title: '提示：',--%>
<%--                 msg: rArr[1]--%>
<%--             });--%>
         }
     });
	}else{
		$('#file').attr("value","");
		$('#type').attr("value","0");
	}
}
</script>