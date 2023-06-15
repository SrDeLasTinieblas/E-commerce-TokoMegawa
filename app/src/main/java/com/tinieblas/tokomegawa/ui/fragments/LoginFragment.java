package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    LoginFragment loginFragment;
    AwesomeValidation awesomeValidation;
    private FragmentLoginBinding fragmentLoginBinding;


    private final LoginRepositoryImp repository = new LoginRepositoryImp();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginFragment = this;
        fragmentLoginBinding = fragmentLoginBinding.inflate(inflater, container, false);

        fragmentLoginBinding.showPassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        fragmentLoginBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        fragmentLoginBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        validatingLogin();

        fragmentLoginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSession();
            }
        });

        return fragmentLoginBinding.getRoot();
    }
    private void validatingLogin() {

        boolean isLogged = repository.getCurrentUser();
        if (isLogged) {
            replaceFragment(new HomeFragment());
        }

    }// valida si el usuario esta logeado

    public void startSession(){
        try {
            // Validando usuario y contraseña
            if (awesomeValidation.validate()) {
                String mail = fragmentLoginBinding.username.getText().toString();
                String pass = fragmentLoginBinding.password.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Boolean status = repository.login(mail, pass);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (status) {
                                    replaceFragment(new HomeFragment());
                                } else {
                                    Toast.makeText(getContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                                    //String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    //sweetAlertDialog.sweetAlertError(Login_Activity.this);
                                }

                            }
                        });
                    }
                }).start();

            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }


}























