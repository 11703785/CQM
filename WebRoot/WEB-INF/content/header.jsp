<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="curuser" value="#session.cur_user" />
<script>
function changePassword(){
		$("#common_dlg").dialog({
			title:"用户密码修改",
			cache:false,
			href:"mainPassword.action",
			resizable: true,
			height: 300,
			width: 400,
			title:"修改密码",
			modal: true
		});
		$('#common_dlg').dialog('open');
}
function valiNewPw(){
		var newPw =document.getElementById("pw").value;
		var Pw = document.getElementById("npw").value;
		if(newPw == ''){
			document.getElementById("font2").innerHTML='<font color=red>新密码不能为空！</font>';
			window.parent.document.getElementById("input2").value="";
			}
		else if(Pw!=''&&Pw != newPw){
			document.getElementById("font2").innerHTML='<font color=red>两次输入的密码不一致！</font>';
			window.parent.document.getElementById("input2").value="";
			}
		else if(newPw.length<8){
			document.getElementById("font2").innerHTML='<font color=red>密码长度至少8位！</font>';
			window.parent.document.getElementById("input2").value="";
			}
		else if(!hasCapital(newPw)||!hasLowercase(newPw)){
			document.getElementById("font2").innerHTML='';
			document.getElementById("StrongPass").innerHTML='<font color=red>密码必须是数字、大写字母、小写字母的组合！</font>';
			window.parent.document.getElementById("input2").value="";
			}
		else{
			document.getElementById("font2").innerHTML='<font color=green>格式正确</font>';
			window.parent.document.getElementById("input2").value="格式正确";
		}
	}
	
	//验证第二次输入新密码
	function valiPw(){
		var Pw = document.getElementById("npw").value;
		var newPw =  document.getElementById("pw").value;
		var font2 = document.getElementById("font2").innerHTML;
		if(Pw == ''){
			document.getElementById("font3").innerHTML='<font color=red>新密码不能为空！</font>';
			window.parent.document.getElementById("input3").value="";
			}
		else if(newPw != ''&& Pw != newPw){
			document.getElementById("font3").innerHTML='<font color=red>两次输入的密码不一致！</font>';
			window.parent.document.getElementById("input3").value="";
			}
		else if(Pw.length<8){
			document.getElementById("font3").innerHTML='<font color=red>密码长度至少8位！</font>';
			window.parent.document.getElementById("input3").value="";
			}
		else if(!hasCapital(Pw)||!hasLowercase(Pw)){
			document.getElementById("font3").innerHTML='';
			document.getElementById("StrongPass").innerHTML='<font color=red>密码必须是数字、大写字母、小写字母的组合！</font>';
			window.parent.document.getElementById("input3").value="";
			}
		else if(font2=='两次输入的密码不一致！'){
			document.getElementById("font2").innerHTML='<font color=red>两次输入的密码不一致</font>';
			document.getElementById("font3").innerHTML='<font color=red>两次输入的密码不一致</font>';
			window.parent.document.getElementById("input2").value="";
			window.parent.document.getElementById("input3").value="";
		}else{
			valiNewPw()
			document.getElementById("font3").innerHTML='<font color=green>格式正确</font>';
			window.parent.document.getElementById("input3").value="格式正确";
			}
	}
	//验证密码中是否含有大写字母
	function hasCapital(str){
		var result = str.match(/^.*[A-Z]+.*$/);
		if(result == null)
			return false;
		return true;
	}
	
	//验证密码中是否含有小写字母
	function hasLowercase(str){
		var result = str.match(/^.*[a-z]+.*$/);
		if(result == null)
			return false;
		return true;
	}
//提交密码保存
function subMi(){
		var oriPass = document.getElementById("oriPass").value;
		var newPass = document.getElementById("npw").value;
		var fontStr3 = document.getElementById("input3").value;
		var fontStr2 = document.getElementById("input2").value;
		if(fontStr3=="格式正确"&&fontStr2=="格式正确"){
			$.messager.confirm("提示", "是否确认修改密码", function(r){
				if(r){		
					$.ajax({
						url:"changePassword.action?user.password="+encodeURI(encodeURI(oriPass)),
						type:'post',
						success:function(result){
							if (result=="no"){
								 $.messager.show({
							          title: '错误提示：',msg: "原密码不正确，请重新输入"
							     });
							}else{	
								$('#userForm').form('submit',{
									url:"saveUserPassword.action?user.password="+encodeURI(encodeURI(newPass)),
									success:function(data){
										if(data=="yes"){
											//$.messager.alert('系统提示', '密码更新成功,请重新登录!');
											$('#changePassword').dialog('close');
											$.messager.confirm('提示', '密码更新成功，是否退出重新登录', function(r){
												if (r){
													window.location.href="logout.action";
												}else{
													$.messager.alert('提示', '您已经更改密码成功，请您牢记新密码! '+newPass.substring(0,1)+"******");
												}
											});
										}else{
											 $.messager.show({
								                 title: '提示：',  msg: "系统提示','系统异常，请稍后再试!"
								             });
										$('#changePassword').dialog('close');
										}
									}
								});
							}
						}
				});
			}
		});	
	}else{
		$.messager.alert('系统提示', '密码格式不正确，或两次密码输入不一致，请重新输入!');
	}
}
//关闭
	changePaclose = function(){
		$('#common_dlg').dialog('close');
	};
	


function help(){
location.href="manualDownload.action";
}
</script>

<table class="header-box" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td rowspan="2" style="width:10%" align="right"><img align="middle" style="width: 72px;height: 69px;" src="resource/images/header_logo.gif" /></td>
		<td style="vertical-align:bottom;"><img style="width: 293px;height: 32px;margin-left: 8px;" src="resource/images/title.gif" /></td>
		<td style="width: 650px;vertical-align:top;padding-top: 3px;">
			<a href="logout.action" class="exit-a">退出</a>
			<a href="javascript:changePassword()" class="password-a">修改密码</a>
		</td>
	</tr>
	<tr>
		<td >&nbsp;</td>

		<td style="height: 20px;text-align: right;" valign="top">
			<font color="white">欢迎您&nbsp;[${loginInfo.orgName}]，&nbsp;[${loginInfo.userId}]&nbsp;${curdate}，${curweek}&nbsp;&nbsp;&nbsp;&nbsp; </font>

		</td>
	</tr>
</table>

<div id="common_dlg" class="easyui-dialog" closed="true" style="padding:10px 20px" data-options="iconCls:'icon-save',onResize:function(){$(this).dialog('center');}"></div>
<div id="second_dlg" class="easyui-dialog" closed="true" style="padding:10px 20px" data-options="iconCls:'icon-save',onResize:function(){$(this).dialog('center');}"></div>
