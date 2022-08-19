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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinieblas.tokomegawa.adptadores.Modelos.Modelo;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.adptadores.RecentlyViewedAdapterRecycler;
import com.tinieblas.tokomegawa.adptadores.hotSalesAdapterRecycler;
import com.tinieblas.tokomegawa.databinding.FragmentHomeBinding;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    HomeFragment context;
    //ArrayList<MainModel> mainModels;

    ArrayList<Modelo> models;
    RecentlyViewedAdapterRecycler recentlyViewedAdapterRecycler;

    Integer[] langLogo= new Integer[0];
    String[] langName = new String[0];

    RequestQueue requestQueue;
    hotSalesAdapterRecycler hotSalesAdapterRecycler;
    private FragmentHomeBinding fragmentHomeBinding;
    ArrayList<String> listdata = new ArrayList<>();
    private final List<ModelohotSales> ListProducts = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=this;
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        

        langLogo = new Integer[]{R.drawable.macbook_air_m1, R.drawable.mbp_shop_,
                R.drawable.mbp_shop_, R.drawable.macbook_air_m1, R.drawable.macbook_air_m1};

        langName = new String[]{"macbook_air_m1", "macbook_air_m2", "macbook_air_m3", "macbook_air_m4", "macbook_air_m5"};
        //setRecyclerViewHotSales();
        setRecyclerViewRecentlyViewd();

        fragmentHomeBinding.buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

        fragmentHomeBinding.buttonSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Cerramos sesion
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getActivity(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
                    replaceFragment(new LoginFragment());
            }
        });
        //fragmentHomeBinding.reciclerViewHotSales.setOnClickListener(new );


        requestQueue = Volley.newRequestQueue(context.getActivity());
        getData();
        return fragmentHomeBinding.getRoot();
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

                            ListProducts.addAll(productsListResponse);

                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context.getActivity(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false);

                            fragmentHomeBinding.reciclerViewHotSales.setLayoutManager(linearLayoutManager2);
                            fragmentHomeBinding.reciclerViewHotSales.setItemAnimator(new DefaultItemAnimator());

                            hotSalesAdapterRecycler = new hotSalesAdapterRecycler(ListProducts,
                                    context.getActivity()/*, getContext()*/);
                            fragmentHomeBinding.reciclerViewHotSales.setAdapter(hotSalesAdapterRecycler);

                            //System.out.println("productsListResponse ==> "+productsListResponse);

                            //System.out.println("ListProducts ==> "+ListProducts.get(2).getTitulo());
                            //System.out.println(new Gson().toJson(ListProducts));

                            listdata.add(new Gson().toJson(ListProducts));


                            Bundle bundle = new Bundle();
                            bundle.putString("lista1",listdata.toString());
                            getParentFragmentManager().setFragmentResult("key1", bundle);

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

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void setRecyclerViewHotSales(){

        /*mainModels = new ArrayList<>();
        for (int i=0; i< langLogo.length;i++){
            MainModel model = new MainModel(langLogo[i], langName[i]);
            mainModels.add(model);
        }
        //Design Horizontal Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false );*/
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
}
























