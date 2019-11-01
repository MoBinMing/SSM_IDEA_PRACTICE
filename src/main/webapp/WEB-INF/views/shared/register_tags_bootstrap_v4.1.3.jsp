

<%
	 String path=request.getContextPath();
	 String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 //basePath:http://localhost:8080/SSMM/
%>
<!-- Bootstrap 核心CSS 文件 -->
<link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="<%=basePath%>css/wireframe.css">
<script type="text/javascript"
	src="<%=basePath%>scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/popper.min-v1.14.3.js"></script>
<script type="text/javascript"
	src="<%=basePath%>scripts/bootstrap.min-v4.1.3.js"></script>