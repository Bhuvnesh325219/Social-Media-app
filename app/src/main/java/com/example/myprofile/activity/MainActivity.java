package com.example.myprofile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.insideactivity.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        //Handler uses for multithreading

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               if(firebaseUser!=null){
                   moveActivityProfile();
               }else{
                   moveActivity();
                finish();}
            }
        },4*1000);

    }

    private  void moveActivity(){
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
       // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private  void moveActivityProfile(){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
        // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

}