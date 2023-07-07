package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.data.remote.SignUpRepositoryImp;
//import com.tinieblas.tokomegawa.domain.repository.SignUpCallback;

public interface SignUpRepository {
    String createUserFirebase(String email, String password, SignUpRepositoryImp.SignUpCallback callback);

    // databaseReference = FirebaseDatabase.getInstance().getReference();
    String createUser(String email, String password);
    //String createUserFirebase(String email, String password, SignUpCallback callback);
    Boolean addUser(String userId, String name, String lasName, String direction, Integer age, String email, String password, String username);

    String ordenandoInformacionDelUsuario(String nombre, String apellido);


}