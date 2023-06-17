package com.tinieblas.tokomegawa.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.tinieblas.tokomegawa.ui.fragments.HomeFragment;
import com.tinieblas.tokomegawa.ui.fragments.HotSalesFragment;
import com.tinieblas.tokomegawa.ui.fragments.LoginFragment;
import com.tinieblas.tokomegawa.ui.fragments.MyCartFragment;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.ui.fragments.RegistroFragment;
import com.tinieblas.tokomegawa.ui.fragments.SettingFragment;
import com.tinieblas.tokomegawa.ui.fragments.iLoveFragment;
import com.tinieblas.tokomegawa.utils.BottomSheetDialog;
import com.tinieblas.tokomegawa.utils.FireBase;
import com.tinieblas.tokomegawa.utils.NavigationContent;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ocultar barra de navegación y barra de estado
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(i -> {
            if (i == 0) {
                decorView.setSystemUiVisibility(hideSystemBar());
            }
        });

        try {
            getDataFirebase();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

        replaceFragment(new LoginFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    /*public void buttonFlotante(View view) {
        replaceFragment(new MyCartFragment());
    }*/

    public void buttonBack(View view) {
        replaceFragment(new HomeFragment());
    }

    /*public void buttonBackHotSales(View view) {
        replaceFragment(new HotSalesFragment());
    }*/

    public void seeModalLower(View view) {
        Toast.makeText(this, "Folder click", Toast.LENGTH_SHORT).show();
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
    }

    public void filtro(View view) {
        View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view, 0, 10, 0);
    }

    public void scogerFiltro(View view) {
        filtro(view);
    }

    public void seeAllHotSales(View view) {
        replaceFragment(new HotSalesFragment());
    }

    public void irHome(View view) {
        NavigationContent.changeFragment(getSupportFragmentManager(), new HomeFragment(), R.id.frameLayoutHome);
    }

    public void irFavoritos(View view) {
        NavigationContent.changeFragment(getSupportFragmentManager(), new iLoveFragment(), R.id.frameLayoutHome);
    }

    public void irSettings(View view) {
        NavigationContent.changeFragment(getSupportFragmentManager(), new SettingFragment(), R.id.frameLayoutHome);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBar());
        }
    }

    private int hideSystemBar() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    public void onClick(View view) {
        // No se realiza ninguna acción en el click del botón
    }

    public void buttonRegistrarse(View view) {
        replaceFragment(new RegistroFragment());
    }

    @Override
    public void onBackPressed() {
        // No se realiza ninguna acción al presionar el botón de retroceso
    }

    private void getDataFirebase() {
        FireBase fireBase = new FireBase();
        //fireBase.setApellidos("hola");
        //Toast.makeText(this, fireBase.getApellidos(), Toast.LENGTH_SHORT).show();
    }
}
