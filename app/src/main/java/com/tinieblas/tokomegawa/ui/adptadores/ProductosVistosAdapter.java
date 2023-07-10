package com.tinieblas.tokomegawa.ui.adptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.ui.activities.DetailsActivity;

import java.util.List;

public class ProductosVistosAdapter extends RecyclerView.Adapter<ProductosVistosAdapter.ProductosVistosAdapterViewHolder> {

    private Context mContext;
    private List<ProductosItem> mProductos;

    public ProductosVistosAdapter(Context mContext, List<ProductosItem> mProductos) {
        this.mContext = mContext;
        this.mProductos = mProductos;
    }

    @NonNull
    @Override
    public ProductosVistosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_recently_viewed, parent, false);
        return new ProductosVistosAdapterViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductosVistosAdapterViewHolder holder, int position) {
        ProductosItem producto = mProductos.get(position);
        holder.textViewTitulo.setText(producto.getNombreProducto());
        holder.textViewPrecio.setText("S/. " + producto.getPrecioUnitario());
        holder.textViewDescripcion.setText(producto.getDescripcionProducto());
        Glide.with(mContext).load(producto.getImagen1()).into(holder.imageViewProducto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductosItem productoSeleccionado = mProductos.get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("producto", productoSeleccionado);
                mContext.startActivity(intent);
            }
        });
    }

    public void setProductosVistos(List<ProductosItem> productos) {
        mProductos = productos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mProductos.size();
    }

    public static class ProductosVistosAdapterViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewProducto;
        public TextView textViewTitulo;
        public TextView textViewPrecio;
        public TextView textViewDescripcion;

        public ProductosVistosAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProducto = itemView.findViewById(R.id.ImgView_Recently);
            textViewTitulo = itemView.findViewById(R.id.textTView_Titulo_ImgView_recently);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecio_recently);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcionImgView_recently);



        }

    }



}






















