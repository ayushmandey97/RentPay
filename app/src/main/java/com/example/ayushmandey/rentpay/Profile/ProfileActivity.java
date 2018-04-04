package com.example.ayushmandey.rentpay.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ayushmandey.rentpay.Home.HomeActivity;
import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by dheeraj on 1/4/18.
 */

public class ProfileActivity extends AppCompatActivity{
    public static final String TAG="ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private TextView name_display, email;
    private Button signOut;
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");

        name_display = (TextView)findViewById(R.id.profileName);
        email =(TextView)findViewById(R.id.email);
        signOut = (Button)findViewById(R.id.signOut);
        setupBottomNavigationView();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        mDatabase.child("Users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name_profile = dataSnapshot.child("name").getValue().toString();
                name_display.setText(name_profile);
                String email_profile = dataSnapshot.child("email").getValue().toString();
                email.setText(email_profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent HomeActivity = new Intent(ProfileActivity.this, com.example.ayushmandey.rentpay.Home.HomeActivity.class);
                HomeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(HomeActivity);
                finish();
            }
        });
    }

    private void setupToolbar(){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);

    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting bottom nav view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    protected void onStart() {

        super.onStart();

    }
}