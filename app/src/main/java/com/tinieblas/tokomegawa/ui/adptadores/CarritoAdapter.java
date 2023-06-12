package com.tinieblas.tokomegawa.ui.adptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityMyCartBinding;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductosItem> mCarrito;
    TextView textView;

    public CarritoAdapter(Context mContext, List<ProductosItem> mCarrito, TextView textView) {
        this.mContext = mContext;
        this.mCarrito = mCarrito;
        this.textView = textView;
    }
    public void setCarrito(List<ProductosItem> carrito) {
        mCarrito = carrito;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.items_mycart, parent, false);
        return new CarritoAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductosItem carrito = mCarrito.get(position);

        double totalPrice = carrito.getPrecioUnitario() * carrito.getAmount();

        // Crear un objeto BigDecimal con el valor del precio total
        BigDecimal bd = new BigDecimal(totalPrice);

        // Redondear el valor a dos decimales
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        // Obtener el valor redondeado como un double
        double roundedPrice = bd.doubleValue();

        holder.nombreTextView.setText(carrito.getNombreProducto());
        holder.descripcion.setText(carrito.getDescripcionProducto());
        holder.precioUnitario.setText("S/. " + roundedPrice);
        holder.amount.setText(String.valueOf(carrito.getAmount()));

        Glide.with(mContext).load(carrito.getImagen1()).into(holder.ImageView);

        holder.buttonAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aumentarCantidad(position);
                calcularSubTotal();
            }
        });
        holder.buttonDisminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disminuirCantidad(position);
                calcularSubTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarrito.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ActivityMyCartBinding activityMyCartBinding;
        public Button buttonDisminuir, buttonAumentar;
        public TextView nombreTextView, precioUnitario, descripcion, amount, SubTotal;

        public android.widget.ImageView ImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            nombreTextView = itemView.findViewById(R.id.textTituloCarrito);
            descripcion = itemView.findViewById(R.id.tvDescripcionMyCart);
            precioUnitario = itemView.findViewById(R.id.textPrecioMyCart);
            amount = itemView.findViewById(R.id.textViewAmount);
            buttonDisminuir = itemView.findViewById(R.id.buttonArrowDerecha3);
            buttonAumentar = itemView.findViewById(R.id.buttonArrowDerecha1);
            SubTotal = itemView.findViewById(R.id.textSubTotal);
            ImageView = itemView.findViewById(R.id.imageView2);

        }
    }

    private void actualizarCarritoEnSharedPreferences(List<ProductosItem> carrito) {
        // Convierte la lista de productos a JSON utilizando Gson
        Gson gson = new Gson();
        String carritoJson = gson.toJson(carrito);

        // Guarda el carrito actualizado en SharedPreferences
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("productos_carrito", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lista_productos_carrito", carritoJson);
        editor.apply();
    }

    public void aumentarCantidad(int position) {
        ProductosItem producto = mCarrito.get(position);
        int cantidadActual = producto.getAmount();
        producto.setAmount(cantidadActual + 1);
        notifyDataSetChanged();
        // Actualiza el carrito en SharedPreferences
        actualizarCarritoEnSharedPreferences(mCarrito);
    }

    public void disminuirCantidad(int position) {
        ProductosItem producto = mCarrito.get(position);
        int cantidadActual = producto.getAmount();
        if (cantidadActual > 1) {
            producto.setAmount(cantidadActual - 1);
            notifyDataSetChanged();
            // Actualiza el carrito en SharedPreferences
            actualizarCarritoEnSharedPreferences(mCarrito);
        }
    }

    @SuppressLint("SetTextI18n")
    public void calcularSubTotal() {
        // Obtén la lista de productos desde SharedPreferences
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("productos_carrito", Context.MODE_PRIVATE);
        String listaProductosJson = sharedPreferences.getString("lista_productos_carrito", "");

        // Convierte el JSON a una lista de ProductosItem utilizando Gson
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductosItem>>(){}.getType();
        List<ProductosItem> listaProductosItems = gson.fromJson(listaProductosJson, type);

        // Calcula el subtotal sumando los precios de los productos
        double subTotal = 0;
        for (ProductosItem producto : listaProductosItems) {
            double precio = producto.getPrecioUnitario() * producto.getAmount();
            subTotal += precio;
        }

        // Crear un objeto BigDecimal con el valor del precio total
        BigDecimal bd = new BigDecimal(subTotal);

        // Redondear el valor a dos decimales
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        // Obtener el valor redondeado como un double
        double roundedPrice = bd.doubleValue();


        // Muestra el subtotal en el TextView
        textView.setText("S/. " + roundedPrice);
    }


}
