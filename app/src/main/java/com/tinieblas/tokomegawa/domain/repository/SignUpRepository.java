package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.data.remote.SignUpRepositoryImp;
import com.tinieblas.tokomegawa.domain.models.ModeloUsuario;

public interface SignUpRepository {
    void createUserFirebase(String email, String password, SignUpRepositoryImp.SignUpCallback callback);
    String createUser(String email, String password);
    /*Boolean addUser(String userId, String name, String lasName,
                    String direction, Integer age, String email,
                    String password, String username);*/
    String ordenandoInformacionDelUsuario(String nombre, String apellido);


    Boolean addUser(ModeloUsuario modeloUsuario /*String userId, String name, String lasName,
                           String direction, Integer age, String email,
                           String password, String username*/);
}