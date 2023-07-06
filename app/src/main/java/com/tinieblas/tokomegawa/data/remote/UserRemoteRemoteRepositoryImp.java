package com.tinieblas.tokomegawa.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.domain.repository.UserRemoteRepository;

public class UserRemoteRemoteRepositoryImp implements UserRemoteRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    private void definingFirebase(){
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

    }
    String userCollections = "Usuarios";
    String nameValue = "nombres";


    // Creamos la funcion para guardar los datos
    @Override
    public String getUser() {
        //final String[] apellidos = new String[1];
        try {
            // Llamamos a la funcion para definir firebase
            definingFirebase();
            // Creamos una nueva collecion en firebase de nombre "usuario"
            DocumentReference documentReference = firebaseFirestore.collection(userCollections).document(getCurrentUserId());

            // Obtenemos el documento de manera sincrónica utilizando Tasks.await()
            Task<DocumentSnapshot> task = documentReference.get();
            DocumentSnapshot document = Tasks.await(task);

            if (document.exists()) {
                // El documento existe, puedes acceder a sus datos
                return document.getString(nameValue);
            } else {
                // El documento no existe
                System.out.println("El documento no existe.");
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return null;

    }


    @Override
    public void uploadUser() {
//        FirebaseData firebaseData = new FirebaseData();
//        firebaseData.getDataUser( new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
//                firebaseFirestore = FirebaseFirestore.getInstance();
//                // Recuperamos nombre
//                name = value.getString("nombres");
//            }
//        });
    }

    @Override
    public String getCurrentUserId() {
        return mAuth.getCurrentUser().getUid();
    }








}
