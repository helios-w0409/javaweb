<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demomysql.Users, com.example.demomysql.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  UserDAO userDAO = new UserDAO();
  List<Users> userList = userDAO.getAllUsers();
  request.setAttribute("userList", userList);
%>

<html>
<head>
  <title>管理员后台</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      margin: 0;
      padding: 0;
      background: linear-gradient(to right, #e0f7fa, #ffffff);
    }
    .container {
      max-width: 1100px;
      margin: 40px auto;
      background: #fff;
      padding: 30px 40px;
      border-radius: 16px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    }
    h2 {
      text-align: center;
      color: #007bff;
      margin-bottom: 30px;
    }
    h3 {
      margin-top: 40px;
      color: #333;
      border-bottom: 2px solid #007bff;
      padding-bottom: 8px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      border: 1px solid #e0e0e0;
      padding: 14px;
      text-align: center;
      font-size: 15px;
    }
    th {
      background-color: #007bff;
      color: white;
    }
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }
    tr:hover {
      background-color: #f1f1f1;
    }
    .btn-container {
      text-align: center;
      margin-top: 40px;
    }
    .btn {
      display: inline-block;
      padding: 12px 22px;
      margin: 10px;
      font-size: 15px;
      border-radius: 8px;
      border: none;
      text-decoration: none;
      color: white;
      background: #28a745;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
    }
    .btn:hover {
      background: #218838;
      transform: translateY(-2px);
    }
    .logout-btn {
      background-color: #dc3545;
    }
    .logout-btn:hover {
      background-color: #c82333;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>欢迎您，管理员</h2>

  <h3>👤 用户列表</h3>
  <table>
    <tr>
      <th>ID</th>
      <th>用户名</th>
      <th>性别</th>
      <th>Email</th>
      <th>角色</th>
      <th>状态</th>
    </tr>
    <c:forEach var="user" items="${userList}">
      <tr>
        <td>${user.userId}</td>
        <td>${user.userName}</td>
        <td>${user.sex}</td>
        <td>${user.email}</td>
        <td>${user.role == 1 ? "管理员" : "普通用户"}</td>
        <td>${user.status}</td>
      </tr>
    </c:forEach>
  </table>

  <div class="btn-container">
    <a href="addUser.jsp" class="btn">➕ 添加用户</a>
    <a href="addQuestion.jsp" class="btn">📝 添加试题</a>
    <a href="ListQuestionServlet" class="btn">📋 管理试题</a>
    <a href="logout.jsp" class="btn logout-btn">⏻ 退出登录</a>
  </div>
</div>
</body>
</html>
