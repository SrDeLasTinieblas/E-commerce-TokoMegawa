package com.tinieblas.tokomegawa.utils;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Alertdialog {

    public void alertSuccess(Context context, String message){
        new SweetAlertDialog(context)
        .setTitleText(message)
        .show();
    }

    public void alertLoading(Context context, OnSignOutListener listener){
        // 4. Loading message
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();

        // Llama al método onSignOutComplete cuando el cierre de sesión se haya completado
        if (listener != null) {
            listener.onSignOutComplete();
        }
    }

    public void alertLoadingStop(Context context){
        // 4. Loading message
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
    }


}


































