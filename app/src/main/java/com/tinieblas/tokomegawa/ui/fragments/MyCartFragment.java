package com.tinieblas.tokomegawa.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.remote.UserRepositoryImp;
import com.tinieblas.tokomegawa.databinding.FragmentMyCartBinding;

public class MyCartFragment extends Fragment {
/**
    MyCartFragment myCartFragment;

    private final UserRepositoryImp repository = new UserRepositoryImp();

    private FragmentMyCartBinding fragmentMyCartBinding;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myCartFragment = this;
        fragmentMyCartBinding = FragmentMyCartBinding.inflate(inflater, container, false);
        //firebaseData.uploadDataFireBase(getActivity());

        buttonBack();

        uploadDataFireBase();
        /*FirebaseData firebaseData = new FirebaseData();
        firebaseData.getDataProductos();*/

    /**    return fragmentMyCartBinding.getRoot();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
/*
    public void getData() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://my-json-server.typicode.com/SrDeLasTinieblas/Peliculas/productos",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Aca estamos diciendo que lo que esta en el carrito lo ponga una list
                            Type typeList = new TypeToken<List<ModelohotSales>>() {}.getType();

                            List<ModelohotSales> productsListResponse = new Gson().fromJson(response, typeList);

                            /*ListProducts.addAll(productsListResponse);

                            adapterGridView = new hotSalesAdapterGridView(context.getContext(), ListProducts);
                            gridView.setAdapter(adapterGridView);*/
/*
                        } catch (Exception e) {
                            Log.d("JSONException", e.getMessage());
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error.networkResponse + " error");
                    }
                }
        );
        // Aqui enviamos la solicitud de la peticion
        requestQueue.add(request);
    }*/
/**
    public void uploadDataFireBase() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                name = repository.getUser();
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyCartBinding.textNombre.setText(name);
                    }
                });

            }
        }).start();

    }

    private void buttonBack() {
        fragmentMyCartBinding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });
    }
*/
}




















