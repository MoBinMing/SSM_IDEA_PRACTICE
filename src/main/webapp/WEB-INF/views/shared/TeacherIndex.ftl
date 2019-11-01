<html >
<head>
<!-- Bootstrap 核心CSS 文件 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>教师主页</title>
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no" name="viewport">
  <link rel="stylesheet" href="${rc.contextPath}/assets/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
  <link rel="stylesheet" href="${rc.contextPath}/assets/css/ready.css">
  <link rel="stylesheet" href="${rc.contextPath}/assets/css/demo.css">
  

   
   <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/js/bootstrap-switch.min.js"></script>
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
    
	function searchPractices777(){
						var val=$("#searchPracticesVal").val();
						var text=$.ajax({url:"/Practice/Teacher/searchPractices?val="+val,async:false});
						alert(text);
		};
		$("input").keyup(function(){
		    searchPractices();
		});
		function searchPractices(){
						var val=$("#searchPracticesVal").val();
						var practiceTbody=$("#practiceTbody");
						$.ajax({
				            type:'POST',
				            url:'/Practice/Teacher/searchPractices',
				            data:'val='+val,
				            dataType:"json",
				            global:false,
				            success:function(data){
				            	alert(data.tbody);
				                practiceTbody.append(data.tbody); 
				            }
				        });
		};
	function deleteCourse(id){
			var an=confirm("确定删除？");
			if(an==true){
				location.href="deleteCourse/"+id;
			}else{
				return false;
			}
		}
    function deletePractice(id){
			var an=confirm("确定删除？");
			if(an==true){
				location.href="/Practice/Teacher/deletePractice/"+id;
			}else{
				return false;
			}
		}
		function deleteQuestion(id){
			var an=confirm("确定删除？");
			if(an==true){
				location.href="/Practice/Teacher/deleteQuestion/"+id;
			}else{
				return false;
			}
		}
		
	function updateReady(id){
			$.get("updateReady/"+id,function(data,status){
				if(data.thisBody=="/Practice/Login/LoginIndexUrl"){
					location.href=data.thisBody;
				}else{
					$("#tbody").html(data.thisBody); 
				}
		    });
	}
	function getStudentManagementHtml(){
			$.get("getStudentManagementHtml",function(data,status){
				if(data.ok!="ok"){
					$("#contentView").html(data.thisBody); 
				}else{
					$("#contentView").html(data.thisBody); 
				}
		    });
	}
	


