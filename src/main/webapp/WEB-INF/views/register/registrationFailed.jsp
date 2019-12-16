<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../shared/tags_bootstrap_v4.3.1.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册失败</title>
</head>
<body align="center">
	<h3>注册失败（写入数据库时）</h3>
	<script>
var tim=3;//设定跳转的时间 
setInterval("refer()",5000); //启动1秒定时 
function refer(){ 
if(tim==0){ 
	location.href="/Practice/Login/LoginIndexUrl"; //#设定跳转的链接地址 
} 
document.getElementById('show').innerHTML="注册失败（写入数据库时）"+tim+"秒后跳转到首页; ";
tim--; // 计数器递减 
//本文转自： 
}
</script>
	<span id="show"></span>
	
</body>
</html>