package com.tinieblas.tokomegawa.ui.fragments;

import static com.google.common.collect.ComparisonChain.start;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.data.remote.SignUpRepositoryImp;
import com.tinieblas.tokomegawa.databinding.FragmentRegistrarseBinding;
import com.tinieblas.tokomegawa.domain.models.RegistroDataModelo;
import com.tinieblas.tokomegawa.ui.activities.MainActivity;
import com.tinieblas.tokomegawa.utils.Alertdialog;
import com.tinieblas.tokomegawa.utils.NavigationContent;

import java.util.ArrayList;
import java.util.List;

public class RegistrarseFragment extends Fragment {

    private FragmentRegistrarseBinding binding;
    private RegistroDataModelo registroData;
    SignUpRepositoryImp repository;
    LoginRepositoryImp repositoryLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrarseBinding.inflate(inflater, container, false);


        setupViews();
        setupListeners();
        validateFields();
        btnRegistro();

        return binding.getRoot();
    }

    private void btnRegistro(){
        binding.buttonRegistro.setOnClickListener(view -> {
            RegistroDataModelo registroData = getRegistroData();
            guardarRegistro(registroData);
        });
    }
    private void guardarRegistro(RegistroDataModelo registroData) {
        repository = new SignUpRepositoryImp();
        repositoryLogin = new LoginRepositoryImp();
        String result = repository.createUser(registroData.getCorreoElectronico(), registroData.getContrasena());
        boolean isSuccess = result != null && !result.isEmpty();

        if (isSuccess) {
            Alertdialog alertDialog = new Alertdialog();
            alertDialog.alertSuccess(getContext(), "Cuenta creada exitosamente");
            Boolean isLogged = repositoryLogin.login(registroData.getCorreoElectronico(), registroData.getContrasena());
            if (isLogged) {
                NavigationContent.cambiarActividad(getActivity(), MainActivity.class);
            }
        }

        Log.e("Resultado ==> ", result);
    }

    private void SubirToDB(){

    }

    private RegistroDataModelo getRegistroData() {
        registroData = new RegistroDataModelo();
        registroData.setNombre(binding.editTextNombre.getText().toString().trim());
        registroData.setApellidos(binding.editTextApelllidos.getText().toString().trim());
        registroData.setCorreoElectronico(binding.editTextCorreoElectronico.getText().toString().trim());
        registroData.setTelefono(binding.editTextPhone.getText().toString().trim());
        registroData.setTipoDocumento(binding.spinnerTipoDocumento.getSelectedItem().toString());
        registroData.setNumDocumento(binding.editTextNumDeDocumento.getText().toString().trim());
        registroData.setContrasena(binding.editTextContrasena.getText().toString().trim());
        registroData.setSwitchValue(binding.switch1.isChecked());
        return registroData;
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























