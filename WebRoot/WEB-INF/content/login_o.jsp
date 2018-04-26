<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String webapp = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>欢迎您登录-陕西省征信查询监测系统</title>
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">

<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<link rel="icon" href="<%=webapp%>/skin/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="<%=webapp%>/skin/demo.css">
<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/bootstrap/css/bootstrap.min.css">
<%--<script type="text/javascript" src="<%=webapp%>/resource/js/common.js"></script>

--%><script language="JavaScript">
$(document).keydown(function(event){ 
	if(event.keyCode == 13){
		if($("#loginName").val()!=""&&$("#password").val()!=""){
			loginSubmit();
		}
	}
}); 
$(document).ready(function(){
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
<body style="background-image: url('<%=webapp%>/resource/images/login3.png');background-repeat:no-repeat;background-size:100%;">
<div>
<div style="">
	<div style="margin-left:auto;margin-right:auto;height:118px;width: 1024px;"></div>
</div>
<div style="height:218px;width: 100%;background-repeat:no-repeat;">
<div style="margin-left:auto;margin-right:auto;height:100%;width: 1024px;">
	<div class="login-box-body" style="float:right;margin-right:20%;margin-top:10%;-moz-border-radius:20px;-webkit-border-radius:20px;border-radius:10px;filter:alpha(Opacity=55);-moz-opacity:0.55;opacity: 0.55;height:245px;width:280px">
    <p class="login-box-msg">欢 迎 登 录</p>
    <form id="loginForm" method="post">
      <div class="form-group has-feedback">
        <input class="form-control" type="text"  placeholder="用户名" id="loginName" name="person.userAccount.loginName">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input class="form-control" type="password" placeholder="密码" id="password" name="person.userAccount.userPwd">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
        </div>
        <div  style="width:230px;margin-left:auto;margin-right:auto;">
          <a href="javascript:loginSubmit();" class="btn btn-primary btn-block btn-flat" style="-moz-border-radius:5px;-webkit-border-radius:5px;border-radius:5px;">登  录</a>
        </div>
      </div>
      
    </form>
  </div>
</div>
</div>
<div>
	<div style="width:1024px;margin-left: auto;margin-right:auto;margin-top:40px;margin-bottom:auto"></div>
</div>

</div>
</body>
</html>