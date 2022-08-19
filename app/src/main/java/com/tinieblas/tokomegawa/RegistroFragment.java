package com.tinieblas.tokomegawa;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.databinding.FragmentRegistroBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistroFragment extends Fragment {

    RegistroFragment registroFragment;
    private FragmentRegistroBinding fragmentRegistroBinding;
    FirebaseFirestore mFirestore;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    ArrayList<Integer> edades = new ArrayList<>();
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        registroFragment = this;
        fragmentRegistroBinding = fragmentRegistroBinding.inflate(inflater, container, false);

        //ArrayList<Integer> edades = new ArrayList<>();
        for (int i = 18; i < 91; i++){
            edades.add(i);
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, edades);

        fragmentRegistroBinding.spinnerOld.setAdapter(adapter);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation((Activity) getContext(),
                R.id.textEmail, Patterns.EMAIL_ADDRESS,
                R.string.invalid_mail);

        awesomeValidation.addValidation((Activity) getContext(),
                R.id.textPassword, ".{6,}",
                R.string.invalid_password);
        mFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        fragmentRegistroBinding.buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpDataToAuthentication();
            }
        });

        // Inflate the layout for this fragment
        return fragmentRegistroBinding.getRoot();
    }

    //                          (nombres, apellidos, direccion, edad, mail, password, username
    private void registerUser(String nombres, String apellidos, String direccion, Integer edad, String mail, String password, String username){
        firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = firebaseAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("nombres", nombres);
                map.put("apellidos", apellidos);
                map.put("direccion", direccion);
                map.put("edad", edad);
                map.put("mail", mail);
                map.put("password", password);
                map.put("username", username);

                //sweetAlertDialog.sweetAlertLoading(this);
                mFirestore.collection("Usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        getActivity().finish();
                        Toast.makeText(getContext(), "Succesfull", Toast.LENGTH_SHORT).show();
                        /*
                        startActivity(new Intent(Registrar_activity.this, Login_Activity.class));
                        sweetAlertDialog.sweetAlertSuccessRegistro(Registrar_activity.this, nombre);
                        Toast.makeText(Registrar_activity.this, "Usuario Registrado con exito", Toast.LENGTH_SHORT).show();
                        UpDataToRealtimeDatabase();*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        System.out.printf(e.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(Registrar_activity.this, "Error al Registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpDataToAuthentication() {
        int spinner_pos = fragmentRegistroBinding.spinnerOld.getSelectedItemPosition();

        String nombres = fragmentRegistroBinding.textNombre.getText().toString();
        String apellidos = fragmentRegistroBinding.textApellido.getText().toString();
        String direccion = fragmentRegistroBinding.textDireccion.getText().toString();
        int edad = edades.get(spinner_pos);
        String mail = fragmentRegistroBinding.textEmail.getText().toString();
        String password = fragmentRegistroBinding.textPassword.getText().toString();
        String username = fragmentRegistroBinding.username.getText().toString();

        // Vereficamos que el email y password no esten vacios
        if (dataIsEmpty(nombres, apellidos, direccion, edad, mail, password, username)) {
            Toast.makeText(getContext(), "Complete los datos", Toast.LENGTH_SHORT).show();
            //System.out.println(edad);
        } else {
            try {
                if (awesomeValidation.validate()) {
                    //registerUser(nombres, apellidos, direccion, edad, mail, password, username);
                    Toast.makeText(getContext(), "Datos completados", Toast.LENGTH_SHORT).show();
                    registerUser(nombres, apellidos, direccion, edad, mail, password, username);
                }else{
                    Toast.makeText(getContext(), "Debes poner los datos correctos", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception E){
                Toast.makeText(getContext(), E.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean dataIsEmpty (String nombres, String apellidos, String direccion, Integer edad, String mail, String password, String username) {
        return mail.isEmpty() || username.isEmpty() || password.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || direccion.isEmpty() || edad == null;
    }// Vereficamos que el email y password no esten vacios, si estan vacios devolvemos true

}








