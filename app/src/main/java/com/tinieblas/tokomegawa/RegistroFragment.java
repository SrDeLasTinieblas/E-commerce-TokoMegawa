package com.tinieblas.tokomegawa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tinieblas.tokomegawa.databinding.FragmentRegistroBinding;

import java.util.ArrayList;

public class RegistroFragment extends Fragment {

    RegistroFragment registroFragment;
    private FragmentRegistroBinding fragmentRegistroBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        registroFragment = this;
        fragmentRegistroBinding = fragmentRegistroBinding.inflate(inflater, container, false);

        ArrayList<Integer> edades = new ArrayList<>();
        for (int i = 18; i < 91; i++){
            edades.add(i);
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, edades);

        fragmentRegistroBinding.spinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return fragmentRegistroBinding.getRoot();
    }
}