<html >
<head>
<!-- Bootstrap 核心CSS 文件 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>教师主页</title>
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no" name="viewport">
  <link rel="stylesheet" href="${rc.contextPath}/teacherCss/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
  <link rel="stylesheet" href="${rc.contextPath}/teacherCss/css/ready.css">
  <link rel="stylesheet" href="${rc.contextPath}/teacherCss/css/demo.css">
  
<!--<script type="text/javascript" src="${rc.contextPath}/scripts/time.js"></script> -->
</head>
<style type="text/css">

 ${csstHtml}
 
</style>
<script type="text/javascript">
var tim=3;//设定跳转的时间 
setInterval("refer()",5000); //启动1秒定时 
function refer(){ 
if(tim==0){ 
	location.href="/Practice/Login/LoginIndexUrl"; //#设定跳转的链接地址 
} 
document.getElementById('show').innerHTML=${Exception} ;
tim--; // 计数器递减 
//本文转自： 
}

</script>
<body align="center">
	<h3>验证信息已过期，请重新登录</h3>
	
	<span id="show"></span>

<h1>${Exception}</h1>

    <script src="${rc.contextPath}/teacherJs/js/core/jquery.3.2.1.min.js"></script>
  <script src="${rc.contextPath}/${rc.contextPath}/teacherJs/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/core/popper.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/core/bootstrap.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/chartist/chartist.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/chartist/plugin/chartist-plugin-tooltip.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/bootstrap-toggle/bootstrap-toggle.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/jquery-mapael/jquery.mapael.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/jquery-mapael/maps/world_countries.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/chart-circle/circles.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/ready.min.js"></script>
  <script src="${rc.contextPath}/teacherJs/js/demo.js" style=""></script>
</body>
</html>
