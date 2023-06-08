package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.respositories.FirebaseData;
import com.tinieblas.tokomegawa.databinding.FragmentMyCartBinding;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MyCartFragment extends Fragment {

    MyCartFragment myCartFragment;
    RequestQueue requestQueue;
    private final FirebaseData firebaseData = new FirebaseData();
    private FragmentMyCartBinding fragmentMyCartBinding;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myCartFragment = this;
        fragmentMyCartBinding = FragmentMyCartBinding.inflate(inflater, container, false);
        firebaseData.uploadDataFireBase(getActivity());

        buttonBack();
        requestQueue = Volley.newRequestQueue(myCartFragment.getActivity());
        apiIpInfo();
        uploadDataFireBase();
        /*FirebaseData firebaseData = new FirebaseData();
        firebaseData.getDataProductos();*/

        return fragmentMyCartBinding.getRoot();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

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
    }

    public void apiIpInfo(){
        //https://ipinfo.io/190.238.238.236/json
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://ipinfo.io/190.238.238.236/json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ob = new JSONObject(response);
                            String city = ob.getString("city");
                            String country = ob.getString("country");
                            //System.out.println(firstName);
                            fragmentMyCartBinding.city.setText(String.format("%s,", city));
                            fragmentMyCartBinding.Pais.setText(country);

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
    }

    public void uploadDataFireBase() {

        /*FireBase fireBase = new FireBase();
        Toast.makeText(getContext(), "==:>> " + fireBase.getNombres(), Toast.LENGTH_SHORT).show();
        fragmentMyCartBinding.textNombre.setText(fireBase.getNombres());*/
        firebaseData.getDataUser(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                name = value.getString("nombres");
                fragmentMyCartBinding.textNombre.setText(name);
            }

        });
    }

    private void buttonBack(){
        fragmentMyCartBinding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });
    }

}




















