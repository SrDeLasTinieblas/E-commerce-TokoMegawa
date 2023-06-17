package com.tinieblas.tokomegawa.ui.adptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.CardProductosHotSalesBinding;
import com.tinieblas.tokomegawa.databinding.FragmentHomeBinding;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.ui.activities.DetailsActivity;
import com.tinieblas.tokomegawa.utils.RandomColor;
//import com.tinieblas.tokomegawa.ui.activities.DetailsProductos;

import java.util.ArrayList;
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

    /*public void setProductos(List<ProductosItem> productos) {
        mProductos = productos;
        notifyDataSetChanged();
    }*/
    public void setProductosList(List<ProductosItem> productosItem) {
        mProductos.clear();
        mProductos.addAll(productosItem);
        notifyDataSetChanged();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {

        ProductosItem producto = mProductos.get(position);
        holder.textViewTitulo.setText(producto.getNombreProducto());
        holder.textViewPrecio.setText("S/. " + producto.getPrecioUnitario());
        holder.textViewDescripcion.setText(producto.getDescripcionProducto());
        Glide.with(mContext).load(producto.getImagen1()).into(holder.imageViewProducto);
        // Generar un color aleatorio
        //RandomColor randomColor = new RandomColor();
        //int cardColor = randomColor.getColor();

        // Establecer el color como fondo de la card


        // Configurar el clic en el elemento del RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtener el producto seleccionado
                ProductosItem productoSeleccionado = mProductos.get(holder.getAdapterPosition());

                // Crear un intent para abrir la actividad DetailsActivity
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("producto", productoSeleccionado);
                mContext.startActivity(intent);

            }
        });
        //holder.itemView.findViewById(R.id.cardImagenHotSales).setBackgroundColor(cardColor);
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
    }
    /*public void filtrar(String texto, List<ProductosItem> productosItem) {
        List<ProductosItem> productosFiltrados = new ArrayList<>();

        // Recorre la lista de productos y agrega los que coincidan con el texto de búsqueda
        for (ProductosItem producto : productosItem) {
            if (producto.getNombreProducto().toLowerCase().contains(texto.toLowerCase())) {
                productosFiltrados.add(producto);
            }
        }

        // Actualiza la lista de productos del adaptador
        setProductos(productosFiltrados);
        Log.e("A filtrar ==> ", productosItem.toString());
        Log.e("Filtrado ==> ", productosFiltrados.toString());

    }*/
    /*public void filtrar(String texto, List<ProductosItem> productosItem) {
        List<ProductosItem> productosFiltrados = new ArrayList<>();

        if (texto.isEmpty()) {
            // Si el texto de búsqueda está vacío, mostrar todos los productos
            productosFiltrados.addAll(productosItem);
        } else {
            // Recorre la lista de productos y agrega los que coincidan con el texto de búsqueda
            for (ProductosItem producto : productosItem) {
                if (producto.getNombreProducto().toLowerCase().contains(texto.toLowerCase())) {
                    productosFiltrados.add(producto);
                }
            }
        }

        // Actualiza la lista de productos del adaptador
        setProductos(productosFiltrados);
    }*/
    /*
    public void filtrarProductos(String texto, List<ProductosItem> productosCompletos) {
        List<ProductosItem> productosFiltrados = new ArrayList<>();

        if (texto.isEmpty()) {
            // Si el texto de búsqueda está vacío, mostrar todos los productos
            productosFiltrados.addAll(productosCompletos);
        } else {
            // Recorre la lista de productos completos y agrega los que coincidan con el texto de búsqueda
            for (ProductosItem producto : productosCompletos) {
                if (producto.getNombreProducto().toLowerCase().contains(texto.toLowerCase())) {
                    productosFiltrados.add(producto);
                }
            }
        }

        // Actualiza la lista de productos del adaptador
        setProductos(productosFiltrados);
    }


    public void filtrando(List<ProductosItem> productosItem) {
        // Agrega el TextWatcher
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String textoBusqueda = editable.toString();
                filtrarProductos(textoBusqueda, productosItem);
                Log.e("Texto Busqueda ==> ", textoBusqueda);
            }
        };

        // Asigna el TextWatcher al EditText
        /*fragmentHomeBinding.editTextSearch.addTextChangedListener(textWatcher);

        // Configura el adaptador en el RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.reciclerViewHotSales.setLayoutManager(linearLayoutManager);
        fragmentHomeBinding.reciclerViewHotSales.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.reciclerViewHotSales.setAdapter(this);*/

        // Filtra los productos al iniciar la aplicación
       /* filtrarProductos("", productosItem);
    }*/









}
