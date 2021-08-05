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
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.modal.OtherDetail;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import static com.example.myprofile.R.id;

public class OtherDetailActivity extends AppCompatActivity {


    private CountryCodePicker ccp;
    static EditText otherCollege,otherCity;
    private Button otherProceed;
    static String  otherCountry="India";
    static MyGlobal myGlobal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_detail);

        initViews();

        myGlobal=new MyGlobal(OtherDetailActivity.this);


        otherProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OtherDetail otherDetail= instanceCreate();

                if(otherDetail.getCountry().equals("")||otherDetail.getCollege().equals("")||otherDetail.getCity().equals("")) {
                    if(otherDetail.getCountry().equals("")){
                        myGlobal.toast("Please select your Country");
                    }
                    if (otherDetail.getCollege().equals("")) {
                        otherCollege.setError("Please Enter College Name");
                    }
                    if (otherDetail.getCity().equals("")) {
                        otherCity.setError("Please Enter City Name");
                    }


                }
                else{
                moveActivity(DescriptionActivity.class,1);}

            }
        });


        ccp.setDefaultCountryUsingNameCode("IN");
        ccp.showFlag(false);
        ccp.showFullName(true);
        ccp.getBaseline();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                otherCountry=selectedCountry.getName();
            }
        });

    }

    static OtherDetail instanceCreate() {
        String country = otherCountry;
        String college=otherCollege.getText().toString();
        String city=otherCity.getText().toString();



        OtherDetail otherDetail= new OtherDetail(country,college,city);

        otherDetail.setCountry(country);
        otherDetail.setCollege(college);
        otherDetail.setCity(city);

        return otherDetail;
    }


    private void initViews() {
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        otherCollege=findViewById(id.otherCollege);
        otherProceed=findViewById(id.otherProceed);
        otherCity=findViewById(id.otherCity);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2,Color.BLUE);
        gd.setCornerRadius(10);
        otherCollege.setBackground(gd);
        otherCity.setBackground(gd);

    }


    private void moveActivity(Class c,int x){
        Intent intent = new Intent(OtherDetailActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }


}