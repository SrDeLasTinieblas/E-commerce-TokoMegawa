package com.tinieblas.tokomegawa.remote;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class FirebaseInstrumentedTest {

    @Test
    public void login() throws ExecutionException, InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Assert.assertEquals("com.tinieblas.tokomegawa", appContext.getPackageName());

        FirebaseApp.initializeApp(appContext);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String email = "axopotamre@gmail.com";
        String password = "123456789";

        Task<AuthResult> signInTask = mAuth.signInWithEmailAndPassword(email, password);
        AuthResult authResult = Tasks.await(signInTask);

        Assert.assertNotNull(authResult);
        Assert.assertEquals(email, authResult.getUser().getEmail());
    }
}