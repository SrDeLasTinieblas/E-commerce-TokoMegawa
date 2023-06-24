package com.tinieblas.tokomegawa.utils;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tinieblas.tokomegawa.R;

public class NavigationContent {

    public static void changeFragment(FragmentManager fragmentManager, Fragment newFragment, int containerId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void cambiarActividad(Context contextoActual, Class<?> claseNuevaActividad) {
        Intent intent = new Intent(contextoActual, claseNuevaActividad);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        contextoActual.startActivity(intent);
    }
    public static void replaceFragment(Context context, Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutLogin, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }




}
