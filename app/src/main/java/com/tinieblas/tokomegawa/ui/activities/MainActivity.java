package com.tinieblas.tokomegawa.ui.activities;

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
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.domain.models.FireBaseModel;
import com.tinieblas.tokomegawa.ui.fragments.HomeFragment;
import com.tinieblas.tokomegawa.ui.fragments.HotSalesFragment;
import com.tinieblas.tokomegawa.ui.fragments.SettingFragment;
import com.tinieblas.tokomegawa.utils.BottomSheetDialog;
import com.tinieblas.tokomegawa.utils.NavigationContent;
import com.tinieblas.tokomegawa.utils.hideMenu;

public class MainActivity extends AppCompatActivity implements /*RecyclerViewInterface, */View.OnClickListener {

    private LoginRepositoryImp repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new LoginRepositoryImp();
        // Ocultar barra de navegación y barra de estado
        View decorView;
        decorView = getWindow().getDecorView();
        getWindow().getDecorView().setSystemUiVisibility(hideMenu.hideSystemBar(decorView));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void buttonBack(View view) {
        replaceFragment(new HomeFragment());
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
        NavigationContent.changeFragment(getSupportFragmentManager(), new HomeFragment(), R.id.frameLayoutHome);
    }

    public void irSettings(View view){
        validateLogin();
    }
    private void validateLogin() {
        boolean isLogged = repository.getCurrentUser();
        if (isLogged) {
            NavigationContent.changeFragment(getSupportFragmentManager(), new SettingFragment(), R.id.frameLayoutHome);

        }
        else {
            Toast.makeText(this, "Debe iniciar sesion primero", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View view) {
    }


    @Override
    public void onBackPressed() {
    }

}

