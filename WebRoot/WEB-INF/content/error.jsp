<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>系统异常</title>
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">

<script language="javascript" src="resource/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script language="javascript" src="resource/adminLTE/bootstrap/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="resource/adminLTE/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="resource/adminLTE/bootstrap/css/bootstrap.min.css">

  </head>
  
  <body>
    <div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<div class="error-container">
					<div class="well">
						<h1 class="grey lighter smaller">
							<span class="blue bigger-125">
								<i class="ace-icon fa fa-sitemap"></i>
							</span>
							${ex}
						</h1>

						<hr />
						<h3 class="lighter smaller">系统试图访问该页面</h3>

						<div>
							<div class="space"></div>
							<h4 class="smaller">请尝试以下操作:</h4>

							<ul class="list-unstyled spaced inline bigger-110 margin-15">
								<li>
									<i class="ace-icon fa fa-hand-o-right blue"></i>
									检查访问地址确认书写正确
								</li>

								<li>
									<i class="ace-icon fa fa-hand-o-right blue"></i>
									查看使用手册或说明文档
								</li>

								<li>
									<i class="ace-icon fa fa-hand-o-right blue"></i>
									将错误信息和出错过程反馈给技术人员
								</li>
							</ul>
						</div>

						<hr/>
						<div class="space"></div>

						<div class="center">
							<a href="index_anonymous.action" class="btn btn-grey">
								<i class="icon-arrow-left"></i>
								返回.
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>
