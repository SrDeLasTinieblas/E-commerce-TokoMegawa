package com.tinieblas.tokomegawa.domain.models;

public class ModeloUsuario {
    private String userId;
    private String name;
    private String lastName;
    private String direction;
    private Integer age;
    private String email;
    private String password;
    private String username;

    // Constructor de la clase User
    public ModeloUsuario(String userId, String name, String lastName, String direction,
                Integer age, String email, String password, String username) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.direction = direction;
        this.age = age;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}



