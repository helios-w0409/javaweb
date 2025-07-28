package com.example.demomysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCheck {

    /**
     * 查询所有用户信息（仅管理员可用）
     */
    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword("******"); // 不返回密码，避免泄露

                String sex = rs.getString("sex");
                user.setSex(sex.charAt(0));  // 设置性别为字符串
                user.setEmail(rs.getString("email"));

                // 将用户信息加入列表
                users.add(user);
            }

        } catch (SQLException e) {
            // 在生产环境中，你应该记录日志而不是仅仅打印堆栈
            e.printStackTrace();
        }

        return users;
    }
}
