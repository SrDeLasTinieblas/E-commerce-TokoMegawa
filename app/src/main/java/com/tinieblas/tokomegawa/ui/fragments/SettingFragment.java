package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentSettingBinding;
import com.tinieblas.tokomegawa.models.User;
import com.tinieblas.tokomegawa.ui.activities.MyCartActivity;
import com.tinieblas.tokomegawa.utils.NavigationContent;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private static final int File = 1;
    private static final int RESULT_OK = 2 ;
    DatabaseReference myRef;
    SettingFragment settingFragment;
    FragmentSettingBinding fragmentSettingBinding;
    private FirebaseFirestore mFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settingFragment = this;
        fragmentSettingBinding = FragmentSettingBinding.inflate( inflater, container, false);

        // Obtener la instancia del SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        // Obtener los valores almacenados en el SharedPreferences
        String nombre = sharedPreferences.getString("userNombre", "");

        fragmentSettingBinding.toBack.setOnClickListener(view ->
                Toast.makeText(getContext(), "ToBack", Toast.LENGTH_SHORT).show()
                //NavigationContent.changeFragment(getSupportFragmentManager(), new SettingFragment(), R.id.frameLayoutHome);
        );

        /**
         *     public void irHome(View view) {
         *         NavigationContent.changeFragment(getSupportFragmentManager(), new HomeFragment(), R.id.frameLayoutHome);
         *     }
         * */

        getNombreUser(nombre);
        return fragmentSettingBinding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    private void getNombreUser(String userId) {
        // Obtener la referencia del TextView dentro del include
        TextView textView = fragmentSettingBinding.incluideNombre.textNombreSettings;
        textView.setText(userId);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(getContext(), "ToBack", Toast.LENGTH_SHORT).show();
    }



}


































