package com.tinieblas.tokomegawa.domain.repository;

public interface SignUpRepository {
    // databaseReference = FirebaseDatabase.getInstance().getReference();
    String createUser(String email, String password);

    Boolean addUser(String userId, String name, String lasName, String direction, Integer age, String email, String password, String username);


}