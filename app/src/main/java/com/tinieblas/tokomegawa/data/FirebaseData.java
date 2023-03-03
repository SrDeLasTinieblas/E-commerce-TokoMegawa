package com.tinieblas.tokomegawa.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FirebaseData {
    private String idUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String name;
    String apellidos;
    //private final FirebaseData firebaseData = new FirebaseData();

    // Creamos la funcion para guardar los datos
    public void getDataUser(EventListener<DocumentSnapshot> listener) {
        //final String[] apellidos = new String[1];
        try {
            // Llamamos a la funcion para definir firebase
            definingFirebase();
            // Creamos una nueva collecion en firebase de nombre "usuario"
            DocumentReference documentReference = firebaseFirestore.collection("Usuarios").document(idUser);
            documentReference.addSnapshotListener(listener);
        } catch (Exception e) {
            System.err.println(e);
        }
        //return apellidos[0];

    }

    public void uploadDataFireBase(Context context) {
        FirebaseData firebaseData = new FirebaseData();
        firebaseData.getDataUser( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                // Recuperamos nombre
                name = value.getString("nombres");
            }
        });
    }

    private void definingFirebase(){
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        idUser = mAuth.getCurrentUser().getUid();
    }

}

