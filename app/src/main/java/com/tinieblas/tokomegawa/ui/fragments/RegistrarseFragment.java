package com.tinieblas.tokomegawa.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.tinieblas.tokomegawa.databinding.FragmentRegistrarseBinding;

import java.util.ArrayList;
import java.util.List;

public class RegistrarseFragment extends Fragment {

    private FragmentRegistrarseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrarseBinding.inflate(inflater, container, false);
        setupViews();
        setupListeners();
        validateFields();
        return binding.getRoot();
    }

    private void setupViews() {
        List<String> opciones = new ArrayList<>();
        opciones.add("DNI");
        opciones.add("Carnet de Extranjeria");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTipoDocumento.setAdapter(adapter);
    }

    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFields();
            }
        };

        binding.editTextNombre.addTextChangedListener(textWatcher);
        binding.editTextApelllidos.addTextChangedListener(textWatcher);
        binding.editTextCorreoElectronico.addTextChangedListener(textWatcher);
        binding.editTextPhone.addTextChangedListener(textWatcher);
        binding.editTextNumDeDocumento.addTextChangedListener(textWatcher);
        binding.editTextContrasena.addTextChangedListener(textWatcher);

        binding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> validateFields());

        binding.buttonRegistro.setOnClickListener(view ->
                Toast.makeText(getContext(), "Registrado", Toast.LENGTH_SHORT).show());
    }

    private void validateFields() {
        boolean isNameValid = !TextUtils.isEmpty(binding.editTextNombre.getText().toString().trim());
        boolean isApellidoValid = !TextUtils.isEmpty(binding.editTextApelllidos.getText().toString().trim());
        boolean isEmailValid = !TextUtils.isEmpty(binding.editTextCorreoElectronico.getText().toString().trim());
        boolean isPhoneValid = !TextUtils.isEmpty(binding.editTextPhone.getText().toString().trim());
        boolean isNumDocumentoValid = !TextUtils.isEmpty(binding.editTextNumDeDocumento.getText().toString().trim());
        boolean isPasswordValid = !TextUtils.isEmpty(binding.editTextContrasena.getText().toString().trim());
        boolean isSwitchValid = binding.switch1.isChecked();

        boolean isButtonEnabled = isSwitchValid && isNumDocumentoValid && isPhoneValid && isApellidoValid &&
                isNameValid && isEmailValid && isPasswordValid;

        binding.buttonRegistro.setEnabled(isButtonEnabled);
        binding.buttonRegistro.setAlpha(isButtonEnabled ? 1.0f : 0.5f);
    }
}























