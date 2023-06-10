package com.tinieblas.tokomegawa.ui.activities;

import static com.tinieblas.tokomegawa.data.constants.Constants.KEY_PRODUCTO;
import static com.tinieblas.tokomegawa.data.constants.Constants.PREFS_NAME_CARRITO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        System.out.println(" ==> obtenerProductos"+obtenerProductos());
    }
    public static List<ProductosItem> obtenerProductos() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME_CARRITO, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String productosJson = sharedPreferences.getString(KEY_PRODUCTO, null);

        if (productosJson != null) {
            Type productListType = new TypeToken<ArrayList<ProductosItem>>() {}.getType();
            return gson.fromJson(productosJson, productListType);
        } else {
            return new ArrayList<>();
        }
    }
}