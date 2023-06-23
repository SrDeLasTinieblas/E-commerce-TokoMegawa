package com.tinieblas.tokomegawa.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import android.os.Build;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class SumaTest {

    private FirebaseAuth mAuth;

    public void setup() {
        MockitoAnnotations.initMocks(this);
        mAuth = Mockito.mock(FirebaseAuth.class);
    }

    @Test
    public void loginTest() throws InterruptedException {
        setup();
        String email = "angelo21@gmail.com";
        String password = "darkangelo";
        final boolean[] loginResult = {false};
        // Simular el comportamiento de FirebaseAuth
        FirebaseUser mockUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(mAuth.getCurrentUser()).thenReturn(mockUser);
        // Simular el inicio de sesión exitoso
        Task<AuthResult> successfulTask = Tasks.forResult(Mockito.mock(AuthResult.class));
        Mockito.when(mAuth.signInWithEmailAndPassword(email, password)).thenReturn(successfulTask);
        // Crear CountDownLatch con un contador de 1
        CountDownLatch latch = new CountDownLatch(1);
        // Realizar el inicio de sesión
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Executors.newSingleThreadExecutor(), task -> {
                    loginResult[0] = task.isSuccessful();
                    // Contar down latch para indicar que la tarea se ha completado
                    latch.countDown();
                });
        // Esperar hasta que la tarea se complete
        latch.await();
        assertTrue(loginResult[0]);
    }
    @Test
    public void loginTest1() throws InterruptedException {
        setup();
        String email = "angelo22@gmail.com";
        String password = "darkangelo";
        final boolean[] loginResult = {false};
        FirebaseUser mockUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(mAuth.getCurrentUser()).thenReturn(mockUser);
        Task<AuthResult> successfulTask = Tasks.forResult(Mockito.mock(AuthResult.class));
        Mockito.when(mAuth.signInWithEmailAndPassword(email, password)).thenReturn(successfulTask);
        Task<AuthResult> failedTask = Tasks.forException(new FirebaseAuthInvalidCredentialsException("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
        Mockito.when(mAuth.signInWithEmailAndPassword(email, "contraseña incorrecta")).thenReturn(failedTask);
        CountDownLatch latch = new CountDownLatch(1);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Executors.newSingleThreadExecutor(), task -> {
                    if (task.isSuccessful()) {
                        loginResult[0] = true;
                    }
                    latch.countDown();
                });
        latch.await();
        mAuth.signInWithEmailAndPassword(email, "contraseña incorrecta")
                .addOnCompleteListener(Executors.newSingleThreadExecutor(), task -> {
                    if (!task.isSuccessful()) {
                        loginResult[0] = false;
                    }
                    latch.countDown();
                });
        latch.await();
        assertTrue(loginResult[0]);
    }





}









