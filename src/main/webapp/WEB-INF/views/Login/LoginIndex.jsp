<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="../shared/tags_bootstrap_v4.1.3.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>登录</title>
</head>
<body>

<div class="align-items-center d-flex cover section-aquamarine py-5" style="background-image: url(<%=basePath%>images/body.jpg);">
    <div class="container">
        <div class="row">
            <div class="col-10 col-sm-10 col-md-10 col-lg-8 col-xl-7 m-auto text-center ">
                <h1 class="mb-0 mt-5 display-4 ">练习管理系统</h1>
                <p class="mb-5 text-nowrap">@2019</p>
            </div>
            <form class="col-10 col-sm-10 col-md-8 col-lg-3 col-xl-4 m-auto p-4 " style="background: rgba(255, 255, 255, 0.1); min-width: fit-content;" onsubmit="return false;" role="form" action="" id="form1" method="POST" >
                <h4 class="mb-1.5 text-center">登录</h4>
                <div class="form-group"> <label for="iphone">Iphone </label>
                    <input class="form-control" placeholder="请输入手机号" id="iphone" name="iphone" type="text" value="13877209240"> </div>
                <div class="form-group"> <label for="password">Password</label>
                    <input class="form-control" placeholder="请输入密码" id="password" name="password" type="password" value="1">
                </div>
                <div class="row">
                    <label class="col-md-6 mb-3">
                        <input id="remember" type="checkbox" name="remember" value="1" style="margin-left: 0;">记住密码 </label>
                    <span class="col-md-6 mb-3" style="text-align: end;">
              <a href="<%=basePath%>/register/addUser" style="color: white;"><u>注册</u></a>
            </span>
                </div>
                <button type="submit" class="btn btn-block p-2 btn-primary" id="btn-submit" name="btn-submit"><b>登录</b></button>
            </form>
        </div>
    </div>
</div>
</body>

<script type="text/javascript" src="<%=basePath%>scripts/login.js"></script>
</html>

