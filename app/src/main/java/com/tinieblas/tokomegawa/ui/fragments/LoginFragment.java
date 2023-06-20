package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentLoginBinding;
import com.tinieblas.tokomegawa.ui.activities.MainActivity;
import com.tinieblas.tokomegawa.utils.NavigationContent;

public class LoginFragment extends Fragment {

    LoginFragment loginFragment;
    AwesomeValidation awesomeValidation;
    private FragmentLoginBinding fragmentLoginBinding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore mFirestore;
    //private Drawable drawableEnd;
    //final int DRAWABLE_END= 0;
    private FirebaseAuth mAuth;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginFragment = this;
        fragmentLoginBinding = fragmentLoginBinding.inflate(inflater, container, false);

        mAuth = FirebaseAuth.getInstance(); // Inicializar mAuth antes de usarlo
        //FirebaseUser currentUser = mAuth.getCurrentUser();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        fragmentLoginBinding.showPassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
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

        validatingLogin();

        fragmentLoginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSession();
            }
        });

        saveUserId(mAuth.getUid());
        //System.out.println("currentUser" + mAuth.getUid());
        return fragmentLoginBinding.getRoot();
    }

    private void saveUserId(String userId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userUid", userId);
        editor.apply();
    }

    private void validatingLogin() {
        FirebaseAuth mAut = FirebaseAuth.getInstance();
        FirebaseUser user = mAut.getCurrentUser();
        if (user != null) {
            replaceFragment(new HomeFragment());
        }

    }// valida si el usuario esta logeado

    public void startSession() {
        try {
            // Validando usuario y contraseña
            if (awesomeValidation.validate()) {
                String mail = fragmentLoginBinding.username.getText().toString();
                String pass = fragmentLoginBinding.password.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance(); // Inicializar firebaseAuth aquí

                firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                saveUserId(mAuth.getUid());
                                //String firstName = document.getString(User.KEY_NOMBRES);
                                //replaceFragment(new HomeFragment());
                                NavigationContent.cambiarActividad(requireActivity(), MainActivity.class);

                                System.out.println("desde el login ==>"+mAuth.getUid());
                            } else {
                                Toast.makeText(getContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                                //String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                //sweetAlertDialog.sweetAlertError(Login_Activity.this);
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("ERROR EN EL LOGIN" + getContext(), ex.toString());
        }

    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    private void updateUI(String user) {
        if (user != null){
            NavigationContent.cambiarActividad(getContext(), MainActivity.class);
            requireActivity().finish();
            //System.out.println("LoginFragment a MainActivity ==> "+ user.getUid());

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userUid", user);
            editor.putString("firstName", mAuth.getCurrentUser().getDisplayName());
            editor.apply();

        }
    }
}























