<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新用户信息</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 400px;
            margin: 50px auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }
        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            text-decoration: none;
            color: #007bff;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>更新您的信息</h2>

    <form action="UpdateProfileServlet" method="post">
        <div class="form-group">
            <label for="userName">用户名:</label>
            <input type="text" id="userName" name="userName" value="${param.userName}" required />
        </div>

        <div class="form-group">
            <label for="email">邮箱:</label>
            <input type="email" id="email" name="email" value="${param.email}" required />
        </div>

        <div class="form-group">
            <label for="sex">性别:</label>
            <select id="sex" name="sex" required>
                <option value="男" ${param.sex == '男' ? 'selected' : ''}>男</option>
                <option value="女" ${param.sex == '女' ? 'selected' : ''}>女</option>
            </select>
        </div>

        <button type="submit">更新信息</button>
    </form>

    <a href="userProfile.jsp" class="back-link">返回个人主页</a>
</div>

</body>
</html>
