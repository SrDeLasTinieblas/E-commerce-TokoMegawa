package com.tinieblas.tokomegawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.tinieblas.tokomegawa.Utils.BottomSheetDialog;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.adptadores.Modelos.RecyclerViewInterface;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {


    private View decorView;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0){
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });

        //gridView.setOnI

        replaceFragment(new HomeFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void onClickHotSalesRecycle(View view){
        //ModelohotSales modelohotSales = new ModelohotSales( new );
        
    }

    public void buttonFlotante(View view){
        replaceFragment(new MyCartFragment());
        /*Intent i = new Intent(this, MyCart_Activity.class);
        startActivity(i);*/
        /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }
    public void buttonBack(View view){
        replaceFragment(new HomeFragment());
    }

    public void buttonBackHotSales(View view){
        replaceFragment(new HotSalesFragment());
    }

    public void buttonDetailsProducts(View view){
        replaceFragment(new DetailsProductsFragment());
    }

    public void seeModalLower(View view){
        Toast.makeText(this , "Folder clcick" , Toast.LENGTH_SHORT).show();

        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }


    public void seeAllHotSales(View view){
        replaceFragment(new HotSalesFragment());
    }

    public void seeAllRecentlyViewed(View view){
        //replaceFragment(new HotSalesFragment());
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

    @Override
    public void onItemClick(int position) {

    }
}





























