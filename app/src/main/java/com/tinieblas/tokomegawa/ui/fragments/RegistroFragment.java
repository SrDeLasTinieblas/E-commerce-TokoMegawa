package com.tinieblas.tokomegawa.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentRegistroBinding;

public class RegistroFragment extends Fragment {
    private FragmentRegistroBinding fragmentRegistroBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRegistroBinding = fragmentRegistroBinding.inflate(inflater, container, false);


        // Inflate the layout for this fragment
        return fragmentRegistroBinding.getRoot();
    }
}