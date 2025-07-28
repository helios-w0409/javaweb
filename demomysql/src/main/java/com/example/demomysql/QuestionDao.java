package com.example.demomysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtil.getConnection(); // 获取数据库连接
            String sql = "SELECT questionId, title, optionA, optionB, optionC, optionD, answer \n" +
                    "FROM question \n" +
                    "ORDER BY RAND() \n" +
                    "LIMIT 4;\n";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // 遍历结果集，将每个问题添加到 List 中
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
                questions.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, ps, rs);  // 关闭连接
        }

        return questions;
    }


        public Question getQuestionById(int id) {
            Question q = null;
            try (Connection conn = JdbcUtil.getConnection()) {
                String sql = "SELECT * FROM question WHERE questionId = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    q = new Question(
                            rs.getInt("questionId"),
                            rs.getString("title"),
                            rs.getString("optionA"),
                            rs.getString("optionB"),
                            rs.getString("optionC"),
                            rs.getString("optionD"),
                            rs.getString("answer")
                    );
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return q;
        }


}
