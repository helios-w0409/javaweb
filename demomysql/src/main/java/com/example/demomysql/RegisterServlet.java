package com.example.demomysql;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/RegisterServlet")  // 添加这行注解
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户提交的数据
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");
        int role = Integer.parseInt(request.getParameter("role")); // 从前端获取 role


        // 检查 sex 是否为空并且是单个字符
        if (sex != null && sex.length() == 1) {
            // 创建用户对象并设置用户信息
            Users newUser = new Users(userName, password, sex.charAt(0), email,role); // 需要把性别从 String 转为 char

            // 调用 DAO 类进行数据库插入
            UserDAO userDAO = new UserDAO();
            boolean isRegistered = userDAO.registerUser(newUser);

            // 根据注册结果返回页面
            if (isRegistered) {
                //response.sendRedirect("login.jsp"); // 注册成功，重定向到登录页面
                // 方式1：添加上下文路径（推荐）
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {
                response.getWriter().println("注册失败，请重试！");
            }
        } else {
            // 如果 sex 为空或者不合法
            response.getWriter().println("性别输入无效，请重新输入！");
        }
    }
}
