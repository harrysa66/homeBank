<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户登录</title>
    	<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/res/css/easyui/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/res/css/easyui/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/res/css/user_login.css">

<script type="text/javascript" src="<%=basePath%>/res/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/easyui-lang-zh_CN.js"></script>
	</head>
	<body id=userlogin_body>
	<form id="form" action="j_spring_security_check" method="post">
		<div></div>
		<div id=user_login>
			<dl>
				<dd id=user_top>
					<ul>
						<li class=user_top_l></li>
						<li class=user_top_c></li>
						<li class=user_top_r></li>
					</ul>
				<dd id=user_main>
					<ul>
						<li class=user_main_l></li>
						<li class=user_main_c>
							<div class=user_main_box>
								<ul>
									<li class=user_main_text>
										用户名：
									</li>
									<li class=user_main_input>
										<input class="txtusernamecssclass easyui-validatebox"  data-options="required:true,missingMessage:'用户名不能为空.'" name="j_username" id="j_username"  maxlength="20">
									</li>
								</ul>
								<ul>
									<li class=user_main_text>
										密 码：
									</li>
									<li class=user_main_input>
										<input class="txtpasswordcssclass easyui-validatebox"   data-options="required:true,missingMessage:'密码不能为空.'" type="password" name="j_password" id="j_password">
									</li>
								</ul>
								<ul>
									<li class=user_main_text>
										验证码：
									</li>
									<li class=user_main_input>
										<input class="vc-text easyui-validatebox" data-options="required:true,missingMessage:'验证码不能为空.'" maxlength="4" type="text" name="vercode" id="vercode">
										<img src="<%=basePath%>/verifyCode.do?time=new Date().getTime()" style="cursor:pointer" id="verifyImg" width="65" height="23" title="看不清点击切换验证码" alt="看不清点击切换验证码" onClick="this.src='<%=basePath%>/verifyCode.do?time='+new Date().getTime()" />
										<!-- <img class="vc-pic" width="65" height="23" title="点击刷新验证码" src="ImageServlet?time=new Date().getTime()"> -->
									</li>
								</ul>
								<ul>
									<li class=user_main_text>
									</li>
									<li class=user_main_input>
										<span style="color: red">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</span>
									</li>
								</ul>
							</div>
						</li>
						<li class=user_main_r>
							<input class="ibtnentercssclass"
								style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px"
								type=image src="res/images/login/user_botton.gif">
						</li>
					</ul>
				<dd id=user_bottom>
					<ul>
						<li class=user_bottom_l></li>
						<li class=user_bottom_c>
							<span style="margin-top: 40px"></span>
						</li>
						<li class=user_bottom_r></li>
					</ul>
				</dd>
			</dl>
		</div>
		<div></div>
		</form>
	</body>
</html>
