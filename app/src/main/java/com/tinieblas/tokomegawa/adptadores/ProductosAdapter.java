package com.tinieblas.tokomegawa.adptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.CardProductosHotSalesBinding;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.ui.utils.RandomColor;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {
    private final Context mContext;
    private List<ProductosItem> mProductos;

    public ProductosAdapter(Context context, List<ProductosItem> productos) {
        mContext = context;
        mProductos = productos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_productos_hot_sales, parent, false);
        return new ProductoViewHolder(view);
    }

    public void setProductos(List<ProductosItem> productos) {
        mProductos = productos;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductosItem producto = mProductos.get(position);
        holder.textViewTitulo.setText(producto.getNombreProducto());
        holder.textViewPrecio.setText("$" + producto.getPrecioUnitario());
        holder.textViewDescripcion.setText(producto.getDescripcionProducto());
        Glide.with(mContext).load(producto.getImagen1()).into(holder.imageViewProducto);
        //holder.bindData(mProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        CardProductosHotSalesBinding cardProductosHotSalesBinding;
        public ImageView imageViewProducto;
        public TextView textViewTitulo;
        public TextView textViewPrecio;
        public TextView textViewDescripcion;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textTituloHotSales);
            textViewPrecio = itemView.findViewById(R.id.textPrecioHotSales);
            textViewDescripcion = itemView.findViewById(R.id.textDescripcionHotSales);
            imageViewProducto = itemView.findViewById(R.id.imageImagenHotSales);
        }
            /*public void bindData(final ProductosItem item) {
                cardProductosHotSalesBinding.textTituloHotSales.setText(item.getNombreProducto());
                cardProductosHotSalesBinding.textDescripcionHotSales.setText(item.getDescripcionProducto());
                cardProductosHotSalesBinding.textPrecioHotSales.setText(String.format("S/%s", (item.getPrecioUnitario())));


            /*RandomColor randomColor = new RandomColor();
            cardProductosHotSalesBinding.cardImagenHotSales.setCardBackgroundColor(randomColor.getColor());
               */
         /*   Glide.with(itemView).load(item.getImagen1())
                .placeholder(R.drawable.frame_headphone)
                        .into(cardProductosHotSalesBinding.imageImagenHotSales);
        }*/
    }
}
