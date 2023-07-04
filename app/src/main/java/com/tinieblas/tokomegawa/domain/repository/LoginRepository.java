package com.tinieblas.tokomegawa.domain.repository;

public interface LoginRepository {

    Boolean getCurrentUser();
    //String getNameUser();

    Boolean login(String email, String password);

    String getUIDUser();


    String getEmailUser();
}
