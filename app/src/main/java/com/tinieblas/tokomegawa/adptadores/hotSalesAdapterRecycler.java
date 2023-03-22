package com.tinieblas.tokomegawa.adptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.ui.utils.RandomColor;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.databinding.RowItemBinding;

import java.util.List;

public class hotSalesAdapterRecycler extends RecyclerView.Adapter<hotSalesAdapterRecycler.ViewHolder> {
    private final List<ModelohotSales> modelohotSales;
    private final Context context;
    private final LayoutInflater layoutInflater;
    //private final RecyclerViewInterface recyclerViewInterface;

    public hotSalesAdapterRecycler(List<ModelohotSales> modelohotSales,
                                   Context context/*,
                                   RecyclerViewInterface recyclerViewInterface*/){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.modelohotSales = modelohotSales;
        //this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public int getItemCount() {
        return modelohotSales.size();
    }

    @NonNull
    @Override
    public hotSalesAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemBinding rowItemBinding = RowItemBinding.inflate(layoutInflater, parent, false);
        //View view = layoutInflater.inflate(R.layout.row_item, null);
        return new ViewHolder(rowItemBinding);
        //return new hotSalesAdapterRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hotSalesAdapterRecycler.ViewHolder holder, int position) {
        holder.bindData(modelohotSales.get(position));

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RowItemBinding rowItemBinding;

        /*TextView Titulo;
        TextView Descripcion;
        ImageView Imagen1;
        //String Imagen2 = ListProducts.get(i).getImagen2();
        //String Imagen3 = ListProducts.get(i).getImagen3();
        //String Imagen4 = ListProducts.get(i).getImagen4();
        //float descuento = ListProducts.get(i).getDescuento();
        TextView Precio;
        String delivery = ListProducts.get(i).getDelivery();*/
        public ViewHolder(@NonNull RowItemBinding rowItemBinding) {

            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
            /*Titulo = itemView.findViewById(R.id.textTituloHotSales);
            Descripcion = itemView.findViewById(R.id.textDescripcionHotSales);
            Imagen1 = itemView.findViewById(R.id.imageImagenHotSales);
            Precio = itemView.findViewById(R.id.textPrecioHotSales);*/
        }

        public void bindData(final ModelohotSales item) {
            rowItemBinding.textTituloHotSales.setText(item.getTitulo());
            rowItemBinding.textDescripcionHotSales.setText(item.getDescripcion());
            rowItemBinding.textPrecioHotSales.setText(String.format("S/%s", (item.getPrecio())));

            RandomColor randomColor = new RandomColor();
            rowItemBinding.cardImagenHotSales.setCardBackgroundColor(randomColor.getColor());

            Glide.with(itemView).load(item.getImagen1())
                    .placeholder(R.drawable.frame_headphone)
                    .into(rowItemBinding.imageImagenHotSales);
        }
    }
}