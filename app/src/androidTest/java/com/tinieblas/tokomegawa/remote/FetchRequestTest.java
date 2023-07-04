package com.tinieblas.tokomegawa.remote;

import static org.junit.Assert.*;
import com.tinieblas.tokomegawa.data.FetchRequest;
import com.tinieblas.tokomegawa.domain.models.RegistroDataModelo;
import com.tinieblas.tokomegawa.domain.repository.respositories.RegistroCallback;
import org.junit.Test;

import java.io.IOException;

public class FetchRequestTest {


    @Test
    public void fetchRegistro() throws IOException {
        FetchRequest fetchRequest = new FetchRequest();
        String url = "http://tokomegawa.somee.com/Registrarse";

        // Crear una instancia de RegistroDataModelo con los datos del registro
        RegistroDataModelo registroData = new RegistroDataModelo();
        registroData.setNombre("Nombre de ejemplo");
        registroData.setApellidos("Apellidos de ejemplo");
        registroData.setTelefono("123456789");
        registroData.setTipoDocumento("DNI");
        registroData.setNumDocumento("12345678");
        registroData.setContrasena("contrasena123");
        registroData.setIUD("IUD de ejemplo");
        registroData.setCorreoElectronico("ejfewfemplo111@example.com");
        registroData.setDepartamento("Lima");
        registroData.setProvincia("Lima");
        registroData.setDistrito("SMPorres");

        // Realizar la solicitud de registro y obtener la respuesta
        String response = fetchRequest.fetchRegistro(url, registroData);

        // Comprobar el resultado de la solicitud
        assertNotNull(response);
    }

}