package com.tinieblas.tokomegawa.domain.repository.respositories;

import android.content.Context;

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
    }

    public void uploadDataFireBase() {
        FirebaseData firebaseData = new FirebaseData();
        firebaseData.getDataUser((value, error) -> {
            firebaseFirestore = FirebaseFirestore.getInstance();

            name = value.getString("nombres");
        });
    }

    private void definingFirebase(){
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        idUser = mAuth.getCurrentUser().getUid();
    }

}

