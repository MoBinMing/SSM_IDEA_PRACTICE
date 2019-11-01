

<%
	 String path=request.getContextPath();
	 String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 //basePath:http://localhost:8080/SSMM/
%>
<!-- Bootstrap 核心CSS 文件 -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/bootstrap.min.css">

<script type="text/javascript" src="<%=basePath%>scripts/time.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/login.js"></script>
