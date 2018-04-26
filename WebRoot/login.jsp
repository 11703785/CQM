<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String webapp = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎您登录-陕西省征信查询监测系统</title>
<link rel="icon" href="<%=webapp%>/skin/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/easyui/themes/icon.css"/>
<script type="text/javascript" src="<%=webapp%>/resource/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=webapp%>/resource/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>

<body class="easyui-layout" scroll="no" style="background-color: #2071a6;">
	<div data-options="region:'north',border:false,split:false" style="background-color: #2071a6;height:22%;overflow:hidden;z-index: 9998px">&nbsp</div>

	<div data-options="region:'center',border:false,plain:true" style="background-color:#fcfcfc;overflow:hidden;">
		<div class="easyui-panel" style="width:100%;height:500px;background:url('<%=webapp%>/resource/images/login001.png') no-repeat center center;border:0px solid #cccccc;">
		
		    <div class="easyui-panel" title="登录" style="width:100%;max-width:300px;padding:30px 60px;">
        <form id="ff" method="post">
           <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'Name:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="email" style="width:100%" data-options="label:'Email:',required:true,validType:'email'">
            </div>

        </form>
        <div style="text-align:center;padding:5px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">Submit</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">Clear</a>
        </div>
    </div>
		
		
		
		
		
		
		</div>

	</div>
 
	<div data-options="region:'south',split:false" style="height:22%;background-color: #2071a6;line-height: 20px;text-align: center;overflow:hidden;">&nbsp</div>

	<%--
	<div id="loginWin" class="easyui-window" title="登录" style="width:350px;height:188px;padding:5px;"
   minimizable="false" maximizable="false" resizable="false" collapsible="false" closable="false">
    <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">
        <form id="loginForm" method="post">
            <div style="padding:5px 0;">
                <label for="login">帐号:</label> 
                <input type="text" name="login" style="width:260px;"></input>
            </div>
            <div style="padding:5px 0;">
                <label for="password">密码:</label>
                <input type="password" name="password" style="width:260px;"></input>
            </div>
             <div style="padding:5px 0;text-align: center;color: red;" id="showMsg"></div>
        </form>
            </div>
            <div region="south" border="false" style="text-align:right;padding:5px 0;">
                <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="login()">登录</a>
                <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cleardata()">重置</a>
            </div>
    </div>
</div>
--%>
</body>

<script type="text/javascript">
/**
document.onkeydown = function(e){
    var event = e || window.event;  
    var code = event.keyCode || event.which || event.charCode;
    if (code == 13) {
        login();
    }
}
$(function(){
    $("input[name='login']").focus();
});
function cleardata(){
    $('#loginForm').form('clear');
}
function login(){
     if($("input[name='login']").val()=="" || $("input[name='password']").val()==""){
         $("#showMsg").html("用户名或密码为空，请输入");
         $("input[name='login']").focus();
    }else{
            //ajax异步提交  
           $.ajax({            
                  type:"POST",   //post提交方式默认是get
                  url:"login.action", 
                  data:$("#loginForm").serialize(),   //序列化               
                  error:function(request) {      // 设置表单提交出错                 
                      $("#showMsg").html(request);  //登录错误提示信息
                  },
                  success:function(data) {
                      document.location = "index.action";
                  }            
            });       
        } 
}*/
</script>
</html>