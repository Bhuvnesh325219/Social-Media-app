package com.example.myprofile.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.modal.Description;

public class DescriptionActivity extends AppCompatActivity {

    static EditText descriptionBio;
    private Button descriptionProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        initViews();


        descriptionProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Description desc= instanceCreate();
                if(desc.getDescription().equals("")){
                 descriptionBio.setError("Please Write Something(Minimum 20 words)");
                }else if(desc.getDescription().length()<20){
                 descriptionBio.setError("Write minimum 20 words");
                }
                else{
                moveActivity(ProfilePictureActivity.class,1);}
            }
        });


    }

    static Description instanceCreate() {
        String description =descriptionBio.getText().toString();

        Description des = new Description(description);
        des.setDescription(description);



        return des;
    }

    private void initViews() {
        descriptionBio=findViewById(R.id.descriptionBio);
        descriptionProceed=findViewById(R.id.descriptionProceed);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2,Color.BLUE);
        gd.setCornerRadius(10);
        descriptionBio.setBackground(gd);



    }

    private void moveActivity(Class c,int x){
        Intent intent = new Intent(DescriptionActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }


}