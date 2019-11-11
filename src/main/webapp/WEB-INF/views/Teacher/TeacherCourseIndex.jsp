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
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.4/css/bootstrap3/bootstrap-switch.min.css" >
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.4/js/bootstrap-switch.min.js"></script>
</head>
<style type="text/css">
  ${csstHtml}

</style>
<script type="text/javascript">
</script>
<script type="text/javascript" src="<%=basePath%>scripts/teacherCourseIndex.js"></script>
</head>
<body onload="onLi()">
<div class="wrapper">
  <div class="main-header text-white" >
    <div class="logo-header align-middle">
      <a href="" class="logo"> 练习题管理系统 </a>
      <button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse" data-target="collapse" aria-controls="sidebar" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <button class="topbar-toggler more"><i class="la la-ellipsis-v"></i></button>
    </div>
    <nav class="navbar navbar-header navbar-expand-lg ">
      <div class="container-fluid text-center">
        <ul class="navbar-nav topbar-nav ml-md-auto align-items-center">
          <li class="nav-item dropdown hidden-caret">
            <ul class="dropdown-menu notif-box" aria-labelledby="navbarDropdown">
              <li>
                <div class="dropdown-title">You have 4 new notification</div>
              </li>
              <li>
                <div class="notif-center">
                  <a href="#">
                    <div class="notif-icon notif-primary"> <i class="la la-user-plus"></i> </div>
                    <div class="notif-content">
                      <span class="block"> New user registered </span>
                      <span class="time">5 minutes ago</span>
                    </div>
                  </a>
                  <a href="#">
                    <div class="notif-icon notif-success"> <i class="la la-comment"></i> </div>
                    <div class="notif-content">
                      <span class="block"> Rahmad commented on Admin </span>
                      <span class="time">12 minutes ago</span>
                    </div>
                  </a>
                  <a href="#">
                    <div class="notif-img">
                      <img src="<%=basePath%>images/${user.gender}.png" alt="Img Profile">
                    </div>
                    <div class="notif-content">
                      <span class="block"> Reza send messages to you </span>
                      <span class="time">12 minutes ago</span>
                    </div>
                  </a>
                  <a href="#">
                    <div class="notif-icon notif-danger"> <i class="la la-heart"></i> </div>
                    <div class="notif-content">
                      <span class="block"> Farrah liked Admin </span>
                      <span class="time">17 minutes ago</span>
                    </div>
                  </a>
                </div>
              </li>
              <li>
                <a class="see-all" href="javascript:void(0);"> <strong>See all notifications</strong> <i class="la la-angle-right"></i> </a>
              </li>
            </ul>
          </li>
          <li class="nav-item dropdown">
            <a class="dropdown-toggle profile-pic" data-toggle="dropdown" href="#" aria-expanded="false">

              <img src="<%=basePath%>userImg/${user.iphone}.jpg" alt="user-img" width="36" class="img-circle"
                   onerror=src='<%=basePath%>images/${user.gender}.png'>
              <span>葛祥友</span>
            </a>
            <ul class="dropdown-menu dropdown-user">
              <li>
                <div class="user-box">
                  <div class="u-img">
                    <img src="<%=basePath%>userImg/${user.iphone}.jpg" alt="user"
                         onerror=src='<%=basePath%>images/${user.gender}.png'>
                  </div>
                  <div class="u-text">
                    <h4>${user.name}</h4>
                    <p class="text-muted">hello ${user.email}</p>
                  </div>
                </div>
              </li>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#"><i class="ti-settings"></i> Account Setting</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="<%=basePath%>Login/Exit"><i class="fa fa-power-off"></i> Logout</a>
            </ul>
            <!-- /.dropdown-user -->
          </li>
        </ul>
      </div>
    </nav>
  </div>
  <div class="sidebar ">
    <div class="scrollbar-inner sidebar-wrapper" style="">
      <div class="user">
        <div class="photo">
          <img src="<%=basePath%>userImg/${user.iphone}.jpg"
               onerror=src='<%=basePath%>images/${user.gender}.png'>
        </div>
        <div class="info">
          <a class="" data-toggle="collapse" href="#collapseExample" aria-expanded="true">
              <span>${user.name}<span class="user-level">${user.email}</span>
                <span class="caret"></span>
              </span>
          </a>
          <div class="clearfix"></div>
          <div class="in collapse show" id="collapseExample" aria-expanded="true" style="">
            <ul class="nav">
              <li>
                <a href="<%=basePath%>Login/Exit">
                  <span class="link-collapse">退出登录</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <ul class="nav">
        <li class="nav-item active">
          <a href="<%=basePath%>Teacher/indexUrl">
            <i class="la la-dashboard"></i>
            <p>课程管理</p>
            <span class="badge badge-count">${fn:length(courses)}   </span>
          </a>
        </li>
        <li class="nav-item">
          <a href="#" onclick="getStudentManagementHtml()">
            <i class="la la-table"></i>
            <p>学生管理</p>
            <span class="badge badge-count">14</span>
          </a>
        </li>
        <li class="nav-item">
          <a href="forms.html">
            <i class="la la-keyboard-o"></i>
            <p>Api接口</p>
            <span class="badge badge-count">50</span>
          </a>
        </li>
        <li class="nav-item">
          <a href="https://icons8.com/line-awesome" target="_blank">
            <i class="la la-fonticons"></i>
            <p>Icons</p>
          </a>
        </li>
      </ul>
    </div>
  </div>
  <div class="main-panel">
    <div class="content" id="contentView" style="">
      <div class="card card-tasks p-1">
        <div class="card-body p-0">
          <ul class="breadcrumb ">
            <li class="breadcrumb-item active">全部课程</li>
            <li class="breadcrumb-item active"></li>
          </ul>
          <div class="row m-0 pb-1">
            <div class="col card-header p-0 col-sm-6 col-md-6col-lg-6">
              <div class="input-group">
                <input id="searchCoursesVal" placeholder="输入关键字搜索课程 ..." type="text" class="form-control input-lg"
                        onkeyup="searchCourses();">
                <span class="input-group-addon btn btn-default" onclick="searchCourses();">
                    <i class="la la-search search-icon"></i>&nbsp; 搜索 </span>
              </div>
            </div>
          </div>
          <div class="row m-0 p-1">
            <div class="btn-group" id="courseAges">
              <button class="btn btn-outline-dark dropdown-toggle p-1" data-toggle="dropdown"> 全部 </button>
              <div class="dropdown-menu">
                <a class="dropdown-item" href="#">2018</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">2017</a>
              </div>
            </div>
            <div class="col text-right p-0 m-0">
              <button type="button" class="btn btn-outline-success p-2 m-0"
                      data-target="#addCourseModal" data-toggle="modal" >添加课程</button>
            </div>
          </div>
          <div class="table-responsive">
            <table class="table table-hover table-bordered table-condensed">
              <thead >
              <tr>
                <th>年级
                </th>
                <th>
                  课程名称
                </th>
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
                      <button id="deleteCourse${item.id}" type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger"
                              onclick="deleteCourse('${item.id}');event.stopPropagation();">
                        <i class="la la-times" >删除</i>
                      </button>
                    </div>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
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
    <footer class="footer">
      <div class="container-fluid">
        <nav class="navbar navbar-default navbar-fixed-bottom"></nav>
        <div class="copyright ml-auto">Author binming mo posted the project to <i class="la la-heart heart text-danger"></i><a href="https://github.com/MoBinMing" target="_blank" title="Github">Github</a> in 2019 </div>
      </div>
    </footer>
  </div>
</div>
<%@ include file="../shared/teacher_Index_footer.jsp"%>
</body>
</html>
