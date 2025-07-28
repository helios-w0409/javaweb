package com.example.demoend;

import java.util.List;

public class People {
    private String name;
    private String gender;
    private String password;
    private List<String> hobbies;
    private List<String> fruits;

    public People(String name, String gender, String password, List<String> hobbies, List<String> fruits) {
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.hobbies = hobbies;
        this.fruits = fruits;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getPassword() { return password; }
    public List<String> getHobbies() { return hobbies; }
    public List<String> getFruits() { return fruits; }

    @Override
    public String toString() {
        return "姓名: " + name + ", 性别: " + gender + ", 爱好: " + hobbies + ", 喜欢的水果: " + fruits;
    }
}
