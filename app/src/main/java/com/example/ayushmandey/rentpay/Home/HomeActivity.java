package com.example.ayushmandey.rentpay.Home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.ayushmandey.rentpay.Utils.CustomAdapter;
import com.example.ayushmandey.rentpay.Utils.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Utils.BottomNavigationViewHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        mAuth = FirebaseAuth.getInstance();
        setupBottomNavigationView();
        setupViewPager();


    }


    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SettingsFragment());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new SearchFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_settings);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo1);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search);
        viewPager.setCurrentItem(1);
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting bottom nav view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(HomeActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    @Override
    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = null;
        final ListView lv = findViewById(R.id.listView11d);
        try {
            currentUser = mAuth.getCurrentUser();
        }catch (Exception e){

        }
        //FirebaseAuth.getInstance().signOut();
        //updateUI(currentUser);
        if(currentUser==null){
            Intent LogIn = new Intent(HomeActivity.this,com.example.ayushmandey.rentpay.Login.LogIn.class);
            startActivity(LogIn);
            finish();
        }
        // FirebaseAuth.getInstance().signOut();
    }

}
