<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>考试成绩</title>
    <!-- 引入 Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .score-container {
            max-width: 600px;
            margin: 50px auto;
            text-align: center;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }

        .score-container h2 {
            font-size: 2rem;
            color: #007bff;
            margin-bottom: 20px;
        }

        .score-container .result {
            font-size: 1.5rem;
            color: #28a745;
            font-weight: bold;
        }

        .score-container .retry-btn {
            margin-top: 20px;
        }

        .score-container .result.fail {
            color: #dc3545;
        }

        .score-container .score-box {
            background-color: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
            display: inline-block;
            font-size: 2.5rem;
            font-weight: bold;
            color: #343a40;
        }
    </style>
</head>
<body class="bg-light">

<%
    // 从 session 获取分数
    Integer score = (Integer) session.getAttribute("score");

    // 如果分数为空，表示用户没有进行考试
    if (score == null) {
        out.println("<h2>没有找到分数，您可能没有完成考试！</h2>");
    } else {
%>

<div class="score-container">
    <h2>您的得分是</h2>

    <div class="score-box fade-in">
        <%= score %> 分
    </div>

    <div class="result <% if (score < 60) { out.print("fail"); } else { out.print("pass"); } %>">
        <%= score < 60 ? "未通过" : "恭喜，您通过了考试！" %>
    </div>

    <div class="retry-btn">
        <a href="exam.jsp" class="btn btn-primary">重新参加考试</a>
    </div>
</div>

<%
    }
%>

<script>
    // 页面加载时的动态效果
    document.addEventListener("DOMContentLoaded", function () {
        document.querySelector('.score-box').classList.add('fade-in');
    });
</script>

</body>
</html>
