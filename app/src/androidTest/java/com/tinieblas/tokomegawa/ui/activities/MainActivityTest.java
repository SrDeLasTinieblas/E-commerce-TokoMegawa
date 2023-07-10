package com.tinieblas.tokomegawa.ui.activities;


import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.runner.AndroidJUnit4;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.ui.fragments.HomeFragment;

import org.junit.Test;
import org.junit.runner.RunWith;

import pe.fernan.utils.test.BaseTesting;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends BaseTesting {

    @Test
    public void openFragment(){

        HomeFragment homeFragment = (HomeFragment) launchFragment(HomeFragment.class);
        homeFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View rootView = homeFragment.requireView();
                FloatingActionButton fab = rootView.findViewById(R.id.fab);
                fab.performClick();

            }
        });


        sleep(20);
    }



    @Test
    public void openActivity(){




        AuthenticationActivity activity = (AuthenticationActivity) launchActivity(AuthenticationActivity.class);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Button buttonSetting = activity.findViewById(R.id.buttonSetting);
                //buttonSetting.performClick();

                //FragmentContainerView fragmentContainerView = activity.findViewById(R.id.frameLayoutHome);

            }
        });



        sleep(20);
    }

}