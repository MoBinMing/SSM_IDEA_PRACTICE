

<%
	 String path=request.getContextPath();
	 String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 //basePath:http://localhost:8080/SSMM/
%>
<!-- Bootstrap 核心CSS 文件 -->
<script type="text/javascript" src="<%=basePath%>scripts/time.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/bootstrap.min-v3.3.7.css">
<script src="<%=basePath%>scripts/jquery.min-v3.2.1.js"></script>
<script src="<%=basePath%>scripts/bootstrap.min-v3.3.7.js"></script>