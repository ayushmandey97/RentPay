package com.example.ayushmandey.rentpay.Register;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ayushmandey.rentpay.Login.LogIn;
import com.example.ayushmandey.rentpay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private Button Register;
    private EditText name,emailr,rPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoginProgress;
    DatabaseReference wDatabase;
    private String displayName;
    private String email;
    private String password;
    String uid;
    //private int flag =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mLoginProgress = new ProgressDialog(this);
        name = (EditText)Register.this.findViewById(R.id.editText4);
        emailr =(EditText)Register.this.findViewById(R.id.editText6);
        rPassword = (EditText)Register.this.findViewById(R.id.editText5);
        Register = (Button) Register.this.findViewById(R.id.button6);
        Register.setText("Register");
        mAuth.signOut();



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 displayName = name.getText().toString();
                 email = emailr.getText().toString();
                 password = rPassword.getText().toString();

                if(displayName.equals("")||email.equals("")||password.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields cannot be left Blank!",Toast.LENGTH_SHORT).show();


                }
                else if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password should be of atleast 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {
                    mLoginProgress.setTitle("Registering");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    register_user(displayName, email, password);
                }
            }
        });



    }

    protected void register_user(final String displayName, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    uid = user.getUid();
                    wDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name",displayName);
                    userMap.put("email",email);
                    userMap.put("uid",uid);
                    wDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                sendEmailVerification();
                            }
                        }
                    });
                } else {
                    mLoginProgress.hide();
                    Toast.makeText(getApplicationContext(), "User already Exists or Email not Verified", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                }
            }
        });

    }


    private void sendEmailVerification() {

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Sign in again after Verification!", Toast.LENGTH_SHORT).show();
                           /* Intent LogIn = new Intent(Register.this,LogIn.class);
                            startActivity(LogIn);
                            finish();*/
                                mLoginProgress.dismiss();
                                Intent login = new Intent (Register.this,LogIn.class);
                                startActivity(login);
                                finish();
                                //Register.setText("LogIn");
                                //verifyEmail();


                            } else {
                                mLoginProgress.hide();
                                Toast.makeText(getApplicationContext(), "Failed to sent Verification Email!", Toast.LENGTH_SHORT).show();
                                //FirebaseAuth.getInstance().signOut();
                            }
                        }
                    });
        }
    }

    /*private void verifyEmail(FirebaseUser user) {
        //FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if(user.isEmailVerified()) {
                flag = 0;
                Intent HomeActivity = new Intent(Register.this, HomeActivity.class);
                startActivity(HomeActivity);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Email is not Verified", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
            }
        }
    }*/
}
