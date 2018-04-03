package com.example.ayushmandey.rentpay.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ayushmandey.rentpay.Home.HomeActivity;
import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Register.Register;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogIn extends AppCompatActivity {


    private Button signin,login;
    private EditText Lemail,Lpassword;
    private FirebaseAuth mAuth;
    private SignInButton signInGoogle;
    private ProgressDialog mLoginProgress;
    private final static  int RC_SIGN_IN =2;
    GoogleApiClient mGoogleApiClient;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //@Override
    /*protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        playVideo();
        mAuth = FirebaseAuth.getInstance();
        mLoginProgress = new ProgressDialog(this);
        signin = (Button)findViewById(R.id.signin);
        signInGoogle=(SignInButton)findViewById(R.id.googlesign);
        login = (Button)findViewById(R.id.button2);
        Lemail= (EditText)findViewById(R.id.email);
        Lpassword=(EditText)findViewById(R.id.password);

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LogIn.this,HomeActivity.class));
                }
            }
        };*/
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(LogIn.this,Register.class);
                startActivity(reg_intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Lemail.getText().toString();
                String password = Lpassword.getText().toString();
                if(email.equals("")||password.equals("")){
                    Toast.makeText(getApplicationContext(),"Email or password can't be left blank!",Toast.LENGTH_SHORT).show();

                }else if(!isValidEmail(email)){
                    Toast.makeText(getApplicationContext(), "Email is not Valid", Toast.LENGTH_SHORT).show();
                } else {
                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email, password);
                }

            }
        });
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                //startActivity(new Intent(LogIn.this,HomeActivity.class));

            }
            else {
                // Google Sign In failed, update UI appropriately
                mLoginProgress.dismiss();
                Toast.makeText(getApplicationContext(), "Auth went wrong", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LogIn.this,HomeActivity.class));
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                            Toast.makeText(getApplicationContext(), "Auth Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user.isEmailVerified()) {
                        mLoginProgress.dismiss();
                        Intent HomeActivity = new Intent(LogIn.this, com.example.ayushmandey.rentpay.Home.HomeActivity.class);
                        startActivity(HomeActivity);
                        finish();
                    }
                    else {
                        mLoginProgress.hide();
                        Toast.makeText(getApplicationContext(),"Email not Verified",Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    mLoginProgress.hide();
                    Toast.makeText(getApplicationContext(),"Check Credentials",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
//    public void playVideo(){
//        VideoView videoView= (VideoView) findViewById(R.id.videoView);
//        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
//        videoView.setVideoURI(uri);
//        videoView.start();
//    }
}

