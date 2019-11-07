<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="../shared/teacher_Index_tags.jsp"%>

<!DOCTYPE html>
<html >
<head>
  <!-- Bootstrap 核心CSS 文件 -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>教师主页</title>
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no" name="viewport">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/ready.css">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/demo.css">
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.min.css" >
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/js/bootstrap-switch.min.js"></script>
</head>
<style type="text/css">
  ${csstHtml}

</style>
<script type="text/javascript">
</script>
<script type="text/javascript" src="<%=basePath%>scripts/teacherIndex.js"></script>
</head>
<body onload="onLi()">

<%@ include file="../shared/teacher_Index_footer.jsp"%>
</body>
</html>
