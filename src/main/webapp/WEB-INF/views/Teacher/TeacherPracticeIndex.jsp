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
<%@ include file="../shared/bootstrap-switch_3.3.4.jsp"%>

<script type="text/javascript" src="<%=basePath%>scripts/teacherPracticeIndex.js"></script>
</head>
<style type="text/css">
  .login-center {
    min-height: calc(100vh - 64px);
  }
</style>

<body onload="onLi()" class="mb-0" style=" background: #00000024;background-size: cover;" >
<%@ include file="../shared/navbar.jsp"%>
<div class="container pt-3 login-center" style="background: #00000024;">
  <ul class="breadcrumb ">
    <li class="breadcrumb-item"> <a href="<%=basePath%>Teacher/indexUrl">全部课程</a> </li>
    <li class="breadcrumb-item active ">${course.age}级&nbsp;${course.name}</li>
  </ul>
  <div class="row m-0 pb-2">
    <div class="col card-header p-0 col-9 col-sm-6 col-md-6 col-lg-6">
      <div class="input-group">
        <input id="searchPracticesVal" placeholder="输入关键字搜索练习 ..." type="text" class="form-control input-lg" onkeyup="searchPractices();">
        <span class="input-group-addon btn btn-primary" onclick="searchPractices();">
            <i class="fa fa-search"></i>&nbsp;搜索</span>
      </div>
    </div>
    <div class="col text-right p-0 m-0 col-3  col-sm-6 col-md-6 col-lg-6" >
      <button type="button" class="btn btn-primary " data-target="#addPracticesModal" data-toggle="modal" >添加练习</button>
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
                <th> 练习名称 </th>
                <th> 题目数 </th>
                <th> 创建时间 </th>
                <th> 是否开放 </th>
                <th> 编辑练习 </th>
                <th> 删除练习 </th>
              </tr>
              </thead>
              <tbody id="practicesTbody">
              <c:forEach var="item" items="${practices}">
<%--                onclick="location.href='<%=basePath%>Teacher/QuestionForPractice/${item.id}';"--%>
                <tr>
                  <td><a href="<%=basePath%>Teacher/QuestionForPractice/${item.id}" class="">${item.name}</a><br>${item.outlines}<br></td>
                  <td>${item.questionCount}</td>
                  <td>${item.strDate}</td>
                  <td>
                    <div class="SwitchIcon p-0 m-0" >
                      <div class="toggle-button-wrapper">
                        <input type="checkbox" id="practice${item.id}" class="toggle-button" name="switch"

                        <c:if test="${item.isReady==1}">
                               checked="checked"
                        </c:if>
                               onclick="event.stopPropagation();SwitchClick(this);">
                        <label for="practice${item.id}" class="button-label" >
                          <span class="circle"></span>
                          <span class="text on">ON</span>
                          <span class="text off">OFF</span>
                        </label>
                      </div>
                    </div>
                  </td>
                  <td class="text-center">
                    <div class="form-button-action">
                      <button type="button" data-toggle="tooltip" title="修改练习" class="btn btn-link <btn-simple-primary"
                              onclick="showUpdatePracticeModal('${item.id}','${item.name}','${item.outlines}');
                                      event.stopPropagation();">
                        <i class="fa fa-pencil-square-o" aria-hidden="true">&nbsp;编辑</i>
                      </button>
                    </div>
                  </td>
                  <td>
                    <div class="form-button-action">
                      <button type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger"
                              onclick="deletePractice('${item.id}');event.stopPropagation();">
                        <i class="fa fa-trash-o" aria-hidden="true">&nbsp;删除</i>
                      </button>
                    </div>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
  </div>
  <div class="modal" id="addPracticesModal">
    <div class="modal-dialog" role="document">
      <div class="modal-content text-left">
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
  <div class="modal" id="updatePracticeModal">
    <div class="modal-dialog" role="document">
      <div class="modal-content text-left">
        <div class="modal-header">
          <h5 class="modal-title bg-light">编辑练习</h5> <button type="button" class="close" data-dismiss="modal"> <span>×</span> </button>
        </div>
        <form id="updatePractice" name="updatePractice" method="post" action="<%=basePath%>Teacher/updatePractice" class="form-horizontal" role="编辑练习" onsubmit="return updatePractice();">
          <div class="modal-body" style="">
            <div class="form-group" style="display: none;">
              <label for="practiceId">练习id</label>
              <input id="practiceId" name="id" type="text" class="form-control" placeholder="">
            </div>
            <div class="form-group">
              <label for="practiceName">练习名称</label>
              <div id="updatePracticeAlert" class="alert alert-warning" style="visibility: hidden; display: none;">
                <a href="#" class="close" data-dismiss="alert">×</a>
                <strong>提示！</strong>练习名称输入不完整！。 </div> <input id="practiceName" name="name" type="text" class="form-control" placeholder="请输入练习名称">
            </div>
            <div class="form-group">
              <label for="practiceOutlines">练习描述</label>
              <textarea id="practiceOutlines" name="outlines" class="form-control" placeholder="请输入练习描述" rows="3"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >确认更新</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<pingendo onclick="window.open('https://pingendo.com/', '_blank')" style="cursor:pointer;position: fixed;bottom: 20px;right:20px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:220px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white">Made with Pingendo Free&nbsp;&nbsp;<img src="https://pingendo.com/site-assets/Pingendo_logo_big.png" class="d-block" alt="Pingendo logo" height="16"></pingendo>
</body>
</html>

