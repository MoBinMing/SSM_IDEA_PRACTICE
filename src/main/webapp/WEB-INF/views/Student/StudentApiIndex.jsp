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
<body class="mb-0 cover" style=" background: #00000024;background-size: cover;" >
<%@ include file="navbar.jsp"%>
<div class="container pt-3 login-center" style="background: #00000024;">
  <ul class="breadcrumb">
    <li class="breadcrumb-item active">Api接口 /</li>
  </ul>

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
  <div class="container">
    <div class="row ">
      <div class="col-md-12 p-0">
        <ul class="nav nav-tabs">
          <li class="nav-item">
            <a class="nav-link active" href="" data-toggle="tab" data-target="#studentApi">
              学生端
              <span class="badge badge-primary badge-pill" >${fn:length(studentApis)}</span>
            </a>
          </li>
        </ul>
        <div class="tab-content mt-2">
          <div class="tab-pane fade show active" id="studentApi" role="tabpanel">
            <div class="row ">
              <div class="col-md-12">
                <div class="row m-0 p-3">
                  <div class="col card-header p-0 col-9 col-sm-6 col-md-12 col-lg-6">
                    <div class="input-group">
                      <input id="searchStudentApisVal" placeholder="Search API" type="text" class="form-control input-lg"
                             onkeyup="searchApis('student');">
                      <span class="input-group-addon btn btn-primary" onclick="searchApis('student');">
                          <i class="fa fa-search"></i>&nbsp;搜索</span>
                    </div>
                  </div>
                </div>
                <div class="container" id="studentApiContainer">
                  <c:forEach var="api" items="${studentApis}">
                    <div class="row mb-4" >
                      <div id="studentApiCol1${api.id}" class="col-md-7">
                        <div class="card">
                          <div class="row m-0 card-header">
                            <div class="col">
                              <font color="#212529">
                                <span style="background-color: rgba(0, 0, 0, 0.03);">${api.name} <span class="badge badge-light" style="">${api.type} </span></span>
                              </font>
                            </div>
                            <c:if test="${api.type=='POST'}">
                              <button onclick="$('#studentApiResult${api.id}').html('${api.requestData}')"> 请求的json </button>

                            </c:if>
                            <c:if test="${api.type=='GET'}">
                              <button onclick="$('#studentApiResult${api.id}').html('${api.requestData}')"> 请求的后缀 </button>

                            </c:if>
                            <button class="ml-2 mr-2" onclick="$('#studentApiResult${api.id}').html('${api.succeedHtml}')"> 成功示例 </button>

                            <button onclick="$('#studentApiResult${api.id}').html('${api.defeatedHtml}')"> 失败示例 </button>
                          </div>
                          <div class="card-body">
                            <blockquote class="blockquote mb-0">
                              <span class="row m-0 p-0">
                                <p class="col m-0 p-0" >url:</p>
                                <input value="复制链接" onclick="$('#studentUrl${api.id}').select();document.execCommand('Copy');" class="" type="button">
                              </span>

                              <input class="type=&quot;text&quot; w-100" size="30" id="studentUrl${api.id}"  value="${api.url}" >
                              <p class="m-0"> 简介： </p>
                              <footer class="blockquote-footer"><cite title="info"> ${api.info}</cite></footer>
                            </blockquote>
                          </div>
                        </div>
                      </div>
                      <div id="studentApiResult${api.id}" class="textarea col-md-5" contenteditable="true">
                        <br />
                      </div>
                    </div>
                  </c:forEach>
                </div>

              </div>
            </div>
          </div>
        </div>
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
<script type="text/javascript" src="<%=basePath%>scripts/teacherApiIndex.js"></script>
</body>
</html>