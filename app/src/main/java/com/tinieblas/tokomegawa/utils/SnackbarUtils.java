package com.tinieblas.tokomegawa.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.tinieblas.tokomegawa.R;

public class SnackbarUtils {

    public static Snackbar make(View view, CharSequence text, int duration, String imageUrl) {
        ViewGroup parent = findSuitableParent(view);
        Snackbar snackbar = Snackbar.make(parent, "", duration);
        customizeSnackbar(snackbar, view.getContext(), imageUrl);
        return snackbar;
    }


    private static void customizeSnackbar(Snackbar snackbar, Context context, String imageUrl) {
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.black));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen.fab_margin));
        int horizontalPadding = context.getResources().getDimensionPixelOffset(R.dimen.snackbar_padding_horizontal);
        int verticalPadding = context.getResources().getDimensionPixelOffset(R.dimen.snackbar_padding_vertical);
        textView.setPadding(horizontalPadding + 10, verticalPadding, horizontalPadding + 10, verticalPadding);

        // Agregar bordes redondeados
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(context.getResources().getDimensionPixelOffset(R.dimen.snackbar_corner_radius));
        background.setColor(ContextCompat.getColor(context, R.color.white));
        snackbarView.setBackground(background);

        // Cargar la imagen utilizando Glide
        ImageView imageView = snackbarView.findViewById(com.google.android.material.R.id.image);
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }



    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                } else {
                    fallback = (ViewGroup) view;
                }
            }
            if (view != null) {
                view = view.getParent() instanceof View ? (View) view.getParent() : null;
            }
        } while (view != null);
        return fallback;
    }
}


