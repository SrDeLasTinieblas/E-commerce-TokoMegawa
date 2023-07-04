package com.tinieblas.tokomegawa.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.databinding.FragmentSettingBinding;
import com.tinieblas.tokomegawa.domain.models.User;
import com.tinieblas.tokomegawa.ui.activities.AuthenticationActivity;
import com.tinieblas.tokomegawa.ui.activities.MainActivity;
import com.tinieblas.tokomegawa.utils.Alertdialog;
import com.tinieblas.tokomegawa.utils.OnSignOutListener;

import java.util.List;

public class SettingFragment extends Fragment {

    private User user;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    SettingFragment settingFragment;
    FragmentSettingBinding fragmentSettingBinding;
    SharedPreferences sharedPreferences;
    private PopupWindow popupWindow;
    private boolean isChecked = false;
    private boolean isSessionClosed = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settingFragment = this;
        fragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false);

        fragmentSettingBinding.switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableDarkMode();
                    Toast.makeText(getContext(), "Modo oscuro activado", Toast.LENGTH_SHORT).show();
                } else {
                    disableDarkMode();
                    Toast.makeText(getContext(), "Modo oscuro desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        fragmentSettingBinding.buttonArrowCupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cupon", Toast.LENGTH_SHORT).show();
            }
        });

        fragmentSettingBinding.btnLenguaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Showfiltro(view);
            }
        });

        fragmentSettingBinding.buttonArrowNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        fragmentSettingBinding.buttonCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
                //SignOut();
                /*Alertdialog alertdialog = new Alertdialog();
                alertdialog.alertLoading(getContext());*/


            }
        });
        LoginRepositoryImp loginRepositoryImp = new LoginRepositoryImp();
        String uidUser = loginRepositoryImp.getUIDUser();
        String emailUser = loginRepositoryImp.getEmailUser();

        fragmentSettingBinding.inforUser.setText(uidUser);
        fragmentSettingBinding.textNombreSettings.setText(emailUser);
        //String uidUser = LoginRepositoryImp.getUIDUser();
        //fragmentSettingBinding.inforUser.setText( repository.getUIDUser());

        return fragmentSettingBinding.getRoot();
    }
    private void SignOut() {
        // Realiza el proceso de cierre de sesión
        Alertdialog alertdialog = new Alertdialog();

        alertdialog.alertLoading(getContext(), new OnSignOutListener() {
            @Override
            public void onSignOutComplete() {
                // Aquí puedes realizar las acciones después de que el cierre de sesión se haya completado
                mAuth.signOut();
                Intent i = new Intent(getContext(), AuthenticationActivity.class);
                startActivity(i);

                // Finaliza la actividad actual (Main Activity)
                requireActivity().finish();
            }
        });
    }

/*
    @Override
    public void onSignOutComplete() {
        // Se llama cuando el cierre de sesión se haya completado
        mAuth.signOut();
        Intent i = new Intent(getContext(), AuthenticationActivity.class);
        startActivity(i);
    }*/


    public void updateData(){
        //Alertdialog alertdialog = new Alertdialog();
        //alertdialog.alertLoading(getContext(), );


        /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("error", "User profile updated.");
                        }
                    }
                });*/
    }
    /*public void SignOut(){
        mAuth.signOut();
        Intent i = new Intent(getContext(), AuthenticationActivity.class);
        startActivity(i);
    }*/
    public void Showfiltro(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            isChecked = false; // Reiniciar el estado al cerrar el popup
        } else {
            View popupView = LayoutInflater.from(requireActivity()).inflate(R.layout.popup_idiomas, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.showAsDropDown(view, 0, 0);
        }
    }

    public void enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        ((MainActivity) requireActivity()).getDelegate().applyDayNight();
        /*if (mode == 0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/
    }
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ((MainActivity) requireActivity()).getDelegate().applyDayNight();
        /*if (mode == 0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }


}























