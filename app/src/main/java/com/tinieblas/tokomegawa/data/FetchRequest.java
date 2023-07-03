package com.tinieblas.tokomegawa.data;

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
    /*public String fetchRegistro(final String urlapi, RegistroDataModelo registroData) throws IOException {
        // Crear un objeto JSON con los datos del registro
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"nombre\": \"23rfd23rdge\"," +
                "\r\n  \"apellido\": \"Luna\",\r\n  \"phone\": \"432421\"," +
                "\r\n  \"tipoDeDocumento\": \"DNI\",\r\n  \"numDocumento\": \"2343424\"," +
                "\r\n  \"contrasena\": \"darkangelo\",\r\n  \"iD_USER\": \"FEWFEWFWFW\"," +
                "\r\n  \"email\": \"alohaaaa@gmail.com\",\r\n  \"departamento\": \"Lima\"," +
                "\r\n  \"provincia\": \"Lima\",\r\n  \"distrito\": \"SMP\"\r\n}");

        Request request = new Request.Builder()
                .url(urlapi)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.toString();
    }*/

    }
}






















