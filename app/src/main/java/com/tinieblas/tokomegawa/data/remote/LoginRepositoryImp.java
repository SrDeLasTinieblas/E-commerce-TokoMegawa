package com.tinieblas.tokomegawa.data.remote;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.domain.repository.LoginRepository;

public class LoginRepositoryImp implements LoginRepository {

    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    private void definingFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
    }



    /*private void definingFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        user = mAuth.getCurrentUser(); // Asignar el valor actual de mAuth.getCurrentUser() a 'user'
    }
*/

    @Override
    public Boolean getCurrentUser() {
        definingFirebase();
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }


    @Override
    public Boolean login(String email, String password) {
        try {
            definingFirebase();

            // Realiza el inicio de sesión de forma asíncrona
            Task<AuthResult> signInTask = mAuth.signInWithEmailAndPassword(email, password);
            AuthResult authResult = Tasks.await(signInTask);

            // Verifica si el inicio de sesión fue exitoso
            if (authResult != null) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }

    @Override
    public String getUIDUser() {
        definingFirebase();
        return mAuth.getUid();
    }

    @Override
    public String getEmailUser() {
        definingFirebase();
        FirebaseUser user = mAuth.getCurrentUser();
        return user.getEmail();
    }


    public String CreateAcount(String email, String password){
        try {
            definingFirebase();
            Task<AuthResult> signInTask = mAuth.createUserWithEmailAndPassword(email, password);
            AuthResult authResult = Tasks.await(signInTask);
            return authResult.toString();
        }catch (Exception e){
            System.out.println(e);
        }
        return "";
    }


    public String updateEmail(String correoActualizado) {
        String error = "";
        try {
            definingFirebase();

            // Reautenticar al usuario
            AuthCredential credential = EmailAuthProvider.getCredential("nuevocorreo@gmail.com", "darkangelo");
            Task<Void> reauthenticateTask = user.reauthenticate(credential);
            Tasks.await(reauthenticateTask); // Esperar a que se complete la tarea de reautenticación

            if (reauthenticateTask.isSuccessful()) {
                // El usuario ha sido reautenticado exitosamente, ahora podemos actualizar el correo electrónico
                Task<Void> updateEmailTask = user.updateEmail(correoActualizado);
                Tasks.await(updateEmailTask); // Esperar a que se complete la tarea de actualización de correo electrónico

                if (updateEmailTask.isSuccessful()) {
                    Log.d("TAG", "User email address updated.");
                    return "User email address updated.";
                }
            }
        } catch (Exception e) {
            error = e.getMessage();
            return "Error updating user email address: " + e.getMessage();
        }

        return "Error updating user email address" + error;
    }



}
