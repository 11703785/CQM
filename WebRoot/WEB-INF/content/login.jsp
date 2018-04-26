<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎您登录-陕西省征信查询监测系统</title>
<link rel="icon" href="skin/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="resource/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="resource/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="resource/easyui/themes/color.css"/>
<script type="text/javascript" src="resource/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resource/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="skin/demo.css">
<script language="JavaScript">
$(document).keydown(function(event){ 
	if(event.keyCode == 13){
		if($("#loginName").val()!=""&&$("#password").val()!=""){
			loginSubmit();
		}
	}
	
	
	$("#loginName").focus();
	
}); 

function loginSubmit(){
	
	$.post("login.action",{"person.userAccount.loginName":$("#loginName").val(),"person.userAccount.userPwd":$("#password").val()},function(result){
		var rArr = result.split(";");
		if (rArr[0]=="false"){//登录失败
			alert("登录失败："+rArr[1]);
		}else{//登录成功
			location.replace("goon.action");
			/*if(rArr[1]=="1" || rArr[1]=="0"){//已注册
			}else{//试用期 或 过期
				location.replace("index_anonymous.action?flag="+rArr[1]+"&version="+rArr[2]+"&days="+rArr[3]);
			}*/
		}
	});
}
//点击enter键进行登陆

</script>
</head>

<body class="easyui-layout" scroll="no" style="background-color: #2071a6;">
	<div data-options="region:'north',border:false,split:false" style="background-color: #2071a6;height:22%;overflow:hidden;z-index: 9998px">&nbsp</div>

	<div data-options="region:'center',border:false,plain:true" style="background-color:#fcfcfc;overflow:hidden;">
	
	<table style="margin:0px;width:100%;height:100%;background:url(resource/images/login001.png) no-repeat center;" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td rowspan="2" style="width:55%" align="right">&nbsp</td>
			<td style="vertical-align:middle;">
			
				<div class="login-box-body" style="margin-left:35px;-moz-border-radius:20px;-webkit-border-radius:20px;border-radius:10px;filter:alpha(Opacity=55);-moz-opacity:0.55;opacity: 0.55;height:225px;width:260px">
			    <p class="login-box-msg">欢 迎 登 录</p>
			    <form id="loginForm" method="post">
			    	<table cellpadding="10" align="center" border="0">
			    		<tr>
			    			<td>登录名:</td>
			    			<td><input class="easyui-textbox" style="height: 30px;width:160px;" type="text" id="loginName" name="person.userAccount.loginName"></input></td>
			    		</tr>
			    		<tr>
			    			<td>密码:</td>
			    			<td><input class="easyui-textbox" style="height: 30px;width:160px;" type="password" id="password" name="person.userAccount.userPwd" ></input></td>
			    		</tr>
			    		<tr>
			    			<td colspan="2" align="right"><a href="javascript:loginSubmit();" style="width:120px;height:30px;margin: 15px;" class="easyui-linkbutton c6">登录</a></td>
			    		</tr>
		
			    	</table>
			    </form>
			  </div>
			
			
			
			</td>

		</tr>
	</table>

	</div>
 
	<div data-options="region:'south',split:false" style="height:22%;background-color: #2071a6;line-height: 20px;text-align: center;overflow:hidden;">&nbsp</div>
</body>
</html>