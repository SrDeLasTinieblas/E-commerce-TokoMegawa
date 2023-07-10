package com.tinieblas.tokomegawa.domain.repository;

import okhttp3.Callback;

public interface PaymentRepository {

    void createCustomer(String apiKeySecreta, Callback callback);

    void getEphericalKey(String apiKeySecreta, String customerID, Callback callback);
    void getClientSecret(String precio, String apiKeySecreta, String customerID, Callback callback);


}




