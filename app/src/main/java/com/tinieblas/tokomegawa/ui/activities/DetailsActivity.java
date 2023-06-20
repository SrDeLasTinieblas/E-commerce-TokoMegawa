package com.tinieblas.tokomegawa.ui.activities;

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
import com.tinieblas.tokomegawa.domain.models.ProductosItem;

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
        hideSystemBar();
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
        //System.out.println("despues de limpiar ==> " + producto);
        try {
            guardarProductosVistos(producto);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        verificarProductoCarrito(producto);
            activityDetailsBinding.animationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Verificar si el producto está en el carrito
                    //borrarProductoCarrito(producto);
                    if (/*productoEnCarrito(producto)*/ EstaEnCarrito) {
                        // El producto está en el carrito, así que lo eliminamos
                        //Toast.makeText(context, "El producto ya esta en el carrito", Toast.LENGTH_SHORT).show();
                        borrarProductoCarrito(producto);
                        // Mostrar animación de eliminación
                        activityDetailsBinding.animationView.setProgress(0);
                        activityDetailsBinding.animationView.pauseAnimation();
                    } else {
                        Toast.makeText(context, "El producto no esta en el carrito", Toast.LENGTH_SHORT).show();
                        // El producto no está en el carrito, así que lo añadimos
                        guardarProductoCarrito(producto);
                        // Mostrar animación de añadir al carrito
                        activityDetailsBinding.animationView.playAnimation();
                    }
                }
            });


    }

    /*public boolean productoEnCarrito(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Verifica si el producto actual ya está guardado en SharedPreferences:
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);

        String listaProductosCarrito = sharedPreferences.getString("lista_productos_carrito", "");
        if (!listaProductosCarrito.isEmpty()) {
            try {
                JSONArray lista = new JSONArray(listaProductosCarrito);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    if (productoLista.toString().equals(productoJson)) {
                        // El producto está en el carrito
                        return true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // El producto no está en el carrito
        return false;
    }*/


    private void mostrarProducto(ProductosItem producto) {
        activityDetailsBinding.textTituloProduct.setText(producto.getNombreProducto());
        activityDetailsBinding.textViewDescripcionDetailsProducts.setText(producto.getDescripcionProducto());
        activityDetailsBinding.textViewDescripcionDetailsProducts.setMovementMethod(new ScrollingMovementMethod());
        activityDetailsBinding.textPrecioDestailsProductos.setText(String.valueOf(producto.getPrecioUnitario()));
        activityDetailsBinding.buttonShippingDetails.setText(String.valueOf(producto.getDelivery()));

        // Verificar si el campo "delivery" es igual a "Free shipping"
        if (producto.getDelivery().equals("Free shipping")) {
            activityDetailsBinding.buttonShippingDetails.setText(String.valueOf(producto.getDelivery()));
        } else {
            activityDetailsBinding.buttonShippingDetails.setText(String.valueOf(producto.getDelivery()));
        }


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

    /**public void GuardarCarrito(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Obtiene una instancia del objeto Editor de SharedPreferences para editar el archivo:
        SharedPreferences.Editor editor = sharedPreferences.edit();

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

            // Muestra la animación si se guardó correctamente
            activityDetailsBinding.animationView.playAnimation();
        } catch (JSONException e) {
            // Si ocurre un error al guardar el producto, muestra un Toast y registra el error en el Log
            Toast.makeText(this, "Error al guardar el producto en el carrito", Toast.LENGTH_SHORT).show();
            Log.e("GuardarCarrito", "Error al guardar el producto en SharedPreferences", e);
        }
    }

    public void verificarProductoGuardado(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Verifica si el producto actual ya está guardado en SharedPreferences:
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);

        String listaProductosCarrito = sharedPreferences.getString("lista_productos_carrito", "");
        if (!listaProductosCarrito.isEmpty()) {
            try {
                JSONArray lista = new JSONArray(listaProductosCarrito);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    if (productoLista.toString().equals(productoJson)) {
                        // El producto ya está guardado, muestra la animación
                        activityDetailsBinding.animationView.playAnimation();
                        Toast.makeText(context, "Producto no guardado", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else {
                        //activityDetailsBinding.animationView.playAnimation();
                        Toast.makeText(context, "Producto ya guardado", Toast.LENGTH_SHORT).show();
                        //borrarProductoGuardado(producto);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void borrarProductoGuardado(ProductosItem producto) {
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
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    if (productoLista.toString().equals(productoJson)) {
                        // Remover el producto de la lista
                        lista.remove(i);
                        editor.putString("lista_productos_carrito", lista.toString());
                        editor.apply();
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

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

    public void borrarProductoCarrito(ProductosItem producto) {
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

                    Toast.makeText(context, "Quitado del carrito", Toast.LENGTH_SHORT).show();
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
    public boolean productoEnCarrito(ProductosItem producto) {
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
                    // El producto ya está guardado, muestra la animación
                    return productoGuardado.getIdProducto() == (producto.getIdProducto());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*
    public void verificarProductoCarritoOnClick(ProductosItem producto) {
        // Crea una instancia de SharedPreferences en la actividad de detalles:
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);

        // Verifica si el producto actual ya está guardado en SharedPreferences:
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);

        String listaProductosCarrito = sharedPreferences.getString("lista_productos_carrito", "");
        if (!listaProductosCarrito.isEmpty()) {
            try {
                JSONArray lista = new JSONArray(listaProductosCarrito);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject productoLista = lista.getJSONObject(i);
                    if (productoLista.toString().equals(productoJson)) {
                        // El producto ya está guardado, borra el producto del carrito
                        borrarProductoCarrito(producto);
                        Toast.makeText(context, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/



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

    public void volver(View view){
        finish();
        this.onBackPressed();
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

    @Override
    public void onBackPressed() {

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
}