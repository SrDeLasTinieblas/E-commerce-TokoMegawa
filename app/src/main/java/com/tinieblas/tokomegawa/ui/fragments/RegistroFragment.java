package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

//import com.android.volley.RequestQueue;

//import com.android.volley.toolbox.Volley;
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

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentRegistroBinding;

import org.json.JSONException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegistroFragment extends Fragment {

    RegistroFragment registroFragment;
    private FragmentRegistroBinding fragmentRegistroBinding;
    FirebaseFirestore mFirestore;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    ArrayList<Integer> edades = new ArrayList<>();
    DatabaseReference databaseReference;

//    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        registroFragment = this;
        fragmentRegistroBinding = fragmentRegistroBinding.inflate(inflater, container, false);

        //requestQueue = Volley.newRequestQueue(registroFragment.getActivity());

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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Aqui pones el codigo
                        try {
                            createAccount();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
                //createAccount();
                //new MyAsyncTask().execute();
            }
        });

        showPassword();
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
                        //getActivity().finish();
                        Toast.makeText(getContext(), "Succesfull", Toast.LENGTH_SHORT).show();

                        replaceFragment(new HomeFragment());
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

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void showPassword(){
        fragmentRegistroBinding.showPassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        fragmentRegistroBinding.textPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        fragmentRegistroBinding.textPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
    }

    public void createAccount() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"" +
                "id\": 0,\r\n  \"" +
                "nombre\": \"alohaaaa\"\r\n}");

        okhttp3.Request  request = new okhttp3.Request.Builder()

                .url("http://webapiventareal.somee.com/addCliente")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        okhttp3.Response response = client.newCall(request).execute();


        /*
        URL url = null; // URL del servidor al que deseas enviar el post

        try {
            url = new URL("https://localhost:7261/addCliente");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST"); // Especifica que se usará el método POST para la solicitud
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); // Configura la cabecera para especificar el tipo de contenido y la codificación
        //conn.setDoOutput(true); // Habilita la escritura de datos a la conexión
        // Habilitamos el envío y recepción de datos
        conn.setDoInput(true);
        conn.setDoOutput(true);

// Crea un objeto JSON con los datos que deseas enviar en el post
        JSONObject postData = new JSONObject();
        postData.put("nombre", "Juan");
        /*postData.put("edad", 25);
        postData.put("email", "juan@mail.com");*/

// Escribe los datos en la conexión
        /*OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(postData.toString());
        writer.flush();
        writer.close();

// Obtiene la respuesta del servidor
        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

// Imprime la respuesta del servidor
        System.out.println("==>"+response.toString());
        Toast.makeText(getContext(), "==>"+response.toString(), Toast.LENGTH_SHORT).show();
*/
    }


}








