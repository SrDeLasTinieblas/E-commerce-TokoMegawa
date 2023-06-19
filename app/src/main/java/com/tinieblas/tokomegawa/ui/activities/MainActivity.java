package com.tinieblas.tokomegawa.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.tinieblas.tokomegawa.databinding.ActivityMainBinding;
import com.tinieblas.tokomegawa.ui.fragments.HomeFragment;
import com.tinieblas.tokomegawa.ui.fragments.HotSalesFragment;
import com.tinieblas.tokomegawa.ui.fragments.LoginFragment;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.ui.fragments.RegistroFragment;
import com.tinieblas.tokomegawa.ui.fragments.SettingFragment;
import com.tinieblas.tokomegawa.ui.fragments.iLoveFragment;
import com.tinieblas.tokomegawa.utils.BottomSheetDialog;
import com.tinieblas.tokomegawa.utils.FireBase;
import com.tinieblas.tokomegawa.utils.NavigationContent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding activityMainBinding;
    private View decorView;
    private PopupWindow popupWindow; // Declarar como variable miembro en tu fragmento

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


        /*activityMainBinding.buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationContent.changeFragment(getSupportFragmentManager(), new HomeFragment(), R.id.frameLayoutHome);
            }
        });*/




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
    private boolean isChecked = false;

    /*public void filtro(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            isChecked = false; // Reiniciar el estado al cerrar el popup
        } else {
            View popupView = getLayoutInflater().inflate(R.layout.popup, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Obtener una referencia al botón button2
            Button Check = popupView.findViewById(R.id.buttonCheck);

            // Actualizar el fondo del botón según el estado actual
            if (isChecked) {
                Check.setBackgroundResource(R.drawable.check1);
            }

            // Agregar el evento onClick al botón button2
            Check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isChecked = !isChecked; // Alternar el estado al hacer clic

                    if (isChecked) {
                        Check.setBackgroundResource(R.drawable.check1);
                    } else {
                        // Aquí puedes establecer el fondo deseado cuando no está marcado
                        Check.setBackgroundResource(R.drawable.check);
                    }

                    // Aquí puedes realizar otras acciones según el estado
                }
            });

            popupWindow.showAsDropDown(view, 0, 0);
        }
    }*/


    public void scogerFiltro(View view) {
        //filtro(view);
        //vibrate(2000);
    }
    public void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator)  getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }
    public void seeAllHotSales(View view) {
        replaceFragment(new HotSalesFragment());
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
