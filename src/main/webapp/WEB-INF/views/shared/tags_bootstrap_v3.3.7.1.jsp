

<%
	 String path=request.getContextPath();
	 String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 //basePath:http://localhost:8080/SSMM/
%>
<!-- Bootstrap 核心CSS 文件 -->
<script src="<%=basePath%>scripts/jquery.min-v3.2.1.js"></script>