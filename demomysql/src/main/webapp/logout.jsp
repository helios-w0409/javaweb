<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>退出登录</title>
</head>
<body>

<%
    // 让用户退出登录，清除 session 中的用户信息
    session.invalidate(); // 使当前的 session 失效

    // 跳转到登录页面
    response.sendRedirect("login.jsp");
%>

</body>
</html>
