package com.example.demomysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;

//@WebServlet("/UpdateProfileServlet")  // 添加这行注解
public class UserUpdate {

    // 修改用户信息，只有本人能更新自己的信息
    public boolean updateUserInfo(int userID, String userName, String password, String sex, String email) {
        // 1. 获取数据库连接
        String url = "jdbc:mysql://localhost:3306/schema_name?useUnicode=true&characterEncoding=utf-8";
        String dbUsername = "root";
        String dbPassword = "41117181925";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {

            // 2. 编写 SQL 更新语句
            String sql = "UPDATE Users SET userName = ?, password = ?, sex = ?, email = ? WHERE userId = ?";

            // 3. 获取 PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // 4. 设置参数
                pstmt.setString(1, userName);
                pstmt.setString(2, password);
                pstmt.setString(3, sex);
                pstmt.setString(4, email);
                pstmt.setInt(5, userID);

                // 5. 执行 SQL 更新
                int count = pstmt.executeUpdate(); // count 为影响的行数

                // 6. 返回结果，表示是否更新成功
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 打印 SQL 异常
            return false;
        }
    }
}
