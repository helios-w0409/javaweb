package com.example.demomysql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    // 使用 Java 自带的日志工具
    private static final Logger logger = Logger.getLogger(UpdateProfileServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 2. 获取当前用户（从 session）
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        if (currentUser == null) {
            logger.warning("未登录用户尝试更新信息，重定向到登录页面");
            response.sendRedirect("login.jsp");
            return;
        }//正常执行

        // 3. 获取表单参数
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");

        // 4. 参数校验（不能为空）
        if (userName == null || password == null || sex == null || email == null ||
                userName.isEmpty() || password.isEmpty() || sex.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "所有字段都必须填写");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // 5. 更新用户对象
        currentUser.setUserName(userName);
        currentUser.setPassword(password); // 实际项目中应该加密
        currentUser.setSex(sex.charAt(0));
        currentUser.setEmail(email);

        // 6. 调用 DAO 更新数据库
        try {
            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.updateUser(currentUser);

            if (success) {
                logger.info("用户更新成功 - ID: " + currentUser.getUserId());
                session.setAttribute("user", currentUser); // 更新 session
                response.sendRedirect("profile.jsp?msg=update_success");
            } else {
                logger.warning("用户更新失败 - ID: " + currentUser.getUserId());
                request.setAttribute("error", "更新失败，请重试");
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.severe("数据库更新失败 - 用户ID: " + currentUser.getUserId() + "，错误: " + e.getMessage());
            request.setAttribute("error", "系统错误，请稍后再试");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }
}
