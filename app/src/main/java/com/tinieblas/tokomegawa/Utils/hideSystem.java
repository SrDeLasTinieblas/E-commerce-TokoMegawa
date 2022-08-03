package com.tinieblas.tokomegawa.Utils;

import android.app.Activity;
import android.view.View;

public class hideSystem extends Activity implements View.OnClickListener {
    private View decorView;


    public void sss(){
        decorView = getWindow().getDecorView();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClick(View view) {

    }
}

