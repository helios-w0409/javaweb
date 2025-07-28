package com.example.demomysql;

public class Users {
    private int userId;
    private String userName;
    private String password;
    private char sex;  // 修改为 String
    private String email;
    private String status; // 用户状态：正常、已注销
    private int role;   // 角色：管理员、普通用户

    // 无参构造方法
    public Users() {}

    // 适用于注册用户（默认状态：正常，角色：普通用户）
    public Users(String userName, String password, char sex, String email,int role) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.status = "正常";  // 默认状态
        this.role = role;  // 默认角色
    }

    // 全参构造方法
    public Users(int userId, String userName, String password, char sex, String email, String status, int role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    // getter和setter方法
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public char getSex() {
        return sex;
    }
    public void setSex(char sex) {
        this.sex = sex;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int  getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
}
