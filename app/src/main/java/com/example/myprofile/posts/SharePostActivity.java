package com.example.myprofile.posts;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.modal.PostData;
import com.example.myprofile.modal.PostUrl;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SharePostActivity extends AppCompatActivity {


    static TextView postLink,postMsg;
    static ImageView postImage;
    static String imageUri;
    static String postSenderName;
    Button postShare;
     MyGlobal myGlobal;


    static FirebaseAuth firebaseAuth;
    static FirebaseUser firebaseUser;

    static FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReference,mref;

    static FirebaseStorage firebaseStorage;
    static StorageReference storageReference;

    static FirebaseFirestore firebaseFirestore;
    static CollectionReference collectionReference;

    FirestoreRecyclerOptions<PostData> firestoreRecyclerOptions;

    private int SELECT_IMAGE_CODE=1;
    private Uri imageuri=null,uri;
    private String TAG="MyTag";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);

        initViews();


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("MyProfileUsers");


        mref=firebaseDatabase.getReference("MyPosts");

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("MyProfileImages");

        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Posts");

        myGlobal=new MyGlobal(SharePostActivity.this);


        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            selectImage();
            }
        });



        postShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();

            }
        });
    }


      public void share(){
          String postText=postMsg.getText().toString();
          String postLinks=postLink.getText().toString();
          Uri imageData  =getPicture();
          String s=imageData.toString();
          if(s.equals("")||s.length()==0||imageData==null && postText.equals("") && postLinks.equals("")){
              myGlobal.toast("You haven't write any text and not select any image");
          }
          else{

              PostData postData = new PostData();
              postData.setPostSenderName(getIntent().getStringExtra("Name"));
              postData.setPostText(postText);
              postData.setPostLinks(postLinks);
              postData.setPostCreatedTime(String.valueOf(System.currentTimeMillis()));

              if(s.equals("")||s.length()==0||imageData==null){
                setPost(postData);
              }
              else{
                  sharePost(postData,imageData);
              }
          }
    }




    public void sharePost(PostData postData,Uri imageData){


            String key = myGlobal.getAlphaNumericString();
            StorageReference PostImages = storageReference.child(key + imageData.getLastPathSegment());

            PostImages.putFile(imageData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            PostImages.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            PostUrl postUrl = new PostUrl();
                                            postUrl.setPostImageUrl(String.valueOf(uri));
                                            // HashMap<String,String> hashMap = new HashMap<>();
                                            //hashMap.put("Url",String.valueOf(uri));
                                            postData.setPostUrl(postUrl);
                                            setPost(postData);
                                        }
                                    });
                        }
                    });


        myGlobal.toast("Post shared");
    }




    public void setPost(PostData postData){
        collectionReference.document().set(postData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //myGlobal.toast("Uploaded");
                    }
                });
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

            postImage.setImageURI(uri);
            setPicture(uri);
        }

    }


    private void setPicture(Uri ur){
        imageuri=ur;
    }
    private Uri getPicture(){
        return imageuri;
    }







    private void initViews() {
        postMsg=findViewById(R.id.postText);
        postLink=findViewById(R.id.postLink);
        postImage=findViewById(R.id.postImage);

        postShare=findViewById(R.id.postShare);

         /*
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2,Color.BLUE);
        gd.setCornerRadius(10);
        postMsg.setBackground(gd);
        postLink.setBackground(gd);
         */
    }







}