package com.example.demomysql;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.demomysql.JdbcUtil;

@WebServlet("/DeleteQuestionServlet")
public class DeleteQuestionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));//request.getParameter("id")获取用户提交的 id
        // Integer.parseInt() id 字符串转换为整数类型 int。如果请求中没有 id 参数，或者参数无法转换为有效的整数，则会抛出异常。
        try (Connection conn = JdbcUtil.getConnection()) {
            String sql = "DELETE FROM question WHERE questionId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ListQuestionServlet");
    }
}

