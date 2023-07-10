package com.tinieblas.tokomegawa.domain.repository;

public interface LoginRepository {

    Boolean getCurrentUser();
    Boolean login(String email, String password);
    String getUIDUser();
    String getEmailUser();




}
