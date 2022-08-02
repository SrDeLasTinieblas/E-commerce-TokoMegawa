package com.tinieblas.tokomegawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tinieblas.tokomegawa.adptadores.MainModel;
import com.tinieblas.tokomegawa.adptadores.Modelo;
import com.tinieblas.tokomegawa.adptadores.RecentlyViewedAdapter;
import com.tinieblas.tokomegawa.adptadores.hotSalesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewHotSales;
    ArrayList<MainModel> mainModels;
    hotSalesAdapter hotSalesAdapter;

    RecyclerView recyclerViewRecentlyViewd;
    ArrayList<Modelo> models;
    RecentlyViewedAdapter recentlyViewedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign Variable
        recyclerViewHotSales = findViewById(R.id.recicler_view_hotSales);
        recyclerViewRecentlyViewd = findViewById(R.id.recicler_view_recently);

        Integer[] langLogo = {R.drawable.macbook_air_m1, R.drawable.mbp_shop_,
                R.drawable.mbp_shop_, R.drawable.macbook_air_m1, R.drawable.macbook_air_m1};

        //Initialize ArrayList
        String[] langName = {"macbook_air_m1", "macbook_air_m2", "macbook_air_m3", "macbook_air_m4", "macbook_air_m5"};

        mainModels = new ArrayList<>();
        for (int i=0; i<langLogo.length;i++){
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

}
