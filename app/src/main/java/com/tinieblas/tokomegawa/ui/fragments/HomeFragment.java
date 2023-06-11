package com.tinieblas.tokomegawa.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;*/
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.respositories.ProductosCallback;
import com.tinieblas.tokomegawa.ui.activities.MyCartActivity;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosAdapter;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.ui.activities.MainActivity;
import com.tinieblas.tokomegawa.ui.adptadores.Modelos.Modelo;
import com.tinieblas.tokomegawa.ui.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.ui.adptadores.Modelos.RecyclerFilter;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosVistosAdapter;
import com.tinieblas.tokomegawa.ui.adptadores.RecentlyViewedAdapterRecycler;
import com.tinieblas.tokomegawa.databinding.FragmentHomeBinding;
import com.tinieblas.tokomegawa.utils.NavigationContent;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {
    HomeFragment context;
    ArrayList<Modelo> models;
    RecentlyViewedAdapterRecycler recentlyViewedAdapterRecycler;
    RecyclerFilter recyclerFilter;
    private ProductosVistosAdapter productosVistosAdapter;
    Integer[] langLogo= new Integer[0];
    String[] langName = new String[0];
    String Nombre, Apellido, Direccion, Email, Username, ID;
    Integer Edad;
    FirebaseFirestore firebaseFirestore;
    // Obtener una instancia de FirebaseAuth
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //RequestQueue requestQueue;
    //hotSalesAdapterRecycler hotSalesAdapterRecycler;
    ProductosAdapter productosAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    List<ProductosItem> productosList = new ArrayList<>();
    private final List<ModelohotSales> ListProducts = new ArrayList<>();
    //private OnButtonSelectedListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=this;
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        productosAdapter = new ProductosAdapter(getContext(), new ArrayList<>());

        langLogo = new Integer[]{R.drawable.macbook_air_m1, R.drawable.mbp_shop_,
                R.drawable.mbp_shop_, R.drawable.macbook_air_m1, R.drawable.macbook_air_m1};

        langName = new String[]{"macbook_air_m1", "macbook_air_m2", "macbook_air_m3", "macbook_air_m4", "macbook_air_m5"};
        //setRecyclerViewHotSales();
        //setRecyclerViewRecentlyViewd();

        fragmentHomeBinding.buttonSalida.setOnClickListener(v -> {
            //Intent intent = new Intent(getContext(), AuthenticationActivity.class);
            //startActivity(intent);
            mAuth.signOut(); // Cerrar sesión
            Intent i=new Intent(getContext(), MainActivity.class);
            startActivity(i);
        });
/*
        fragmentHomeBinding.buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });
        fragmentHomeBinding.buttoniLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new iLoveFragment());
            }
        });
        fragmentHomeBinding.buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SettingFragment());
            }
        });*/

        //requestQueue = Volley.newRequestQueue(context.getActivity());

        //System.out.println(" fetch ==>"+fetchProductos());

        //System.out.println("==> fetchProductos "+fetchProductos());
        fetchProductos(new ProductosCallback() {
            @Override
            public void onProductosFetched(List<ProductosItem> productos) {
                context.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addCards(productos);
                    }
                });
            }
        });

        //addCards(productosList);
        fragmentHomeBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationContent.cambiarActividad(getContext(), MyCartActivity.class);
            }
        });
        setCardsFilter();
        getListaProductoVistos();
        return fragmentHomeBinding.getRoot();
    }

    private void fetchProductos(ProductosCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("http://tokomegawa.somee.com/getProductos")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        Gson gson = new Gson();
                        com.tinieblas.tokomegawa.models.Producto.Response responseObject = gson.fromJson(responseData, com.tinieblas.tokomegawa.models.Producto.Response.class);
                        List<ProductosItem> productos = responseObject.getProductos();
                        callback.onProductosFetched(productos);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void addCards(List<ProductosItem> productosItemList) {
        // Crear e inicializar el adaptador
        ProductosAdapter productosAdapter = new ProductosAdapter(requireContext(), productosItemList);

        // Configurar el diseño del RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
            fragmentHomeBinding.reciclerViewHotSales.setLayoutManager(linearLayoutManager);
            fragmentHomeBinding.reciclerViewHotSales.setItemAnimator(new DefaultItemAnimator());


        // Asignar el adaptador al RecyclerView en el hilo principal de la UI utilizando runOnUiThread()
        context.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragmentHomeBinding.reciclerViewHotSales.setAdapter(productosAdapter);
            }
        });
    }

    private void getListaProductoVistos() {
        // Obtener la instancia del SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("productos_vistos", Context.MODE_PRIVATE);
        // Obtener los valores almacenados en el SharedPreferences
        String productosVistosJson = sharedPreferences.getString("lista_productos_vistos", "");

        if (productosVistosJson.isEmpty()) {
            // No hay items en el SharedPreferences
            Log.d("HomeFragment", "No hay items disponibles");
            return;
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<ProductosItem>>() {}.getType();
        List<ProductosItem> productosVistos = gson.fromJson(productosVistosJson, listType);

        if (productosVistos == null) {
            // La lista de productos vistos es nula
            Log.d("HomeFragment", "La lista de productos vistos es nula");
            return;
        }

        Collections.reverse(productosVistos);

        fragmentHomeBinding.reciclerViewRecently.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productosVistosAdapter = new ProductosVistosAdapter(getContext(), productosVistos);
        fragmentHomeBinding.reciclerViewRecently.setAdapter(productosVistosAdapter);
        System.out.println("productosVistosJson ==> "+productosVistosJson);
        System.out.println(" cantidad de productos vistos ==> "+ productosList.size());

    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void setCardsFilter(){
        /*langLogo = new Integer[]{R.drawable.frame_headphone, R.drawable.earphone,
                R.drawable.frame_headphone, R.drawable.earphone, R.drawable.frame_headphone};*/

        //langName = new String[]{"Headset", "earphone", "Headset", "Headset", "Headset"};


        models = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            Modelo model = new Modelo(langLogo[i], langName[i]);
            models.add(model);
        }

        //Design Horizontal Layout
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context.getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        fragmentHomeBinding.cardFilterRecyclerView.setLayoutManager(linearLayoutManager2);
        fragmentHomeBinding.cardFilterRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Initial adaptador
        recyclerFilter = new RecyclerFilter(context.getActivity(), models);
        fragmentHomeBinding.cardFilterRecyclerView.setAdapter(recyclerFilter);


    }

    public void setRecyclerViewRecentlyViewd(){

        models = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            Modelo model = new Modelo(langLogo[i], langName[i]);
            models.add(model);
        }
        //Design Horizontal Layout
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context.getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        fragmentHomeBinding.reciclerViewRecently.setLayoutManager(linearLayoutManager2);
        fragmentHomeBinding.reciclerViewRecently.setItemAnimator(new DefaultItemAnimator());

        // Initial adaptador
        recentlyViewedAdapterRecycler = new RecentlyViewedAdapterRecycler(context.getActivity(), models);
        fragmentHomeBinding.reciclerViewRecently.setAdapter(recentlyViewedAdapterRecycler);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHomeBinding = null;
    }

    @Override
    public void onClick(View view) {

    }
    /*public void irMyCart(){
        NavigationContent.cambiarActividad(getContext(), MyCartActivity.class);
    }*/

    /*@Override
    public void onClick(View view) {
        System.out.println("onClick");
        SignOut();
        System.out.println("2222222222222222222222222222");
    }

    public void SignOut(){
        //mAuth.signOut(); // Cerrar sesión
        System.out.println("SignOut");
    }
*/
    /*public interface OnButtonSelectedListener {
        void onButtonSelected(int buttonId);
    }*/

    //@Override
    /*public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnButtonSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonSelectedListener");
        }*/

}
























