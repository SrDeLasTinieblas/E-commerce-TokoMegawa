package com.tinieblas.tokomegawa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.databinding.FragmentLoginBinding;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    LoginFragment loginFragment;
    AwesomeValidation awesomeValidation;
    private FragmentLoginBinding fragmentLoginBinding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore mFirestore;
    //private Drawable drawableEnd;
    //final int DRAWABLE_END= 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginFragment = this;
        fragmentLoginBinding = fragmentLoginBinding.inflate(inflater, container, false);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        firebaseAuth = FirebaseAuth.getInstance();

        /*fragmentLoginBinding.password.setOnTouchListener(new View.OnTouchListener() {
            //@SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_END = 0;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (fragmentLoginBinding.password.getRight() - fragmentLoginBinding.password.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        Toast.makeText(getContext(), "ALOHAAA", Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }
                return false;
            }
        });*/
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
        FirebaseAuth mAut = FirebaseAuth.getInstance();
        FirebaseUser user = mAut.getCurrentUser();
        if (user != null) {
            replaceFragment(new HomeFragment());
        }

    }// valida si el usuario esta logeado

    public void startSession(){
        try {
            // Validando usuario y contraseña
            if (awesomeValidation.validate()) {
                String mail = fragmentLoginBinding.username.getText().toString();
                String pass = fragmentLoginBinding.password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                replaceFragment(new HomeFragment());
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                //sweetAlertDialog.sweetAlertError(Login_Activity.this);
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                });
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























