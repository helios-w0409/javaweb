package com.example.demomysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDelete {

    // 注销用户（删除操作）
    public boolean deleteUser(int userID) {
        // 1. 获取数据库连接
        String url = "jdbc:mysql://localhost:3306/schema_name?useUnicode=true&characterEncoding=utf-8";
        String dbUsername = "root";
        String dbPassword = "41117181925";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // 2. 定义 SQL 语句
            String sql = "DELETE FROM Users WHERE userId = ?";

            // 3. 获取 PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // 4. 设置参数
                pstmt.setInt(1, userID);

                // 5. 执行 SQL 删除
                int count = pstmt.executeUpdate(); // count 为影响的行数

                // 6. 返回结果，表示是否删除成功
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 打印 SQL 异常
            return false;

        }
    }
}
