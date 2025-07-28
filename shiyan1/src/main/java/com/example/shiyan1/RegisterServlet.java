package com.example.shiyan1;

import com.example.shiyan1.Users; // 修正导入
import com.example.shiyan1.JdbcUtil; // 确保 JdbcUtil 也在正确的包路径下

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取前端数据
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");

        // 2. 封装到 Users 对象
        Users user = new Users(userName, password, sex, email);

        // 3. 数据库插入
        boolean isSuccess = registerUser(user);

        // 4. 跳转页面
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(isSuccess ? "注册成功！<a href='register.jsp'>去登录</a>" : "注册失败，请重试！");
    }

    private boolean registerUser(Users user) {
        String sql = "INSERT INTO Users (userName, password, sex, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassWord()); // 修正拼写
            pstmt.setString(3, user.getSex());
            pstmt.setString(4, user.getEmail());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
