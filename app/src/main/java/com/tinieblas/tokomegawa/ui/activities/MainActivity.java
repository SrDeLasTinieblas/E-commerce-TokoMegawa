package com.tinieblas.tokomegawa.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityMainBinding;
import com.tinieblas.tokomegawa.databinding.ActivityMyCartBinding;
import com.tinieblas.tokomegawa.domain.models.FireBaseModel;
import com.tinieblas.tokomegawa.ui.fragments.HomeFragment;
import com.tinieblas.tokomegawa.ui.fragments.HotSalesFragment;
import com.tinieblas.tokomegawa.ui.fragments.LoginFragment;
import com.tinieblas.tokomegawa.ui.fragments.MyCartFragment;
import com.tinieblas.tokomegawa.ui.fragments.SettingFragment;
import com.tinieblas.tokomegawa.ui.fragments.iLoveFragment;
import com.tinieblas.tokomegawa.utils.BottomSheetDialog;
import com.tinieblas.tokomegawa.utils.NavigationContent;

public class MainActivity extends AppCompatActivity implements /*RecyclerViewInterface, */View.OnClickListener {
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
            getDataFireBase();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }


    public void buttonFlotante(View view) {
        replaceFragment(new MyCartFragment());
    }

    public void buttonBack(View view) {
        replaceFragment(new HomeFragment());
    }

    public void buttonBackHotSales(View view) {
        replaceFragment(new HotSalesFragment());
    }

    public void seeModalLower(View view) {
        Toast.makeText(this, "Folder clcick", Toast.LENGTH_SHORT).show();

        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }

    public void filtro(View view) {
        // Infla el archivo XML que define la vista del popup
        View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        // Crea un objeto PopupWindow con la vista del popup
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // Muestra el popup en la posición deseada
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void scogerFiltro(View view) {
        filtro(view);
    }

    public void seeAllHotSales(View view) {
        replaceFragment(new HotSalesFragment());
    }

    public void irHome(View view){
        System.out.println("irHome");
        NavigationContent.changeFragment(getSupportFragmentManager(), new HomeFragment(), R.id.frameLayoutHome);
    }

    public void irFavoritos(View view){
        System.out.println("irFavoritos");
        NavigationContent.changeFragment(getSupportFragmentManager(), new iLoveFragment(), R.id.frameLayoutHome);
    }

    public void irSettings(View view){
        System.out.println("irSettings");
        NavigationContent.changeFragment(getSupportFragmentManager(), new SettingFragment(), R.id.frameLayoutHome);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBar());
        }
    }

    private int hideSystemBar(){
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });
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
        //replaceFragment(new RegistroFragment());
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

    private void getDataFireBase(){
        FireBaseModel fireBaseModel = new FireBaseModel();
        //fireBaseModel.setApellidos("hola");

        //Toast.makeText(this,fireBaseModel.getApellidos() , Toast.LENGTH_SHORT).show();

    }


}

