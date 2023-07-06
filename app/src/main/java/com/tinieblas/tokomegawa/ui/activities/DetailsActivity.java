package com.tinieblas.tokomegawa.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityDetailsBinding;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.utils.hideMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ActivityDetailsBinding activityDetailsBinding;
    private View decorView;
    boolean EstaEnCarrito = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityDetailsBinding.getRoot());

        decorView = getWindow().getDecorView();
        getWindow().getDecorView().setSystemUiVisibility(hideMenu.hideSystemBar(decorView));

        context = this;
        ProductosItem producto = (ProductosItem) getIntent().getSerializableExtra("producto");
        mostrarProducto(producto);
        btnImages(producto);

        try {
            guardarProductosVistos(producto);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        btnCarrito(producto);
    }


    public void btnCarrito(ProductosItem producto) {
        verificarProductoCarrito(producto);
        activityDetailsBinding.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarProductoCarrito(producto);

                if (EstaEnCarrito) {
                    // El producto está en el carrito, así que lo eliminamos
                    //Toast.makeText(context, "Eliminado del carrito", Toast.LENGTH_SHORT).show();
                    borrarUnProductoDelCarrito(producto);
                    snackbars("Quitado del carrito");
                    // Mostrar animación de eliminación
                    activityDetailsBinding.animationView.setProgress(0);
                    activityDetailsBinding.animationView.pauseAnimation();
                    EstaEnCarrito = false; // Actualizar el estado de EstaEnCarrito
                } else {
                    // El producto no está en el carrito, así que lo añadimos
                    //Toast.makeText(context, "Agregando al carrito", Toast.LENGTH_SHORT).show();
                    guardarProductoCarrito(producto);
                    snackbars("Agregado al carrito");
                    // Mostrar animación de añadir al carrito
                    activityDetailsBinding.animationView.playAnimation();
                    EstaEnCarrito = true; // Actualizar el estado de EstaEnCarrito
                }
            }
        });
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

    public void guardarProductoCarrito(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Obtiene una instancia del objeto Editor de SharedPreferences para editar el archivo:
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Obtiene el valor de textCantidad y establece ese valor en la propiedad amount del producto
        int cantidad = Integer.parseInt(activityDetailsBinding.textCantidad.getText().toString());
        producto.setAmount(cantidad);

        // Convierte el objeto producto a JSON utilizando Gson:
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);

        // Guarda el producto en SharedPreferences
        try {
            JSONArray lista;
            String listaProductosCarrito = sharedPreferences.getString("lista_productos_carrito", "");
            if (!listaProductosCarrito.isEmpty()) {
                lista = new JSONArray(listaProductosCarrito);
            } else {
                lista = new JSONArray();
            }
            lista.put(new JSONObject(productoJson));
            editor.putString("lista_productos_carrito", lista.toString());
            editor.apply();
            activityDetailsBinding.animationView.playAnimation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void borrarUnProductoDelCarrito(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Obtiene una instancia del objeto Editor de SharedPreferences para editar el archivo:
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convierte el objeto producto a JSON utilizando Gson:
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);

        String listaProductosCarrito = sharedPreferences.getString("lista_productos_carrito", "");

        if (!listaProductosCarrito.isEmpty()) {
            try {
                JSONArray lista = new JSONArray(listaProductosCarrito);

                // Buscar el producto por su ID en la lista
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    int idProductoLista = productoLista.getInt("idProducto");

                    //System.out.println("idProductoLista ==> "+idProductoLista + "  ==  " + "getIdProducto ==> "+producto.getIdProducto());

                    // Comparar el ID del producto actual con el ID del producto en la lista
                    if (producto.getIdProducto() == idProductoLista) {

                        // Remover el producto de la lista
                        lista.remove(i);
                        editor.putString("lista_productos_carrito", lista.toString());
                        editor.apply();
                        break;
                    }
                    //System.out.println("idProductoLista ==> "+idProductoLista + "  ==  " + "getIdProducto ==> "+producto.getIdProducto());

                    //Toast.makeText(context, "Quitado del carrito", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void verificarProductoCarrito(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Verifica si el producto actual ya está guardado en SharedPreferences:
        Gson gson = new Gson();
        //String productoJson = gson.toJson(producto);

        String listaProductosCarrito = sharedPreferences.getString("lista_productos_carrito", "");
        if (!listaProductosCarrito.isEmpty()) {
            try {
                JSONArray lista = new JSONArray(listaProductosCarrito);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    ProductosItem productoGuardado = gson.fromJson(productoLista.toString(), ProductosItem.class);
                    if (productoGuardado.getIdProducto() == (producto.getIdProducto())) {
                        // El producto ya está guardado, muestra la animación
                        activityDetailsBinding.animationView.playAnimation();
                        EstaEnCarrito = true;
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

    public void snackbars(String mensaje) {
        // Crear el Snackbar
        Snackbar snackbar = Snackbar.make(decorView, "", Snackbar.LENGTH_SHORT);

        // Obtener la vista del Snackbar
        View snackbarView = snackbar.getView();

        // Configurar el fondo y el radio de los bordes
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(getResources().getDimensionPixelOffset(R.dimen.snackbar_corner_radius));
        background.setColor(ContextCompat.getColor(context, R.color.white));
        snackbarView.setBackground(background);

        // Obtener el TextView del Snackbar
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);

        // Configurar la imagen y el texto del TextView
        //Drawable drawable = getResources().getDrawable(R.drawable.check1);
        //textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.fab_margin));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(mensaje);
        textView.setTextColor(ContextCompat.getColor(context, R.color.black));

        // Mostrar el Snackbar
        snackbar.show();
    }



    public void volver(View view){
        finish();
        this.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {

    }

}