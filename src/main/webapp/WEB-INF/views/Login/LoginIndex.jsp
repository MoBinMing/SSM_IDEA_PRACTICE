<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>登录练习管理系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../shared/tags_bootstrap_v4.3.1.jsp"%>
</head>
<style type="text/css">
    .login-center {
        height: calc(100vh - 56px);
    }
</style>
<body class="mb-0 " style=" background: #00000024;background-size: cover;" >
<%@ include file="navbar.jsp"%>
<div class="container pt-3 login-center">
    <div class="row align-items-center login-center">
        <div class="p-5 col-md-8 ">
            <h3 class="display-4 mb-3 font-weight-bold">后台管理</h3>
            <p class="mb-4 lead">这是一个SpringMvc+MyBatis整合开发的练习题管理系统</p>
        </div>
        <div class="p-5 col-md-4">
            <h3 class="mb-3">登录</h3>
            <form action="" id="form1" method="POST" onsubmit="return false;" role="form">
                <div class="form-group"> <label for="iphone" >手机号：</label>
                    <input class="form-control" placeholder="请输入手机号" id="iphone" name="iphone" type="text" value="13877209240"> </div>
                <div class="form-group"> <label for="password" >Password：</label>
                    <input class="form-control" placeholder="请输入密码" id="password" name="password" type="password" value="1">
                </div>
                <div class="row">
                    <label class="col-md-6 mb-3">
                        <input  id="remember" type="checkbox" name="remember" value="1" style="margin-left: 2px;">&nbsp;记住密码 </label>
                </div>
                <button type="submit" class="btn btn-block p-2 btn-dark" id="btn-submit" name="btn-submit"><b>登录</b></button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath%>scripts/login.js"></script>
</body>
</html>