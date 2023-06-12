package com.tinieblas.tokomegawa.ui.adptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductosItem> mCarrito;

    public CarritoAdapter(Context mContext, List<ProductosItem> mCarrito) {
        this.mContext = mContext;
        this.mCarrito = mCarrito;
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
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolder holder, int position) {
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

        //Glide.with(mContext).load(holder.ImageView).into(holder.ImageView);
        Glide.with(mContext).load(carrito.getImagen1()).into(holder.ImageView);
    }


    @Override
    public int getItemCount() {
        return mCarrito.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView, precioUnitario, descripcion, amount;

        public android.widget.ImageView ImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            nombreTextView = itemView.findViewById(R.id.textTituloCarrito);
            descripcion = itemView.findViewById(R.id.tvDescripcionMyCart);
            precioUnitario = itemView.findViewById(R.id.textPrecioMyCart);
            amount = itemView.findViewById(R.id.textViewAmount);

            ImageView = itemView.findViewById(R.id.imageView2);

        }
    }

}
