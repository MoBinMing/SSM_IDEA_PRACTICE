<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../shared/tags_bootstrap_v3.3.7.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教务管理系统</title>
<script
	src="chrome-extension://mooikfkahbdckldjjndioackbalphokd/assets/prompt.js"></script>
<script type="text/javascript">
		function myPractices(){
		    if(confirm("是否改变工作的台账显示导出状态？")){
		        $.ajax({
		            type:'POST',
		            url:'/Practice/Teacher/Index',
		            data:{},
		            dataType:"json",
		            global:false,
		            success:function(body){
		            	alert("跳转成功");
		            },
		            error: DWZ.ajaxError
		        });
		    }
		
		}

		</script>
<style type="text/css">
.line {
	word-break: break-all
}
</style>
</head>
<body
	style="background: url(http://localhost:8080/Practice/images/body.jpg); background-size: 100% 100%;">

	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="page-header" style="">
					<h1>
						练习题管理系统 <small>@2019</small>
					</h1>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column"></div>
				</div>
				、
				<div class="row clearfix">
					<div class="col-md-9 column well well-sm" id="thisBody">
						${thisBody}</div>
					、
					<div class="col-md-3 column">
						<div class="list-group">
							<a href="#" class="list-group-item active">菜单</a>
							<div class="list-group-item">${UserExit}</div>
							<div class="list-group-item">${myPractice}</div>
							<div class="list-group-item">
								<a href="/Practice/Teacher/authority_management"
									class="list-group-item">权限管理</a>
							</div>
							<div class="list-group-item">
								<a href="/Practice/Teacher/api_management" class="list-group-item">Api管理</a>
							</div>
							<a class="list-group-item active">Help</a>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column"></div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>