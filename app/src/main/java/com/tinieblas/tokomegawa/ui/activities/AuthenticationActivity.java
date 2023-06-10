package com.tinieblas.tokomegawa.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tinieblas.tokomegawa.databinding.ActivityAuthenticationBinding;
//import com.tinieblas.tokomegawa.ui.ativities.databinding.ActivityAuthenticationBinding;


public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.tinieblas.tokomegawa.databinding.ActivityAuthenticationBinding binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }

}