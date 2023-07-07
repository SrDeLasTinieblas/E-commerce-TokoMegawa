package com.tinieblas.tokomegawa.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
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
    private FirebaseUser userFirebase;
    private FirebaseAuth mAuth;
    private SettingFragment settingFragment;
    private FragmentSettingBinding fragmentSettingBinding;
    private static final int REQUEST_CODE_GALLERY = 1;

    private PopupWindow popupWindow;
    private boolean isChecked = false;
    private boolean isSessionClosed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingFragment = this;
        fragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false);

        userFirebase = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        setUpViews();
        setUpListeners();
        loadData();

        return fragmentSettingBinding.getRoot();
    }

    private void setUpViews() {
        // Set up initial views and data
        fragmentSettingBinding.inforUser.setText(new LoginRepositoryImp().getEmailUser());
        getDataInfoUser();
    }

    private void setUpListeners() {
        // Set up listeners for button clicks and switches
        fragmentSettingBinding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableDarkMode();
                Toast.makeText(getContext(), "Modo oscuro activado", Toast.LENGTH_SHORT).show();
            } else {
                disableDarkMode();
                Toast.makeText(getContext(), "Modo oscuro desactivado", Toast.LENGTH_SHORT).show();
            }
        });

        fragmentSettingBinding.buttonArrowCupon.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Cupon", Toast.LENGTH_SHORT).show();
        });

        fragmentSettingBinding.btnLenguaje.setOnClickListener(view -> {
            Showfiltro(view);
        });

        fragmentSettingBinding.buttonArrowNombre.setOnClickListener(view -> {
            //updatePhoto();

            //updateEmail("nuevoCorreo@gmail.com");
        });
        fragmentSettingBinding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        fragmentSettingBinding.buttonCerrarSession.setOnClickListener(view -> {
            SignOut();
        });

        fragmentSettingBinding.switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                fragmentSettingBinding.textNotification.setText("Yes");
            } else {
                Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
                fragmentSettingBinding.textNotification.setText("No");
            }
        });




        getDataInfoUser();
    }

    @Override
    public void onResume() {

        getDataInfoUser();
        super.onResume();
    }

    private void loadData() {
        // Load initial data or perform any other necessary tasks
    }

    private void SignOut() {
        Alertdialog alertdialog = new Alertdialog();
        alertdialog.alertLoading(getContext(), new OnSignOutListener() {
            @Override
            public void onSignOutComplete() {
                mAuth.signOut();
                Intent i = new Intent(getContext(), AuthenticationActivity.class);
                startActivity(i);
                requireActivity().finish();
            }
        });
    }

    private void getDataInfoUser() {
        if (userFirebase != null) {
            for (UserInfo profile : userFirebase.getProviderData()) {
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                String name = profile.getDisplayName();

                Glide.with(this)
                        .load(photoUrl)
                        .placeholder(R.drawable.male_user)
                        .into(fragmentSettingBinding.imageView2);

                fragmentSettingBinding.textNombreSettings.setText(email);
                fragmentSettingBinding.textNombreSettings.setText(name);
            }
        }
    }




    // Método para abrir la galería
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    // Escucha el resultado de la selección de la imagen de la galería
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada de la galería
            Uri imageUri = data.getData();

            // Llama a la función de actualización de la foto con la URI de la imagen seleccionada
            updatePhoto(imageUri);
        }
    }

    public void updateEmail(String correoActualizado) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(correoActualizado).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("TAG", "User email address updated.");
            }
        });
    }
    public void updatePhoto(Uri imageUri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(imageUri)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "User profile updated.", Toast.LENGTH_SHORT).show();
                getDataInfoUser();
            }
        });
    }

    public void Showfiltro(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            isChecked = false;
        } else {
            View popupView = LayoutInflater.from(requireActivity()).inflate(R.layout.popup_idiomas, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.showAsDropDown(view, 0, 0);
        }
    }

    public void enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        ((MainActivity) requireActivity()).getDelegate().applyDayNight();
    }

    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ((MainActivity) requireActivity()).getDelegate().applyDayNight();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}























