package com.example.myprofile.insideactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Button verifyPrevious,verifyEmail;
    TextView verifyStatus;

    String status="Email verified";
    MyGlobal myGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

    initViews();

    firebaseAuth=FirebaseAuth.getInstance();
    firebaseUser=firebaseAuth.getCurrentUser();
    myGlobal =new MyGlobal(VerificationActivity.this);


    if(firebaseUser.isEmailVerified()){
        verifyStatus.setText(status);

    }




    verifyPrevious.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveActivity(OtherOptionActivity.class,2);
        }
    });

    verifyEmail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(firebaseUser.isEmailVerified()){
                myGlobal.toast("Your Email is already Verified");
            }else{
                firebaseUser.sendEmailVerification();
                myGlobal.toast("Email verification link send on you email");
            }


        }
    });


    }


    private void moveActivity(Class c,int x){
        Intent intent = new Intent(VerificationActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }



    private void initViews() {
        verifyPrevious=findViewById(R.id.verifyPrevious);
        verifyEmail=findViewById(R.id.verifyEmail);
        verifyStatus=findViewById(R.id.verifyStatus);
    }



}