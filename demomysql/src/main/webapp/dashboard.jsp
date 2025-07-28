<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户主页</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            animation: fadeIn 1s ease-in-out;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            animation: slideUp 0.5s ease-in-out;
        }

        h1 {
            color: #333;
            font-size: 24px;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
            color: #555;
            margin-bottom: 10px;
        }

        label {
            font-weight: bold;
            display: block;
            margin: 10px 0 5px;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        input:focus, select:focus {
            border-color: #007BFF;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        button {
            width: 100%;
            padding: 12px;
            background: #007BFF;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background: #0056b3;
        }

        .logout {
            display: block;
            margin-top: 20px;
            color: red;
            text-decoration: none;
            font-weight: bold;
        }

        .logout:hover {
            text-decoration: underline;
        }

        /* 动画效果 */
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @keyframes slideUp {
            from {
                transform: translateY(20px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <h1>欢迎, ${sessionScope.user.userName}!</h1>
    <p>这是用户的主页。</p>

    <p><strong>用户名:</strong> ${sessionScope.user.userName}</p>
    <p><strong>邮箱:</strong> ${sessionScope.user.email}</p>
    <p><strong>性别:</strong> ${sessionScope.user.sex}</p>

    <!-- 用户信息更新表单 -->
    <h3>更新用户信息</h3>
    <form action="updateProfile.jsp" method="post" id="updateForm">
        <label for="userName">用户名:</label>
        <input type="text" id="userName" name="userName" value="${sessionScope.user.userName}" required />

        <label for="email">邮箱:</label>
        <input type="email" id="email" name="email" value="${sessionScope.user.email}" required />

        <label for="sex">性别:</label>
        <select id="sex" name="sex" required>
            <option value="男" ${sessionScope.user.sex == '男'.charAt(0) ? 'selected' : ''}>男</option>
            <option value="女" ${sessionScope.user.sex == '女'.charAt(0) ? 'selected' : ''}>女</option>
        </select>

        <button type="submit">更新信息</button>
    </form>

    <!-- 参加考试按钮 -->
    <form action="ExamServlet" method="post">
        <input type="submit" class="btn btn-primary w-100 mt-3" value="参加考试" />
    </form>

    <!-- 用户注销 -->
    <a href="logout.jsp" class="logout">退出登录</a>
</div>

<script>
    // 动态表单验证和反馈
    document.getElementById('updateForm').addEventListener('submit', function(event) {
        const userName = document.getElementById('userName').value;
        const email = document.getElementById('email').value;

        if (userName === "" || email === "") {
            alert("请确保所有字段都已填写！");
            event.preventDefault();
        }
    });
</script>

</body>
</html>
