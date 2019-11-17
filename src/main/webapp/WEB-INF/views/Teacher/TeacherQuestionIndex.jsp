<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ include file="../shared/tags_bootstrap_v4.3.1.jsp"%>

<!DOCTYPE html>
<html >
<head>
	<title>题目管理</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="mb-0" style=" background: #00000024;background-size: cover;">
<%@ include file="navbar.jsp"%>
<div class="container pt-3 login-center" style="background: #00000024;">
	<ul class="breadcrumb ">
		<li class="breadcrumb-item"> <a href="<%=basePath%>Teacher/indexUrl">全部课程</a> </li>
		<li class="breadcrumb-item"> <a href="<%=basePath%>Teacher/getPracticeByCourseId/${course.id}">${course.age}级&nbsp;${course.name}</a></li>
		<li class="breadcrumb-item active">${practice.name}</li>
	</ul>
	<div class="row m-0 pb-2">
		<div class="col card-header p-0 col-9 col-sm-6 col-md-6 col-lg-6">
			<div class="input-group">
				<input id="searchPracticesVal" placeholder="输入关键字搜索练习 ..." type="text" class="form-control input-lg" onkeyup="searchQuestions();">
				<span class="input-group-addon btn btn-primary" onclick="searchQuestions();">
            <i class="fa fa-search"></i>&nbsp;搜索</span>
			</div>
		</div>
		<div class="col text-right p-0 m-0 col-3  col-sm-6 col-md-6 col-lg-6">
			<button type="button" class="btn btn-primary " data-target="#addPracticesModal" data-toggle="modal">添加练习</button>
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
	<div class="main-panel">
		<div class="content" id="contentView" style="">
			<div class="card card-tasks p-1" style="background: #6c757d;">
				<div class="card-body p-0" >
					<div class="card card-tasks p-1" style="background: #bcbcbc;">
						<div class="card-body p-0">
							<div class="row">
								<c:forEach var="question" items="${questions}">
									<div class="col-xs-12	col-sm-12	col-md-6 col-lg-6 mb-3">
										<div class="col card text-left">
											<div class="row card-header mt-0">
												<ul class="nav nav-tabs card-header-tabs">
													<li class="nav-item">
														<a class="nav-link active" data-toggle="tab" data-target="#questionTab${question.id}">题目</a>
													</li>
													<li class="nav-item">
														<a class="nav-link" href="" data-toggle="tab" data-target="#editQuestionTab${question.id}">修改</a>
													</li>
												</ul>
												<span class="col pr-0" style="text-align: end;">
													<button class="button btn-danger" type="button" onclick="deleteQuestion('${question.id}');">
														<i class="fa fa-trash fa-1x" aria-hidden="true" >&nbsp;删除</i>
													</button>

												</span>
											</div>
											<div class="card-body">
												<div class="tab-content mt-2">
													<div class="tab-pane fade active show" id="questionTab${question.id}" role="tabpanel">
														<div class="m-2"><b> ${question.number}、${question.content} </b> <span class="badge badge-light"> ${item.strQuestionType}</span> </div>
														<blockquote class="blockquote mb-0">
															<ul>
																<c:forEach var="option" items="${question.options}">
																	<li>
																		<c:if test="${option.isAnswer==1}">
																			<u class="m-0" style="color: #31ff00;height:2px;"> ${option.label}、${option.content} </u>
																		</c:if>
																		<c:if test="${option.isAnswer==0}"> ${option.label}、${option.content} </c:if>
																	</li>
																</c:forEach>
															</ul>
															<footer class="blockquote-footer">正确答案： <c:forEach var="option" items="${question.options}">
																<c:if test="${option.isAnswer==1}">${option.label}</c:if>
															</c:forEach>
															</footer>
															<footer class="blockquote-footer"> 题目解析：<br>
																<cite title="Source Title"> ${question.analysis} </cite>
															</footer>
														</blockquote>
													</div>
													<div class="tab-pane fade " id="editQuestionTab${question.id}" role="tabpanel">
														<form role="form" id="updateForm${question.id}">
															<input name="id" hidden value="${question.id}">
															<input name="number" hidden value="${question.number}">
															<div class="form-group p-1">
																<label class="control-label p-0 m-0 form-control-static"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题号：${question.number} </label>
															</div>
															<div class="form-group p-1">
																<div class="row m-0">
																	<label for="editQuestionContent${question.id}" class="p-0 m-0 control-label form-control-static"> &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;题目： </label>
																	<textarea id="editQuestionContent${question.id}" name="content" class="col w-100 p-0" placeholder="请输入题目" >${question.content}</textarea>
																</div>
															</div>
															<div class="form-group p-1">
																<div class="row m-0">
																	<label for="editQuestionType${question.id}" class="p-0 m-0 control-label form-control-static"> 题目类型： </label>
																	<select id="editQuestionType${question.id}" name="questionType" class="col form-control input-sm w-100"
																			onchange="editSelectOnChange('${question.id}')">
																			<option data-amount="<div id='radio_${question.id}' class='radio'>
																							<c:forEach var="option" items="${question.options}">
																								<li id='optionItem${question.id}${option.id}'>
																									<div class='row m-0 p-0 ' style='align-items: center;'>
																										<input class='col p-0 m-0 ' placeholder='请输入选项内容' value='${option.content}'>
																										<label class='ml-2 mr-2 mb-0'>
																											<input type='radio' name='optradio' <c:if test="${option.isAnswer==1}"> checked='checked'</c:if>>设为答案
																										</label>
																										<span class='p-0 m-0  text-right'>
																										  <i class='fa fa-trash-o' aria-hidden='true' onclick='deleteOption(<c:out value="\"#optionItem${question.id}${option.id}\""></c:out>);'>&nbsp;删除</i>
																										</span>
																								</div>
																								</li>
																							</c:forEach>
																							</div>"
																					<c:if test="${question.questionType == 0}">
																						selected
																					</c:if>
																					value='单项选择'
																					>单项选择
																			</option>
																			<option data-amount="<div id='checkBox_${question.id}' >
																									<c:forEach var="option" items="${question.options}">
																									<li id='optionItem${question.id}${option.id}'>
																										<div class='row m-0 p-0 ' style='align-items: center;'>
																											<input id='content${question.id}${option.id}' name='content' class='col p-0 m-0 ' placeholder='请输入选项内容' value='${option.content}'>
																											<label class='ml-2 mr-2 mb-0 pb-0'>
																												<label class='mb-0'><input type='checkbox' <c:if test="${option.isAnswer==1}"> checked='checked'</c:if>>答案</label>
																											</label>
																											<span class='p-0 m-0  text-right'>
																												  <i class='fa fa-trash-o' aria-hidden='true' onclick='deleteOption(<c:out value="\"#optionItem${question.id}${option.id}\""></c:out>);'>&nbsp;删除</i>
																												</span>
																										</div>
																									</li>
																									</c:forEach>
																							</div>"
																					<c:if test="${question.questionType==1}">
																					selected
																					</c:if>
																					value='多项选择'
																					>多项选择
																			</option>
																			<option data-amount="<div id='radio2_${question.id}' class='radio'>
																							<c:forEach var="option" items="${question.options}">
																								<li id='optionItem${question.id}${option.id}'>
																									<div class='row m-0 p-0 ' style='align-items: center;'>
																										<input class='col p-0 m-0 ' placeholder='请输入选项内容' value='${option.content}'>
																										<label class='ml-2 mr-2 mb-0'>
																											<input type='radio' name='optradio' <c:if test="${option.isAnswer==1}"> checked='checked'</c:if>>设为答案
																										</label>
																										<span class='p-0 m-0  text-right'>
																										  <i class='fa fa-trash-o' aria-hidden='true' onclick='deleteOption(<c:out value="\"#optionItem${question.id}${option.id}\""></c:out>);'>&nbsp;删除</i>
																										</span>
																								</div>
																								</li>
																							</c:forEach>
																							</div>" <c:if test="${question.questionType==2}">
																				selected
																				</c:if>
																					value='判断题'
																				>
																				判断题
																			</option>
																	</select>
																</div>
															</div>
															<div class="form-group p-1">
																<div class="row m-0 p-0">
																	<label class="p-0 m-0 control-label form-control-static"> 设置选项： </label>
																	<span class="col w-100 p-0 m-0  text-right">
																		<i class="fa fa-plus fa-1x" aria-hidden="true" onclick="addOption('${question.id}');">&nbsp;添加选项</i>
																	</span>
																</div>
															</div>
															<div class="form-group p-1">
																<ol id="editQuestion${question.id}" class="" type="A">
																	<c:if test="${question.questionType==0}">

																		<div id="radio_${question.id}" class="radio" >
																			<c:forEach var="option" items="${question.options}">
																				<li id="optionItem${question.id}${option.id}">
																					<div class="row m-0 p-0 " style="align-items: center;">
																						<input class="col p-0 m-0 " name="optionContent${question.id}${option.id}" placeholder="请输入选项内容" value="${option.content}">
																						<label class="ml-2 mr-2 mb-0">
																							<input type="radio" name="optionIsAnswer${question.id}${option.id}"  <c:if test="${option.isAnswer==1}"> checked="checked"</c:if>>设为答案
																						</label>
																						<span class="p-0 m-0  text-right">
																					  <i class="fa fa-trash-o" aria-hidden="true" onclick="deleteOption('#optionItem${question.id}${option.id}');">&nbsp;删除</i>
																					</span>
																					</div>
																				</li>
																			</c:forEach>
																		</div>
																	</c:if>
																	<c:if test="${question.questionType==1}">
																		<div id="checkBox_${question.id}" >
																			<c:forEach var="option" items="${question.options}">
																				<li id="optionItem${question.id}${option.id}">
																					<div class="row m-0 p-0 " style="align-items: center;">
																						<input id="content${question.id}${option.id}" name="optionContent${question.id}${option.id}" class="col p-0 m-0 " placeholder="请输入选项内容" value="${option.content}">
																						<label class="ml-2 mr-2 mb-0 pb-0">
																							<label class="mb-0"><input type="checkbox" name="optionIsAnswer${question.id}${option.id}" value="" <c:if test="${option.isAnswer==1}"> checked="checked"</c:if>>答案</label>
																						</label>
																						<span class="p-0 m-0  text-right">
																					  <i class="fa fa-trash-o" aria-hidden="true" onclick="deleteOption('#optionItem${question.id}${option.id}');">&nbsp;删除</i>
																					</span>
																					</div>
																				</li>
																			</c:forEach>
																		</div>
																	</c:if>
																	<c:if test="${question.questionType==2}">
																		<div id="radio2_${question.id}" class="radio" >
																			<c:forEach var="option" items="${question.options}">
																				<li id="optionItem${question.id}${option.id}">
																					<div class="row m-0 p-0 " style="align-items: center;">
																						<input class="col p-0 m-0 " name="optionContent${question.id}${option.id}" placeholder="请输入选项内容" value="${option.content}">
																						<label class="ml-2 mr-2 mb-0">
																							<input type="radio" name="optionIsAnswer${question.id}${option.id}"  <c:if test="${option.isAnswer==1}"> checked="checked"</c:if>>设为答案
																						</label>
																						<span class="p-0 m-0  text-right">
																					  <i class="fa fa-trash-o" aria-hidden="true" onclick="deleteOption('#optionItem${question.id}${option.id}');">&nbsp;删除</i>
																					</span>
																					</div>
																				</li>
																			</c:forEach>
																		</div>
																	</c:if>
																</ol>
															</div>
															<button class="button btn-success" type="button" onclick="updateQuestion('#updateForm${question.id}');">确认更改</button>
														</form>
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
		</div>
	</div>
</div>
<div class="fixed-bottom" id="hint">

</div>
<script type="text/javascript" src="<%=basePath%>scripts/teacherQuestionIndex.js"></script>
<%--<pingendo onclick="window.open('https://pingendo.com/', '_blank')" style="cursor:pointer;position: fixed;bottom: 20px;right:20px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:220px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white">Made with Pingendo Free&nbsp;&nbsp;<img src="https://pingendo.com/site-assets/Pingendo_logo_big.png" class="d-block" alt="Pingendo logo" height="16"></pingendo>--%>

</body>
</html>
