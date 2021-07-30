package com.example.myprofile.insideactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myprofile.R;
import com.example.myprofile.modal.Home;
import com.example.myprofile.news.NewsActivity;
import com.example.myprofile.posts.PostActivity;
import com.example.myprofile.posts.SharePostActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {


    ImageView profileImage;
    TextView profileName,profileCollege,profileEmail,profileCity;
    Button profileOtherDetails;
    ImageButton profileOtherOptions,profileNews,profilePost,profileSharePost;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    FirebaseUser firebaseUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        initViews();


         firebaseAuth=FirebaseAuth.getInstance();

         firebaseDatabase=FirebaseDatabase.getInstance();
         databaseReference=firebaseDatabase.getReference("MyProfileUsers");

         firebaseStorage=FirebaseStorage.getInstance();
         storageReference=firebaseStorage.getReference("MyProfileImages");

         Home home;


         firebaseUser=firebaseAuth.getCurrentUser();

          databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {



                    String key=firebaseUser.getUid();
                  storageReference.child("images/" + key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {


                          Glide.with(ProfileActivity.this).load(uri).circleCrop().into(profileImage);

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });









                 // Map<String,Object> data = (Map<String,Object>)snapshot.getValue();
                  profileName.setText(snapshot.child("PersonalDetails").child("Name").getValue().toString());
                  profileEmail.setText(snapshot.child("ContactDetails").child("Email").getValue().toString());
                  profileCity.setText(snapshot.child("PersonalDetails").child("City").getValue().toString());
                  profileCollege.setText(snapshot.child("OtherDetails").child("College").getValue().toString());

              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });


        profileOtherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(MoreDetailActivity.class,0);
            }
        });
        profileOtherOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveActivity(OtherOptionActivity.class,1);
            }
        });
       profilePost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) { moveActivity(PostActivity.class,2); }
       });

       profileSharePost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               moveActivity(SharePostActivity.class,0);
           }
       });

       profileNews.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               moveActivity(NewsActivity.class,0);
           }
       });



    }

    private void moveActivity(Class c,int x){
        Intent intent = new Intent(ProfileActivity.this, c);
        intent.putExtra("Name",profileName.getText());
        startActivity(intent);
        if(x==1){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if(x==2){
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        }
    }

    private void initViews() {
        profileImage=findViewById(R.id.profileImage);

        profileName=findViewById(R.id.profileName);
        profileCollege=findViewById(R.id.profileCollegeText);
        profileEmail=findViewById(R.id.profileEmailText);
        profileCity=findViewById(R.id.profileCityText);

        profileOtherDetails=findViewById(R.id.profileOtherDetails);
        profileOtherOptions=findViewById(R.id.profileOtherOptions);
        profileNews=findViewById(R.id.profileNews);
        profilePost=findViewById(R.id.profilePost);
        profileSharePost=findViewById(R.id.profileSharePost);
    }
}