
package com.example.usersystemserver.servlet;

import com.example.usersystemserver.util.JDBCUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    // 跨域设置方法（可以封装复用）
    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        // 读取请求体字符串
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonStr = sb.toString().trim();
        String username = "";
        String password = "";

        // 简单解析json字符串获取用户名和密码（你可以用更好的json解析）
        try {
            int uStart = jsonStr.indexOf("\"username\"");
            if (uStart != -1) {
                int uColon = jsonStr.indexOf(":", uStart);
                int uQuoteStart = jsonStr.indexOf("\"", uColon + 1);
                int uQuoteEnd = jsonStr.indexOf("\"", uQuoteStart + 1);
                username = jsonStr.substring(uQuoteStart + 1, uQuoteEnd);
            }
            int pStart = jsonStr.indexOf("\"password\"");
            if (pStart != -1) {
                int pColon = jsonStr.indexOf(":", pStart);
                int pQuoteStart = jsonStr.indexOf("\"", pColon + 1);
                int pQuoteEnd = jsonStr.indexOf("\"", pQuoteStart + 1);
                password = jsonStr.substring(pQuoteStart + 1, pQuoteEnd);
            }
        } catch (Exception e) {
            out.write("{\"success\": false, \"message\": \"JSON 解析失败\"}");
            return;
        }

        if (username.isEmpty() || password.isEmpty()) {
            out.write("{\"success\": false, \"message\": \"用户名或密码不能为空\"}");
            return;
        }

        // 查询数据库验证用户名密码，并获取角色
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT role FROM users WHERE username = ? AND password = ?")) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                // 登录成功，返回角色给前端
                out.write("{\"success\": true, \"role\": \"" + role + "\"}");
            } else {
                out.write("{\"success\": false, \"message\": \"用户名或密码错误\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"success\": false, \"message\": \"服务器异常：" + e.getMessage() + "\"}");
        }

        out.flush();
        out.close();
    }
}
