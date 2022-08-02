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

import java.util.ArrayList;

public class hotSalesAdapter extends RecyclerView.Adapter<hotSalesAdapter.ViewHolder> {
    ArrayList<MainModel> mainModels;
    Context context;

    public hotSalesAdapter(Context context, ArrayList<MainModel> mainModels ){
        this.context = context;
        this.mainModels = mainModels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set logo ImageView
        holder.imageView.setImageResource(mainModels.get(position).getLangLogo());
        holder.textView.setText(mainModels.get(position).getLangName());

    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize Variable
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            imageView = itemView.findViewById(R.id.ImgView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}

