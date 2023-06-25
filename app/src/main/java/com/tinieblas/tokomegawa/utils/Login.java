package com.tinieblas.tokomegawa.utils;

import android.app.Activity;

import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.ui.activities.MainActivity;

public class Login {

    public void validateLogin(Activity activity) {
        LoginRepositoryImp repository;
        repository = new LoginRepositoryImp();
        boolean isLogged = repository.getCurrentUser();
        if (isLogged) {
            NavigationContent.cambiarActividad(activity, MainActivity.class);
        }
    }
}
