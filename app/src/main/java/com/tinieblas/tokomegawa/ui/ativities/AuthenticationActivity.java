package com.tinieblas.tokomegawa.ui.ativities;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tinieblas.tokomegawa.databinding.ActivityAuthenticationBinding;
//import com.tinieblas.tokomegawa.ui.ativities.databinding.ActivityAuthenticationBinding;

import com.tinieblas.tokomegawa.R;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.tinieblas.tokomegawa.databinding.ActivityAuthenticationBinding binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }

}