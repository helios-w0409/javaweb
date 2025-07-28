package com.example.usersystemserver.servlet;

import com.example.usersystemserver.util.JDBCUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/api/users/delete")
public class UserDeleteServlet extends HttpServlet {

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);

        response.setContentType("application/json;charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonStr = sb.toString().trim();
        int id = -1;

        // 简单手动解析id，格式为{"id":数字}
        try {
            int idIndex = jsonStr.indexOf("\"id\"");
            if (idIndex != -1) {
                int colonIndex = jsonStr.indexOf(":", idIndex);
                int commaIndex = jsonStr.indexOf(",", colonIndex);
                int endIndex = commaIndex == -1 ? jsonStr.indexOf("}", colonIndex) : commaIndex;
                String idStr = jsonStr.substring(colonIndex + 1, endIndex).trim();
                id = Integer.parseInt(idStr);
            }
        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"message\": \"请求参数错误\"}");
            return;
        }

        if (id == -1) {
            response.getWriter().write("{\"success\": false, \"message\": \"缺少用户ID\"}");
            return;
        }

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();

            if (affected > 0) {
                response.getWriter().write("{\"success\": true, \"message\": \"删除成功\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"用户不存在\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"服务器异常: " + e.getMessage() + "\"}");
        }
    }
}
