<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>用户注册</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    .container { width: 400px; margin: 0 auto; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; }
    input[type="text"], input[type="password"], input[type="email"], select {
      width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;
    }
    button { padding: 10px 15px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:hover { background-color: #45a049; }
    .error { color: red; }
    .login-link {
      display: block;
      margin-top: 15px;
      text-align: center;
    }
    .login-link a {
      text-decoration: none;
      color: #007bff;
    }
    .login-link a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>用户注册</h2>
  <form action="RegisterServlet" method="post">
    <div class="form-group">
      <label for="userName">用户名:</label>
      <input type="text" id="userName" name="userName" required>
    </div>
    <div class="form-group">
      <label for="password">密码:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <div class="form-group">
      <label for="sex">性别:</label>
      <select id="sex" name="sex" required>
        <option value="男" ${sessionScope.user.sex == '男' ? 'selected' : ''}>男</option>
        <option value="女" ${sessionScope.user.sex == '女' ? 'selected' : ''}>女</option>
      </select>
    </div>
    <div class="form-group">
      <label for="role">角色:</label>
      <select id="role" name="role">
        <option value="0">普通用户</option>
        <option value="1">管理员</option>
      </select>
    </div>

    <div class="form-group">
      <label for="email">邮箱:</label>
      <input type="email" id="email" name="email" required>
    </div>
    <div class="form-group">
      <button type="submit">注册</button>
    </div>
    <div class="error">${error}</div>
  </form>

  <!-- 添加已有账号登录链接 -->
  <div class="login-link">
    <p>已有账号？<a href="login.jsp">立即登录</a></p>
  </div>
</div>
</body>
</html>
