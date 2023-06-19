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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
/*
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;*/
import com.google.android.material.slider.RangeSlider;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.APIs;
import com.tinieblas.tokomegawa.data.FetchRequest;
import com.tinieblas.tokomegawa.respositories.ProductosCallback;
import com.tinieblas.tokomegawa.ui.activities.AuthenticationActivity;
import com.tinieblas.tokomegawa.ui.activities.MyCartActivity;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosAdapter;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.ui.adptadores.Modelos.RecyclerFilter;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosVistosAdapter;
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
    private HomeFragment context;
    private RecyclerFilter recyclerFilter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PopupWindow popupWindow;
    private ProductosAdapter productosAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    private final List<ProductosItem> productosListOriginal = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this;
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        // Crear el adaptador de productos
        productosAdapter = new ProductosAdapter(getContext());

        // Configurar el RecyclerView de productos destacados
        fragmentHomeBinding.reciclerViewHotSales.setLayoutManager(new LinearLayoutManager(context.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentHomeBinding.reciclerViewHotSales.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.reciclerViewHotSales.setAdapter(productosAdapter);

        // Configurar el RecyclerView de productos recientemente vistos
        fragmentHomeBinding.reciclerViewRecently.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Botón de salida
        fragmentHomeBinding.buttonSalida.setOnClickListener(v -> {
            mAuth.signOut(); // Cerrar sesión
            Intent i = new Intent(getContext(), AuthenticationActivity.class);
            startActivity(i);
        });

        // Botón para ir al carrito de compras
        fragmentHomeBinding.fab.setOnClickListener(view -> NavigationContent.cambiarActividad(getContext(), MyCartActivity.class));

        fetchProductos(new ProductosCallback() {
            @Override
            public void onProductosFetched(List<ProductosItem> productos) {
                context.requireActivity().runOnUiThread(() -> addCards(productos));
                productosListOriginal.addAll(productos);
            }
        });

        fragmentHomeBinding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarTitulo(editable.toString());
            }
        });

        fragmentHomeBinding.buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro(view);
            }
        });

        getListaProductoVistos();
        //fetchGet();
        return fragmentHomeBinding.getRoot();
    }

    private boolean isChecked = false;
    public void filtro(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            isChecked = false; // Reiniciar el estado al cerrar el popup
        } else {
            View popupView = LayoutInflater.from(requireActivity()).inflate(R.layout.popup, null);
            //View popupView = getLayoutInflater().inflate(R.layout.popup, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Obtener una referencia al botón button2
            Button Check = popupView.findViewById(R.id.buttonCheck);

            // Actualizar el fondo del botón según el estado actual
            if (isChecked) {
                Check.setBackgroundResource(R.drawable.check1);
            }

            // Agregar el evento onClick al botón button2
            Check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isChecked = !isChecked; // Alternar el estado al hacer clic

                    if (isChecked) {
                        Check.setBackgroundResource(R.drawable.check1);
                    } else {
                        // Aquí puedes establecer el fondo deseado cuando no está marcado
                        Check.setBackgroundResource(R.drawable.check);
                    }

                    // Aquí puedes realizar otras acciones según el estado
                }
            });

            RangeSlider rangeSlider = popupView.findViewById(R.id.rangeSlider);
            rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull RangeSlider slider) {

                }

                @Override
                public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                    List<Float> values = slider.getValues();
                    float desde = values.get(0);
                    float hasta = values.get(1);
                    filtrarPrecio(desde, hasta);
                }
            });
            popupWindow.showAsDropDown(view, 0, 0);
        }
    }
    private void filtrarTitulo(String texto){
        List<ProductosItem> listaFiltrar = new ArrayList<>();
        for (ProductosItem item: productosListOriginal){
            if (item.getNombreProducto().toLowerCase().contains(texto.toLowerCase())){
                listaFiltrar.add(item);
            }

        }
        //productosAdapter.differ.submitList(new ArrayList<>(listaFiltrar));
        productosAdapter.setProductosList(listaFiltrar);
        Log.e("listaFiltrar",listaFiltrar.toString());
        Log.e("productosListOriginal",productosListOriginal.toString());
    }
    private void filtrarPrecio(float desde, float hasta){
        List<ProductosItem> filtrarLista = new ArrayList<>();
        for (ProductosItem item: productosListOriginal){
            if ( desde <= item.getPrecioUnitario() && item.getPrecioUnitario() <= hasta ){
                filtrarLista.add(item);
            }
        }
        productosAdapter.setProductosList(filtrarLista);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListaProductoVistos();
    }

    private void fetchProductos(ProductosCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                //.url("http://tokomegawa.somee.com/getProductos")
                .url(APIs.ApiUrlBase + APIs.ApiProductos)
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

    private void fetchGet() {
        FetchRequest fetcher = new FetchRequest();
        //String apiUrl = APIs.ApiUrlBase + APIs.ApiProductos;

        String apiUrl = APIs.ApiUrlBase + "/getCategorias";
        fetcher.fetchCategorias(apiUrl, categoriasResponses -> Log.e("productos fetch ==> ", categoriasResponses.toString()));
    }


    private void addCards(List<ProductosItem> productosItemList) {
        // Actualizar la lista de productos destacados
        productosAdapter.setProductosList(productosItemList);
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

        // Actualizar el adaptador de productos vistos recientemente
        ProductosVistosAdapter productosVistosAdapter = new ProductosVistosAdapter(getContext(), productosVistos);
        fragmentHomeBinding.reciclerViewRecently.setAdapter(productosVistosAdapter);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
    public void SignOut(View view){
        mAuth.signOut(); // Cerrar sesión
        System.out.println("SignOut");
    }
    @Override
    public void onClick(View view) {
        // Aquí puedes manejar los eventos de clic si es necesario
    }
}


