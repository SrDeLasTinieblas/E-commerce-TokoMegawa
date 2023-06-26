package com.tinieblas.tokomegawa.ui.adptadores;

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
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.ui.activities.DetailsActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {
    private final Context mContext;
    private List<ProductosItem> mProductos;
    SharedPreferences sharedPreferences;

    public ProductosAdapter(Context context, List<ProductosItem> productos) {
        mContext = context;
        mProductos = productos;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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

    public void setProductosList(List<ProductosItem> productosItem) {
        //mProductos.clear();
        //mProductos.addAll(productosItem);
        //notifyDataSetChanged();
        differ.submitList(new ArrayList<>(productosItem));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductosItem producto = differ.getCurrentList().get(position);
        holder.textViewTitulo.setText(producto.getNombreProducto());
        holder.textViewPrecio.setText("S/. " + producto.getPrecioUnitario());
        holder.textViewDescripcion.setText(producto.getDescripcionProducto());
        Glide.with(mContext).load(producto.getImagen1()).into(holder.imageViewProducto);
        // Generar un color aleatorio
        //RandomColor randomColor = new RandomColor();
        //int cardColor = randomColor.getColor();

        // Verificar si el campo "delivery" es igual a "Free shipping"
        if (producto.getDelivery().equals("Free shipping")) {
            holder.buttonShipping.setVisibility(View.VISIBLE);
        } else {
            holder.buttonShipping.setVisibility(View.INVISIBLE);
        }

        // Configurar el OnClickListener para el botón
        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MutatingSharedPrefs")
            @Override
            public void onClick(View v) {
                // Obtener la lista actual de productos guardados en SharedPreferences
                Set<String> productosGuardados = sharedPreferences.getStringSet("productos_guardados", new HashSet<String>());
                int idProducto = producto.getIdProducto();

                // Verificar si el producto ya está en la lista
                if (productosGuardados.contains(String.valueOf(idProducto))) {
                    // Si el producto ya está en la lista, eliminarlo
                    productosGuardados.remove(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto eliminado" + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                } else {
                    // Si el producto no está en la lista, agregarlo
                    productosGuardados.add(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto guardado" + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                }

                // Guardar la lista actualizada en SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("productos_guardados", productosGuardados);
                editor.apply();

                // Actualizar la apariencia del botón según si el producto está en la lista o no
                actualizarAparienciaBoton(holder.favorito, productosGuardados.contains(String.valueOf(idProducto)));
            }
        });

        // Configurar el clic en el elemento del RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtener el producto seleccionado
                ProductosItem productoSeleccionado = differ.getCurrentList().get(holder.getAdapterPosition());

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
        return differ.getCurrentList().size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        //CardProductosHotSalesBinding cardProductosHotSalesBinding;
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
            // Producto guardado: establecer apariencia deseada
            button.setBackgroundResource(R.drawable.vector_ilike1);
            //button.setText("Producto guardado");
        } else {
            // Producto no guardado: establecer apariencia deseada
            button.setBackgroundResource(R.drawable.vector_ilike);
            //button.setText("Guardar producto");
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