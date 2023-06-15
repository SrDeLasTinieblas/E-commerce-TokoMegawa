package com.tinieblas.tokomegawa.data.remote;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.domain.repository.LoginRepository;
import com.tinieblas.tokomegawa.ui.fragments.HomeFragment;

public class LoginRepositoryImp implements LoginRepository {



    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    private void definingFirebase(){
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

    }



    @Override
    public Boolean getCurrentUser() {
        definingFirebase();
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

    @Override
    public Boolean login(String email, String password) {
        try {

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




}
