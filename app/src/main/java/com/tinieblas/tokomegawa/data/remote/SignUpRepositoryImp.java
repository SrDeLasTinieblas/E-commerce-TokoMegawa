package com.tinieblas.tokomegawa.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.domain.models.ModeloUsuario;
import com.tinieblas.tokomegawa.domain.repository.SignUpRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SignUpRepositoryImp implements SignUpRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String collectionUser = "Usuarios";

    private void definingFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
    }

   public void createUser(String email, String password, SignUpCallback callback) {
       definingFirebase();
       mAuth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       String uid = task.getResult().getUser().getUid();
                       callback.onSuccess(uid);
                   } else {
                       String errorMessage = task.getException().getMessage();
                       callback.onFailure(errorMessage);
                   }
               });
   }
    public String ordenandoInformacionDelUsuario(String nombre, String apellido) {
        String[] apellidos = apellido.split(" ");
        StringBuilder nuevoApellido = new StringBuilder();

        // Verificar si hay más de un apellido
        if (apellidos.length > 1) {
            // Obtener el primer apellido
            String primerApellido = apellidos[0];

            // Obtener el segundo apellido
            String segundoApellido = apellidos[1].substring(0, 1) + ".";

            nuevoApellido.append(primerApellido);
            nuevoApellido.append(" ");
            nuevoApellido.append(segundoApellido);
        } else {
            nuevoApellido.append(apellido);
        }

        // Construir el nuevo nombre con el formato: Apellido, Inicial del segundo apellido. Nombre
        StringBuilder nuevoNombre = new StringBuilder();
        nuevoNombre.append(nuevoApellido.toString());
        nuevoNombre.append(" ");
        nuevoNombre.append(nombre);

        return nuevoNombre.toString();
    }

    @Override
    public void createUserFirebase(String email, String password, SignUpCallback callback) {
        definingFirebase();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        callback.onSuccess(uid);
                    } else {
                        String errorMessage = task.getException().getMessage();
                        callback.onFailure(errorMessage);
                    }
                });
    }

    @Override
    public String createUser(String email, String password) {
            definingFirebase();
            try {
                Task<AuthResult> signInTask = mAuth.createUserWithEmailAndPassword(email, password);
                AuthResult authResult = Tasks.await(signInTask);

                return authResult.getUser().getUid();
            } catch (Exception exception) {
                return exception.toString();
            }
    }

    @Override
    public Boolean addUser(ModeloUsuario modeloUsuario /*String userId, String name, String lasName,
                           String direction, Integer age, String email,
                           String password, String username */) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", modeloUsuario.getUserId());
            map.put("nombres", modeloUsuario.getName());
            map.put("apellidos", modeloUsuario.getLastName());
            map.put("direccion", modeloUsuario.getDirection());
            map.put("edad", modeloUsuario.getAge());
            map.put("mail", modeloUsuario.getEmail());
            map.put("password", modeloUsuario.getPassword());
            map.put("username", modeloUsuario.getUsername());

            DocumentReference documentRef = firebaseFirestore.collection(collectionUser).document(modeloUsuario.getUserId());

            // Realiza la operación de escritura de forma asíncrona
            Task<Void> setTask = documentRef.set(map);
            Tasks.await(setTask);
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
        }


        return false;
    }


    public interface SignUpCallback {
        void onSuccess(String uid);
        void onFailure(String errorMessage);

    }

}










