package com.example.demomysql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.demomysql.Question;

import java.io.IOException;
import java.util.List;

//判卷功能 从 Session 获取题目列表 → 获取用户答案 → 和正确答案比对 → 计算得分 → 保存分数 → 跳转到 score.jsp 展示
@WebServlet("/JudgeServlet")
public class JudgeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 从 session 中获取试题
        HttpSession session = request.getSession();
        List<Question> examQuestions = (List<Question>) session.getAttribute("examQuestions");

        int score = 0;

        for (int i = 0; i < examQuestions.size(); i++) {//for循环获取每一道题目
            // 获取用户提交的答案
            //request.getParameter(...) 是用来获取前端表单 <input> 提交过来的数据
            String userAnswer = request.getParameter("answer" + i);//动态生成了每道题的 name 属性值，i 是当前题目的序号。
            if (userAnswer == null) userAnswer = ""; // 避免空指针 如果用户没答题，避免 null 值，给一个空字符串
            userAnswer = userAnswer.trim();//trim() 方法会去除字符串的前后空白字符

            // 获取正确答案
            String correctAnswer = examQuestions.get(i).getAnswer().trim();//getAnswer()返回当前题目的正确答案。

            // 忽略大小写比较
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {//比较这两个字符是否相同 忽略大小写
                score += 25; // 每题25分
            }
        }

        // 保存分数到 session，供 score.jsp 使用
        session.setAttribute("score", score);

        // 跳转到分数页面
        response.sendRedirect("score.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // 支持 GET 请求
    }
}
