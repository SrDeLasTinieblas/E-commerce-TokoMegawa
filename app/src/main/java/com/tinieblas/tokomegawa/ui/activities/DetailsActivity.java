package com.tinieblas.tokomegawa.ui.activities;

import static com.tinieblas.tokomegawa.data.constants.Constants.KEY_PRODUCTO;
import static com.tinieblas.tokomegawa.data.constants.Constants.PREFS_NAME_CARRITO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityDetailsBinding;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.utils.Shared;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ActivityDetailsBinding activityDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityDetailsBinding.getRoot());
        context = this;
        ProductosItem producto = (ProductosItem) getIntent().getSerializableExtra("producto");

        activityDetailsBinding.animationView.setOnClickListener(view -> {
            agregarProducto(context, producto);
        });

        mostrarProducto(producto);

        btnImages(producto);
    }

    private void mostrarProducto(ProductosItem producto) {
        activityDetailsBinding.textTituloProduct.setText(producto.getNombreProducto());
        activityDetailsBinding.textViewDescripcionDetailsProducts.setText(producto.getDescripcionProducto());
        activityDetailsBinding.textViewDescripcionDetailsProducts.setMovementMethod(new ScrollingMovementMethod());
        activityDetailsBinding.textPrecioDestailsProductos.setText(String.valueOf(producto.getPrecioUnitario()));

        Glide.with(this).
                load(producto.getImagen1()).
                placeholder(R.drawable.place_holder).
                into(activityDetailsBinding.imageDetailsProducts1);

        Glide.with(this).
                load(producto.getImagen1()).
                placeholder(R.drawable.place_holder).
                into(activityDetailsBinding.imageDetailsProducts1A);

        Glide.with(this).
                load(producto.getImagen2()).
                placeholder(R.drawable.place_holder).
                into(activityDetailsBinding.imageDetailsProducts2B);

        Glide.with(this).
                load(producto.getImagen3()).
                placeholder(R.drawable.place_holder).
                into(activityDetailsBinding.imageDetailsProducts3C);

        /** imageDetailsProducts1
         * imageDetailsProducts1A
         * imageDetailsProducts2B
         *
         * */

    }

    public void btnImages(ProductosItem producto){
        activityDetailsBinding.imageDetailsProducts1A.setOnClickListener(view -> Glide.with(activityDetailsBinding.getRoot())
                .load(producto.getImagen1())
                .placeholder(R.drawable.place_holder)
                .into(activityDetailsBinding.imageDetailsProducts1));

        activityDetailsBinding.imageDetailsProducts2B.setOnClickListener(view -> Glide.with(activityDetailsBinding.getRoot())
                .load(producto.getImagen2())
                .placeholder(R.drawable.place_holder)
                .into(activityDetailsBinding.imageDetailsProducts1));

        activityDetailsBinding.imageDetailsProducts3C.setOnClickListener(view -> Glide.with(activityDetailsBinding.getRoot())
                .load(producto.getImagen3())
                .placeholder(R.drawable.place_holder)
                .into(activityDetailsBinding.imageDetailsProducts1));
    }

    public void btnAumentando(View view) {
        // Obtener valor actual de cantidad
        int cantidad = Integer.parseInt(activityDetailsBinding.textCantidad.getText().toString());

        // Incrementar en uno la cantidad
        cantidad++;

        // Obtener precio unitario
        double precioUnitario = Double.parseDouble(activityDetailsBinding.textPrecioDestailsProductos.getText().toString());

        // Calcular precio total
        double precioTotal = cantidad * precioUnitario;

        // Actualizar los valores en la vista
        activityDetailsBinding.textCantidad.setText(String.valueOf(cantidad));
        activityDetailsBinding.textPrecioDestailsProductos.setText(String.format("%.2f", precioTotal));

    }

    public void btnDisminuyendo(View view) {
        ProductosItem producto = (ProductosItem) getIntent().getSerializableExtra("producto");

        if (activityDetailsBinding != null) {
            // Obtener valor actual de cantidad
            double cantidad = Double.parseDouble(activityDetailsBinding.textCantidad.getText().toString().replaceAll("S/", ""));

            // Disminuir en uno la cantidad si es mayor o igual a 1
            if (cantidad >= 2) {
                cantidad--;

                // Calcular precio total
                double precioTotal = cantidad * producto.getPrecioUnitario();

                // Actualizar los valores en la vista
                activityDetailsBinding.textCantidad.setText(String.valueOf(cantidad));
                activityDetailsBinding.textPrecioDestailsProductos.setText(String.format("%.2f", precioTotal));
            }else{
                activityDetailsBinding.textCantidad.setText(String.valueOf(1));
            }
        }
    }

    public static void agregarProducto(Context context, ProductosItem producto) {
        List<ProductosItem> productosList = Shared.obtenerProductos(context);
        productosList.add(producto);

        Gson gson = new Gson();
        String productosJson = gson.toJson(productosList);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME_CARRITO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PRODUCTO, productosJson);
        editor.apply();
        System.out.println("productosJson" + productosJson);
    }


    @Override
    public void onClick(View view) {

    }
}