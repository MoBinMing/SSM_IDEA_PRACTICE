<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<nav class="navbar navbar-expand-lg navbar-light" style="background: #0000004f;;">
    <div class="container">
        <button class="navbar-toggler navbar-toggler-right border-0 bg-light" type="button" data-toggle="collapse" data-target="#navbar5" style="">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand text-white" href="javascript:location.reload();" >
            <i class="fa d-inline fa-lg fa-circle-o"></i>
            <b> 练习题管理后台</b>
        </a>
        <div class="collapse navbar-collapse" id="navbar5">
            <c:if test="${user!=null}">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item"> <a class="nav-link text-white" href="<%=basePath%>Teacher/indexUrl">课程管理</a> </li>
                    <li class="nav-item"> <a class="nav-link text-white" href="#">学生管理</a> </li>
                    <li class="nav-item"> <a class="nav-link text-white" href="<%=basePath%>ApiIndex/teacherApiIndexUrl">Api</a> </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <div class="row m-0" style="align-items: center;">
                            <img class="img-fluid d-block rounded-circle"  src="<%=basePath%>userImg/${user.iphone}.jpg"
                                 alt="头像" onerror="src='<%=basePath%>images/${user.gender}.png'"
                                 style="width: 40px;height: 40px;background-color: white;">
                            <div class="col m-0 text-white" style="align-items:center;">
                                欢迎回来：<br>
                                ${user.name}
                            </div>
                        </div>
                    </li>
                </ul>
                <a class="btn navbar-btn ml-md-2 btn-danger"
                   href="<%=basePath%>Login/Exit"><i class="fa fa-sign-out" aria-hidden="true">退出登录</i></a>
            </c:if>
        </div>
    </div>
</nav>
