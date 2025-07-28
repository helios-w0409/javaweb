package com.example.demomysql;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import com.example.demomysql.JdbcUtil;
import com.example.demomysql.Question;

@WebServlet("/ListQuestionServlet")
public class ListQuestionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Question> list = new ArrayList<>();//创建一个 ArrayList 来存储从数据库中获取的所有试题对象。
        try (Connection conn = JdbcUtil.getConnection()) {//获取连接
            String sql = "SELECT * FROM question";
            PreparedStatement ps = conn.prepareStatement(sql);//预编译SQL 防止SQL注入
            ResultSet rs = ps.executeQuery();//结果集对象
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("questionId"),
                        rs.getString("title"),
                        rs.getString("optionA"),
                        rs.getString("optionB"),
                        rs.getString("optionC"),
                        rs.getString("optionD"),
                        rs.getString("answer")
                );
                list.add(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("questionList", list);
        request.getRequestDispatcher("questionList.jsp").forward(request, response);
    }
}
