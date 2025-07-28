<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demomysql.Question" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>参加考试</title>
  <!-- Bootstrap 5 引入 -->
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
  </style>
</head>
<body class="bg-light">
<div class="container py-5">
  <div class="text-center mb-4">
    <h2 class="text-primary">📝 在线考试</h2>
  </div>

  <%
    List<Question> examQuestions = (List<Question>) session.getAttribute("examQuestions");//获取后端传来的信息
    if (examQuestions == null || examQuestions.isEmpty()) {
  %>
  <div class="alert alert-warning text-center">暂无试题，请联系管理员。</div>
  <%
  } else {
  %>
  <form action="JudgeServlet" method="post" class="fade-in">

    <div class="accordion" id="questionAccordion">
      <%
        for (int i = 0; i < examQuestions.size(); i++) {//examQuestions.size() 返回 examQuestions 列表中的元素数量。
          Question q = examQuestions.get(i);//获取每道题目
      %>
      <div class="accordion-item">
        <h2 class="accordion-header" id="heading<%= i %>">
          <button class="accordion-button <%= i != 0 ? "collapsed" : "" %>" type="button" data-bs-toggle="collapse"
                  data-bs-target="#collapse<%= i %>" aria-expanded="<%= i == 0 %>" aria-controls="collapse<%= i %>">
            第 <%= i + 1 %> 题：<%= q.getTitle() %>
          </button>
        </h2>
        <div id="collapse<%= i %>" class="accordion-collapse collapse <%= i == 0 ? "show" : "" %>"
             aria-labelledby="heading<%= i %>" data-bs-parent="#questionAccordion">
          <div class="accordion-body">
            <div class="form-check">
              <input class="form-check-input" type="radio" name="answer<%= i %>" id="A<%= i %>" value="A" required>
              <label class="form-check-label" for="A<%= i %>">A. <%= q.getOptionA() %></label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="answer<%= i %>" id="B<%= i %>" value="B">
              <label class="form-check-label" for="B<%= i %>">B. <%= q.getOptionB() %></label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="answer<%= i %>" id="C<%= i %>" value="C">
              <label class="form-check-label" for="C<%= i %>">C. <%= q.getOptionC() %></label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="answer<%= i %>" id="D<%= i %>" value="D">
              <label class="form-check-label" for="D<%= i %>">D. <%= q.getOptionD() %></label>
            </div>

            <input type="hidden" name="qid<%= i %>" value="<%= q.getQuestionId() %>">
          </div>
        </div>
      </div>
      <% } %>
    </div>

    <div class="text-center mt-4">
      <button type="submit" class="btn btn-success btn-lg px-5">提交答案</button>
    </div>

  </form>
  <% } %>
</div>
</body>
</html>
