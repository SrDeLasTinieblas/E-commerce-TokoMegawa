package com.tinieblas.tokomegawa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.adptadores.hotSalesAdapterRecycler;
import com.tinieblas.tokomegawa.data.FirebaseData;
import com.tinieblas.tokomegawa.databinding.FragmentMyCartBinding;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MyCartFragment extends Fragment {

    MyCartFragment myCartFragment;
    View view;
    RequestQueue requestQueue;
    Button back;
    private final FirebaseData firebaseData = new FirebaseData();
    private FragmentMyCartBinding fragmentMyCartBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myCartFragment = this;
        fragmentMyCartBinding = FragmentMyCartBinding.inflate(inflater, container, false);

        //back = view.findViewById(R.id.buttonBack);

        firebaseData.uploadDataFireBase(getActivity());
        //fragmentMyCartBinding.textNombre.setText(ge);
        fragmentMyCartBinding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

        requestQueue = Volley.newRequestQueue(myCartFragment.getActivity());
        apiIpInfo();
        return fragmentMyCartBinding.getRoot();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
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

                            // Aca estamos diciendo que lo que esta en el carrito lo ponga una list
                            /*Type typeList = new TypeToken<List<ModelohotSales>>() {}.getType();

                            List<ModelohotSales> productsListResponse = new Gson().fromJson(response, typeList);

                            //ListProducts.addAll(productsListResponse);

                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context.getActivity(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false);*/
                            //fragmentHomeBinding.reciclerViewHotSales.setLayoutManager(linearLayoutManager2);
                            //fragmentHomeBinding.reciclerViewHotSales.setItemAnimator(new DefaultItemAnimator());

                            //hotSalesAdapterRecycler = new hotSalesAdapterRecycler(ListProducts,
                            //        context.getActivity()/*, getContext()*/);
                            //fragmentHomeBinding.reciclerViewHotSales.setAdapter(hotSalesAdapterRecycler);

                            //System.out.println("productsListResponse ==> "+productsListResponse);

                            //System.out.println("ListProducts ==> "+ListProducts.get(2).getTitulo());
                            //System.out.println(new Gson().toJson(ListProducts));

                            //listdata.add(new Gson().toJson(ListProducts));


                            //Bundle bundle = new Bundle();
                           // bundle.putString("lista1",listdata.toString());
                           // getParentFragmentManager().setFragmentResult("key1", bundle);

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




}




















