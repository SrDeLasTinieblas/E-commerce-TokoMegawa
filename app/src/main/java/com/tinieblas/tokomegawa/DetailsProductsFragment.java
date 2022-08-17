package com.tinieblas.tokomegawa;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.constants.Constants;
import com.tinieblas.tokomegawa.databinding.DetailsProductsFragmentBinding;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailsProductsFragment extends Fragment {

    private ModelohotSales modelohotSales;
    DetailsProductsFragment context;
    //private SharedPreferences preferences;

    public int valor = 1;
    //GridView gridView;
    private final List<ModelohotSales> ListProductos = new ArrayList<>();

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

                //ModelohotSales modelohotSales;
                modelohotSales = result.getParcelable(Constants.INTENT_NAME_MODELO);
                ListProductos.add(modelohotSales);
                /*String titulo = result.getString("titulo");
                String descripcion = result.getString("descripcion");
                img1 = result.getString("img1");
                img2 = result.getString("img2");
                img3 = result.getString("img3");
                String img4 = result.getString("img4");
                float descuento = result.getFloat("descuento");
                float precio = result.getFloat("precio");
                String delivery = result.getString("delivery");
                int cantidad = result.getInt("cantidad");
                float PrecioTotal = result.getFloat("PrecioTotal");*/

                detailsProductsFragmentBinding.textTituloProduct.setText(modelohotSales.getTitulo());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setMovementMethod(new ScrollingMovementMethod());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setText(modelohotSales.getDescripcion());
                detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.valueOf("S/ "+modelohotSales.getPrecio()));
                //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.valueOf("S/ "+modelohotSales.getPreciototal()));
                //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
                //detailsProductsFragmentBinding.textCantidad.setText(cantidad);


                //detailsProductsFragmentBinding.imageDetailsProducts.setImageResource(img1);

                GlideImage();
                onClickListener(modelohotSales.getPrecio(), modelohotSales.getPreciototal());

                btnImages(modelohotSales.getImagen1(), modelohotSales.getImagen2(), modelohotSales.getImagen3());
                GuardarOrRemover();
                cargarDatosDelCarrito(modelohotSales.getId());
            }
        });

        detailsProductsFragmentBinding.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarOrRemover();
            }
        });


        //onClickListener(valor/*, precio*/);

        /*gridView = detailsProductsFragmentBinding.getRoot().findViewById(R.id.gridViewProducts);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context.getContext(), DetailsProductsFragment.class);

                intent.putExtra("ModelohotSales", ListProductos.get(i));
                startActivity(intent);
            }
        });*/
        ;
        //preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        //preferences = context.getActivity().getSharedPreferences("compras", Context.MODE_PRIVATE);

        return detailsProductsFragmentBinding.getRoot();

    }

    public void sendCarrito(){
        GuardarOrRemover();

    }
    boolean beSharedPreferences = true;
    boolean inCarrito = false;
    private final List<ModelohotSales> ListProductsCarrito = new ArrayList<>();

    private void cargarDatosDelCarrito(Integer id){
        /*if(preferences.contains("compras")){
            String datos = preferences.getString("compras", "");
            Type typeList = new TypeToken<List<ModelohotSales>>(){}.getType();
            ListProductsCarrito.addAll(new Gson().fromJson(datos,typeList));

            for (ModelohotSales modelo : ListProductsCarrito) {
                if (modelo.getId().equals(id)) {
                    beSharedPreferences = true;
                    inCarrito = inCarrito;
                    valor = this.modelohotSales.getCantidad();
                    break;
                }
            }

        }*/
    }

    private void GuardarOrRemover(){
        // Si hay datos
        if (beSharedPreferences){
            // si hay datos
            beSharedPreferences = false;
            ListProductsCarrito.add(modelohotSales);
            Toast.makeText(context.getContext(), "Añadido al carrito", Toast.LENGTH_SHORT).show();
            //detailsProductsFragmentBinding.animationView.setMinAndMaxProgress(0f, 0.20f);
            detailsProductsFragmentBinding.animationView.playAnimation();
            //detailsProductsFragmentBinding.animationView.pauseAnimation();


        }else {
            beSharedPreferences = true;

            Toast.makeText(context.getContext(), "Quitado del carrito", Toast.LENGTH_SHORT).show();
            detailsProductsFragmentBinding.animationView.setProgress(0);
            detailsProductsFragmentBinding.animationView.pauseAnimation();

            //detailsProductsFragmentBinding.animationView.playAnimation();
        }
    }


    private boolean CheckCartData(Integer idCarrito){


        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (getArguments() != null && getArguments().containsKey(ModelohotSales.Parcel)){
            ModelohotSales modelohotSales = getArguments().getParcelable(ModelohotSales.Parcel);
            detailsProductsFragmentBinding.textCantidad.setText(modelohotSales.getCantidad());
        }*/
    }

    public void GlideImage(){
        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen1())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts1);

        detailsProductsFragmentBinding.buttonShipping.setText(modelohotSales.getDelivery());

        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen1())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts1A);

        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen2())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts2B);

        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen3())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts3C);
    }

    public void btnImages(String img1, String img2, String img3){
        detailsProductsFragmentBinding.imageDetailsProducts1A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img1)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });

        detailsProductsFragmentBinding.imageDetailsProducts2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img2)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });

        detailsProductsFragmentBinding.imageDetailsProducts3C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img3)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });
    }

    public void onClickListener(float precioTotal, float Total){
        detailsProductsFragmentBinding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAumentando(precioTotal, Total);
            }
        });

        detailsProductsFragmentBinding.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDisminuyendo(precioTotal);
            }
        });
    }

    public void btnAumentando(float precioTotal, float total){
        modelohotSales.setCantidad(++valor);
        //System.out.println(modelohotSales.getCantidad());

        BigDecimal bd = new BigDecimal(modelohotSales.getPreciototal()).setScale(2, RoundingMode.HALF_UP);
        detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
        detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.format("S/%s", bd));

        //Intent intent = new Intent(context.getContext(), ModelohotSales.class);
        //intent.putExtra("PrecioTotal", precioTotal);
        //System.out.println(total);
        //detailsProductsFragmentBinding.textCantidad.setText(s);
        //System.out.println(++valor);
        //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
        //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(("S/"+(modelohotSales.getPrecioTotal())));

    }

    public void btnDisminuyendo(float precioTotal){
        if (valor > 1){
            modelohotSales.setCantidad(--valor);
            //System.out.println(modelohotSales.getCantidad());

            BigDecimal bd = new BigDecimal(modelohotSales.getPreciototal()).setScale(2, RoundingMode.HALF_UP);
            detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.format("S/%s", bd));
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





















