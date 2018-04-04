package com.example.ayushmandey.rentpay.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ayushmandey.rentpay.R;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dheeraj on 1/4/18.
 */

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        String[] vals = {"About","Contact Us","Sign Out"};
        final ArrayList<String> items= new ArrayList<String>();
        for (int i = 0; i < vals.length; ++i) {
            items.add(vals[i]);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,items);
        ListView listView = view.findViewById(R.id.settingslist);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Toast.makeText(getContext(), "Version 1.0.0", Toast.LENGTH_SHORT).show();
                }
                if(position==2){
                    Toast.makeText(getContext(), "Signing out..", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getContext(),HomeActivity.class);
                    startActivity(i);
                }
            }
        });

        return view;
    }
}