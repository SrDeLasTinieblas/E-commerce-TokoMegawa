package com.tinieblas.tokomegawa;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.constants.Constants;
import com.tinieblas.tokomegawa.databinding.DetailsProductsFragmentBinding;

import java.util.Objects;

public class DetailsProductsFragment extends Fragment {

    private ModelohotSales modelohotSales;
    DetailsProductsFragment context;
    ImageView imageView1, imageView2, imageView3;
    String img1, img2, img3;
    public int valor = 1;
    //GridView gridView;
    //private final List<ModelohotSales> ListProductos = new ArrayList<>();

    private DetailsProductsFragmentBinding detailsProductsFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this;
        //getClassModelohotSales();
        detailsProductsFragmentBinding = DetailsProductsFragmentBinding.inflate(inflater, container, false);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String titulo = result.getString("titulo");
                String descripcion = result.getString("descripcion");
                img1 = result.getString("img1");
                img2 = result.getString("img2");
                img3 = result.getString("img3");
                String img4 = result.getString("img4");
                Float descuento = result.getFloat("descuento");
                float precio = result.getFloat("precio");
                String delivery = result.getString("delivery");
                int cantidad = result.getInt("cantidad");

                detailsProductsFragmentBinding.textTituloProduct.setText(titulo);
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setMovementMethod(new ScrollingMovementMethod());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setText(descripcion);
                detailsProductsFragmentBinding.textPrecioDestailsProductos.setText("S/ "+precio);

                detailsProductsFragmentBinding.textCantidad.setText(cantidad);



                //detailsProductsFragmentBinding.imageDetailsProducts.setImageResource(img1);

                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img1)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);

                detailsProductsFragmentBinding.buttonShipping.setText(delivery);

                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img1)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1A);

                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img2)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts2B);

                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img3)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts3C);

                onClickListener(titulo);
            }

        });
        btnImages();



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

    @Override
    public void onStart() {
        super.onStart();
        /*if (getArguments() != null && getArguments().containsKey(ModelohotSales.Parcel)){
            ModelohotSales modelohotSales = getArguments().getParcelable(ModelohotSales.Parcel);
            detailsProductsFragmentBinding.textCantidad.setText(modelohotSales.getCantidad());

        }*/
    }

    public void btnImages(){
        imageView1 = detailsProductsFragmentBinding.imageDetailsProducts1A.findViewById(R.id.imageDetailsProducts1A);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img1)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });

        imageView2 = detailsProductsFragmentBinding.imageDetailsProducts2B.findViewById(R.id.imageDetailsProducts2B);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img2)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });

        imageView3 = detailsProductsFragmentBinding.imageDetailsProducts3C.findViewById(R.id.imageDetailsProducts3C);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img3)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });
    }

    public void onClickListener(String cantidad){
        detailsProductsFragmentBinding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAumentando(cantidad);
            }
        });

        detailsProductsFragmentBinding.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDisminuyendo();
            }
        });
    }

    public void btnAumentando(String cantidad){
        //modelohotSales.setCantidad(++valor);
        //++valor;
        int v = valor;
        //valor *
        //String s = String.valueOf(cantidad);

        detailsProductsFragmentBinding.textCantidad.setText(cantidad);
        //detailsProductsFragmentBinding.textCantidad.setText(s);
        //System.out.println(++valor);
        //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
        //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(("S/"+(modelohotSales.getPrecioTotal())));

    }
    public void btnDisminuyendo(){
        if (valor > 1){
            --valor;
            int v = valor;
            String s = String.valueOf(v);
            detailsProductsFragmentBinding.textCantidad.setText(s);
            //modelohotSales.setCantidad(--valor);
            //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText("S/"+(modelohotSales.getPrecioTotal()));
        }
        else {
            detailsProductsFragmentBinding.textCantidad.setText("1");
            //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText("S/"+(modelohotSales.getPrecioTotal()));

        }

    }


    private void getClassModelohotSales(){
        //Bundle bundle = getActivity().getIntent().getExtras();
        //modelohotSales = bundle.getParcelable(ModelohotSales.Parcel);

        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            modelohotSales = bundle.getParcelable("modelohotSales");
        }*/
        /*if (getArguments() != null && getArguments().containsKey(ModelohotSales.Parcel)){
            ModelohotSales modelohotSales = getArguments().getParcelable(ModelohotSales.Parcel);
            detailsProductsFragmentBinding.textCantidad.setText(modelohotSales.getCantidad());

        }*/

    }


}





















