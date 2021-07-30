package com.example.myprofile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class PasswordForgetActivity extends AppCompatActivity {


    EditText forgetEmail;
    Button forgetSend,forgetPrevious;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private boolean exists;
    private MyGlobal myGlobal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);

    initViews();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        myGlobal=new MyGlobal(PasswordForgetActivity.this);


        forgetPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(HomeActivity.class,0);
            }
        });


       forgetSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendLink();
        }
    });

    }




    private void sendLink() {

        String email=forgetEmail.getText().toString();
        if(email.equals("")){
            myGlobal.toast("Please Enter email");
            return;
        }

        firebaseAuth.fetchSignInMethodsForEmail(forgetEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser =task.getResult().getSignInMethods().isEmpty();

                       if(String.valueOf(isNewUser)=="true"){
                           myGlobal.toast("Email is not exist");
                       }
                       else{
                           firebaseAuth.sendPasswordResetEmail(forgetEmail.getText().toString());
                           myGlobal.toast("Password reset link has been send on your registered email");
                       }


                    }
                });


    }





    private void moveActivity(Class c,int x){
        Intent intent = new Intent(PasswordForgetActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }



    private void initViews() {
        forgetEmail=findViewById(R.id.forgetEmail);
        forgetSend=findViewById(R.id.forgetSend);
        forgetPrevious=findViewById(R.id.forgetPrevious);
    }

}