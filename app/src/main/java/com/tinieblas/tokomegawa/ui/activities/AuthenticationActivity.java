package com.tinieblas.tokomegawa.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityAuthenticationBinding;
import com.tinieblas.tokomegawa.ui.fragments.LoginFragment;
import com.tinieblas.tokomegawa.utils.NavigationContent;
import com.tinieblas.tokomegawa.utils.hideMenu;


public class AuthenticationActivity extends AppCompatActivity {
    private View decorView;
    ActivityAuthenticationBinding activityAuthenticationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        getWindow().getDecorView().setSystemUiVisibility(hideMenu.hideSystemBar(decorView));

        activityAuthenticationBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());

        replaceFragment(new LoginFragment());
        System.out.println("Authentication activity");
        setContentView(activityAuthenticationBinding.getRoot());
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutLogin, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}