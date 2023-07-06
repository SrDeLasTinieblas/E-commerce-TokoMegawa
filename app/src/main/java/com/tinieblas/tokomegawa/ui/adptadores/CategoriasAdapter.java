package com.tinieblas.tokomegawa.ui.adptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ItemsFilterCardsBinding;
import com.tinieblas.tokomegawa.domain.models.CategoriaModelo;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;

import java.util.ArrayList;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private ArrayList<CategoriaModelo> categoriaModelos;
    private final Context context;
    private final OnCategoriaClickListener onCategoriaClickListener;
    private int selectedPosition = -1;
    private final ArrayList<CategoriaModelo> productosList;
    private final ArrayList<String> categorias;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ProductosItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnCategoriaClickListener {
        void onCategoriaClick(CategoriaModelo categoria);
    }

    public CategoriasAdapter(Context context, ArrayList<String> categorias, ArrayList<CategoriaModelo> productosList, OnCategoriaClickListener onCategoriaClickListener) {
        this.context = context;
        this.categorias = categorias;
        this.productosList = productosList;
        this.onCategoriaClickListener = onCategoriaClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsFilterCardsBinding binding = ItemsFilterCardsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriaModelo categoria = productosList.get(position);
        holder.bind(categoria);
    }

    @Override
    public int getItemCount() {
        return categorias != null ? categorias.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemsFilterCardsBinding binding;

        public ViewHolder(@NonNull ItemsFilterCardsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CategoriaModelo categoria) {
            binding.iconImageView.setImageResource(categoria.getLangLogo());
            binding.textTitulo.setText(categoria.getLangName());

            // Cambiar el color de fondo de la tarjeta si está seleccionada
            if (selectedPosition == getAdapterPosition()) {
                binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            } else {
                binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }

            // Configurar el OnClickListener para la tarjeta de categoría
            binding.getRoot().setOnClickListener(view -> {
                int previousSelectedPosition = selectedPosition;
                selectedPosition = getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);
                CategoriaModelo categoriaSeleccionada = productosList.get(selectedPosition);
                onCategoriaClickListener.onCategoriaClick(categoriaSeleccionada);
            });
        }
    }

    public CategoriaModelo getSelectedCategoria() {
        if (selectedPosition != -1 && selectedPosition < productosList.size()) {
            return productosList.get(selectedPosition);
        }
        return null;
    }
}
