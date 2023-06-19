package com.tinieblas.tokomegawa.utils;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class SumaTest {

    @Test
    public void sumando() {
        System.out.println("Sumando..");
        assertEquals(4, 2 + 2);
        //Assert.assertEquals(5, 6);
    }

    public void addition_isCorrect() {
        //System.out.println("adition");
        //Log.d("TAG", "Este es un mensaje de prueba");
        assertEquals(5, 2 + 2);
    }

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    /*public boolean startSession(String email, String password) {
        try {
            // Validando usuario y contraseña
            if (awesomeValidation.validate()) {
                firebaseAuth = FirebaseAuth.getInstance(); // Inicializar firebaseAuth aquí

                Task<AuthResult> signInTask = firebaseAuth.signInWithEmailAndPassword(email, password);
                try {
                    Tasks.await(signInTask); // Esperar a que se complete el inicio de sesión
                    // Inicio de sesión fallido
                    return signInTask.isSuccessful();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (Exception ex) {
            // Manejo de excepciones
        }
        return false; // Retornar false en caso de que no se cumplan las condiciones anteriores
    }

    @Test
    public void testStartSession() {
        // Prueba 1: Inicio de sesión exitoso
        boolean result1 = startSession("angelo22@gmail.com", "darkangelo");
        assertTrue(result1);
        // Prueba 2: Inicio de sesión fallido
        //boolean result2 = startSession("example@example.com", "password");
        //assertFalse(result2);
    }

*/

}









