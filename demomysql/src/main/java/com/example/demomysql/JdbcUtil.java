package com.example.demomysql;

import java.sql.*;

public class JdbcUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // 修改驱动类
    private static final String URL = "jdbc:mysql://localhost:3306/schema_name?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "41117181925";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库驱动加载失败！");
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("数据库连接失败：" + e.getMessage());
        }
        return conn;
    }

    /**
     * 关闭数据库资源
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库资源（无 ResultSet）
     */
    public static void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null);
    }
}
