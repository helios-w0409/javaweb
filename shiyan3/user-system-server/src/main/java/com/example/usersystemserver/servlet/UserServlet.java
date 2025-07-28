package com.example.usersystemserver.servlet;

import com.example.usersystemserver.model.User;
import com.example.usersystemserver.util.JDBCUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/users")
public class UserServlet extends HttpServlet {

    // 统一设置跨域响应头
    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    // 处理预检请求
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // 处理 GET 请求，返回用户列表
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        List<User> users = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, username, role FROM users");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"服务器异常: " + e.getMessage() + "\"}");
            return;
        }

        // 拼接 JSON 字符串
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\": true, \"users\": [");

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            sb.append("{")
                    .append("\"id\":").append(u.getId()).append(",")
                    .append("\"username\":\"").append(u.getUsername()).append("\",")
                    .append("\"role\":\"").append(u.getRole()).append("\"")
                    .append("}");

            if (i < users.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]}");

        response.getWriter().write(sb.toString());
    }
}
