package  com.example.demomysql;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Servlet 负责处理添加试题的表单提交请求
 * 它通过 POST 方法接收参数，并将数据插入数据库中的 question 表
 */
@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        //获取表单数据 与前端name一致
        String title = request.getParameter("title");
        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");
        String answer = request.getParameter("answer");

        try (Connection conn = JdbcUtil.getConnection()) {
            String sql = "INSERT INTO question (title, optionA, optionB, optionC, optionD, answer) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, optionA);
            ps.setString(3, optionB);
            ps.setString(4, optionC);
            ps.setString(5, optionD);
            ps.setString(6, answer);
            ps.executeUpdate();//执行语句插入数据库
            response.sendRedirect("ListQuestionServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("添加失败");
        }
    }
}
