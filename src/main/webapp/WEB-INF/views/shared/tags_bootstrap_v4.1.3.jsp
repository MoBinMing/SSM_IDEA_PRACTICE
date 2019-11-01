
<%
	 String path=request.getContextPath();
	 String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 //basePath:http://localhost:8080/SSMM/
%>
<!-- Bootstrap æ ¸å¿CSS æä»¶ -->
<link rel="icon" href="https://templates.pingendo.com/assets/Pingendo_favicon.ico">
<link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>css/bootstrap-v4.1.3.css" type="text/css">
<script type="text/javascript" src="<%=basePath%>scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/bootstrap.min-v4.1.3.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/aquamarine.css" type="text/css">
<!-- Script: Make my navbar transparent when the document is scrolled to top -->
<script src="<%=basePath%>scripts/navbar-ontop.js"></script>
<!-- Script: Animated entrance -->
<script src="<%=basePath%>scripts/animate-in.js"></script>
<!-- c标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<base href="<c:url value='/' />">

