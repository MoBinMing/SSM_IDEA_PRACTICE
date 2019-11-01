<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../shared/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册成功</title>
</head>
<body align="center">
	<h3>注册成功</h3>
	<script>
var tim=5;//设定跳转的时间 
setInterval("refer()",1000); //启动1秒定时 
function refer(){ 
if(tim==0){ 
	location.href="/Practice/Login/LoginIndexUrl"; //#设定跳转的链接地址 
} 
document.getElementById('show').innerHTML="注册成功  "+tim+"秒后跳转到登录页; ";
tim--; // 计数器递减 
//本文转自： 
}
</script>
	<span id="show"></span>
</body>
</html>