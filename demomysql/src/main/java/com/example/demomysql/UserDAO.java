package com.example.demomysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import java.util.List;
import java.util.ArrayList;


public class UserDAO {
    // 添加这行定义
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());
    /**
     * 通过用户名和密码验证用户登录
     * @param userName 用户名
     * @param password 密码
     * @return 如果验证成功，返回对应的用户信息；否则返回null
     */
    public Users login(String userName, String password) {
        // 定义SQL查询语句
        String sql = "SELECT * FROM Users WHERE userName = ? AND password = ?";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置SQL语句中的参数
            pstmt.setString(1, userName);
            pstmt.setString(2, password); // 在实际应用中，最好对密码进行加密存储和验证

            // 执行查询
            ResultSet rs = pstmt.executeQuery();

            // 如果有记录，说明用户存在，返回用户信息
            if (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password")); // 你可以选择是否加密存储密码
                user.setSex(rs.getString("sex").charAt(0)); // 将性别从 String 转换为 char
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getInt("role")); // 确保 role 存储的是 0 或 1
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 错误打印，生产环境应记录日志
        }

        // 如果没有找到用户，返回null
        return null;
    }

    /**
     * 用户注册
     * @param user 用户对象
     * @return 如果插入成功返回true，否则返回false
     */
    public boolean registerUser(Users user) {
        // 定义SQL插入语句
        String sql = "INSERT INTO Users (userName, password, sex, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置SQL语句中的参数
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword()); // 可以加密密码后再存储
            pstmt.setString(3, String.valueOf(user.getSex())); // 将性别的 char 转换为 String
            pstmt.setString(4, user.getEmail());

            // 执行插入操作
            int count = pstmt.executeUpdate();

            // 如果影响的行数大于0，说明插入成功
            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // 错误打印，生产环境应记录日志
        }

        // 如果插入失败，返回false
        return false;
    }


    public Users getUserById(int userId) {
        // 定义SQL查询语句
        String sql = "SELECT * FROM Users WHERE userId = ?";

        // 定义数据库连接对象和结果集对象
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置SQL语句中的参数
            pstmt.setInt(1, userId);

            // 执行查询
            ResultSet rs = pstmt.executeQuery();

            // 如果有记录，说明用户存在，返回用户信息
            if (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getString("sex").charAt(0)); // 将性别从 String 转换为 char
                user.setEmail(rs.getString("email"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 错误打印，生产环境应记录日志
        }

        // 如果没有找到用户，返回null
        return null;
    }

    public boolean updateUser(Users user) {
        String sql = "UPDATE Users SET email = ? WHERE userName = ?"; // 更新 email

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 绑定正确的参数
            pstmt.setString(1, user.getEmail()); // 设置 email
            pstmt.setString(2, user.getUserName()); // 根据用户名更新记录

            int count = pstmt.executeUpdate(); // 执行更新操作
            return count > 0; // 如果更新了记录，返回 true
        } catch (SQLException e) {
            logger.severe("数据库错误: " + e.getMessage()); // 打印错误日志
        }
        return false; // 如果没有更新任何记录，返回 false
    }



    public List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setSex(rs.getString("sex").charAt(0));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getInt("role"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }



}
