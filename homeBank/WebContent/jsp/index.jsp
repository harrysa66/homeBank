<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>财务系统</title>
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/res/css/easyui/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/res/css/easyui/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/res/css/main.css">

<script type="text/javascript" src="<%=basePath%>/res/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/highcharts.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/charts.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/base.js"></script>
<script>
$(function(){
	$('#tabs').tabs('add',{
		title:'首页',
		tools:[{
	        iconCls:'icon-mini-refresh',
	        handler:function(){
	        	var tab = $('#tabs').tabs('getSelected');
	        	tab.panel('refresh', 'main.jsp');
	        }
	    }],
        href:'main.jsp',
        closable:false
	});
	$('.easyui-tree').tree({
        onClick:function(node){
        	if (node.attributes.url != '' && node.attributes.url != null)
            {
                if ($('.easyui-tabs').tabs('exists', node.text))
                {
                    $('.easyui-tabs').tabs('select', node.text);
                }
                else
                {
                    $('.easyui-tabs').tabs('add',{   
                        title:node.text,   
                        href:node.attributes.url,   
                        closable:true  
                    });
                }
            }
        }
    });
});
</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',noheader:true,split:false" style="height:66px;background-color:rgb(30,62,123)">
        <div  class="ui-login">
 		<div class="ui-login-info">
	 		<span style="color: #D7E8F7;">欢迎</span> <span class="orange">${user.nickname}</span> <span class="orange">[${userIp}]</span><span style="color: #D7E8F7;">登录系统 </span>
	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 		<c:if test="${user.username == 'admin' }">
	 		<a class="add-info-btn"  href="javascript:void(0);">新增用户</a>
	 		<a class="user-info-btn"  href="javascript:void(0);">查看用户</a> <span style="color:#D7E8F7;">|</span>
	 		</c:if>
	 		<a class="update-info-btn"  href="javascript:void(0);">修改资料</a>
	 		<a class="modify-pwd-btn"  href="javascript:void(0);">修改密码</a> <span style="color:#D7E8F7;">|</span>
 			<a class="logout-btn" href="j_spring_security_logout">退出</a>
 		</div>
 	</div>
    </div>
    <div data-options="region:'south',noheader:true,split:false" style="height:50px;"></div>
    <div data-options="region:'west',title:'菜单',split:true" style="width:200px;">
        <div class="easyui-accordion"  border="false" id='menu'>
            ${menus}
         </div>
    </div>
    <div data-options="region:'center'," style="padding:1px;">
        <div id='tabs' class="easyui-tabs" data-options="fit:true,border:false">
        </div>
    </div>
    <div id="window" class="easyui-window" data-options="shadow:false,modal:true,closed:true,collapsible:false,minimizable:false" style="width:530px;padding:10px;top:200%;">
        
    </div>
    
    <!--  modify password start -->
	<div id="modify-pwd-win"  class="easyui-dialog" buttons="#editPwdbtn" title="修改用户密码" data-options="closed:true,modal:true" style="width:350px;height:200px;">
		<form id="pwdForm" action="modifyPassword.do" class="ui-form" method="post">
     		 <input type="hidden" name="id" value="${user.id}">
     		 <input type="hidden" name="username" value="${user.username}">
     		 <div class="ui-edit">
	           <div class="fitem">  
	              <label>旧密码:</label>  
	              <input id="oldPwd" name="oldPwd" type="password" class="easyui-validatebox"  data-options="required:true"/>
	           </div>
	            <div class="fitem">  
	               <label>新密码:</label>  
	               <input id="newPwd" name="newPwd" type="password" class="easyui-validatebox" data-options="required:true" />
	           </div> 
	           <div class="fitem">  
	               <label>重复密码:</label>  
	              <input id="rpwd" name="rpwd" type="password" class="easyui-validatebox"   required="required" validType="equals['#newPwd']" />
	           </div> 
	         </div>
     	 </form>
     	 <div id="editPwdbtn" class="dialog-button" >  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-submit">提交</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-close">关闭</a>  
         </div>
	</div>
	<!-- modify password end  -->
	
	 <!--  update info start -->
	<div id="update-info-win"  class="easyui-dialog" buttons="#editInfobtn" title="修改用户信息" data-options="closed:true,modal:true" style="width:350px;height:200px;">
		<form id="infoForm" action="updateInfo.do" class="ui-form" method="post">
     		 <input type="hidden" name="id" value="${user.id}">
     		 <div class="ui-edit">
     		 	<div class="fitem">  
	              <label>用户名:</label>  
	              ${user.username}
	           </div>
	           <div class="fitem">  
	              <label>昵称:</label>  
	              <input id="nickname" name="nickname" type="text" class="easyui-validatebox" value="${user.nickname}"  data-options="required:true"/>
	           </div>
	         </div>
     	 </form>
     	 <div id="editInfobtn" class="dialog-button" >  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-info-submit">提交</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-info-close">关闭</a>  
         </div>
	</div>
	<!-- update info end  -->
	
	<!--  add info start -->
	<div id="add-info-win"  class="easyui-dialog" buttons="#addInfobtn" title="新增用户信息" data-options="closed:true,modal:true" style="width:350px;height:200px;">
		<form id="addinfoForm" action="updateInfo.do" class="ui-form" method="post">
     		 <input type="hidden" name="id">
     		 <div class="ui-edit">
     		 	<div class="fitem">  
	              <label>用户名:</label>  
	              <input id="username" name="username" type="text" class="easyui-validatebox"  data-options="required:true"/>
	           </div>
	           <div class="fitem">  
	              <label>昵称:</label>  
	              <input id="nickname" name="nickname" type="text" class="easyui-validatebox"  data-options="required:true"/>
	           </div>
	         </div>
     	 </form>
     	 <div id="addInfobtn" class="dialog-button" >  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-addinfo-submit">提交</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-addinfo-close">关闭</a>  
         </div>
	</div>
	<!-- add info end  -->
	
	<!--  user info start -->
	<div id="user-info-win"  class="easyui-window" title="用户信息列表" data-options="closed:true,modal:true" style="width:610px;height:550px;">
		<table id="datagrid_user"></table>
	</div>
	<!-- user info end  -->
</body>
</html>