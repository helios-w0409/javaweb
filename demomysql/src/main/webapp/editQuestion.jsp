<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, com.example.demomysql.JdbcUtil" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>编辑试题</title>
  <!-- 引入 Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
          <h4 class="mb-0">编辑试题</h4>
        </div>
        <div class="card-body">

          <%
            String idParam = request.getParameter("id");
            if (idParam != null && idParam.matches("\\d+")) {
              int id = Integer.parseInt(idParam);
              Connection conn = null;
              PreparedStatement ps = null;
              ResultSet rs = null;
              try {
                conn = JdbcUtil.getConnection();
                ps = conn.prepareStatement("SELECT * FROM question WHERE questionId = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
          %>

          <form action="UpdateQuestionServlet" method="post">
            <input type="hidden" name="questionId" value="<%= id %>">

            <div class="mb-3">
              <label class="form-label">题目</label>
              <input type="text" class="form-control" name="title" value="<%= rs.getString("title") %>">
            </div>
            <div class="mb-3">
              <label class="form-label">选项 A</label>
              <input type="text" class="form-control" name="optionA" value="<%= rs.getString("optionA") %>">
            </div>
            <div class="mb-3">
              <label class="form-label">选项 B</label>
              <input type="text" class="form-control" name="optionB" value="<%= rs.getString("optionB") %>">
            </div>
            <div class="mb-3">
              <label class="form-label">选项 C</label>
              <input type="text" class="form-control" name="optionC" value="<%= rs.getString("optionC") %>">
            </div>
            <div class="mb-3">
              <label class="form-label">选项 D</label>
              <input type="text" class="form-control" name="optionD" value="<%= rs.getString("optionD") %>">
            </div>
            <div class="mb-3">
              <label class="form-label">正确答案（A/B/C/D）</label>
              <input type="text" class="form-control" name="answer" value="<%= rs.getString("answer") %>">
            </div>

            <div class="text-center">
              <button type="submit" class="btn btn-success px-4">保存修改</button>
            </div>
          </form>

          <%
          } else {
          %>
          <p class="text-danger text-center">未找到试题信息！</p>
          <%
            }
          } catch (Exception e) {
          %>
          <p class="text-danger text-center">发生错误：<%= e.getMessage() %></p>
          <%
            } finally {
              if (rs != null) try { rs.close(); } catch (Exception ignore) {}
              if (ps != null) try { ps.close(); } catch (Exception ignore) {}
              if (conn != null) try { conn.close(); } catch (Exception ignore) {}
            }
          } else {
          %>
          <p class="text-danger text-center">错误：未提供或非法的试题 ID 参数！</p>
          <%
            }
          %>

        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
