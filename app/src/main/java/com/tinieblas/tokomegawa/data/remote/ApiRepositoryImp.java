package com.tinieblas.tokomegawa.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.domain.repository.ApiRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRepositoryImp implements ApiRepository {

    private Context context;
    public ApiRepositoryImp(Context context) {
        this.context = context;
    }

    private String API = "http://tokomegawa.somee.com/getProductos";
    @Override
    public List<ProductosItem> fetchProductos() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(API)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseData = response.body().string();
                try {
                    Gson gson = new Gson();
                    com.tinieblas.tokomegawa.data.local.Response responseObject = gson.fromJson(responseData, com.tinieblas.tokomegawa.data.local.Response.class);
                    List<ProductosItem> productos = responseObject.getProductos();

                    // return productos;
                    /* TODO

                    A partir de ahora vamos a filtrar los datos con favoritos, para ello
                    tenemos que llamar a favoritesRepository


                     */

//                    ProductsFavoriteRepositoryImp favoriteRepo = new ProductsFavoriteRepositoryImp(context);
//                    List<ProductosItem> finalProductos = new ArrayList<>();
//
//                    for (ProductosItem itemInRemote: productos){
//                        ProductosItem itemInLocal = favoriteRepo.get(itemInRemote.getIdProducto());
//                        if(itemInLocal != null){
//
//                        } else {
//                            finalProductos.add(itemInRemote);
//                        }
//                    }




                    return productos;
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {

        }
        return new ArrayList<ProductosItem>();
    }
}
