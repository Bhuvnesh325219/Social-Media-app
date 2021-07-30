package com.example.myprofile.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.modal.ProfilePicture;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ProfilePictureActivity extends AppCompatActivity {


    private static final int SELECT_IMAGE_CODE = 1;


    private ImageView picturePic;
    private Button pictureSelect,pictureProceed;
     static Uri uri;
     static Uri imageuri;
     static String TAG="Hello";
     MyGlobal myGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

       initView();
       myGlobal=new MyGlobal(this);

       pictureProceed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(getPicture().equals("")){
                   myGlobal.toast("Please select you profile picture");
               }else{
               moveActivity(PasswordActivity.class,1);}
           }
       });

       pictureSelect.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectImage();
           }
       });



    }

    private void toast(String toString) {
        Toast.makeText(ProfilePictureActivity.this,toString,Toast.LENGTH_LONG).show();
    }

    private void selectImage() {
        runtimePermission();

    }

    private void runtimePermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                gallaryIntent();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();


    }

    private void gallaryIntent() {

        Intent intent=  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,SELECT_IMAGE_CODE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_IMAGE_CODE && resultCode==RESULT_OK ){
            uri = data.getData();
            picturePic.setImageURI(uri);
            setPicture(uri);
        }

    }


    static void setPicture(Uri ur){
        imageuri=ur;
    }
    static Uri getPicture(){
        return imageuri;
    }


    static ProfilePicture instanceCreate() {
        Uri picture = getPicture();
        //Log.d(TAG, "instanceCreate: "+picture);
        ProfilePicture pic = new ProfilePicture(picture);
        pic.setPicture(picture);

        return pic;
    }

    private void initView() {
        picturePic=findViewById(R.id.picturePic);
        pictureSelect=findViewById(R.id.pictureSelect);
        pictureProceed=findViewById(R.id.pictureProceed);
    }

    private void moveActivity(Class c,int x){
        Intent intent = new Intent(ProfilePictureActivity.this, c);
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }


}