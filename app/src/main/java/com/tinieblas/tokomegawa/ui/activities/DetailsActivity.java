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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        /*activityDetailsBinding.animationView.setOnClickListener(view -> {
            try {
                agregarProducto(context, producto);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });*/
        //System.out.println("antes de limpiar ==> " + producto);
        mostrarProducto(producto);

        btnImages(producto);

        //clearSharedPreferences("productos_vistos");
        System.out.println("despues de limpiar ==> " + producto);
        try {
            guardarProductosVistos(producto);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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

    public void agregarProducto(Context context, ProductosItem producto) throws JSONException {
        /*List<ProductosItem> productosList = Shared.obtenerProductos(context);
        productosList.add(producto);

        Gson gson = new Gson();
        String productosJson = gson.toJson(productosList);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME_CARRITO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PRODUCTO, productosJson);
        editor.apply();
        System.out.println("productosJson" + productosJson);*/

        //Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("carrito", MODE_PRIVATE);

        //Obtiene una instancia del objeto Editor de SharedPreferences para editar el archivo:
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Obtiene la lista actual de productos vistos desde SharedPreferences:
        //String listaProductosCarrito = sharedPreferences.getString("lista_carrito", "");

        //Crea un objeto JSONObject para el producto actual y añade sus datos:
        JSONObject productoActual = new JSONObject();

            productoActual.put("idProducto", producto.getIdProducto());
            productoActual.put("nombreProducto", producto.getNombreProducto());
            productoActual.put("descripcionProducto", producto.getDescripcionProducto());
            productoActual.put("imagen1", producto.getImagen1());
            productoActual.put("imagen2", producto.getImagen2());
            productoActual.put("imagen3", producto.getImagen3());
            productoActual.put("precioUnitario", producto.getPrecioUnitario());
            productoActual.put("cantidadDisponible", producto.getCantidadDisponible());
            productoActual.put("categoria", producto.getCategoria());
            productoActual.put("delivery", producto.getDelivery());

    }
    public void guardarProductosVistos(ProductosItem producto) throws JSONException {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_vistos", MODE_PRIVATE);

        // Obtiene una instancia del objeto Editor de SharedPreferences para editar el archivo:
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convierte el objeto producto a JSON utilizando Gson:
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);

        // Si la lista actual no está vacía, verifica si el producto actual ya está en la lista y muévelo al final si es así:
        String listaProductosVistos = sharedPreferences.getString("lista_productos_vistos", "");

        if (!listaProductosVistos.isEmpty()) {
            try {
                JSONArray lista = new JSONArray(listaProductosVistos);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    if (productoLista.getString("nombreProducto").equals(producto.getNombreProducto())) {
                        lista.remove(i);
                        break;
                    }
                }
                lista.put(new JSONObject(productoJson));
                editor.putString("lista_productos_vistos", lista.toString());
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONArray lista = new JSONArray();
            lista.put(new JSONObject(productoJson));
            editor.putString("lista_productos_vistos", lista.toString());
            editor.apply();
        }
    }

    public void clearSharedPreferences(String s){
        SharedPreferences preferences = context.getSharedPreferences(s, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

    }
    @Override
    public void onClick(View view) {

    }
}