${javascriptHtml}
/* bootstrap开关控件，初始化 */
function onLi(){
	 $('#mySwitch input').bootstrapSwitch();
}
</script>
<body onLoad="onLi()">
${courses}
<div class="wrapper">
    <div class="main-header">
      <div class="logo-header align-middle">
        <a href="" class="logo"> 练习题管理系统 </a>
        <button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse" data-target="collapse" aria-controls="sidebar" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <button class="topbar-toggler more"><i class="la la-ellipsis-v"></i></button>
      </div>
      <nav class="navbar navbar-header navbar-expand-lg">
        <div class="container-fluid">
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
                        <img src="${rc.contextPath}/images/${portrait}" alt="Img Profile">
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
              <a class="dropdown-toggle profile-pic" data-toggle="dropdown" href="#" aria-expanded="false"> <img src="${rc.contextPath}/images/${portrait}" alt="user-img" width="36" class="img-circle"><span>${userName}</span> </a>
              <ul class="dropdown-menu dropdown-user">
                <li>
                  <div class="user-box">
                    <div class="u-img"><img src="${rc.contextPath}/images/${portrait}" alt="user"></div>
                    <div class="u-text">
                      <h4>${userName}</h4>
                      <p class="text-muted">hello ：${Email}</p>
                    </div>
                  </div>
                </li>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#"><i class="ti-settings"></i> Account Setting</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/Practice/Login/Exit"><i class="fa fa-power-off"></i> Logout</a>
              </ul>
              <!-- /.dropdown-user -->
            </li>
          </ul>
        </div>
      </nav>
    </div>
    <div class="sidebar">
      <div class="scrollbar-inner sidebar-wrapper" style="">
        <div class="user">
          <div class="photo">
            <img src="${rc.contextPath}/images/${portrait}">
          </div>
          <div class="info">
            <a class="" data-toggle="collapse" href="#collapseExample" aria-expanded="true">
              <span > ${userName} <span class="user-level">${Email}</span>
                <span class="caret"></span>
              </span>
            </a>
            <div class="clearfix"></div>
            <div class="in collapse show" id="collapseExample" aria-expanded="true" style="">
              <ul class="nav">
                <li>
                  <a href="/Practice/Login/Exit">
                    <span class="link-collapse">退出登录</span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <ul class="nav">
          <li class="nav-item active">
            <a href="/Practice/Teacher/Index">
              <i class="la la-dashboard"></i>
              <p>课程管理</p>
              <span class="badge badge-count">${myCoursesSize}</span>
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
        <div class="container-fluid"> 
		   <div class="row"> 
		    <h4 class="col page-title">课程管理</h4> 
		    <div class="col text-right"> 
		     <button type="button" class="btn btn-success" data-target="#addCourseModal" data-toggle="modal">添加课程 </button> 
		    </div> 
		   </div> 
		    <div class="col-md-12" style="padding: 0;"> 
		    <div class="card card-tasks"> 
		     <div class="card-body "> 
		      <div class="table-full-width"> 
		       <table class="table"> 
		        <thead> 
		         <tr> 
		          <th>课程</th> 
		          <th>课程介绍</th> 
		          <th> 练习数</th> 
		          <th> 操作 </th> 
		         </tr> 
		        </thead> 
		        <tbody>
		        
		        <c:choose>
				   <c:when test="${courses.size} > 0 "> 
				         <c:forEach var="course" items="${courses}">
						         <tr>
						          <td><a href="/Practice/Teacher/getPracticeByCourseId/${course.id}">{course.name}</a></td>
						          <td>${course.intro}</td>
						          <td>${course.practiceSize}</td>
						          <td class="td-actions text-left"> 
						           <div class="form-button-action"> 
						            <button type="button" data-toggle="tooltip" title="" class="btn btn-link btn-simple-danger" data-original-title="删除"><i class="la la-times" onclick="deleteCourse('${course.id}')">删除</i></button> 
						           </div> </td>
						         </tr>
						</c:forEach>  
				   </c:when>
				   <c:otherwise>
				   
				   </c:otherwise>
				</c:choose>
       
		</tbody> 
		       </table> 
		      </div> 
		     </div> 
		    </div> 
		   </div> 
		   <div class="modal" id="addCourseModal"> 
		    <div class="modal-dialog" role="document"> 
		     <div class="modal-content"> 
		      <div class="modal-header"> 
		       <h5 class="modal-title bg-light">添加课程</h5> 
		       <button type="button" class="close" data-dismiss="modal"> <span>&times;</span> </button> 
		      </div> 
		      <form id="addCourses" name="addCourses" method="post" action="/Practice/Teacher/addCourses" class="form-horizontal" role="添加练习" onsubmit="return addPracticesTest();"> 
		       <div class="modal-body" style=""> 
		        <div class="form-group"> 
		         <label for="name">课程名称</label> 
		         <div id="myAlert2" class="alert alert-warning" style="visibility: hidden; display: none;"> 
		          <a href="#" class="close" data-dismiss="alert">&times;</a> 
		          <strong>提示！</strong>课程名称输入不完整！。 
		         </div> 
		         <input id="name" name="name" type="text" class="form-control" placeholder="请输入课程名称" /> 
		        </div> 
		        <div class="form-group"> 
		         <label for="outlines">课程描述</label> 
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
		
      </div>
      
      <footer class="footer">
        <div class="container-fluid">
          <nav class="navbar navbar-default navbar-fixed-bottom"></nav>
          <div class="copyright ml-auto">Author binming mo posted the project to <i class="la la-heart heart text-danger"></i><a href="http://www.cssmoban.com/" target="_blank" title="Github">Github</a> in 2019 </div>
        </div>
      </footer>
    </div>
  </div>
 <script type="text/javascript" src="${rc.contextPath}/assets/js/core/jquery.3.2.1.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/core/popper.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/core/bootstrap.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/chartist/chartist.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/chartist/plugin/chartist-plugin-tooltip.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/bootstrap-toggle/bootstrap-toggle.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/jquery-mapael/jquery.mapael.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/jquery-mapael/maps/world_countries.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/chart-circle/circles.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/ready.min.js"></script>
  <script type="text/javascript" src="${rc.contextPath}/assets/js/demo11111111111.js"></script>
</body>
</html>
