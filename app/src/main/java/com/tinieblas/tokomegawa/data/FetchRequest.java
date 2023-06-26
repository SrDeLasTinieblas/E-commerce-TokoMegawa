package com.tinieblas.tokomegawa.data;

import okhttp3.OkHttpClient;

public class FetchRequest {
    private OkHttpClient client;

    public FetchRequest() {
        client = new OkHttpClient.Builder().build();
    }

    /*@SuppressLint("StaticFieldLeak")
    public void fetchCategorias(final String url, final CategoriasCallback callback) {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        try {
                            Gson gson = new Gson();
                            CategoriasResponse responseObject = gson.fromJson(responseData, CategoriasResponse.class);

                            return responseObject.getCategorias();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(List<CategoriasResponse> categoriasResponses) {
                if (categoriasResponses != null) {
                    callback.onCategoriasFetched(categoriasResponses);
                }
            }
        }.execute();
    }*/

}
