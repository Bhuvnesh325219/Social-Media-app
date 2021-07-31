package com.example.myprofile.search;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myprofile.R;
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

public class SearchPeopleActivity extends AppCompatActivity {



    ImageView searchPeopleImage;
    TextView searchPeopleName,searchPeopleEmail,searchPeopleCity,searchPeopleCollege;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_people);


        initViews();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("MyProfileUsers");

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("MyProfileImages");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();



        databaseReference.child(getIntent().getStringExtra("Key"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                String key=getIntent().getStringExtra("Key");
                storageReference.child("images/" + key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        Glide.with(SearchPeopleActivity.this).load(uri).circleCrop().into(searchPeopleImage);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




                // Map<String,Object> data = (Map<String,Object>)snapshot.getValue();
                searchPeopleName.setText(snapshot.child("PersonalDetails").child("Name").getValue().toString());
                searchPeopleEmail.setText(snapshot.child("ContactDetails").child("Email").getValue().toString());
                searchPeopleCity.setText(snapshot.child("PersonalDetails").child("City").getValue().toString());
                searchPeopleCollege.setText(snapshot.child("OtherDetails").child("College").getValue().toString());

            }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

    private void initViews() {
        searchPeopleImage=findViewById(R.id.search_people_Image);
        searchPeopleName=findViewById(R.id.search_people_Name);
        searchPeopleEmail=findViewById(R.id.search_people_EmailText);
        searchPeopleCity=findViewById(R.id.search_people_CityText);
        searchPeopleCollege=findViewById(R.id.search_people_CollegeText);
    }
}