<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>课程管理</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="../shared/tags_bootstrap_v4.3.1.jsp"%>
</head>
<style type="text/css">
  .login-center {
    min-height: calc(100vh - 64px);
  }
</style>

<body class="mb-0" style=" background: #00000024;background-size: cover;" >
<%@ include file="../shared/navbar.jsp"%>
<div class="container pt-3 login-center" style="background: #00000024;">
  <ul class="breadcrumb">
    <li class="breadcrumb-item active">课程管理 /</li>
  </ul>
  <div class="row m-0 pb-2">
    <div class="col card-header p-0 col-9 col-sm-6 col-md-6 col-lg-6">
      <div class="input-group">
        <input id="searchCoursesVal" placeholder="输入关键字搜索课程 ..." type="text" class="form-control input-lg" onkeyup="searchCourses();">
        <span class="input-group-addon btn btn-primary" onclick="searchCourses();">
            <i class="fa fa-search"></i>&nbsp;搜索</span>
      </div>
    </div>
    <div class="col text-right p-0 m-0 col-3  col-sm-6 col-md-6 col-lg-6" >
      <button type="button" class="btn btn-primary " data-target="#addCourseModal" data-toggle="modal">添加课程</button>
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
  <div class="table-responsive" >
    <table class="table table-hover table-sm table-bordered ">
      <thead>
      <tr>
        <th>年级 </th>
        <th> 课程名称 </th>
        <th>课程介绍</th>
        <th> 练习数</th>
        <th> 操作 </th>
      </tr>
      </thead>
      <tbody id="coursesTbody">
      <c:forEach var="item" items="${courses}">
        <tr onclick="location.href='<%=basePath%>Teacher/getPracticeByCourseId/${item.id}';">
          <td>${item.age}</td>
          <td>
            <button type="button" class="btn btn-link" onclick="toPractice('${item.id}');">${item.name}</button>
          </td>
          <td>${item.intro}</td>
          <td>${item.practiceSize}</td>
          <td class="td-actions text-left">
            <div class="form-button-action">
              <button id="deleteCourse${item.id}" type="button" data-toggle="tooltip" title="删除"
                      class="btn btn-link btn-simple-danger">
                <i class="fa fa-trash-o" aria-hidden="true"
                   onclick="deleteCourse('${item.id}');event.stopPropagation();">&nbsp;删除</i>
              </button>
            </div>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  <div class="modal" id="addCourseModal">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title bg-light">添加课程</h5>
          <button type="button" class="close" data-dismiss="modal"> <span>×</span> </button>
        </div>
        <form id="addCourses" name="addCourses" method="post" action="<%=basePath%>Teacher/addCourses" class="form-horizontal" role="添加课程" onsubmit="return addPracticesTest();">
          <div class="modal-body" style="">
            <div class="form-group">
              <label for="age">年级：</label>
              <input id="age" name="age" type="number" maxlength="4" value="2020"
                     oninput="value=value.replace(/[^\d]/g,'');
                         if(value.length>4)value=value.slice(0,4);">
              <div id="ageAlert" class="alert alert-warning p-0 m-0" style="visibility: hidden; display: none;">
                <a href="#" class="close p-0 m-0" data-dismiss="alert">×</a>
                <strong class="p-0 m-0">提示！</strong>年级输入非法
              </div>
            </div>
            <div class="form-group">
              <label for="name">课程名称</label>
              <div id="myAlert2" class="alert alert-warning" style="visibility: hidden; display: none;">
                <a href="#" class="close" data-dismiss="alert">×</a>
                <strong>提示！</strong>课程名称输入不完整！。
              </div>
              <input id="name" name="name" type="text" class="form-control" placeholder="请输入课程名称">
            </div>
            <div class="form-group">
              <label for="intro">课程描述</label>
              <textarea id="intro" name="intro" class="form-control" placeholder="请输入课程描述" rows="3"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">确认添加</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>

</div>
<script type="text/javascript" src="<%=basePath%>scripts/teacherCourseIndex.js"></script>
</body>
</html>