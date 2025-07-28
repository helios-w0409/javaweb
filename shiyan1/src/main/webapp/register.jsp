<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>用户注册</title>
</head>
<body>
<h2>用户注册</h2>
<form action="RegisterServlet" method="post">
  用户名: <input type="text" name="userName" required><br><br>
  密码: <input type="password" name="password" required><br><br>
  性别:
  <input type="radio" name="sex" value="男" required>男
  <input type="radio" name="sex" value="女" required>女<br><br>
  邮箱: <input type="email" name="email" required><br><br>
  <input type="submit" value="注册">
</form>
</body>
</html>
