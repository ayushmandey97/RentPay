package com.example.ayushmandey.rentpay.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Users;
import com.example.ayushmandey.rentpay.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dheeraj on 1/4/18.
 */

public class ChatActivity extends AppCompatActivity{
    public static final String TAG="ChatActivity";
    private static final int ACTIVITY_NUM = 1;

    private FirebaseAuth mAuth;
    private DatabaseReference wDatabase, rDatabase;
    private FirebaseDatabase mfirebaseDatabase;
    private String uid;
    private String email;
    private String name;
    private ListView chats;
    private List<Users> chat;
    private List<String> uids;
    ArrayList<String> temp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.d(TAG, "onCreate: started");
        //setupBottomNavigationView();



        temp = new ArrayList<>();
        chats = (ListView) findViewById(R.id.listView);
        mAuth = FirebaseAuth.getInstance();
        chat = new ArrayList<Users>();
        uids = new ArrayList<String>();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        rDatabase = mfirebaseDatabase.getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        chats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String user = temp.get(i).toString();
                Intent chatIntent = new Intent(ChatActivity.this,ChatWindow.class);
                chatIntent.putExtra("uid" , user);
                startActivity(chatIntent);
            }
        });
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting bottom nav view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ChatActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }



    protected void onStart() {

        super.onStart();
        rDatabase.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                uids.clear();
                //  chat.clear();

                for( DataSnapshot User : dataSnapshot.getChildren() )

                {
                    if(User.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        continue;

                    }
                    uids.add(User.child("name").getValue().toString());
                    temp.add(User.child("uid").getValue().toString());

                    // chat.add(User.getValue(Users.class));

                    // Toast.makeText(getApplicationContext(),"D",Toast.LENGTH_LONG).show();
                    //ArrayList<String> array_users = new ArrayList<>();
                    //
                    ArrayAdapter users_list = new ArrayAdapter(ChatActivity.this,android.R.layout.simple_list_item_1,uids);
                    chats.setAdapter(users_list);

                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });



    }
}