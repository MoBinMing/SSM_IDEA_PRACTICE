<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../shared/tags_bootstrap_v4.1.3.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>注册页面</title>
<script type="text/javascript">
	//页面加载完成后
	$(document).ready(function() {
		//邮箱输入框焦点失去时msg_email
		$('input').blur(function() {
			var thisvar = $(this).val();//获取密码框的值
			var idd = $(this).attr('id');
			var divid = "msg_" + idd.toString();
			if (thisvar == "") {
				document.getElementById(divid).innerHTML = "注册信息输入不完整";
			} else {
				document.getElementById(divid).innerHTML = "<br/>";
			}

		});
	});
	function checkfunction() {
		var email = $('#email').val();
		var password = $('#password').val();
		var password2 = $('#password2').val();
		var name = $('#name').val();
		var studentId = $('#studentId').val();
		var iphone = $('#iphone').val();
		var ss = 0;
		if (email == "") {
			document.getElementById("msg_email").innerHTML = "请输入邮箱";//在div显示错误信息
			ss = 1;
		}
		if (password == "") {
			document.getElementById("msg_password").innerHTML = "请输入密码";//在div显示错误信息
			ss = 1;
		}
		if (password2 == "") {
			document.getElementById("msg_password2").innerHTML = "请确认输入密码";//在div显示错误信息
			ss = 1;
		}
		if (password2 != password) {
			document.getElementById("msg_password2").innerHTML = "输入密码密码不一致";//在div显示错误信息
			ss = 1;
		}
		if (name == "") {
			document.getElementById("msg_name").innerHTML = "请输入姓名";//在div显示错误信息
			ss = 1;
		}
		if (studentId == "") {
			document.getElementById("msg_studentId").innerHTML = "请输入学号";//在div显示错误信息
			ss = 1;
		}
		if (iphone == "") {
			document.getElementById("msg_iphone").innerHTML = "请输入手机号";//在div显示错误信息
			ss = 1;
		}
		if (iphone.length != 11) {
			document.getElementById("msg_iphone").innerHTML = "手机号格式不正确";//在div显示错误信息
			ss = 1;
		}
		if (ss == 1) {
			return false;
		}

		$.ajax({
			type : 'POST',
			url : '/Practice/register/ealidationStudentEmail',
			data : {
				email : email,
				iphone : iphone,
				studentId : studentId
			},
			dataType : 'json',
			async : false, // ----------（加个它，能改变变量的值）      
			success : function(data) {
				//console.log(data.status);
				if (data.code == 1) {
					document.getElementById('form1').submit();
					/* {# window.location.href='/' #} */
				} else if (data.code == 0) {
					alert(data.info);
				}
			}
		});
		/* $.post('App:/Practice/register/ealidationEmail' , {
		                "email": email
		            }, function (data) {
		                if (data.code == 1) {
		                    alert('邮箱可用');
		                    document.getElementById('form1').submit()
		                     {# window.location.href='/' #}
		                }else if (data.code == 0) {
		                    alert("邮箱已注册")
		                }
		}) */
	}
	function teacherCheckfunction() {
		var email = $('#teacherEmail').val();
		var password = $('#teacherPassword').val();
		var password2 = $('#teacherPassword2').val();
		var name = $('#teacherName').val();
		var teacherId = $('#teacherId').val();
		var iphone = $('#teacherIphone').val();
		var ss = 0;
		if (email == "") {
			document.getElementById("msg_teacherEmail").innerHTML = "请输入邮箱";//在div显示错误信息
			ss = 1;
		}
		if (password == "") {
			document.getElementById("msg_teacherPassword").innerHTML = "请输入密码";//在div显示错误信息
			ss = 1;
		}
		if (password2 == "") {
			document.getElementById("msg_teacherPassword2").innerHTML = "请确认输入密码";//在div显示错误信息
			ss = 1;
		}
		if (password2 != password) {
			document.getElementById("msg_teacherPassword2").innerHTML = "输入密码密码不一致";//在div显示错误信息
			ss = 1;
		}
		if (name == "") {
			document.getElementById("msg_teacherName").innerHTML = "请输入姓名";//在div显示错误信息
			ss = 1;
		}
		if (teacherId == "") {
			document.getElementById("msg_teacherId").innerHTML = "请输入学号";//在div显示错误信息
			ss = 1;
		}
		if (iphone == "") {
			document.getElementById("msg_teacherIphone").innerHTML = "请输入手机号";//在div显示错误信息
			ss = 1;
		}
		if (iphone.length != 11) {
			document.getElementById("msg_teacherIphone").innerHTML = "手机号格式不正确";//在div显示错误信息
			ss = 1;
		}
		if (ss == 1) {
			return false;
		}
		$.ajax({
			type : 'POST',
			url : '/Practice/register/ealidationTeacherEmail',
			data : {
				email : email,
				iphone : iphone,
				teacherId : teacherId
			},
			dataType : 'json',
			async : false, // ----------（加个它，能改变变量的值）      
			success : function(data) {
				//console.log(data.status);
				if (data.code == 1) {
					document.getElementById('teacherForm').submit();
					/* {# window.location.href='/' #} */
				} else if (data.code == 0) {
					alert(data.info);
				}
			}
		});
		/* $.post('App:/Practice/register/ealidationEmail' , {
		                "email": email
		            }, function (data) {
		                if (data.code == 1) {
		                    alert('邮箱可用');
		                    document.getElementById('form1').submit()
		                     {# window.location.href='/' #}
		                }else if (data.code == 0) {
		                    alert("邮箱已注册")
		                }
		}) */
	}
	//密码1提示
	function checkpwdStudentPassword() {
		var p2 = document.form1.password2.value;
		if (document.getElementById("password").value != p2) {
			if (p2 != "") {
				document.getElementById("msg_password").innerHTML = "<br/>";//清除提示
			} else {
				document.getElementById("msg_password").innerHTML = "<br/>";//清除提示
				document.getElementById("msg_password2").innerHTML = "<br/>";//清除提示
			}

		}
	}
	//密码2提示
	function checkpwdStudentPassword2() {
		var p1 = document.form1.password.value;//获取密码框的值
		var p2 = document.form1.password2.value;//获取重新输入的密码值
		if (p1 == "") {
			document.getElementById("msg_password").innerHTML = "请输入密码";//在div显示错误信息
		}
		if (p2 == "") {
			document.getElementById("msg_password2").innerHTML = "请输入密码";//在div显示错误信息
		}
		if (p1 == p2) {
			document.getElementById("msg_password").innerHTML = "<br/>";//在div显示错误信息\
			document.getElementById("msg_password2").innerHTML = "<br/>";//在div显示错误信息

		} else {
			document.getElementById("msg_password").innerHTML = "两次输入密码不一致，请重新输入";//在div显示错误信息\
			document.getElementById("msg_password2").innerHTML = "两次输入密码不一致，请重新输入";//在div显示错误信息
			//document.form1.password2.focus();//焦点放到密码框
		}
	}
	function checkpwdStudentIphone() {

	}
	function checkpwdStudentEmail() {

	}

	function checkpwdTeacherPassword() {
		var p2 = document.teacherForm.teacherPassword2.value;
		if (document.getElementById("teacherPassword").value != p2) {
			if (p2 != "") {
				document.getElementById("msg_teacherPassword").innerHTML = "<br/>";//清除提示
			} else {
				document.getElementById("msg_teacherPassword").innerHTML = "<br/>";//清除提示
				document.getElementById("msg_teacherPassword2").innerHTML = "<br/>";//清除提示
			}

		}
	}

	function checkpwdTeacherPassword2() {
		var p1 = document.teacherForm.teacherPassword.value;//获取密码框的值
		alert(p1);
		var p2 = document.teacherForm.teacherPassword2.value;//获取重新输入的密码值
		if (p1 == "") {
			document.getElementById("msg_teacherPassword").innerHTML = "请输入密码";//在div显示错误信息
		}
		if (p2 == "") {
			document.getElementById("msg_teacherPassword2").innerHTML = "请输入密码";//在div显示错误信息
		}
		if (p1 == p2) {
			document.getElementById("msgteacherPassword").innerHTML = "<br/>";//在div显示错误信息\
			document.getElementById("msg_teacherPassword2").innerHTML = "<br/>";//在div显示错误信息

		} else {
			document.getElementById("msg_teacherPassword").innerHTML = "两次输入密码不一致，请重新输入";//在div显示错误信息\
			document.getElementById("msg_teacherPassword2").innerHTML = "两次输入密码不一致，请重新输入";//在div显示错误信息
			//document.form1.password2.focus();//焦点放到密码框
		}
	}
	function checkpwdTeacherIphone() {

	}
	function checkpwdTeacherEmail() {

	}
