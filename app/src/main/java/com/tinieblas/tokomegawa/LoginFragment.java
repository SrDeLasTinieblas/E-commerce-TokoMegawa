package com.tinieblas.tokomegawa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinieblas.tokomegawa.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    LoginFragment loginFragment;

    private FragmentLoginBinding fragmentLoginBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginFragment = this;
        fragmentLoginBinding = fragmentLoginBinding.inflate(inflater, container, false);


        return fragmentLoginBinding.getRoot();
    }
}