package com.example.ayushmandey.rentpay.Utils;

import android.util.Log;

import com.example.ayushmandey.rentpay.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by dheeraj on 30/3/18.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHelper";
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: setting bottom nav view");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
}
