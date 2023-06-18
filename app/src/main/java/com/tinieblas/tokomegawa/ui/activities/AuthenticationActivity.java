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


public class AuthenticationActivity extends AppCompatActivity {
    private View decorView;
    ActivityAuthenticationBinding activityAuthenticationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBar();
        activityAuthenticationBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());


        /*LinearLayout llButtons = findViewById(R.id.llButtons);
        llButtons.setVisibility(View.GONE);*/
        replaceFragment(new LoginFragment());
        //FragmentManager fragmentManager;
        //NavigationContent.changeFragment(getSupportFragmentManager(), new LoginFragment(), R.id.frameLayoutHome);
        //NavigationContent.changeFragment(getSupportFragmentManager(), new SettingFragment(), R.id.frameLayoutHome);
        setContentView(activityAuthenticationBinding.getRoot());
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutLogin, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
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
}