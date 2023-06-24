package com.tinieblas.tokomegawa.ui.adptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.domain.models.Modelo;

import java.util.ArrayList;

public class AdapterRecyclerFilter extends RecyclerView.Adapter<AdapterRecyclerFilter.ViewHolder> {
    ArrayList<Modelo> modelos;
    Context context;
    //final AdapterView.OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(Modelo item);
    }

    public AdapterRecyclerFilter(Context context, ArrayList<Modelo> models ){
        this.context = context;
        this.modelos = models;
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
        /*void bindData(final ListElement item){

        }*/

    }
}











































