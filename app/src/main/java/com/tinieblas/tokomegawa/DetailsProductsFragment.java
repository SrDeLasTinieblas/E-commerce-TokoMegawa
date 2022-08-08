package com.tinieblas.tokomegawa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.constants.Constants;
import com.tinieblas.tokomegawa.databinding.DetailsProductsFragmentBinding;

public class DetailsProductsFragment extends Fragment {

    DetailsProductsFragment context;

    //GridView gridView;
    //private final List<ModelohotSales> ListProductos = new ArrayList<>();

    private DetailsProductsFragmentBinding detailsProductsFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this;
        detailsProductsFragmentBinding = DetailsProductsFragmentBinding.inflate(inflater, container, false);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String titulo = result.getString("titulo");
                String descripcion = result.getString("descripcion");
                String img1 = result.getString("img1");
                String img2 = result.getString("img2");
                String img3 = result.getString("img3");
                String img4 = result.getString("img4");
                String descuento = result.getString("descuento");
                String precio = result.getString("precio");
                String delivery = result.getString("delivery");

                detailsProductsFragmentBinding.textTituloProduct.setText(titulo);
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setText(descripcion);
                detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(precio);
                //detailsProductsFragmentBinding.imageDetailsProducts.setImageResource(img1);

                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img1)
                        .placeholder(R.drawable.frame_headphone)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);

                detailsProductsFragmentBinding.buttonShipping.setText(delivery);

                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img2)
                        .placeholder(R.drawable.frame_headphone)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts2);

            }
        });

        /*gridView = detailsProductsFragmentBinding.getRoot().findViewById(R.id.gridViewProducts);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context.getContext(), DetailsProductsFragment.class);

                intent.putExtra("ModelohotSales", ListProductos.get(i));
                startActivity(intent);
            }
        });*/

        return detailsProductsFragmentBinding.getRoot();

    }

}





















