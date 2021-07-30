package com.example.myprofile.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.modal.PersonalDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class PersonalDetailActivity extends AppCompatActivity {



    private DatePickerDialog personalDatepicker;
    static EditText personalName,personalEmail,personalDOB,personalMobile;
    private Button personalProceed;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private  boolean exists;
    private MyGlobal myGlobal;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);


        initViews();

        firebaseAuth=FirebaseAuth.getInstance();
        myGlobal= new MyGlobal(PersonalDetailActivity.this);


        personalProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersonalDetail personalDetail =instanceCreate();

        if(personalDetail.getName().equals("")||personalDetail.getEmail().equals("")
        ||personalDetail.getMobile().equals("")||personalDetail.getDob().equals("")
                ||!myGlobal.emailValidate(personalDetail.getEmail())) {
            if(!myGlobal.emailValidate(personalDetail.getEmail())){
                myGlobal.toast("Enter a valid email address");
            }
            if (personalDetail.getName().equals("")) {
                personalName.setError("Please Enter Name");
            }
            if (personalDetail.getEmail().equals("")) {
                personalEmail.setError("Please Enter Email");
            }
            if (personalDetail.getMobile().equals("")) {
                personalMobile.setError("Please Enter Mobile number");
            }
            if (personalDetail.getDob().equals("")) {
                personalDOB.setError("Please Select Your DOB");
            }
        }
              else {

                  firebaseAuth.fetchSignInMethodsForEmail(personalDetail.getEmail())
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if(isNewUser){
                                moveActivity(OtherDetailActivity.class,1);
                            }
                            else{
                                myGlobal.toast("Email already exist");
                            }
                        }
                    });
        }
            }
        });


        personalDOB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                datepick();
            }
        });

    }



   private void setExist(boolean b){
        exists=b;
    }
    private  boolean getExists(){
        return exists;
    }


    static PersonalDetail instanceCreate() {
        String name = personalName.getText().toString();
        String email= personalEmail.getText().toString();
        String mobile=personalMobile.getText().toString();
        String dob= personalDOB.getText().toString();

        PersonalDetail personalDetail = new PersonalDetail(name,email,mobile,dob);
        personalDetail.setName(name);
        personalDetail.setEmail(email);
        personalDetail.setMobile(mobile);
        personalDetail.setDob(dob);

      return personalDetail;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void datepick() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        personalDatepicker = new DatePickerDialog(PersonalDetailActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        personalDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        personalDatepicker.show();


    }
    private void initViews() {
        personalName=findViewById(R.id.personalName);
        personalEmail=findViewById(R.id.personalEmail);
        personalDOB=findViewById(R.id.personalDOB);
        personalMobile=findViewById(R.id.personalMobile);
        personalProceed=findViewById(R.id.personalProceed);


        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2,Color.BLUE);
        gd.setCornerRadius(10);
        personalEmail.setBackground(gd);
        personalName.setBackground(gd);
         personalDOB.setBackground(gd);
         personalMobile.setBackground(gd);

    }



    private void moveActivity(Class c,int x){
        Intent intent = new Intent(PersonalDetailActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }



}