package com.tinieblas.tokomegawa.domain.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tinieblas.tokomegawa.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder>{

    private ArrayList<CategoriaModelo> categoriaModelos;
    private Context context;
    private OnCategoriaClickListener onCategoriaClickListener;
    private int selectedPosition = -1;
    private List<ProductosItem> productosList;
    public interface OnCategoriaClickListener {
        void onCategoriaClick(CategoriaModelo categoria);
    }

    public CategoriasAdapter(Context context, ArrayList<CategoriaModelo> categoriaModelos, List<ProductosItem> productosList,
                             OnCategoriaClickListener onCategoriaClickListener) {
        this.context = context;
        this.categoriaModelos = categoriaModelos;
        this.productosList = productosList;
        this.onCategoriaClickListener = onCategoriaClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_filter_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriaModelo categoria = categoriaModelos.get(position);
        holder.imageView.setImageResource(categoria.getLangLogo());
        holder.textView.setText(categoria.getLangName());

        // Cambiar el color de fondo de la tarjeta si está seleccionada
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }

        // Configurar el OnClickListener para la tarjeta de categoría
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la posición de la tarjeta de categoría seleccionada
                int previousSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);

                // Obtener la categoría seleccionada
                CategoriaModelo categoriaSeleccionada = categoriaModelos.get(selectedPosition);

                // Llamar al método de clic en la categoría del listener
                onCategoriaClickListener.onCategoriaClick(categoriaSeleccionada);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriaModelos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iconImageView);
            textView = itemView.findViewById(R.id.textTitulo);
        }
    }
}







































