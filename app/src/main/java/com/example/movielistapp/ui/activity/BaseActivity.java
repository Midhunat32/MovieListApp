package com.example.movielistapp.ui.activity;

import android.app.AppComponentFactory;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {


    public void loadFragment(Fragment fragment, boolean addToBackstack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.contentFrame, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
    }

    public boolean isNetworkAvailable() {
        Boolean returnValue;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        returnValue = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        return returnValue;
    }
}
