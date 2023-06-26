package com.tinieblas.tokomegawa.remote;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginRepositoryImpTest {

    String email = "angelo22@gmail.com";
    String password = "darkangelo";
    LoginRepositoryImp repository;


    @Before
    public void setup(){
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //FirebaseApp.initializeApp(appContext);
        repository = new LoginRepositoryImp();
    }

    @Test
    public void getEmailUser() {
        Boolean isLogged = repository.login(email, password);
        Assert.assertEquals("The user "+ email + " is not Logged",true, isLogged);
    }

    @Test
    public void login(){
    }
}