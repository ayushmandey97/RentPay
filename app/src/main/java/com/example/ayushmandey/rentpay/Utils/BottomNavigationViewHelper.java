package com.example.ayushmandey.rentpay.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.ayushmandey.rentpay.Add.AddActivity;
import com.example.ayushmandey.rentpay.Chat.ChatActivity;
import com.example.ayushmandey.rentpay.Home.HomeActivity;
import com.example.ayushmandey.rentpay.Notification.NotificationActivity;
import com.example.ayushmandey.rentpay.Profile.ProfileActivity;
import com.example.ayushmandey.rentpay.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by dheeraj on 30/3/18.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHelper";
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.ic_house:
                        Intent intent1 = new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_chat:
                        Intent intent4 = new Intent(context, ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_circle:
                        Intent intent5 = new Intent(context, AddActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        context.startActivity(intent5);
                        break;
                    case R.id.ic_notification:
                        Intent intent2 = new Intent(context, NotificationActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_profile:
                        Intent intent3 = new Intent(context, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        context.startActivity(intent3);
                        break;
                }

                return false;
            }
        });
    }
}
