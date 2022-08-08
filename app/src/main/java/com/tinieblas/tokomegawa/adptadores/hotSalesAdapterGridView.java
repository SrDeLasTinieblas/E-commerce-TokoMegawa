package com.tinieblas.tokomegawa.adptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;

import java.util.List;

public class hotSalesAdapterGridView extends BaseAdapter {

    Context context;
    List<ModelohotSales> modelohotSales;
    //private

    public hotSalesAdapterGridView(Context context, List<ModelohotSales> modelohotSales) {
        this.context = context;
        this.modelohotSales = modelohotSales;
    }

    @Override
    public int getCount() {
        return modelohotSales.size();
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
            view = inflater.inflate(R.layout.gridview_hot_sales_card, viewGroup, false);

        }

        String tituloActual = modelohotSales.get(i).getTitulo();
        String descripcionActual = modelohotSales.get(i).getDescripcion();
        float precioActual = modelohotSales.get(i).getPrecio();
        String imagen1Actual = modelohotSales.get(i).getImagen1();

        TextView tittle = view.findViewById(R.id.textTView_Titulo_HotSales);
        TextView descripcion = view.findViewById(R.id.textViewDescripcionHotSales);
        TextView precio = view.findViewById(R.id.textViewPrecioHotSales);
        ImageView imagen1 = view.findViewById(R.id.ImgView_hotSales);

        tittle.setText(tituloActual);
        descripcion.setText(descripcionActual);
        precio.setText(String.format("S/%s", precioActual));

        Glide.with(view)
                .load(imagen1Actual)
                .placeholder(R.drawable.frame_headphone)
                .into(imagen1);
        return view;
    }
}























