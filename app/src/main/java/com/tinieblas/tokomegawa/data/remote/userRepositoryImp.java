package com.tinieblas.tokomegawa.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.tinieblas.tokomegawa.domain.repository.UserRepository;
import com.tinieblas.tokomegawa.respositories.FirebaseData;
/*
public class userRepositoryImp implements UserRepository {

    private String idUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String name;
    String apellidos;
    //private final FirebaseData firebaseData = new FirebaseData();

    String userCollections = "Usuarios";
    String nameValue = "";

    // Creamos la funcion para guardar los datos
   /** @Override
    public String getUser() {
        //final String[] apellidos = new String[1];
        try {
            // Llamamos a la funcion para definir firebase
            definingFirebase();
            // Creamos una nueva collecion en firebase de nombre "usuario"
            DocumentReference documentReference = firebaseFirestore.collection("Usuarios").document(idUser);

            Task<DocumentSnapshot> task = documentReference.get();
            DocumentSnapshot document = Tasks.await(task);

            if (document.exists()) {
                return document.getString("nombre");
            } else {
                System.out.println("El documento no existe");
            }

            //documentReference.addSnapshotListener(listener);
        } catch (Exception e) {
            System.err.println(e);
        }

    }*/
/*
    @Override
    public void uploadUser() {
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



    @Override
    public String getCurrentUserID() {
        return mAuth.getCurrentUser().getUid();
    }

    private void definingFirebase(){
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
    }








}*/
