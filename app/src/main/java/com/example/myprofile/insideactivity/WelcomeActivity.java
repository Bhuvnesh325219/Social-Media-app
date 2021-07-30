package com.example.myprofile.insideactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class WelcomeActivity extends AppCompatActivity {


    TextView welcomeName;
    Button welcomeLogin;
    String welcomeEmail,welcomePassword;
    private ProgressBar welcomeProgressBar;

    FirebaseAuth firebaseAuth;
    private MyGlobal myGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initViews();

        firebaseAuth=FirebaseAuth.getInstance();
        myGlobal=new MyGlobal(WelcomeActivity.this);

        welcomeName.setText(getIntent().getStringExtra("Name"));


        welcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                userlogin(intent.getStringExtra("Email"),intent.getStringExtra("Password"));
            }
        });

    }


    private void userlogin(String email,String password) {
            welcomeProgressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            welcomeProgressBar.setVisibility(View.GONE);
                            moveActivity(ProfileActivity.class,0);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    welcomeProgressBar.setVisibility(View.GONE);
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    myGlobal.toast("Invalid Password");
                }
                else if(e instanceof FirebaseAuthInvalidUserException){
                    myGlobal.toast("You are not registered");
                }
            }
        });;

    }


    private void moveActivity(Class c,int x){
        Intent intent = new Intent(WelcomeActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }


    private void initViews() {
        welcomeName=findViewById(R.id.welcomeName);
        welcomeLogin=findViewById(R.id.welcomeLogin);
        welcomeProgressBar=findViewById(R.id.welcomeProgressBar);
        welcomeProgressBar.setVisibility(View.GONE);
    }
}