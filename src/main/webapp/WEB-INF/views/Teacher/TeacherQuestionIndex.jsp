<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ include file="../shared/teacher_Index_tags.jsp"%>

<!DOCTYPE html>
<html >
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>教师主页</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no" name="viewport">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/ready.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/demo.css">
</head>

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
											<img src="<%=basePath%>images/${user.iphone}.png" alt="Img Profile"
												 onerror="src='<%=basePath%>images/男.png'">
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
		<div class="content" id="contentView" style="" >
			<div class="card card-tasks p-1">
				<div class="card-body p-0">
					<ul class="breadcrumb ">
						<li class="breadcrumb-item"> <a href="<%=basePath%>Teacher/indexUrl">全部课程</a> </li>
						<li class="breadcrumb-item"> <a href="<%=basePath%>Teacher/getPracticeByCourseId/${course.id}">${course.age}级&nbsp;${course.name}</a></li>
						<li class="breadcrumb-item active">${practice.name}</li>
					</ul>
					<div class="row m-0 pb-1">
						<div class="card-header p-0 m-0 col-sm-9 col-md-6 col-lg-6">
							<div class="input-group">
								<input id="searchPracticesVal" placeholder="输入关键字搜索练习 ..." type="text" class="form-control input-lg"
									   onkeyup="searchPractices();">
								<span class="input-group-addon btn btn-default" onclick="searchPractices();">
                    <i class="la la-search search-icon"></i>&nbsp; 搜索 </span>
							</div>
						</div>
						<div class="text-right p-0 m-0 col-sm-3 col-md-6 col-lg-6">
							<button type="button" class="btn btn-outline-success p-2 ml-1" data-target="#addPracticesModal" data-toggle="modal" >添加练习</button>
						</div>
					</div>
					<div class="card card-tasks p-1">
						<div class="card-body p-0">
							<div class="row">
								<c:forEach var="item" items="${questions}">
								<div class="col-xs-12	col-sm-12	col-md-6	col-lg-6">
									<div class="col card text-left">
										<div class="card-header mt-0">
											<ul class="nav nav-tabs card-header-tabs">
												<li class="nav-item" >
													<a class="nav-link active" data-toggle="tab" data-target="#question${item.id}">题目</a>
												</li>
												<li class="nav-item">
													<a class="nav-link" href="" data-toggle="tab" data-target="#editQuestion${item.id}">修改</a>
												</li>
												<li class="nav-item">
													<a class="nav-link disabled" data-toggle="tab" data-target="#tabthree">Tab 3</a>
												</li>
											</ul>
										</div>
										<div class="card-body">
											<div class="tab-content mt-2">
												<div class="tab-pane fade active show" id="question${item.id}" role="tabpanel">
													<div class="m-2"><b>  ${item.number}、${item.content} </b> <span class="badge badge-light"> ${item.strQuestionType}</span> </div>
													<blockquote class="blockquote mb-0">
														<Ul>
															<c:forEach var="option" items="${item.options}">
															<li>
																<c:if test="${option.isAnswer==1}">
																	<u class="m-0" style="color: #31ff00;height:2px;">
																			${option.label}、${option.content}
																	</u>
																</c:if>
																<c:if test="${option.isAnswer==0}">
																	${option.label}、${option.content}
																</c:if>
															</li>
															</c:forEach>
														</Ul>
														<footer class="blockquote-footer">正确答案：
															<c:forEach var="option" items="${item.options}">
																<c:if test="${option.isAnswer==1}">${option.label}</c:if>
															</c:forEach>
														</footer>
														<footer class="blockquote-footer">
																题目解析：<br>
																<cite title="Source Title">
																		${item.analysis}
																</cite>
														</footer>
													</blockquote>
												</div>
												<div class="tab-pane fade " id="editQuestion${item.id}" role="tabpanel">
													<form role="form">
														<div class="form-group p-1">
															<label class="control-label p-0 m-0 form-control-static"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题号：${item.number} </label>
														</div>
														<div class="form-group p-1">
															<div class="row m-0">
																<label for="editQuestionContent" class="p-0 m-0 control-label form-control-static"> &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;题目： </label>
																<textarea id="editQuestionContent" class="col w-100 p-0" placeholder="请输入题目"> ${item.content}</textarea>
															</div>
														</div>
														<div class="form-group p-1">
															<div class="row m-0">
																<label for="editQuestionType" class="p-0 m-0 control-label form-control-static"> 题目类型： </label>
																<select id="editQuestionType" class="col form-control input-sm w-100">
																	<c:if test="${item.questionType==0}">
																		<option value="0">单项选择</option>
																		<option value="1">多项选择</option>
																		<option value="2">不定项选择</option>
																		<option value="3">判断题</option>
																	</c:if>
																	<c:if test="${item.questionType==1}">
																		<option value="1">多项选择</option>
																		<option value="2">不定项选择</option>
																		<option value="3">判断题</option>
																		<option value="0">单项选择</option>
																	</c:if>
																	<c:if test="${item.questionType==2}">
																		<option value="2">不定项选择</option>
																		<option value="0">单项选择</option>
																		<option value="1">多项选择</option>
																		<option value="3">判断题</option>
																	</c:if>
																	<c:if test="${item.questionType==3}">
																		<option value="3">判断题</option>
																		<option value="0">单项选择</option>
																		<option value="1">多项选择</option>
																		<option value="2">不定项选择</option>
																	</c:if>
																</select>
															</div>
														</div>
														<div class="form-group p-1">
															<div class="row m-0 p-0">
																<label class="p-0 m-0 control-label form-control-static"> 设置选项： </label>
																<span class="col w-100 p-0 m-0  text-right"><i class="la la-plus-square m-2">添加选项</i></span>

																<div class="col-md-12">

																	<ol class="" type="A">
																		<li><input class="p-0" placeholder="请输入选项名称">
																			<div class="radio">
																				<label><input type="radio" name="optradio">Option 1</label>
																			</div>
																			<i class="m-1 la la-trash-alt">
																			</i>
																		</li>
																	</ol>
																</div>
															</div>
														</div>
													</form>
												</div>
												<div class="tab-pane fade" id="tabthree" role="tabpanel">

												</div>
											</div>
										</div>
									</div>
								</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
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
