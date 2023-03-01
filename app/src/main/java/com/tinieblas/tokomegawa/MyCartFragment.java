package com.tinieblas.tokomegawa;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinieblas.tokomegawa.Utils.FireBase;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.adptadores.hotSalesAdapterRecycler;
import com.tinieblas.tokomegawa.data.FirebaseData;
import com.tinieblas.tokomegawa.databinding.FragmentMyCartBinding;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

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




















