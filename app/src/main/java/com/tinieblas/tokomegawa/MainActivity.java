package com.tinieblas.tokomegawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.tinieblas.tokomegawa.Utils.BottomSheetDialog;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.adptadores.Modelos.RecyclerViewInterface;
import com.tinieblas.tokomegawa.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {

    private ModelohotSales modelohotSales;
    private SharedPreferences preferences;
    private View decorView;
    private final List<ModelohotSales> ListProducts = new ArrayList<>();
    boolean beSharedPreferences = true;
    boolean inCarrito = false;
    private final List<ModelohotSales> ListProductsCarrito = new ArrayList<>();

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

        Intent i = new Intent(this, DetailsProductsFragment.class);
        i.putExtra("key","2");

        //gridView.setOnI
        //getClassModelo();
        replaceFragment(new HomeFragment());
        //System.out.println(modelohotSales.getTitulo());
        //GuardarOrRemover();

    }

    private void main(){
        preferences = getSharedPreferences(Constants.INTENT_NAME_CARRITO, Context.MODE_PRIVATE);
        //CheckCartData(modelohotSales.getId());
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

    public void buttonFlotante(View view) {

        /*int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                break;
*/
        //ModelohotSales modelohotSales = new ModelohotSales("");

        //Bundle bundle = getIntent().getExtras();
        //modelohotSales = bundle.getParcelable(Constants.INTENT_NAME);
        //System.out.println("hola ==> ");

        replaceFragment(new LoginFragment());

        //Intent intent = new Intent(MainActivity.this, DetailsProductsFragment.class);
        //intent.putExtra(Constants.INTENT_NAME, "5");

        //startActivity(intent);
        //System.out.println((modelohotSales.getTitulo()));
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

    public void getClassModelo() {
        //Intent intent = new Intent(MainActivity.this, DetailsProductsFragment.class);
        //ModelohotSales modelohotSales = new ModelohotSales();

        Bundle bundle = getIntent().getExtras();
        modelohotSales = bundle.getParcelable("lista1");

        System.out.println((modelohotSales));
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

    public void butonRegistrarse(View view){
        replaceFragment(new RegistroFragment());
    }

    public void buttonHeart(View view) {

        //if (pre){

        //GuardarOrRemover();
        //Thread.sleep(3000);
        //animationView.cancelAnimation();

        //}else {
            //animationView.cancelAnimation();
        //}
    }

    /*private boolean CheckCartData(Integer idCarrito){



    return false;
    }*/


    /*private void GuardarOrRemover(){
       LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animationView);
        if (beSharedPreferences){
            beSharedPreferences = false;
            Toast.makeText(this, "prefrerences: "+beSharedPreferences, Toast.LENGTH_SHORT).show();
            animationView.playAnimation();
        }else {
            beSharedPreferences = true;
            ListProductsCarrito.add(modelohotSales);
            Toast.makeText(this, "prefrerences: "+beSharedPreferences, Toast.LENGTH_SHORT).show();
            animationView.cancelAnimation();
        }
    }*/




    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();

        /*if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }*/
        /*int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();*/
    }
}





























