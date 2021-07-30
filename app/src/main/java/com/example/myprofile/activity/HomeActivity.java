package com.example.myprofile.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.insideactivity.ProfileActivity;
import com.example.myprofile.modal.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {


   static EditText homeEmail,homePassword;
   private TextView homeForgetPassword;
   private Button homeLogin,homeRegister;
   private ProgressBar homeProgressBar;

   static String email,password;

   private FirebaseAuth firebaseAuth;
   private FirebaseAuth.AuthStateListener authStateListener;
   String TAG="Hello";
   private FirebaseUser firebaseUser;
   private MyGlobal myGlobal;
   private Context context=HomeActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();





        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        myGlobal=new MyGlobal(HomeActivity.this);



        homeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Home home =instanceCreate();

                 if(home.getEmail().equals("")||home.getPassword().equals("")){
                    if(home.getEmail().equals("")){
                        homeEmail.setError("Please Enter email");
                    }
                    if(home.getPassword().equals("")){
                        homePassword.setError("Please Enter Password");
                    }
                 }
                    else{
                     userlogin();
                 }
            }
        });
        homeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeregister();
            }
        });


        homeForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               moveActivityForgetPassword();
            }
        });





    }

    private void moveActivityForgetPassword() {
        Intent intent = new Intent(HomeActivity.this, PasswordForgetActivity.class);
        startActivity(intent);

    }


    static Home instanceCreate() {

        email =homeEmail.getText().toString();
        password=homePassword.getText().toString();

        Home home =new Home(email,password);
        home.setEmail(email);
        home.setPassword(password);

        return home;
    }

    private void homeregister() {
        moveActivity(PersonalDetailActivity.class,0);
    }



    private void userlogin() {
          homeProgressBar.setVisibility(View.VISIBLE);
        Home home= HomeActivity.instanceCreate();

        //Log.d(TAG, "userlogin: "+home.getEmail());
        //Log.d(TAG, "userlogin: "+home.getPassword());

        //toast(home.getEmail()+" "+home.getPassword());


        firebaseAuth.signInWithEmailAndPassword(home.getEmail(),home.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    homeProgressBar.setVisibility(View.GONE);
                //moveActivity();
                //myGlobal.moveActivity(HomeActivity.this,ProfileActivity.class);
                moveActivity(ProfileActivity.class,0);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                homeProgressBar.setVisibility(View.GONE);
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    //toast("Invalid Password");
                    myGlobal.toast("Invalid Password");
                }
                else if(e instanceof FirebaseAuthInvalidUserException){
                    myGlobal.toast("You are not registered");
                }
            }
        });;

    }



    private void initViews() {
        homeEmail=findViewById(R.id.homeEmail);
        homePassword=findViewById(R.id.homePassword);

        homeForgetPassword=findViewById(R.id.homeForgetPassword);

        homeLogin=findViewById(R.id.homeLogin);
        homeRegister=findViewById(R.id.homeRegister);
        homeProgressBar=findViewById(R.id.homeProgressBar);
      homeProgressBar.setVisibility(View.GONE);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2,Color.BLUE);
        gd.setCornerRadius(10);
        homeEmail.setBackground(gd);
        homePassword.setBackground(gd);

    }



    private void moveActivity(Class c,int x){
        Intent intent = new Intent(HomeActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }

}