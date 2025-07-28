package com.example.demomysql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.example.demomysql.QuestionDao;
import com.example.demomysql.Question;

//点击开始考试跳转此处
//主要作用是从数据库获取所有试题，随机抽取其中的4道题，并存储到用户的 Session 中
@WebServlet("/ExamServlet")//访问路径
public class ExamServlet extends HttpServlet {//为什么继承自HttpServlet？  Servlet处理web请求，比如按钮点击类似行为，其使用的协议是Http
                                              //继承 HttpServlet，就可以重写 doGet() 和 doPost() 方法，来分别处理不同类型的 HTTP 请求
                                              // 继承 HttpServlet 能做什么？
                                              //可以重写以下方法：
                                              //doGet()：处理 GET 请求（如点击链接、地址栏直接访问）
                                              //doPost()：处理 POST 请求（如表单提交）
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)//点击考试触发
            throws ServletException, IOException {

        // 获取全部题目 JDBC
        QuestionDao dao = new QuestionDao();
        List<Question> allQuestions = dao.getAllQuestions();//调用getAllQuestions()方法，访问所有数据库内的问题，并且返回list集合

        // 随机打乱题目列表  洗牌算法，打乱题目顺序
        Collections.shuffle(allQuestions);

        // 取前4道题作为考试题                         List.subList(fromIndex, toIndex) 是 Java 提供的标准方法，用来从列表中截取一部分数据。
        List<Question> examQuestions = allQuestions.subList(0, 4);//list接口 这个方法包含起始索引，不包含结束索引

        // 保存到 Session 供 exam.jsp 使用
        HttpSession session = request.getSession();//把当前考试要用的4道题保存到服务器的内存中
        session.setAttribute("examQuestions", examQuestions);//用户访问 exam.jsp 时可以直接从 Session 拿到这些题
        //Session 是跟用户绑定的（基于 Cookie 或 URL 重写）。
        //张三抽的题和李四抽的题不一样
        //使用 Session 可以为每位用户保存独立的 examQuestions 列表

        // 跳转到考试页面
        response.sendRedirect("exam.jsp");
        //sendRedirect：✅ 浏览器重新请求 浏览器地址栏变化 用户知道跳转了  适合完成操作后跳转  表单不会重复提交

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // 支持 GET 和 POST 调用
    }//doGet() 调用 doPost()

}
