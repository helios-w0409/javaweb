package com.example.demoend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/form")
public class FormServlet extends HttpServlet {
    private static final List<People> peopleList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求和响应编码，防止乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // 获取表单数据
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String password = req.getParameter("password1");  // ✅ 确保正确传递 password
        List<String> hobbies = req.getParameterValues("hobby") != null ? Arrays.asList(req.getParameterValues("hobby")) : new ArrayList<>();
        List<String> fruits = req.getParameterValues("fruits") != null ? Arrays.asList(req.getParameterValues("fruits")) : new ArrayList<>();

        // ✅ 传递 5 个参数，确保匹配 People 的构造函数
        People person = new People(name, gender, password, hobbies, fruits);
        peopleList.add(person);

        // 控制台输出，确认数据
        System.out.println("=== 用户信息 ===");
        System.out.println(person);
        System.out.println("=================");

        // 生成 HTML 响应
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset=\"utf-8\"><title>表单提交结果</title></head>");
        out.println("<body>");

        out.println("<h1>提交成功！</h1>");
        out.println("<p>当前存储人数：" + peopleList.size() + "</p>");

        // 显示所有用户信息
        out.println("<h2>所有用户数据：</h2><ul>");
        for (People p : peopleList) {
            out.println("<li>姓名: " + p.getName() + ", 性别: " + p.getGender() +
                    ", 爱好: " + p.getHobbies() + ", 喜欢的水果: " + p.getFruits() + "</li>");
        }
        out.println("</ul>");

        // 显示请求头信息
        out.println("<h2>请求头信息：</h2>");
        out.println("<table width=\"80%\" border=\"1\" align=\"center\">");
        out.println("<tr bgcolor=\"#949494\"><th>Header 名称</th><th>Header 值</th></tr>");

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String paramName = headerNames.nextElement();
            String paramValue = req.getHeader(paramName);
            out.println("<tr><td>" + paramName + "</td><td>" + paramValue + "</td></tr>");
        }
        out.println("</table>");

        // **请求信息（优化显示）**
        out.println("<h2>请求信息：</h2>");
        out.println("<table width=\"80%\" border=\"1\" align=\"center\">");
        out.println("<tr><td><strong>请求方式:</strong></td><td>" + req.getMethod() + "</td></tr>");
        out.println("<tr><td><strong>客户端的 IP 地址:</strong></td><td>" + req.getRemoteAddr() + "</td></tr>");
        out.println("<tr><td><strong>应用名字 (上下文):</strong></td><td>" + req.getContextPath() + "</td></tr>");
        out.println("<tr><td><strong>URI:</strong></td><td>" + req.getRequestURI() + "</td></tr>");
        out.println("<tr><td><strong>请求字符串:</strong></td><td>" + (req.getQueryString() != null ? req.getQueryString() : "无") + "</td></tr>");
        out.println("<tr><td><strong>Servlet 所映射的路径:</strong></td><td>" + req.getServletPath() + "</td></tr>");
        out.println("<tr><td><strong>客户端的完整主机名:</strong></td><td>" + req.getRemoteHost() + "</td></tr>");
        out.println("</table>");

        // 添加返回按钮
        out.println("<br><button onclick='window.location.href=\"index.jsp\"'>返回</button>");
        out.println("</body></html>");
    }
}
