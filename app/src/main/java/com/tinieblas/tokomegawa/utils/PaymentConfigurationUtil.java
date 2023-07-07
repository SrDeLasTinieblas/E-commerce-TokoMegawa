package com.tinieblas.tokomegawa.utils;

import android.content.Context;

import com.stripe.android.PaymentConfiguration;


public class PaymentConfigurationUtil {

    public static void init(Context context, String apiKeyPublica) {
        PaymentConfiguration.init(context, apiKeyPublica);
    }
}
