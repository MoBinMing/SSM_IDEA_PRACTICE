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

<script type="text/javascript" src="<%=basePath%>scripts/adminAddTeacherIndex.js"></script>
</head>

<style type="text/css">
  .login-center {
    min-height: calc(100vh - 64px);
  }
  .textarea{
    width: 400px;
    background:white;
    min-height: 20px;
    max-height: 300px;
    _height: 120px;
    margin-left: auto;
    margin-right: auto;
    padding: 3px;
    outline: 0;
    border: 1px solid #a0b3d6;
    font-size: 12px;
    line-height: 24px;
    padding: 2px;
    word-wrap: break-word;
    overflow-x: hidden;
    overflow-y: auto;

    border-color: rgba(82, 168, 236, 0.8);
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1), 0 0 8px rgba(82, 168, 236, 0.6);
  }
</style>
<body onload="onLi()" class="mb-0" style=" background: #00000024;background-size: cover;" >
<%@ include file="navbar.jsp"%>
<div class="container pt-3 login-center" style="background: #00000024;">
  <ul class="breadcrumb ">
    <li class="breadcrumb-item"> <a href="<%=basePath%>Admin/AdminTeacherIndexUrl">教师管理</a> </li>
    <li class="breadcrumb-item active ">添加教师</li>
  </ul>

  <div id="addHint">
  </div>

  <div class="row m-0">
    <div class="table-responsive">
      <table class="table table-striped table-hover table-sm table-bordered">
        <thead>
        <tr>
          <th > 教工号 </th>
          <th> iphone </th>
          <th> 姓名 </th>
          <th> 性别 </th>
          <th> email </th>
          <th> 密码 </th>
          <th> 确认密码 </th>
          <th> 授权管理 </th>
          <th style="text-align: end;"> 添加教师 </th>
        </tr>
        </thead>
        <tbody id="teachersTbody">
        <tr>
          <td>
            <input type="text" class="" id="teacherId" style="max-width: 1300px;">
          </td>
          <td>
            <input type="number" class="" id="teacherIphone" style="max-width: 130px;" oninput="value=value.replace(/[^\d]/g,'');
                         if(value.length>11)value=value.slice(0,11); ">
          </td>
          <td>
            <input type="text" class="" id="teacherName" style="max-width: 60px;" oninput="
                         if(value.length>5)value=value.slice(0,5); ">
          </td>
          <td>
            <select id="teacherGender" style="width: auto;">
              <option value="女">女</option>
              <option value="男" selected="">男</option>
            </select>
          </td>
          <td>
            <input type="email" class="" id="teacherEmail" style="max-width: 180px;">
          </td>
          <td>
            <input type="password" class="" id="teacherPassword" style="max-width: 100px;">
          </td>
          <td>
            <input type="password" class="" id="teacherPassword1" style="max-width: 100px;">
          </td>
          <td>
            <select id="teacherValid" style="width: auto;">
              <option value="1" selected="">授权</option>
              <option value="2">拒绝</option>
              <option value="0">未授权</option>
            </select>
          </td>
          <td style="text-align: right;margin: 0px;padding: 0px;">
            <button type="button" class="btn btn-danger btn-sm m-0 " onclick="addTeacher();">确认添加</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="row m-0">
    <div class="col-md-8 p-0">
      <h5 class="pi-draggable">批量添加教师&nbsp; <small class="text-muted">请输入教工号，换行或","为一个教工号,工号长度限制为11</small></h5>
    </div>
    <div class="col-md-4 p-0 "style="text-align: right;">
      默认密码： <input id="teacherPw" placeholder="请设置默认密码" value="123456" >
      <button type="button" class="btn btn-success btn-sm" onclick="addTeachers();">批量添加</button>
    </div>
  </div>
  <div class="row m-0 mt-2">
    <div id="teachersVal" onload="onLi()" class="textarea col-md-12" contenteditable="true" style="height: 200px;">
      <br />
    </div>
  </div>

<%--  <pingendo  >--%>
<%--    <div class="" id="hint">--%>
<%--    </div>--%>
<%--  </pingendo>--%>

</div>
<div class="fixed-bottom" id="hint">

</div>
<script>
  document.querySelector('div[contenteditable="true"]').addEventListener("paste", function(e) {
    e.stopPropagation();
    e.preventDefault();
    var text = '', event = (e.originalEvent || e);
    if (event.clipboardData && event.clipboardData.getData) {
      text = event.clipboardData.getData('text/plain');
    } else if (window.clipboardData && window.clipboardData.getData) {
      text = window.clipboardData.getData('Text');
    }
    if (document.queryCommandSupported('insertText')) {
      document.execCommand('insertText', false, text);
    } else {
      document.execCommand('paste', false, text);
    }
  });
</script>
</body>

</html>

