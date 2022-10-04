package com.dreamer.classLoader.demo;

public class User {

    private Integer age;

    private String name;


    private Integer phone;

    private String address;


    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                '}';
    }
}
