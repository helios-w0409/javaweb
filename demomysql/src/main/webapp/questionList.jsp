<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demomysql.Question" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理试题</title>
    <!-- 引入 Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .fade-in {
            animation: fadeIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            0% { opacity: 0; transform: translateY(10px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        .table th, .table td {
            text-align: center;
        }

        .btn {
            text-decoration: none;
            border-radius: 5px;
            padding: 6px 12px;
        }

        .btn-edit {
            background-color: #007bff;
            color: white;
        }

        .btn-edit:hover {
            background-color: #0056b3;
        }

        .btn-delete {
            background-color: #dc3545;
            color: white;
        }

        .btn-delete:hover {
            background-color: #c82333;
        }

        .btn-add {
            margin: 10px 0;
        }

        .table th {
            cursor: pointer;
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-5">
    <h2 class="text-center text-primary mb-4 fade-in">试题管理</h2>

    <div class="d-flex justify-content-between mb-4">
        <a href="addQuestion.jsp" class="btn btn-success btn-add">➕ 添加试题</a>
    </div>

    <table class="table table-striped table-bordered table-hover fade-in">
        <thead>
        <tr>
            <th>ID</th>
            <th>题目</th>
            <th>A</th>
            <th>B</th>
            <th>C</th>
            <th>D</th>
            <th>答案</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Question> list = (List<Question>) request.getAttribute("questionList");
            for (Question q : list) {
        %>
        <tr>
            <td><%= q.getQuestionId() %></td>
            <td><%= q.getTitle() %></td>
            <td><%= q.getOptionA() %></td>
            <td><%= q.getOptionB() %></td>
            <td><%= q.getOptionC() %></td>
            <td><%= q.getOptionD() %></td>
            <td><%= q.getAnswer() %></td>
            <td>
                <a href="editQuestion.jsp?id=<%= q.getQuestionId() %>" class="btn btn-edit">编辑</a>
                <a href="javascript:void(0);" class="btn btn-delete" onclick="confirmDelete('<%= q.getQuestionId() %>')">删除</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<!-- 删除确认模态框 -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModalLabel">确认删除</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                您确定要删除此试题吗？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                <a id="confirmDeleteBtn" href="#" class="btn btn-danger">删除</a>
            </div>
        </div>
    </div>
</div>

<script>
    // 动态添加删除确认链接
    function confirmDelete(questionId) {
        const deleteUrl = 'DeleteQuestionServlet?id=' + questionId;
        const confirmBtn = document.getElementById('confirmDeleteBtn');
        confirmBtn.setAttribute('href', deleteUrl);
        const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
        modal.show();
    }
</script>

</body>
</html>
