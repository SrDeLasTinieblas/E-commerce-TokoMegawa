package com.tinieblas.tokomegawa.utils;

import static com.tinieblas.tokomegawa.data.constants.Constants.KEY_PRODUCTO;
import static com.tinieblas.tokomegawa.data.constants.Constants.PREFS_NAME_CARRITO;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Shared {

    public static List<ProductosItem> obtenerProductos(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME_CARRITO, Context.MODE_PRIVATE);
        String productosJson = sharedPreferences.getString(KEY_PRODUCTO, null);

        System.out.println("productosJson"+productosJson);
        if (productosJson != null) {
            Gson gson = new Gson();
            Type productListType = new TypeToken<ArrayList<ProductosItem>>() {}.getType();

            return gson.fromJson(productosJson, productListType);
        } else {
            return new ArrayList<>();
        }
    }




}
