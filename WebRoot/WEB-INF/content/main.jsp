<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String webapp = request.getContextPath();%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>甘肃省农（牧）户信用信息管理系统</title>
		
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
		
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/jQueryUI/jquery-ui.min.js"></script>
		<!-- bootstrap js -->
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/bootstrap/js/bootstrap.min.js"></script>
		
		<!-- ztree js -->
		<script type="text/javascript" src="<%=webapp%>/resource/adminLTE/zTree/js/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="<%=webapp%>/resource/adminLTE/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
		<!-- validator js -->
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/validator/js/bootstrapValidator.js"></script>
		
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/select2/select2.full.min.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/dist/js/app.min.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/datepicker/bootstrap-datepicker.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/chartjs/Chart.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/flot/jquery.flot.min.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/flot/jquery.flot.resize.min.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/flot/jquery.flot.pie.min.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/flot/jquery.flot.categories.min.js"></script>
		
		<!-- sys js -->
		<script language="javascript" src="<%=webapp%>/resource/js/sysmanage.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/js/confirm.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/js/template.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/js/datatable.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/js/rateindex.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/js/ratetask.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/js/jquery-form.js"></script>
		
		<script language="javascript" src="<%=webapp%>/resource/echart/js/echarts.js"></script>
		<script language="javascript" src="<%=webapp%>/resource/echart/js/macarons.js"></script>
		
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/dist/css/AdminLTE.min.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/font-awesome-4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/zTree/css/zTreeStyle.css" type="text/css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/dist/css/skins/_all-skins.min.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/validator/css/bootstrapValidator.css">
		
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/iCheck/flat/blue.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/datepicker/datepicker3.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/select2/select2.min.css">
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/iCheck/all.css">
		
		<!-- datatables js -->
		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/media/css/dataTables.bootstrap.min.css">
 		<link rel="stylesheet" href="<%=webapp%>/resource/adminLTE/plugins/media/css/uikit.min.css">
		<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/adminLTE/plugins/media/css/dataTables.uikit.min.css">
 		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/media/js/jquery.dataTables.min.js"></script>
 		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/media/js/dataTables.uikit.min.js"></script>
		
		<!-- datetime js -->
		<link rel="stylesheet" type="text/css" href="<%=webapp%>/resource/adminLTE/plugins/datetimepicker/bootstrap-datetimepicker.min.css">
 		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
 		<script language="javascript" src="<%=webapp%>/resource/adminLTE/plugins/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
 		<script language="javascript" src="<%=webapp%>/resource/js/jquery.blockUI.js"></script>
		
		<style type="text/css">
		.border-radius {
		    -webkit-border-radius: 0;
		    -moz-border-radius: 0;
		    border-radius: 0;
		}
		html,body{height:100%} 
		</style>
		<script type="text/javascript">
			$(document).ready(function(){
				//默认加载首页
				loadContent("mainHome.action");
			});
		</script>
		<script type="text/javascript">
		function changePassword(){
		$("#myModal").load("mainPassword.action",function() {
		$("#myModal").modal('show');   
	});
	}
		</script>
