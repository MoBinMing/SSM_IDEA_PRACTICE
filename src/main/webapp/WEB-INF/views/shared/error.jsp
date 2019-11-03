<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/3
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //basePath:http://localhost:8080/SSMM/
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
    ${errorInfo}
    <a href="<%=basePath%>" >前往登录页</a>
</head>
<body>

</body>
</html>
