package com.tinieblas.tokomegawa.adptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.adptadores.Modelos.MainModel;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;

import java.util.ArrayList;
import java.util.List;

public class hotSalesAdapter extends  BaseAdapter {

    //ArrayList<MainModel> mainModels;
    Context context;
    List<ModelohotSales> ListProducts;
    //List<ModelohotSales> ListProductsOriginal;

    public hotSalesAdapter(Context context, List<ModelohotSales> listProducts) {
        //this.mainModels = mainModels;
        this.context = context;
        this.ListProducts = listProducts;
        //ListProductsOriginal = listProductsOriginal;
    }

    @Override
    public int getCount() {
        return ListProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item, viewGroup, false);
        }
        String Titulo = ListProducts.get(i).getTitulo();
        String Descripcion = ListProducts.get(i).getDescripcion();
        String Imagen1 = String.valueOf(ListProducts.get(i).getImagen1());
        //String Imagen2 = ListProducts.get(i).getImagen2();
        //String Imagen3 = ListProducts.get(i).getImagen3();
        //String Imagen4 = ListProducts.get(i).getImagen4();
        //float descuento = ListProducts.get(i).getDescuento();
        float precio = ListProducts.get(i).getPrecio();
        //String delivery = ListProducts.get(i).getDelivery();

        TextView textTitulo = view.findViewById(R.id.textTituloHotSales);
        TextView textDescripcion = view.findViewById(R.id.textDescripcionHotSales);
        //TextView imageImagen1 = view.findViewById(R.id.imageImagenHotSales);
        TextView textPrecio = view.findViewById(R.id.textPrecioHotSales);
        /*TextView textTitulo = view.findViewById(R.id.textTituloHotSales);
        TextView textTitulo = view.findViewById(R.id.textTituloHotSales);
        TextView textTitulo = view.findViewById(R.id.textTituloHotSales);
        TextView textTitulo = view.findViewById(R.id.textTituloHotSales);
        TextView textTitulo = view.findViewById(R.id.textTituloHotSales);*/
        ImageView imageImagen1 = view.findViewById(R.id.imageImagenHotSales);

        textTitulo.setText(Titulo);
        textDescripcion.setText(Descripcion);
        //imageImagen1.setText(Imagen1);
        //textPrecio.setText();
        Glide.with(view)
                .load(Imagen1)
                .placeholder(R.drawable.frame_headphone)
                .into(imageImagen1);

        return view;
    }


    /*public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize Variable
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            imageView = itemView.findViewById(R.id.ImgView);
            textView = itemView.findViewById(R.id.textView);
        }
    }*/
}

