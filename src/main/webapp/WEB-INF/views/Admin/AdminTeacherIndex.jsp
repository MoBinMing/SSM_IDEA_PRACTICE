<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="../shared/tags_bootstrap_v4.3.1.jsp"%>

<!DOCTYPE html>
<html >
<head>
  <title>练习管理</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<script type="text/javascript" src="<%=basePath%>scripts/adminTeacherIndex.js"></script>
</head>

<style type="text/css">
  .login-center {
    min-height: calc(100vh - 64px);
  }

</style>
<body onload="onLi()" class="mb-0" style=" background: #00000024;background-size: cover;" >
<%@ include file="navbar.jsp"%>
<div class="container pt-3 login-center" style="background: #00000024;">
  <ul class="breadcrumb ">
    <li class="breadcrumb-item"> <a href="<%=basePath%>Admin/AdminTeacherIndexUrl">教师管理 /</a> </li>
  </ul>
  <div class="row m-0 pb-2">
    <div class="col card-header p-0 col-9 col-sm-6 col-md-6 col-lg-6">
      <div class="input-group">
        <input id="searchTeachersVal" placeholder="输入关键字搜索教师 ..." type="text" class="form-control input-lg"
                onkeydown="keyup_submit(event);">
        <span class="input-group-addon btn btn-primary" onclick="searchTeachers('admin');">
            <i class="fa fa-search"></i>&nbsp;搜索</span>
      </div>
    </div>
    <div class="col text-right p-0 m-0 col-3  col-sm-6 col-md-6 col-lg-6" >
      <a class="btn btn-primary" href="<%=basePath%>Admin/AdminAddTeacherIndexUrl" >添加教师</a>
    </div>
  </div>
  <!--<div class="row m-0 p-1">
    <div class="btn-group" id="courseAges">
      <button class="btn btn-outline-dark dropdown-toggle p-1" data-toggle="dropdown"> 全部 </button>
      <div class="dropdown-menu">
        <a class="dropdown-item" href="#">2018</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="#">2017</a>
      </div>
    </div>
  </div>-->
<%--  <div class="alert alert-danger alert-dismissable" role="alert" id="notice">--%>
<%--    <button class="close" type="button" data-dismiss="alert">&times;</button>--%>
<%--    注意：为控制滥用情况，本站CNAME/DNS接入限制5个/人/天，如有大量接入需求请使用(官方DNS)强制接入或使用其他第三方合作平台接入后再来本站管理！<i id="notice2"></i>--%>
<%--  </div>--%>
<%--  <script>--%>
<%--    var maxtime = 5;--%>
<%--    function CountDown() {--%>
<%--      if (maxtime >= 0) {--%>
<%--        minutes = Math.floor(maxtime / 60);--%>
<%--        seconds = Math.floor(maxtime % 60);--%>
<%--        msg = "[提示 " + seconds + " 秒后自动关闭]";--%>

<%--        if (maxtime == 0){msg = "关闭中！";}--%>
<%--        document.all["notice2"].innerHTML = msg;--%>
<%--        --maxtime;--%>
<%--      } else{--%>
<%--        clearInterval(timer);--%>

<%--      }--%>
<%--    }--%>
<%--    timer = setInterval("CountDown()", 1000);--%>

<%--    window.setTimeout(function(){--%>
<%--      $("#notice").alert('close');--%>
<%--    },10000);--%>
<%--  </script>--%>

<%--  <div class="alert alert-success alert-dismissible" id="teacherSuccessHint">--%>
<%--    <button type="button" class="close" data-dismiss="alert">&times;</button>--%>
<%--    <strong id="teacherSuccessHintTitle">删除成功!</strong> <span id="teacherSuccessHintBody"></span>--%>
<%--  </div>--%>
  <div id="searchHint">
  <c:if test="${fn:length(teachers)==0}">

      <div class="alert alert-danger alert-dismissible">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong >提示：</strong> <span>当前数据库无教师!</span>
      </div>

  </c:if>
  </div>
  <div class="table-responsive">
    <table class="table table-striped table-hover table-sm table-bordered" style="text-align: center;">
      <thead>
      <tr>
        <th> 教工号 </th>
        <th> iphone </th>
        <th> 姓名 </th>
        <th> 性别 </th>
        <th> email </th>
        <th> 密码 </th>
        <th> 更新信息 </th>
        <th> 授权管理 </th>
        <th> 删除教师 </th>
      </tr>
      </thead>
      <tbody id="teachersTbody">
      <c:if test="${fn:length(teachers)>0}">
        <c:forEach var="teacher" items="${teachers}">
          <tr>
            <td id="teacherId${teacher.teacherId}">
                ${teacher.teacherId}
            </td>
            <td>
              <input type="number" class="" id="teacherIphone${teacher.teacherId}" style="max-width: 130px;"
                     value="${teacher.iphone}" oninput="value=value.replace(/[^\d]/g,'');
                         if(value.length>11)value=value.slice(0,11); ">
            </td>
            <td>
              <input type="text" class="" id="teacherName${teacher.teacherId}" style="max-width: 60px;"
                     value="${teacher.name}" oninput="
                         if(value.length>5)value=value.slice(0,5); ">
            </td>
            <td>
              <select id="teacherGender${teacher.teacherId}" style="width: auto;">
                <option value="女" <c:if test="${teacher.gender=='女'}"> selected</c:if>>女</option>
                <option value="男" <c:if test="${teacher.gender=='男'}"> selected</c:if>>男</option>
              </select>
            </td>
            <td>
              <input type="text" class="" id="teacherEmail${teacher.teacherId}" style="max-width: 180px;"
                     value="${teacher.email}">
            </td>
            <td>
              <input type="text" class="" id="teacherPassword${teacher.teacherId}" style="max-width: 60px;"
                     value="${teacher.password}">
            </td>
            <td >
              <button type="button" class="btn btn-secondary btn-sm m-0"
                      onclick="return updateTeacher('${teacher.teacherId}');">更新</button>
            </td>
            <td>
              <select id="teacherValid${teacher.teacherId}" style="width: auto;"
                      onchange="validTeacher(this,'${teacher.teacherId}')">
                <option value="1" <c:if test="${teacher.valid=='1'}"> selected</c:if>>授权</option>
                <option value="2" <c:if test="${teacher.valid=='2'}"> selected</c:if>>拒绝</option>
                <option value="0" <c:if test="${teacher.valid=='0'}"> selected</c:if>>未授权</option>
              </select>
            </td>
            <td >
              <button type="button" class="btn btn-danger btn-sm m-0" onclick="deleteTeacher('${teacher.teacherId}','${teacher.name}');">删除</button>
            </td>
          </tr>
        </c:forEach>
      </c:if>
      </tbody>
    </table>
  </div>

  <div class="modal " id="hintModal">
    <div class="modal-dialog " role="document">
      <div class="modal-content text-left ">
        <div class="alert alert-success alert-dismissible m-0" id="teacherSuccessHint" >
          <button type="button" class="close" data-dismiss="alert">×</button>
          <strong id="teacherSuccessHintTitle">删除成功!</strong> <span id="teacherSuccessHintBody"></span>
        </div>
      </div>
    </div>
  </div>

<%--  <pingendo  >--%>
<%--    <div class="" id="hint">--%>
<%--    </div>--%>
<%--  </pingendo>--%>

</div>
<div class="fixed-bottom" id="hint">

</div>
</body>
</html>

