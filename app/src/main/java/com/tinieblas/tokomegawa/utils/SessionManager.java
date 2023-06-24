package com.tinieblas.tokomegawa.utils;

import com.tinieblas.tokomegawa.ui.fragments.LoginFragment;

public class SessionManager extends LoginFragment {

/*   private boolean isSuccessfulLogin;
    //private FragmentLoginBinding fragmentLoginBinding;

    public void startSession(AwesomeValidation awesomeValidation, LoginRepositoryImp repository) {
        if (awesomeValidation.validate()) {
            // Obtener el email y la contraseña ingresados por el usuario
            String email = "angelo22@gmail.com";
            String password = "darkangelo";

            // Inicializar Firebase si aún no se ha inicializado
            if (FirebaseApp.getApps(ApplicationProvider.|getApplicationContext()).isEmpty()) {
                FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext());
            }

            // Iniciar sesión con Firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Inicio de sesión exitoso
                                isSuccessfulLogin = true;
                            } else {
                                // Error en el inicio de sesión
                                isSuccessfulLogin = false;
                            }
                        }
                    });
        } else {
            isSuccessfulLogin = false;
        }
    }

    public boolean isSuccessfulLogin() {
        return isSuccessfulLogin;
    }*/

}

