package com.tinieblas.tokomegawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tinieblas.tokomegawa.adptadores.MainModel;
import com.tinieblas.tokomegawa.adptadores.Modelo;
import com.tinieblas.tokomegawa.adptadores.RecentlyViewedAdapter;
import com.tinieblas.tokomegawa.adptadores.hotSalesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View decorView;
    RecyclerView recyclerViewHotSales;
    ArrayList<MainModel> mainModels;
    hotSalesAdapter hotSalesAdapter;

    RecyclerView recyclerViewRecentlyViewd;
    ArrayList<Modelo> models;
    RecentlyViewedAdapter recentlyViewedAdapter;

    Integer[] langLogo= new Integer[0];
    String[] langName = new String[0];
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
        recyclerViewHotSales = findViewById(R.id.recicler_view_hotSales);
        recyclerViewRecentlyViewd = findViewById(R.id.recicler_view_recently);

        langLogo = new Integer[]{R.drawable.macbook_air_m1, R.drawable.mbp_shop_,
                R.drawable.mbp_shop_, R.drawable.macbook_air_m1, R.drawable.macbook_air_m1};

        //Initialize ArrayList
        langName = new String[]{"macbook_air_m1", "macbook_air_m2", "macbook_air_m3", "macbook_air_m4", "macbook_air_m5"};
        setRecyclerViewHotSales();
        setRecyclerViewRecentlyViewd();



    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void buttonFlotante(View view){
        Intent i = new Intent(this, MyCart_Activity.class);
        startActivity(i);
        /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }

    public void setRecyclerViewHotSales(){
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





























