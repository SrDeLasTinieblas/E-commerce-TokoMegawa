package com.tinieblas.tokomegawa.remote;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.data.remote.SignUpRepositoryImp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.manipulation.Ordering;

public class SignUpRepositoryImpTest {
    String email = "vaeaaaaa@gmail.com";
    String password = "1234567890";

    SignUpRepositoryImp repository;

    @Before
    public void setup(){
        repository = new SignUpRepositoryImp();
    }
    @Test
    public void testCreateUser() {
        String result = repository.createUser(email, password);
        boolean isSuccess = result != null && !result.isEmpty();
        assertTrue(isSuccess);
    }

    @Test
    public void addUser() {
    }
}