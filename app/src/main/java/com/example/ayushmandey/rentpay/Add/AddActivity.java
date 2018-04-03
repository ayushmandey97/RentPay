package com.example.ayushmandey.rentpay.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ayushmandey.rentpay.ProductDetails.ProductDetailsActivity;
import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Utils.BottomNavigationViewHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddActivity extends AppCompatActivity {
    public static final String TAG = "AddActivity";
    private static final int ACTIVITY_NUM = 2;

    Button submit, gallery, capture;
    EditText title, price, age, desc;
    ImageView im;
    String imageUrl;
    String timestamp;
    Geocoder geocoder;

    LocationManager mLocationManager;
    Location mCurrentLocation;

    ProgressDialog progress, itemAddProgress;
    StorageReference storage;
    FirebaseDatabase database;
    Uri uri;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_INTENT = 1;

    double lattitude, longitude;
    List<Address> addresses;


    DatabaseReference myRef;
    int flag = 0;

    FirebaseUser user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Log.d(TAG, "onCreate: started");
        //setupBottomNavigationView();

        itemAddProgress = new ProgressDialog(this);

        submit = (Button) findViewById(R.id.submit);
        gallery = (Button) findViewById(R.id.gallery);
        capture = (Button) findViewById(R.id.camera);

        title = (EditText) findViewById(R.id.age);
        desc = (EditText) findViewById(R.id.desc);
        price = (EditText) findViewById(R.id.price);
        age = (EditText) findViewById(R.id.age);
        im = (ImageView) findViewById(R.id.productImage);

        progress = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance().getReference();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, CAMERA_INTENT);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting the location using GPS

                try {


                    ActivityCompat.requestPermissions(AddActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    if(checkLocationPermission()){
                        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                        System.out.println("************* HELOOOOO MAIN *************");
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                    }



                } catch (SecurityException e) {
                    //PERMISSION DENIED
                    Toast.makeText(getApplicationContext(), "EXCEPTIONNNNNNN", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progress.setMessage("Uploading ...");
        progress.show();

        if ((requestCode == GALLERY_INTENT || requestCode == CAMERA_INTENT) && resultCode == RESULT_OK) {

            uri = data.getData();
            final StorageReference filepath = storage.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();
                    Toast.makeText(AddActivity.this, "Upload completed!", Toast.LENGTH_SHORT).show();
                    im.setImageURI(uri);

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            //do something with downloadUrl
                            imageUrl = downloadUrl.toString();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress.dismiss();
                    Toast.makeText(AddActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    //***** LOCATIONS ********
    public boolean checkLocationPermission(){
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    private final LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            if(flag == 0){

                System.out.println("************* HELOOOOO *************");
                lattitude = location.getLatitude();
                longitude = location.getLongitude();

                //Putting retrieved data into the database
                myRef = database.getReference("Items");
                myRef = myRef.push();

                user = FirebaseAuth.getInstance().getCurrentUser();
                myRef.child("userId").setValue(user.getUid());

                myRef.child("title").setValue(title.getText().toString());
                myRef.child("price").setValue(price.getText().toString());
                myRef.child("productAge").setValue(age.getText().toString());
                myRef.child("desc").setValue(desc.getText().toString());

                timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                myRef.child("timeStamp").setValue(timestamp);

                myRef.child("image").setValue(imageUrl);

                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try{
                    addresses = geocoder.getFromLocation(lattitude, longitude, 1);
                }catch (IOException e){
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                cityName = cityName.split(",")[2].trim();

                myRef.child("locality").setValue(cityName);
                Toast.makeText(getApplicationContext(), "Product data successfully added!", Toast.LENGTH_SHORT).show();
            }
            flag = 1;

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }
    };



    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting bottom nav view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(AddActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}