package com.tinieblas.tokomegawa.ui.activities;

import static com.tinieblas.tokomegawa.data.constants.Constants.KEY_PRODUCTO;
import static com.tinieblas.tokomegawa.data.constants.Constants.PREFS_NAME_CARRITO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityDetailsBinding;
import com.tinieblas.tokomegawa.databinding.ActivityMyCartBinding;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.ui.adptadores.CarritoAdapter;
import com.tinieblas.tokomegawa.utils.NavigationContent;
import com.tinieblas.tokomegawa.utils.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity {
    private View decorView;
    Context context;
    ActivityMyCartBinding activityMyCartBinding;

    CarritoAdapter mCarritoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyCartBinding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(activityMyCartBinding.getRoot());
        hideSystemBar();
        context = this;
        obtenerProductosDelCarrito();

        //System.out.println(" ==> obtenerProductos "+Shared.obtenerProductos(context));
        // Este código es para ocultar la barra de navegación y la barra de estado en una actividad
    }
    private int hideSystemBar(){
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    public List<ProductosItem> obtenerProductosDelCarrito() {
        // Obtén la lista de productos desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);
        String listaProductosJson = sharedPreferences.getString("lista_productos_carrito", "");

        // Convierte el JSON a una lista de ProductosItem utilizando Gson
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductosItem>>(){}.getType();
        List<ProductosItem> listaProductosItems = gson.fromJson(listaProductosJson, type);
        mostrarCardsCarrito(listaProductosItems);
        return listaProductosItems;
    }

    public void mostrarCardsCarrito(List<ProductosItem> carrito){
        // Mostrar los datos del carrito
        //activityMyCartBinding.viewSwitcher.setDisplayedChild(0); // Muestra el RecyclerView
        //activityMyCartBinding.animateView.setVisibility(View.INVISIBLE);
        if (carrito != null && !carrito.isEmpty()) {
            mCarritoAdapter = new CarritoAdapter(MyCartActivity.this, carrito);
            activityMyCartBinding.RecyclerViewMyCart.setAdapter(mCarritoAdapter);
            activityMyCartBinding.RecyclerViewMyCart.setLayoutManager(new LinearLayoutManager(MyCartActivity.this, RecyclerView.VERTICAL, false));
        }else {
            Toast.makeText(context, "No hay datos en el carrito", Toast.LENGTH_SHORT).show();
        }
    }




    public void volver(View view){
        NavigationContent.cambiarActividad(this, MainActivity.class);
    }

}