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
  function addPracticesTest() {
    var name = $("#name").val();
    if (name == "") {
      $("#myAlert2").css("visibility", "visible");
      $("#myAlert2").css("display", "inline");
      return false;
    }
    return true;
  }
  //添加练习信息不全提示框
  $(function() {
    $('#addCourseModal').on('hidden.bs.modal', function() {
      $("#myAlert2").css("visibility", "hidden");
      $("#myAlert2").css("display", "none");
    });
  });
  $("inputCourse").keyup(function() {
    searchCourses();
  });

  function searchCourses() {
    var val = $("#ssearchCoursesVal").val();
    var coursesTbody = $("#coursesTbody");
    $.ajax({
      type: 'POST',
      url: '<%=basePath%>Teacher/ssearchCourses',
      data: 'val=' + val,
      dataType: "json",
      global: false,
      success: function(data) {
        //alert(data.tbody);
        //coursesTbody.append(data.tbody);
      }
    });
  };

  function searchPractices777() {
    var val = $("#searchPracticesVal").val();
    var text = $.ajax({
      url: "<%=basePath%>Teacher/searchPractices?val=" + val,
      async: false
    });
    alert(text);
  };
  $("input").keyup(function() {
    searchPractices();
  });

  function searchPractices() {
    var val = $("#searchPracticesVal").val();
    var practiceTbody = $("#practiceTbody");
    $.ajax({
      type: 'POST',
      url: '<%=basePath%>Teacher/searchPractices',
      data: 'val=' + val,
      dataType: "json",
      global: false,
      success: function(data) {
        alert(data.tbody);
        practiceTbody.append(data.tbody);
      }
    });
  };

  function deleteCourse(id) {
    var an = confirm("确定删除？");
    if (an == true) {
      location.href = "deleteCourse/" + id;
    } else {
      return false;
    }
  }

  function deletePractice(id) {
    var an = confirm("确定删除？");
    if (an == true) {
      location.href = "<%=basePath%>Teacher/deletePractice/" + id;
    } else {
      return false;
    }
  }

  function deleteQuestion(id) {
    var an = confirm("确定删除？");
    if (an == true) {
      location.href = "<%=basePath%>Teacher/deleteQuestion/" + id;
    } else {
      return false;
    }
  }

  function updateReady(id) {
    $.get("updateReady/" + id, function(data, status) {
      if (data.thisBody == "<%=basePath%>Login/LoginIndexUrl") {
        location.href = data.thisBody;
      } else {
        $("#tbody").html(data.thisBody);
      }
    });
  }

  function getStudentManagementHtml() {
    $.get("getStudentManagementHtml", function(data, status) {
      if (data.ok != "ok") {
        $("#contentView").html(data.thisBody);
      } else {
        $("#contentView").html(data.thisBody);
      }
    });
  }
  /* bootstrap开关控件，初始化 */
  function onLi() {
    $('#mySwitch input').bootstrapSwitch();
  }
</script>
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
        <nav id="navigations" class="breadcrumb m-1">
          <a class="breadcrumb-item" href="#">Home</a>
          <a class="breadcrumb-item" href="#">Library</a>
          <a class="breadcrumb-item" href="#">Data</a>
          <span class="breadcrumb-item active">Bootstrap</span>
        </nav>
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
                      <img src="<%=basePath%>images/nan.png" alt="Img Profile">
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
            <a class="dropdown-toggle profile-pic" data-toggle="dropdown" href="#" aria-expanded="false"> <img src="<%=basePath%>images/nan.png" alt="user-img" width="36" class="img-circle"><span>葛祥友</span> </a>
            <ul class="dropdown-menu dropdown-user">
              <li>
                <div class="user-box">
                  <div class="u-img"><img src="<%=basePath%>images/nan.png" alt="user"></div>
                  <div class="u-text">
                    <h4>葛祥友</h4>
                    <p class="text-muted">hello ：t@qq.com</p>
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
          <img src="<%=basePath%>images/nan.png">
        </div>
        <div class="info">
          <a class="" data-toggle="collapse" href="#collapseExample" aria-expanded="true">
              <span> 葛祥友 <span class="user-level">t@qq.com</span>
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
            <span class="badge badge-count">3</span>
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
      <div class="card card-tasks p-2">
        <div class="card-body ">
          <div class="row">
            <div class="col card-header p-0 ">
              <form class="card-title navbar-left navbar-form nav-search mr-md-3" action="">
                <div class="input-group">
                  <input id="ssearchCoursesVal" type="text" placeholder="输入关键字搜索 ..." class="form-control" onkeydown="if(event.keyCode==13){event.keyCode=0;event.returnValue=false;searchCourses();}">
                  <div class="input-group-append">
                      <span class="input-group-text">
                        <i class="la la-search search-icon"></i>
                      </span>
                  </div>
                </div>
              </form>
            </div>
            <div class="col text-right p-0">
              <button type="button" class="btn btn-success" data-target="#addCourseModal" data-toggle="modal"><i class="la la-plus-square">添加课程</i>  </button>
               </div>
          </div>
          <div class="table-responsive">
            <table class="table table-hover">
              <thead>
              <tr>
                <th > 课程: <div class="btn-group">
                  <button class="btn btn-outline-dark dropdown-toggle p-1" data-toggle="dropdown"> 2019 </button>
                  <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">2018</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">2017</a>
                  </div>
                </div>
                </th>
                <th>课程介绍</th>
                <th> 练习数</th>
                <th> 操作 </th>
              </tr>
              </thead>
              <tbody id="“coursesTbody”">
              <c:forEach var="item" items="${courses}">
                <tr>
                  <td><a href="<%=basePath%>Teacher/getPracticeByCourseId/" >${item.name}</a></td>
                  <td>${item.intro}</td>
                  <td>${item.practiceSize}</td>
                  <td class="td-actions text-left">
                    <div class="form-button-action">
                      <button type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger">
                        <i class="la la-times" onclick="deleteCourse('${item.id}')">删除</i>
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
            <form id="addCourses" name="addCourses" method="post" action="/Practice/Teacher/addCourses" class="form-horizontal" role="添加练习" onsubmit="return addPracticesTest();">
              <div class="modal-body" style="">
                <div class="form-group">
                  <label for="name">课程名称</label>
                  <div id="myAlert2" class="alert alert-warning" style="visibility: hidden; display: none;">
                    <a href="#" class="close" data-dismiss="alert">×</a>
                    <strong>提示！</strong>课程名称输入不完整！。 </div>
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
