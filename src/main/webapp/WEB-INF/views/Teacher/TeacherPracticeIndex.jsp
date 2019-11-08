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

  .SwitchIcon {
  }

  .toggle-button {
    display: none;
  }

  .button-label {
    position: relative;
    display: inline-block;
    width: 80px;
    height: 30px;
    background-color: #ccc;
    box-shadow: #ccc 0px 0px 0px 2px;
    border-radius: 30px;
    overflow: hidden;
  }

  .circle {
    position: absolute;
    top: 0;
    left: 0;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: #fff;
  }

  .button-label .text {
    line-height: 30px;
    font-size: 18px;
    text-shadow: 0 0 2px #ddd;
  }

  .on {
    color: #fff;
    display: none;
    text-indent: -45px;
  }

  .off {
    color: #fff;
    display: inline-block;
    text-indent: 34px;
  }

  .button-label .circle {
    left: 0;
    transition: all 0.3s;
  }

  .toggle-button:checked + label.button-label .circle {
    left: 50px;
  }

  .toggle-button:checked + label.button-label .on {
    display: inline-block;
  }

  .toggle-button:checked + label.button-label .off {
    display: none;
  }

  .toggle-button:checked + label.button-label {
    background-color: #19e236;
  }

  .div {
    height: 20px;
    width: 30px;
    background: #51ccee;
  }

</style>
<script type="text/javascript">
  function SwitchClick(dom) {
    var checked = dom.checked;
    var id=dom.id;
    if(checked) {
      $.get(getRootPath()+"Teacher/updatePracticeReadyToOn?pid="+id,
              function(data,status){
                  if(data.updateOk){
                    alert("开放成功");
                    location.reload();
                  }else{
                    alert("开放失败！");
                  }
              });
    } else {
      $.get(getRootPath()+"Teacher/updatePracticeReadyToOff?pid="+id,function(data,status){
        if(data.updateOk){
          alert("停止开放成功");
          location.reload();
        }else{
          alert("停止开放失败！");
        }
      });
    }
  }
</script>
<script type="text/javascript" src="<%=basePath%>scripts/teacherIndex.js"></script>
</head>
<body id="body" onload="onLi()">
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
                      <img src="<%=basePath%>images/${Teacher.gender}.png" alt="Img Profile">
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

              <img src="<%=basePath%>userImg/${Teacher.iphone}.jpg" alt="user-img" width="36" class="img-circle"
                   οnerrοr="this.src='<%=basePath%>images/${Teacher.gender}.png'">
              <span>葛祥友</span>
            </a>
            <ul class="dropdown-menu dropdown-user">
              <li>
                <div class="user-box">
                  <div class="u-img">
                    <img src="<%=basePath%>userImg/${Teacher.iphone}.jpg" alt="user"
                         οnerrοr="this.src='<%=basePath%>images/${Teacher.gender}.png'">
                  </div>
                  <div class="u-text">
                    <h4>${Teacher.name}</h4>
                    <p class="text-muted">hello ${Teacher.email}</p>
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
          <img src="<%=basePath%>userImg/${Teacher.iphone}.jpg"
               οnerrοr="this.src='<%=basePath%>images/${Teacher.gender}.png'">
        </div>
        <div class="info">
          <a class="" data-toggle="collapse" href="#collapseExample" aria-expanded="true">
              <span>${Teacher.name}<span class="user-level">${Teacher.email}</span>
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
    <div class="content" id="contentView" style="" >
      <div class="card card-tasks p-2">
        <div class="card-body ">
          <ul class="breadcrumb p-1 m-0">
            <li class="breadcrumb-item"> <a href="<%=basePath%>Teacher/indexUrl">全部课程</a> </li>
            <li class="breadcrumb-item active"><a href="<%=basePath%>Teacher/getPracticeByCourseId/${course.id}">${course.name}</a></li>
          </ul>
          <div class="row m-0 pb-2">
            <div class=" card-header p-0 ">
              <div class="input-group">
                <input id="searchCoursesVal" placeholder="请输入关键字" type="text" class="form-control input-lg ">
                <span class="input-group-addon btn btn-default " onclick="searchCourses();">
                    <i class="la la-search search-icon"></i></span>
              </div>
            </div>
            <div class="col text-right p-0">
              <button type="button" class="btn btn-outline-success" data-target="#addCourseModal" data-toggle="modal">添加课程 </button>
            </div>
          </div>
          <div class="table-responsive">
            <table class="table table-hover table-bordered ">
              <thead>
              <tr>
                <th> 练习名称 </th>
                <th> 题目数 </th>
                <th> 上传时间 </th>
                <th> 是否开放 </th>
                <th class="text-center"> 操作 </th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td><a href="<%=basePath%>Teacher/managingCurrentExercises/25" class="">锁定</a><br>1213131313468<br></td>
                <td>Planning new project structure</td>
                <td>Planning new project structure</td>
                <td>
                  <div class="SwitchIcon m-0">
                    <div class="toggle-button-wrapper">
                      <input type="checkbox" id="toggle-button" name="switch" οnclick="SwitchClick()" checked="" style="">
                      <label for="toggle-button" class="button-label">
                        <span class="circle"></span>
                        <span class="text on">ON</span>
                        <span class="text off" style="">OFF</span>
                      </label>
                    </div>
                  </div>
                </td>
                <td class="td-actions text-right">
                  <div class="form-button-action">
                    <button type="button" data-toggle="tooltip" title="修改练习" class="btn btn-link <btn-simple-primary">
                      <i class="la la-edit">编辑</i></button>
                    <button type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger">
                      <i class="la la-times">删除</i>
                    </button>
                  </div>
                </td>
              </tr>
              <tr></tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal" id="addPracticesModal">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title bg-light">添加练习</h5> <button type="button" class="close" data-dismiss="modal"> <span>×</span> </button>
            </div>
            <form id="addPractices" name="addPractices" method="post" action="<%=basePath%>Teacher/addPractices" class="form-horizontal" role="添加练习" onsubmit="return addPracticesTest();">
              <div class="modal-body" style="">
                <div class="form-group">
                  <label for="name">练习名称</label>
                  <div id="myAlert2" class="alert alert-warning" style="visibility: hidden; display: none;">
                    <a href="#" class="close" data-dismiss="alert">×</a>
                    <strong>提示！</strong>练习名称输入不完整！。 </div> <input id="name" name="name" type="text" class="form-control" placeholder="请输入练习名称">
                </div>
                <div class="form-group">
                  <label for="outlines">练习描述</label>
                  <textarea id="outlines" name="outlines" class="form-control" placeholder="请输入练习描述" rows="3"></textarea>
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
