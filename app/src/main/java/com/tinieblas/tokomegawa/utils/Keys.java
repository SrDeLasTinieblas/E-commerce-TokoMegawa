package com.tinieblas.tokomegawa.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Keys {

    public interface KeysLoadListener {
        void onPublicKeyLoaded(String publicKey);
        void onSecretKeyLoaded(String secretKey);
        void onKeysLoadFailed();
    }

    public void loadKeysAsync(Context context, List<String> llaves, KeysLoadListener listener) {
        new LoadKeysAsyncTask(context, llaves, listener).execute();
    }

    private static class LoadKeysAsyncTask extends AsyncTask<Void, String, Void> {
        private final Context context;
        private final List<String> llaves;
        private final KeysLoadListener listener;

        public LoadKeysAsyncTask(Context context, List<String> llaves, KeysLoadListener listener) {
            this.context = context;
            this.llaves = llaves;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Properties properties = new Properties();
            try (InputStream inputStream = context.getAssets().open("api.properties")) {
                properties.load(inputStream);
                for (String llave : llaves) {
                    String apiKey = properties.getProperty(llave);
                    if (apiKey != null) {
                        publishProgress(llave, apiKey);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... keys) {
            String llave = keys[0];
            String apiKey = keys[1];
            if (llave.equals("apikey.stripe_publica")) {
                listener.onPublicKeyLoaded(apiKey);
            } else if (llave.equals("apikey.stripe_secreta")) {
                listener.onSecretKeyLoaded(apiKey);
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            listener.onKeysLoadFailed();
        }
    }
}
