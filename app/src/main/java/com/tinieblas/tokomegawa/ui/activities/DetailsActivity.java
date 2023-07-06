package com.tinieblas.tokomegawa.ui.activities;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.local.ProductCartRepositoryImp;
import com.tinieblas.tokomegawa.data.local.ProductsViewedRepositoryImp;
import com.tinieblas.tokomegawa.databinding.ActivityDetailsBinding;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.utils.hideMenu;

import org.json.JSONException;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ActivityDetailsBinding activityDetailsBinding;
    private View decorView;
    boolean EstaEnCarrito = false;


    private ProductCartRepositoryImp repositoryCart;
    private ProductsViewedRepositoryImp repositoryViewed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityDetailsBinding.getRoot());

        decorView = getWindow().getDecorView();
        getWindow().getDecorView().setSystemUiVisibility(hideMenu.hideSystemBar(decorView));

        context = this;
        repositoryCart = new ProductCartRepositoryImp(this);
        repositoryViewed = new ProductsViewedRepositoryImp(this);


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

    public void btnImages(ProductosItem producto) {
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
            } else {
                activityDetailsBinding.textCantidad.setText(String.valueOf(1));
            }
        }
    }

    public void guardarProductoCarrito(ProductosItem producto) {

        // Obtiene el valor de textCantidad y establece ese valor en la propiedad amount del producto
        int cantidad = Integer.parseInt(activityDetailsBinding.textCantidad.getText().toString());
        producto.setAmount(cantidad);

        List<ProductosItem> lista = repositoryCart.getAll();
        lista.add(producto);
        repositoryCart.insertAll(lista);

        activityDetailsBinding.animationView.playAnimation();

    }

    public void borrarUnProductoDelCarrito(ProductosItem producto) {
        repositoryCart.delete(producto.getIdProducto());

    }

    public void verificarProductoCarrito(ProductosItem producto) {
        ProductosItem productosItemOfRepo = repositoryCart.get(producto.getIdProducto());

        if(productosItemOfRepo != null){
            activityDetailsBinding.animationView.playAnimation();
            EstaEnCarrito = true;
        }
    }

    public void guardarProductosVistos(ProductosItem producto) throws JSONException {
        repositoryViewed.save(producto);
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


    public void volver(View view) {
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