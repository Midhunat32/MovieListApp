package com.example.movielistapp.utility;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayChecker {

    public static boolean isDeviceTab(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return true;  //higher device
        }else{
            return false;  // smaller device
        }
    }
}
