package com.example.demomysql;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    // 处理GET请求
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从Session中获取用户ID（假设用户登录后，用户ID已经存储在Session中）
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            // 如果没有登录，重定向到登录页面
            response.sendRedirect("login.jsp");
            return;
        }

        // 创建UserDAO实例，获取用户信息
        UserDAO userDAO = new UserDAO();
        Users user = userDAO.getUserById(userId); // 修改为通过UserDAO查询用户信息

        if (user != null) {
            // 将用户信息传递给前端页面
            request.setAttribute("user", user);
            // 转发到用户资料页面（userProfile.jsp）
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
        } else {
            // 用户信息未找到，跳转到错误页面
            response.sendRedirect("errorPage.jsp");
        }
    }
}
