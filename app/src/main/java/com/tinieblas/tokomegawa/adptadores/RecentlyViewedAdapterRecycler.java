package com.tinieblas.tokomegawa.adptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.adptadores.Modelos.Modelo;

import java.util.ArrayList;

public class RecentlyViewedAdapterRecycler extends RecyclerView.Adapter<RecentlyViewedAdapterRecycler.ViewHolder>  {
    ArrayList<Modelo> modelos;
    Context context;

    public RecentlyViewedAdapterRecycler(Context context, ArrayList<Modelo> models ){
        this.context = context;
        this.modelos = models;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_recently_viewed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set logo ImageView
        holder.imageView.setImageResource(modelos.get(position).getLangLogo());
        holder.textView.setText(modelos.get(position).getLangName());
    }

    @Override
    public int getItemCount() {
        return modelos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            imageView = itemView.findViewById(R.id.ImgView_Recently);
            textView = itemView.findViewById(R.id.textTitulo_recently_viewd);
        }
    }
}
