package com.example.myprofile.insideactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoreDetailActivity extends AppCompatActivity {


    TextView moreDetailCountry,moreDetailDOB,moreDetailMobile,moreDetailDes;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;

    Button moreDetailPrevious;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);


        initViews();


        moreDetailPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            moveActivity(ProfileActivity.class,2);
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("MyProfileUsers");



        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Map<String,Object> data = (Map<String, Object>)snapshot.getValue();



                moreDetailCountry.setText(snapshot.child("OtherDetails").child("Country").getValue().toString());
                moreDetailMobile.setText(snapshot.child("ContactDetails").child("Mobile").getValue().toString());
                moreDetailDOB.setText(snapshot.child("PersonalDetails").child("DOB").getValue().toString());
                moreDetailDes.setText(snapshot.child("OtherDetails").child("Description").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void moveActivity(Class c,int x){
        Intent intent = new Intent(MoreDetailActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }





    private void initViews() {
        moreDetailCountry=findViewById(R.id.moreDetailCountryText);
        moreDetailDes=findViewById(R.id.moreDetailDesText);
        moreDetailMobile=findViewById(R.id.moreDetailMobileText);
        moreDetailDOB=findViewById(R.id.moreDetailDOBText);

        moreDetailPrevious=findViewById(R.id.moreDetailsPrevious);

    }
}