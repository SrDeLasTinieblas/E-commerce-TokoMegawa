package com.tinieblas.tokomegawa.data;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.tinieblas.tokomegawa.domain.models.RegistroDataModelo;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FetchRequest {
    private final OkHttpClient client;

    public FetchRequest() {
        client = new OkHttpClient.Builder().build();
    }

    public String fetchRegistro(final String urlapi, RegistroDataModelo registroData) throws IOException {
        // Crear un objeto JSON con los datos del registro
        JSONObject json = new JSONObject();
        try {
            json.put("nombre", registroData.getNombre());
            json.put("apellido", registroData.getApellidos());
            json.put("phone", registroData.getTelefono());
            json.put("tipoDeDocumento", registroData.getTipoDocumento());
            json.put("numDocumento", registroData.getNumDocumento());
            json.put("contrasena", registroData.getContrasena());
            json.put("iD_USER", registroData.getIUD());
            json.put("email", registroData.getCorreoElectronico());
            json.put("departamento", registroData.getDepartamento());
            json.put("provincia", registroData.getProvincia());
            json.put("distrito", registroData.getDistrito());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        // Crear una solicitud HTTP POST
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url(urlapi)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return response.message();
            }
        }

    }

    /*public String fetchRegistro(final String urlapi, RegistroDataModelo registroData) {
        // Crear un objeto JSON con los datos del registro
        JSONObject json = new JSONObject();
        try {
            // ... tu código para crear el objeto JSON ...
            json.put("nombre", registroData.getNombre());
            json.put("apellido", registroData.getApellidos());
            json.put("phone", registroData.getTelefono());
            json.put("tipoDeDocumento", registroData.getTipoDocumento());
            json.put("numDocumento", registroData.getNumDocumento());
            json.put("contrasena", registroData.getContrasena());
            json.put("iD_USER", registroData.getIUD());
            json.put("email", registroData.getCorreoElectronico());
            json.put("departamento", registroData.getDepartamento());
            json.put("provincia", registroData.getProvincia());
            json.put("distrito", registroData.getDistrito());
        } catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }

        // Crear una solicitud HTTP POST
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url(urlapi)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        // Utilizar AsyncTask para realizar la solicitud de red en un hilo secundario
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return response.message();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    // Manejar la respuesta aquí
                } else {
                    // Manejar el caso de error
                }
            }
        };

        asyncTask.execute();
        return asyncTask.toString();
    }
*/


}






















