package com.tinieblas.tokomegawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View decorView;
    RequestQueue requestQueue;
    /*RecyclerView recyclerViewHotSales;
    ArrayList<MainModel> mainModels;
    hotSalesAdapter hotSalesAdapter;

    RecyclerView recyclerViewRecentlyViewd;
    ArrayList<Modelo> models;
    RecentlyViewedAdapter recentlyViewedAdapter;

    Integer[] langLogo= new Integer[0];
    String[] langName = new String[0];*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();
        //FloatingActionButton fab = findViewById(R.id.fab);


        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0){
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });

        // Assign Variable
        /*recyclerViewHotSales = findViewById(R.id.recicler_view_hotSales);
        recyclerViewRecentlyViewd = findViewById(R.id.recicler_view_recently);

        langLogo = new Integer[]{R.drawable.macbook_air_m1, R.drawable.mbp_shop_,
                R.drawable.mbp_shop_, R.drawable.macbook_air_m1, R.drawable.macbook_air_m1};

        //Initialize ArrayList
        langName = new String[]{"macbook_air_m1", "macbook_air_m2", "macbook_air_m3", "macbook_air_m4", "macbook_air_m5"};
        setRecyclerViewHotSales();
        setRecyclerViewRecentlyViewd();*/

        replaceFragment(new HomeFragment());


    }

    /*public void setPrimerFragment(View view){
        replaceFragment(new HomeFragment());
    }*/

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void buttonFlotante(View view){
        replaceFragment(new MyCartFragment());
        /*Intent i = new Intent(this, MyCart_Activity.class);
        startActivity(i);*/
        /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }

    private void getData() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://my-json-server.typicode.com/SrDeLasTinieblas/Api-feik-Productos/db",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //Sleep(5000);

                            // Aca estamos diciendo que lo que esta en el carrito lo ponga una list
                            //Type typeList = new TypeToken<List<Carrito>>() {}.getType();

                            /**
                             * Creamos una list con la response que nos llega de la api y le decimos que con gson
                             *  lo convierta a un tipo de lista y lo guarde en "productsListResponse"
                             */
                            //List<Carrito> productsListResponse = new Gson().fromJson(response, typeList);

                            // llenamos el productsList con los datos que ya hemos guardado antes
                            //productsList.addAll(productsListResponse);

                            /**
                             * Aqui usamos lo que tenemos en la clase de adaptador y lo instanciamos
                             *  le pasamos como parametro el contexto y la lista de productos
                             *  para despues actualizar todo lo que tenemos en el adaptador
                             */
                            //adaptador = new Adaptador(MainActivity.this, productsList);
                            //gridView.setAdapter(adaptador);

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


    /*public void setRecyclerViewHotSales(){
        mainModels = new ArrayList<>();
        for (int i=0; i< langLogo.length;i++){
            MainModel model = new MainModel(langLogo[i], langName[i]);
            mainModels.add(model);
        }
        //Design Horizontal Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false );
        recyclerViewHotSales.setLayoutManager(linearLayoutManager);
        recyclerViewHotSales.setItemAnimator(new DefaultItemAnimator());

        // Initialize adaptador
        hotSalesAdapter = new hotSalesAdapter(MainActivity.this, mainModels);
        recyclerViewHotSales.setAdapter(hotSalesAdapter);
    }

    public void setRecyclerViewRecentlyViewd(){
        models = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            Modelo model = new Modelo(langLogo[i], langName[i]);
            models.add(model);
        }
        //Design Horizontal Layout
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerViewRecentlyViewd.setLayoutManager(linearLayoutManager2);
        recyclerViewRecentlyViewd.setItemAnimator(new DefaultItemAnimator());

        // Initial adaptador
        recentlyViewedAdapter = new RecentlyViewedAdapter(MainActivity.this, models);
        recyclerViewRecentlyViewd.setAdapter(recentlyViewedAdapter);

    }

    public void DetailsProducts(View view){
        Intent i = new Intent(this, Details_Activity.class);
        startActivity(i);
    }
*/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBar());
        }
    }

    private int hideSystemBar(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    public void onClick(View view) {
    }
}





























