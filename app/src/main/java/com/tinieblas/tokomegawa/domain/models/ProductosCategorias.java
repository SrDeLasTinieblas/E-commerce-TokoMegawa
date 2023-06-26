package com.tinieblas.tokomegawa.domain.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.ui.activities.DetailsActivity;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductosCategorias extends RecyclerView.Adapter<ProductosCategorias.ProductoViewHolder> {
    private final Context mContext;
    private List<ProductosItem> mProductos;
    SharedPreferences sharedPreferences;

    public ProductosCategorias(Context context, List<ProductosItem> productos) {
        mContext = context;
        mProductos = productos;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*@NonNull
    @Override
    public ProductosCategorias.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(com.tinieblas.tokomegawa.R.layout.card_productos_hot_sales, parent, false);
        return new ProductoViewHolder(view);
    }*/

    public void setProductos(List<ProductosItem> productos) {
        mProductos = productos;
        notifyDataSetChanged();
    }

    public void setProductosList(List<CategoriaModelo> categorias) {
        List<ProductosItem> productosList = new ArrayList<>();

        for (CategoriaModelo categoria : categorias) {
            productosList.addAll(categoria.getProductosList());
        }

        differ.submitList(productosList);
    }

/*
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductosItem producto = differ.getCurrentList().get(position);
        holder.textViewTitulo.setText(producto.getNombreProducto());
        holder.textViewPrecio.setText("S/. " + producto.getPrecioUnitario());
        holder.textViewDescripcion.setText(producto.getDescripcionProducto());
        Glide.with(mContext).load(producto.getImagen1()).into(holder.imageViewProducto);

        if (producto.getDelivery().equals("Free shipping")) {
            holder.buttonShipping.setVisibility(View.VISIBLE);
        } else {
            holder.buttonShipping.setVisibility(View.INVISIBLE);
        }

        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MutatingSharedPrefs")
            @Override
            public void onClick(View v) {
                Set<String> productosGuardados = sharedPreferences.getStringSet("productos_guardados",
                        new HashSet<>());
                int idProducto = producto.getIdProducto();

                if (productosGuardados.contains(String.valueOf(idProducto))) {
                    productosGuardados.remove(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto eliminado" + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                } else {
                    productosGuardados.add(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto guardado" + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("productos_guardados", productosGuardados);
                editor.apply();

                actualizarAparienciaBoton(holder.favorito, productosGuardados.contains(String.valueOf(idProducto)));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductosItem productoSeleccionado = differ.getCurrentList().get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("producto", productoSeleccionado);
                mContext.startActivity(intent);
            }
        });
    }*/

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(com.tinieblas.tokomegawa.R.layout.card_productos_hot_sales, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductosItem producto = differ.getCurrentList().get(position);
        holder.textViewTitulo.setText(producto.getNombreProducto());
        holder.textViewPrecio.setText("S/. " + producto.getPrecioUnitario());
        holder.textViewDescripcion.setText(producto.getDescripcionProducto());
        Glide.with(mContext).load(producto.getImagen1()).into(holder.imageViewProducto);

        if (producto.getDelivery().equals("Free shipping")) {
            holder.buttonShipping.setVisibility(View.VISIBLE);
        } else {
            holder.buttonShipping.setVisibility(View.INVISIBLE);
        }

        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MutatingSharedPrefs")
            @Override
            public void onClick(View v) {
                Set<String> productosGuardados = sharedPreferences.getStringSet("productos_guardados",
                        new HashSet<>());
                int idProducto = producto.getIdProducto();

                if (productosGuardados.contains(String.valueOf(idProducto))) {
                    productosGuardados.remove(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto eliminado" + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                } else {
                    productosGuardados.add(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto guardado" + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("productos_guardados", productosGuardados);
                editor.apply();

                actualizarAparienciaBoton(holder.favorito, productosGuardados.contains(String.valueOf(idProducto)));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductosItem productoSeleccionado = differ.getCurrentList().get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("producto", productoSeleccionado);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProducto;
        public TextView textViewTitulo;
        public TextView textViewPrecio;
        public TextView textViewDescripcion;
        public Button buttonShipping, favorito;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textTituloHotSales);
            textViewPrecio = itemView.findViewById(R.id.textPrecioHotSales);
            textViewDescripcion = itemView.findViewById(R.id.textDescripcionHotSales);
            imageViewProducto = itemView.findViewById(R.id.imageImagenHotSales);
            buttonShipping = itemView.findViewById(R.id.rectangle_5);
            favorito = itemView.findViewById(R.id.akar_icons_);
        }
    }

    private void actualizarAparienciaBoton(Button button, boolean productoGuardado) {
        if (productoGuardado) {
            button.setBackgroundResource(R.drawable.vector_ilike1);
        } else {
            button.setBackgroundResource(R.drawable.vector_ilike);
        }
    }

    private final DiffUtil.ItemCallback<ProductosItem> differCallback = new DiffUtil.ItemCallback<ProductosItem>() {
        @Override
        public boolean areItemsTheSame(ProductosItem oldItem, ProductosItem newItem) {
            return oldItem.getIdProducto() == newItem.getIdProducto();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(ProductosItem oldItem, ProductosItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public final AsyncListDiffer<ProductosItem> differ = new AsyncListDiffer<ProductosItem>(this, differCallback);

}
