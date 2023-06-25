package com.tinieblas.tokomegawa.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentSettingBinding;
import com.tinieblas.tokomegawa.ui.activities.MainActivity;

public class SettingFragment extends Fragment {

    SettingFragment settingFragment;
    FragmentSettingBinding fragmentSettingBinding;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settingFragment = this;
        fragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false);

        fragmentSettingBinding.switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableDarkMode();
                    Toast.makeText(getContext(), "Modo oscuro activado", Toast.LENGTH_SHORT).show();
                } else {
                    disableDarkMode();
                    Toast.makeText(getContext(), "Modo oscuro desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        fragmentSettingBinding.buttonArrowCupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cupon", Toast.LENGTH_SHORT).show();
            }
        });
        
        

        return fragmentSettingBinding.getRoot();
    }
    public void enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        ((MainActivity) requireActivity()).getDelegate().applyDayNight();
        /*if (mode == 0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/
    }
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ((MainActivity) requireActivity()).getDelegate().applyDayNight();
        /*if (mode == 0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }


}























