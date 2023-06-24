package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding fragmentLoginBinding;
    private LoginRepositoryImp repository;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        repository = new LoginRepositoryImp();

        fragmentLoginBinding.showPassword.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    fragmentLoginBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case MotionEvent.ACTION_UP:
                    fragmentLoginBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
            }
            return true;
        });

        validateLogin();

        fragmentLoginBinding.buttonLogin.setOnClickListener(view -> startSession());

        return fragmentLoginBinding.getRoot();
    }

    private void validateLogin() {
        boolean isLogged = repository.getCurrentUser();
        if (isLogged) {
            replaceFragment(new HomeFragment());
        }
    }

    private void startSession() {
        String email = fragmentLoginBinding.username.getText().toString();
        String password = fragmentLoginBinding.password.getText().toString();

        // Validando usuario y contraseña
        if (validateCredentials()) {
            new Thread(() -> {
                boolean status = repository.login(email, password);
                requireActivity().runOnUiThread(() -> {
                    if (status) {
                        replaceFragment(new HomeFragment());
                    } else {
                        Toast.makeText(getContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        }
    }

    private boolean validateCredentials() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Agregar validaciones necesarias
        awesomeValidation.addValidation(fragmentLoginBinding.username, Patterns.EMAIL_ADDRESS, "Correo electrónico inválido");
        awesomeValidation.addValidation(fragmentLoginBinding.password, "^.{6,}$", "La contraseña debe tener al menos 6 caracteres");

        return awesomeValidation.validate();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}