</script>
<style type="text/css">
.mb-3, .my-3 {
	margin-bottom: 0.5rem !important;
}
/*向上三角形*/
.triangle_border_upp {
	width: 0;
	height: 0;
	border-width: 0 30px 30px;
	border-style: solid;
	border-color: transparent transparent #333; /*透明 透明  灰*/
	margin: 40px auto;
	position: relative;
}
</style>
</head>
<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-md navbar-dark fixed-top" style="background: #2f4f4f94 !important">
    <div class="container" style="background: darkslategray;">
      <div class="row">
        <div class="col-md-12">
          <ul class="nav nav-pills">
            <li class="nav-item"> <a href="<%=basePath%>Login/LoginIndexUrl" class="nav-link">返回登录</a> </li>
            <li class="nav-item"> <a class="active nav-link" href="" data-toggle="pill" data-target="#tabtwo">注册学生</a> </li>
            <li class="nav-item"> <a href="" class="nav-link" data-toggle="pill" data-target="#tabthree">注册教师</a> </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
  <div class="align-items-center d-flex cover section-aquamarine py-5">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <div class="tab-content">
            <div class="tab-content mt-2">
              <div class="tab-pane fade show active" id="tabtwo" role="tabpanel">
                <div style="margin: 20;">
                  <div class="container">
                    <div class="row">
                      <div class="text-center col-md-7 mx-auto">
                        <img src="<%=basePath%>images/student.png">
                        <h3>
                          <b>学生注册</b>
                        </h3>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="container">
                  <hr class="mb-4">
                  <div class="row">
                    <div class="col-md-8 mx-auto">
                      <form class="needs-validation" id="form1" name="form1" method="post" action="/Practice/register/addStudentUrl">
                        <div class="row">
                          <div class="col-md-6 mb-3">
                            <label for="iphone">手机号：</label> <input type="number" class="form-control" id="iphone" placeholder="请输入手机号" required="required" name="iphone" oninput="if(value.length>11)value=value.slice(0,11)" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" onkeyup="checkpwdStudentIphone">
                            <div class="invalid-feedback">Valid last name is required.</div>
                            <div id="msg_iphone" style="color: red;">
                              <br>
                            </div>
                          </div>
                          <div class="col-md-6 mb-3">
                            <label for="studentId">学号：</label> <input type="number" class="form-control" id="studentId" placeholder="请输入学号" required="required" value="" name="studentId" oninput="if(value.length>11)value=value.slice(0,11)" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
                            <div class="invalid-feedback">Valid first name is required.</div>
                            <div id="msg_studentId" style="color: red;">
                              <br>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6 mb-3">
                            <label for="name">姓名：</label>
                            <div class="input-group">
                              <div class="input-group-prepend">
                                <span class="input-group-text">@</span>
                              </div>
                              <input type="text" class="form-control" id="name" placeholder="请输入姓名" required="required" name="name">
                              <div class="invalid-feedback" style="width: 100%;"> Your username is required.</div>
                            </div>
                            <div id="msg_name" style="color: red;">
                              <br>
                            </div>
                          </div>
                          <div class="col-md-6 mb-3">
                            <label for="gender">性别</label> <select class="custom-select d-block w-100" id="gender" required="" name="gender">
                              <option value="男" selected="">男</option>
                              <option value="女">女</option>
                            </select>
                            <div class="invalid-feedback">Please provide a valid state.</div>
                          </div>
                        </div>
                        <div class="mb-3">
                          <label for="email">邮箱：<br></label> <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱地址" required="required" onkeyup="checkpwdStudentEmail">
                          <div class="invalid-feedback">Please enter a valid email address for shipping updates.</div>
                          <div id="msg_email" style="color: red;">
                            <br>
                          </div>
                        </div>
                        <div class="mb-3">
                          <label for="password">密码</label> <input type="password" class="form-control" id="password" required="" placeholder="请输入密码" name="password" onkeyup="checkpwdStudentPassword()">
                          <div class="invalid-feedback">Please enter your shipping address.</div>
                          <div id="msg_password" style="color: red;">
                            <br>
                          </div>
                        </div>
                        <div class="mb-3">
                          <label for="password2">确认密码<br></label> <input type="password" class="form-control" id="password2" name="password2" placeholder="请再次输入密码" required="required" onkeyup="checkpwdStudentPassword2()">
                          <div id="msg_password2" style="color: red;">
                            <br>
                          </div>
                        </div>
                        <input class="btn btn-primary btn-lg btn-block" id="button" name="button" type="button" onclick="checkfunction()" value="注册">
                      </form>
                    </div>
                  </div>
                </div>
                <hr class="mb-4">
              </div>
              <div class="tab-pane fade" id="tabthree" role="tabpanel">
                <div style="margin: 20;">
                  <div class="container">
                    <div class="row">
                      <div class="text-center col-md-7 mx-auto">
                        <img src="<%=basePath%>images/teacher.png">
                        <h3>
                          <b>教师注册</b>
                        </h3>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="container">
                  <hr class="mb-4">
                  <div class="row">
                    <div class="col-md-8 mx-auto">
                      <form class="needs-validation" id="teacherForm" name="teacherForm" method="post" action="/Practice/register/addTeacherUrl">
                        <div class="row">
                          <div class="col-md-6 mb-3">
                            <label for="teacherIphone">手机号：</label> <input type="number" class="form-control" id="teacherIphone" placeholder="请输入手机号" required="required" value="" name="iphone" oninput="if(value.length>11)value=value.slice(0,11)" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" onkeyup="checkpwdTeacherIphone()">
                            <div class="invalid-feedback">Valid last name is required.</div>
                            <div id="msg_teacherIphone" style="color: red;">
                              <br>
                            </div>
                          </div>
                          <div class="col-md-6 mb-3">
                            <label for="teacherId">教工号：</label> <input type="number" class="form-control" id="teacherId" placeholder="请输入教职工号" required="required" value="" name="teacherId" oninput="if(value.length>11)value=value.slice(0,8)" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
                            <div class="invalid-feedback">Valid first name is required.</div>
                            <div id="msg_teacherId" style="color: red;">
                              <br>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6 mb-3">
                            <label for="teacherName">姓名：</label>
                            <div class="input-group">
                              <div class="input-group-prepend">
                                <span class="input-group-text">@</span>
                              </div>
                              <input type="text" class="form-control" id="teacherName" placeholder="请输入姓名" required="required" name="name" onkeyup="checkpwdTeacherEmail()">
                              <div class="invalid-feedback" style="width: 100%;"> Your username is required.</div>
                            </div>
                            <div id="msg_teacherName" style="color: red;">
                              <br>
                            </div>
                          </div>
                          <div class="col-md-6 mb-3">
                            <label for="teachergender">性别</label> <select class="custom-select d-block w-100" id="teachergender" required="" name="gender">
                              <option value="男" selected="">男</option>
                              <option value="女">女</option>
                            </select>
                            <div class="invalid-feedback">Please provide a valid state.</div>
                          </div>
                        </div>
                        <div class="mb-3">
                          <label for="teacherEmail">邮箱：<br></label> <input type="email" class="form-control" id="teacherEmail" name="email" placeholder="请输入邮箱地址" required="required">
                          <div class="invalid-feedback">Please enter a valid email address for shipping updates.</div>
                          <div id="msg_teacherEmail" style="color: red;">
                            <br>
                          </div>
                        </div>
                        <div class="mb-3">
                          <label for="teacherPassword">密码</label> <input type="password" class="form-control" id="teacherPassword" required="" placeholder="请输入密码" name="password" onkeyup="checkpwd3()">
                          <div class="invalid-feedback">Please enter your shipping address.</div>
                          <div id="msg_teacherPassword" style="color: red;">
                            <br>
                          </div>
                        </div>
                        <div class="mb-3">
                          <label for="teacherPassword2">确认密码<br></label> <input type="password" class="form-control" id="teacherPassword2" name="teacherPassword2" placeholder="请再次输入密码" required="required" onkeyup="checkpwd4()">
                          <div id="msg_teacherPassword2" style="color: red;">
                            <br>
                          </div>
                        </div>
                        <hr class="mb-4">
                        <input class="btn btn-primary btn-lg btn-block" id="button1" name="button" type="button" onclick="teacherCheckfunction()" value="注册">
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="py-5 text-muted text-center">
        <div class="container">
          <div class="row">
            <div class="col-md-12 my-4">
              <p class="mb-1">© 2019-2020 练习题管理系统</p>
              <ul class="list-inline">
                <li class="list-inline-item"><a href="/Practice/Login/LoginIndexUrl">主页</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>