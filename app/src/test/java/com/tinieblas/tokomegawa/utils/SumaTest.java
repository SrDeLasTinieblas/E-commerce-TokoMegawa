package com.tinieblas.tokomegawa.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.junit.Test;


public class SumaTest {

    /*@Test
    public void sumando() {
        assertEquals(4, 2 + 2);
    }*/
/*
    public void addition_isCorrect() {
        //System.out.println("adition");
        //Log.d("TAG", "Este es un mensaje de prueba");
        assertEquals(5, 2 + 2);
    }
*/
    //AwesomeValidation awesomeValidation;
    //FirebaseAuth firebaseAuth;

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
    private FirebaseAuth mAuth;

    @Test
    public void loginTest() {
        mAuth = FirebaseAuth.getInstance();

        // Aquí puedes ingresar el correo electrónico y la contraseña para hacer el testing
        String email = "angelo22@gmail.com";
        String password = "darkangelo";

        final boolean[] loginResult = {false}; // Variable booleana para almacenar el resultado del inicio de sesión

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // El inicio de sesión fue exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // El usuario está autenticado, puedes realizar acciones adicionales aquí
                            loginResult[0] = true; // Establecer el resultado del inicio de sesión como verdadero
                        }
                    } else {
                        // El inicio de sesión falló
                        loginResult[0] = false; // Establecer el resultado del inicio de sesión como falso
                    }
                });

        // Verificar el resultado esperado
        assertTrue(loginResult[0]);
    }

}









