package com.example.ayushmandey.rentpay.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ayushmandey.rentpay.ProductDetails.ProductDetailsActivity;
import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Utils.CustomAdapter;
import com.example.ayushmandey.rentpay.Utils.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import com.example.ayushmandey.rentpay.Home.HomeActivity;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by dheeraj on 1/4/18.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    DatabaseReference db;
    List<Post> p = new ArrayList<>();
    CustomAdapter adp;
    ListView lv;
    View view;
    int i;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        lv = view.findViewById(R.id.listView11d);
        db = FirebaseDatabase.getInstance().getReference("Items");
        adp = new CustomAdapter(getContext() , p);
        lv.setAdapter(adp);


        try {
            db = FirebaseDatabase.getInstance().getReference("Items");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    p.clear();
                    for (DataSnapshot Postzz : dataSnapshot.getChildren()) {
                        p.add(Postzz.getValue(Post.class));
                    }

                    adp = new CustomAdapter(getContext(), p);
                    lv.setAdapter(adp);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            super.onStart();
        }catch (Exception e){
            e.printStackTrace();
        }

        EditText search_bar = (EditText) view.findViewById(R.id.search);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //Displaying product information on selection
        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Post item = (Post)lv.getItemAtPosition(position);
                FirebaseDatabase f = FirebaseDatabase.getInstance();
                DatabaseReference ref = f.getReference("Items");
                i = 0;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot Postzz : dataSnapshot.getChildren()) {

                            if(i == position){
                                Post post = Postzz.getValue(Post.class);
                                String pid = post.getPid();
                                System.out.println("********** " + pid + " **********");

                                Bundle bn = new Bundle();
                                bn.putString("productId", pid);
                                Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                                intent.putExtras(bn);
                                startActivity(intent);

                            }

                            i++;

                        }

                        adp = new CustomAdapter(getContext(), p);
                        lv.setAdapter(adp);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });



        return view;
    }

}
