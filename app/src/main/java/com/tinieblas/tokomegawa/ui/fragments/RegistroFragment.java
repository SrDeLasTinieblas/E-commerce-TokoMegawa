package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.remote.SignUpRepositoryImp;
import com.tinieblas.tokomegawa.databinding.FragmentRegistroBinding;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegistroFragment extends Fragment {

    RegistroFragment registroFragment;
    private FragmentRegistroBinding fragmentRegistroBinding;
    AwesomeValidation awesomeValidation;
    ArrayList<Integer> edades = new ArrayList<>();

//    RequestQueue requestQueue;


    private final SignUpRepositoryImp repository = new SignUpRepositoryImp();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        registroFragment = this;
        fragmentRegistroBinding = fragmentRegistroBinding.inflate(inflater, container, false);

        //requestQueue = Volley.newRequestQueue(registroFragment.getActivity());

        //ArrayList<Integer> edades = new ArrayList<>();
        for (int i = 18; i < 91; i++) {
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
    private void registerUser(String nombres, String apellidos, String direccion, Integer edad, String mail, String password, String username) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String userId = repository.createUser(mail, password);
                Boolean status = repository.addUser(userId, nombres, apellidos, direccion, edad, mail, password, username );
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(status){
                            Toast.makeText(getContext(), "Succesfull", Toast.LENGTH_SHORT).show();
                            replaceFragment(new HomeFragment());
                        } else {

                        }

                    }
                });

            }
        }).start();

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


                } else {
                    Toast.makeText(getContext(), "Debes poner los datos correctos", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception E) {
                Toast.makeText(getContext(), E.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean dataIsEmpty(String nombres, String apellidos, String direccion, Integer edad, String mail, String password, String username) {
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
    public void showPassword() {
        fragmentRegistroBinding.showPassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
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

        okhttp3.Request request = new okhttp3.Request.Builder()

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








