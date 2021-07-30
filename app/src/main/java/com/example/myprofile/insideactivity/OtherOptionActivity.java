package com.example.myprofile.insideactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.activity.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Intent.createChooser;

public class OtherOptionActivity extends AppCompatActivity {


    Button otherLogout;
    Button otherPrevious;
    TextView otherVerify,otherShare;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    MyGlobal myGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_option);


        initViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myGlobal=new MyGlobal(this);

        otherVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(VerificationActivity.class, 0);
            }
        });
        otherShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/link");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Share Demo");

                String appLink;
                try{
                appLink= "https://play.google.com/store/apps/details?="+
                        getApplicationContext().getOpPackageName();}
                catch (Exception e){
                    appLink= "https://play.google.com/store/apps/details?="+
                            getApplicationContext().getOpPackageName();
                }

                String shareBody="Hey! Download the my app :"+"\n"+appLink;
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(createChooser(intent,"Share using"));

            }
        });


        otherPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(ProfileActivity.class, 2);
            }
        });




        otherLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                moveActivity(HomeActivity.class, 0);
            }
        });


    }


    private void moveActivity(Class c,int x){
        Intent intent = new Intent(OtherOptionActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }



    private void initViews() {
        otherLogout=findViewById(R.id.otherLogout);
        otherPrevious=findViewById(R.id.otherPrevious);
        otherVerify=findViewById(R.id.otherVerify);
        otherShare=findViewById(R.id.otherShare);
    }





}