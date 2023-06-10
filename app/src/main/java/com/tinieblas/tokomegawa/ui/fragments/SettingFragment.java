package com.tinieblas.tokomegawa.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private static final int File = 1;
    private static final int RESULT_OK = 2 ;
    DatabaseReference myRef;
    SettingFragment settingFragment;
    FragmentSettingBinding fragmentSettingBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settingFragment = this;
        fragmentSettingBinding = fragmentSettingBinding.inflate( inflater, container, false);


        return fragmentSettingBinding.getRoot();

    }
    public void fileUpload(){
        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("**");
        startActivityForResult(intent, File);*/
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }



    /*public void onActivityResult(int requestCode, int resltCode, Intent data){
        super.onActivityResult(requestCode, requestCode, data);
        if(requestCode == File){
            if(resltCode == RESULT_OK){

            }
        }
    }*/

}























