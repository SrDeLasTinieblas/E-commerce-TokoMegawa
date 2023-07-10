package com.tinieblas.tokomegawa.remote;

import com.tinieblas.tokomegawa.data.remote.PaymentRepositoryImp;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import okhttp3.Callback;

public class PaymentRepositoryImpTest {

    private PaymentRepositoryImp paymentRepository;
    private Callback mockCallback;

    @Before
    public void setup() {
        paymentRepository = new PaymentRepositoryImp();
        //mockCallback = Mockito.mock(Callback.class);
    }

    @Test
    public void testCreateCustomer() throws IOException {
        // Configurar el comportamiento del mockCallback
       /* Mockito.doAnswer(invocation -> {
            Request request = invocation.getArgument(0);
            // Aquí puedes definir la lógica para simular la respuesta del servidor según el request
            ResponseBody responseBody = ResponseBody.create(null, "customer_created_successfully");
            Response response = new Response.Builder()
                    .request(request)
                    .protocol(null)
                    .code(200)
                    .message("OK")
                    .body(responseBody)
                    .build();
            mockCallback.onResponse(null, response);
            return null;
        }).when(mockCallback).onResponse(Mockito.any(), Mockito.any());

        // Llamar al método de createCustomer con el URL simulado del servidor y el mockCallback
        paymentRepository.createCustomer("http://localhost/", mockCallback);

        // Verificar que se haya realizado la llamada a la API y se haya llamado al mockCallback con la respuesta esperada
        Mockito.verify(mockCallback).onResponse(Mockito.any(), Mockito.any());*/
    }
}