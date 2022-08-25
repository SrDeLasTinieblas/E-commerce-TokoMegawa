package com.tinieblas.tokomegawa.adptadores.Modelos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tinieblas.tokomegawa.R;

import java.util.ArrayList;

public class RecyclerFilter extends RecyclerView.Adapter<RecyclerFilter.ViewHolder> {
    ArrayList<Modelo> modelos;
    Context context;

    public RecyclerFilter(Context context, ArrayList<Modelo> models ){
        this.context = context;
        this.modelos = models;
    }

    @NonNull
    @Override
    public RecyclerFilter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_filter_cards, parent, false);
        return new RecyclerFilter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFilter.ViewHolder holder, int position) {
        // Set logo ImageView
        //holder.imageView
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
            imageView = itemView.findViewById(R.id.iconImageView);
            textView = itemView.findViewById(R.id.textTitulo);
        }
    }
}
