package com.tinieblas.tokomegawa.data.remote;

import com.tinieblas.tokomegawa.domain.repository.PaymentRepository;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PaymentRepositoryImp implements PaymentRepository {
    private final OkHttpClient client;
    private final String name = "Authorization";
    private final String value = "Bearer";
    public PaymentRepositoryImp() {
        client = new OkHttpClient();
    }

    @Override
    public void createCustomer(String apiKeySecreta, Callback callback) {
        RequestBody requestBody = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url("https://api.stripe.com/v1/customers")
                .addHeader(name, value + apiKeySecreta)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    @Override
    public void getEphericalKey(String apiKeySecreta, String customerID, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("customer", customerID)
                .build();

        Request request = new Request.Builder()
                .url("https://api.stripe.com/v1/ephemeral_keys")
                .addHeader(name, value + apiKeySecreta)
                .addHeader("Stripe-Version", "2020-08-27")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    @Override
    public void getClientSecret(String precio, String apiKeySecreta, String customerID, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("customer", customerID)
                .add("amount", precio)
                .add("currency", "pen")  // Cambiar de "usd" a "sol"
                .add("automatic_payment_methods[enabled]", "true")
                .build();

        Request request = new Request.Builder()
                .url("https://api.stripe.com/v1/payment_intents")
                .addHeader(name, value + apiKeySecreta)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

}