package com.example.demomysql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        // 确保用户是管理员
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }
        //获取用户信息
        UserDAO userDAO = new UserDAO();
        List<Users> userList = userDAO.getAllUsers();
        //存入request作用域
        request.setAttribute("userList", userList);
        //将数据抛给前端
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}
