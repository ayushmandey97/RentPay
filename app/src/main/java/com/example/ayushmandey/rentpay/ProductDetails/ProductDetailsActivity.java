package com.example.ayushmandey.rentpay.ProductDetails;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ayushmandey.rentpay.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static java.security.AccessController.getContext;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView title, price, desc, prodAge, location;
    ImageView productImage;
    DatabaseReference database;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        title = (TextView)findViewById(R.id.title);
        price = (TextView)findViewById(R.id.price);
        desc = (TextView)findViewById(R.id.desc);
        prodAge = (TextView)findViewById(R.id.age);
        location = (TextView)findViewById(R.id.location);
        productImage = (ImageView)findViewById(R.id.productImage);

        //GET productId through the previous activity's intent

        Bundle bn = getIntent().getExtras();
        String pid = bn.getString("productId");

        database = FirebaseDatabase.getInstance().getReference("Items").child(pid);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title.setText(dataSnapshot.child("title").getValue().toString());
                price.setText(dataSnapshot.child("price").getValue().toString());
                prodAge.setText(dataSnapshot.child("productAge").getValue().toString());
                location.setText(dataSnapshot.child("locality").getValue().toString());
                desc.setText(dataSnapshot.child("desc").getValue().toString());

                Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString()).into(productImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