</head>
<body class="hold-transition skin-blue layout-top-nav">
<div class="wrapper">
<header class="main-header">
	<nav class="navbar navbar-static-top">
		<div class="container" style="margin: 0px;padding-left: 35px;width: 100%;background-image: url('<%=webapp%>/resource/images/hometop.png');background-repeat:no-repeat;background-size:100% 100%;">
			<!-- logo图片 -->
			<div class="navbar-header">
			  <img style="height: 65px;" src="skin/logo.png">
			</div>
			
			<!-- 右上角菜单 -->
			<div class="navbar-custom-menu" >
			  <ul class="nav navbar-nav" style="padding-right: 10px;padding-top:2px;">
			    <li class="dropdown messages-menu">
			      <a href="#" class="dropdown-toggle" style="padding: 7px;">
			        <i class="fa fa-question-circle text-red" style="font-size:16px;"></i>&nbsp;帮助
			      </a>
			    </li>
			    <li class="dropdown messages-menu">
			      <a href="#" onclick="changePassword()" class="dropdown-toggle" style="padding: 7px;">
			        <i class=" fa fa-pencil-square text-yellow" style="font-size:16px;"></i>&nbsp;修改密码
			      </a>
			    </li>
			    <!-- 登录用户信息 -->
			      <li class="dropdown notifications-menu">
			        <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 7px;">
			          <i class="fa fa-user text-black" style="font-size:16px;"></i>&nbsp;${person.personName} <span class="caret"></span>
			        </a>
			        <ul class="dropdown-menu">
			          <li class="header"><b>用户详情</b></li>
			          <li>
			            <ul class="menu">
			              <li>
			                <a href="#">
		      					<dd style="font-size: 14px;"><b>所属部门：</b>${person.department.deptName}</dd>
		      					<dd style="font-size: 14px;"><b>登录名称：</b>${person.userAccount.loginName}</dd>
		      					<dd style="font-size: 14px;"><b>电子邮箱：</b>${person.email}</dd>
		     					<dd style="font-size: 14px;"><b>电话号码：</b>${person.mobileNumber}</dd>
		     					<dd style="font-size: 14px;"><b>用户角色：</b>${person.roleNames}</dd>
			                </a>
			              </li>
			            </ul>
			          </li>
			          <li class="footer"><a href="logout.action"><span style="color: blue;">退出</span></a></li>
			        </ul>
			      </li>
			    </ul>
			  </div>
			  <div style="position: absolute;bottom: 35px;right: 65px;"><font color="white">${curdate}，${curweek}</font></div>
		</div>
		<!-- 中部导航菜单 -->
		<div class="container"
			style="background-color:#faf9f9; width: 100%; padding-left: 20px;">
			
			<div class="btn-group" role="group">
				<c:forEach items="${dirMenus}" var="dir" varStatus="status">
					<c:if test="${!empty dir.resUrl}">
						<div class="btn-group">
							<button type="button" onclick="loadContent('${dir.resUrl}')" class="btn btn-default btn-flat dropdown-toggle">
								<i class="${dir.pictureclass}" style="font-size:14px;"></i>&nbsp;${dir.resName}
							</button>
						</div>
					</c:if>
					<c:if test="${empty dir.resUrl}">
						<div class="btn-group">
							<button type="button"
								class="btn btn-default btn-flat dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i class="${dir.pictureclass}" style="font-size:14px;"></i>&nbsp;${dir.resName}
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="padding: 0px;">
							  	<c:forEach items="${optMenus}" var="opt" >
								  	<c:if test="${opt.parent.resId == dir.resId}">
									    <li style="margin-top: 2px;margin-bottom: 2px;"><a href="#" onclick="loadContent('${opt.resUrl}')">${opt.resName}</a></li>
									    <li class="divider" style="margin: 0px;"></li>
								  	</c:if>
								</c:forEach>
							</ul>
						</div>
					</c:if>
	        	</c:forEach>
			</div>
		</div>
	</nav>
</header>

  <!-- 中间内容区域 -->
  <div id="content-wrapper" class="content-wrapper" style="padding-left: 30px;padding-right: 40px;">
 		
  </div>
   <!-- 底部内容区域 -->
  <footer class="main-footer" style="padding: 2px;">
    <div class="container" >
      <div class="pull-right hidden-xs">
        <b>Version</b> 1.0.0
      </div>
      <strong>Copyright &copy; 2014-2016 </strong> All rights
      reserved.
    </div>
  </footer>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
</div>
<div id = "contentPwd"></div>


<div id="loading" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
            <img src="resource/images/loading.gif">请您耐心等待，数据处理中......
            </div>
        </div>
    </div>
</div>
</body>
</html>