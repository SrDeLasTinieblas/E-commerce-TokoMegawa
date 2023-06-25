package com.tinieblas.tokomegawa.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class FirebaseAuthenticationTest {
    @Mock
    private FirebaseAuth mAuth;
    // Obtener el contexto de la aplicación
    Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //FirebaseApp.initializeApp(TestFirebaseApp.getInstance());
        mAuth = Mockito.mock(FirebaseAuth.class);
    }


    @Test
    public void testSignInWithEmailAndPassword() {
        // Datos de prueba
        String email = "example@example.com";
        String password = "password123";

        // Simular el inicio de sesión exitoso
        FirebaseUser mockUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(mAuth.getCurrentUser()).thenReturn(mockUser);

        // Iniciar sesión con el correo electrónico y contraseña proporcionados
        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // El inicio de sesión fue exitoso
                            FirebaseUser user = mAuth.getCurrentUser();
                            Assert.assertNotNull(user);  // Verificar que el usuario no sea nulo
                            Assert.assertEquals(email, user.getEmail());  // Verificar el correo electrónico
                            // Aquí puedes agregar más aserciones si es necesario
                            System.out.println("Inicio de sesión exitoso");
                        } else {
                            // El inicio de sesión falló
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthInvalidUserException) {
                                System.out.println("El correo electrónico no está registrado");
                            } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                System.out.println("La contraseña es incorrecta");
                            } else {
                                System.out.println("Error al iniciar sesión: " + exception.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
    }
}
