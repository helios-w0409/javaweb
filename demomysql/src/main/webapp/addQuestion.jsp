<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>添加试题</title>
    <!-- Bootstrap 5 引入 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">添加新试题</h4>
                </div>
                <div class="card-body">
                    <form action="AddQuestionServlet" method="post">
                        <div class="mb-3">
                            <label class="form-label">题目</label>
                            <input type="text" class="form-control" name="title" placeholder="例如：李泽言的生日？">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">选项 A</label>
                            <input type="text" class="form-control" name="optionA" placeholder="例如：1月2号">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">选项 B</label>
                            <input type="text" class="form-control" name="optionB" placeholder="例如：1月13号">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">选项 C</label>
                            <input type="text" class="form-control" name="optionC" placeholder="例如：1月14号">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">选项 D</label>
                            <input type="text" class="form-control" name="optionD" placeholder="例如：1月3号">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">正确答案（A/B/C/D）</label>
                            <input type="text" class="form-control" name="answer">
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-success px-4">提交试题</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
