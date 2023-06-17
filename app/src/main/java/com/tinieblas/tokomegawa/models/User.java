package com.tinieblas.tokomegawa.models;


public class User {
    // angelo22@gmail.com
    // darkangelo
    public static final String KEY_NOMBRES = "nombres";
    public static final String KEY_APELLIDOS = "apellidos";

    private final String uid;
    private final String firstName;
    private final String lastName;

    public User(String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}