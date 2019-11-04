<%
	 String path=request.getContextPath();
	 String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 //basePath:http://localhost:8080/SSMM/
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="<%=basePath%>assets/js/core/jquery.3.2.1.min.js"></script>
<base href="<c:url value='/' />">


