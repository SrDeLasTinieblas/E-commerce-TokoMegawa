package com.tinieblas.tokomegawa.utils;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Alertdialog {

    public void alertSuccess(Context context){
        new SweetAlertDialog(context)
        .setTitleText("Here's a message!")
        .show();
    }

    public void alertLoading(Context context){
        // 4. Loading message
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();
    }
}


































