package com.tinieblas.tokomegawa.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tinieblas.tokomegawa.Constants;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentHotSalesBinding;
import com.tinieblas.tokomegawa.domain.models.ModelohotSales;

import java.util.ArrayList;
import java.util.List;

public class HotSalesFragment extends Fragment {

    HotSalesFragment context;
    private final List<ModelohotSales> ListProducts = new ArrayList<>();
    GridView gridView;

    private FragmentHotSalesBinding fragmentHotSalesBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        context = this;
        fragmentHotSalesBinding = fragmentHotSalesBinding.inflate(inflater, container, false);

        gridView = fragmentHotSalesBinding.gridViewProducts.findViewById(R.id.gridViewProducts);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.INTENT_NAME_MODELO, ListProducts.get(i));

                getParentFragmentManager().setFragmentResult("key", bundle);

            }
        });

        return fragmentHotSalesBinding.getRoot();
    }
}

























