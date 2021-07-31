package com.example.myprofile.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.insideactivity.WelcomeActivity;
import com.example.myprofile.modal.Description;
import com.example.myprofile.modal.OtherDetail;
import com.example.myprofile.modal.Password;
import com.example.myprofile.modal.PersonalDetail;
import com.example.myprofile.modal.ProfilePicture;
import com.example.myprofile.search.SearchData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class PasswordActivity extends AppCompatActivity {


    static EditText passwordNew,passwordConfirm;
    Button passwordRegister;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;

    String TAG="Hello";
    private String ImageUri;
    private MyGlobal myGlobal;

    private ProgressBar passwordProgressbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

          initViews();






        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("MyProfileUsers");

        firebaseStorage =FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("MyProfileImages/");

        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("MySearchData");

        myGlobal=new MyGlobal(PasswordActivity.this);





        passwordRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password password =instanceCreate();

                ;if(password.getPassword().equals("")||password.getConfirmpassword().equals("")) {
                    if (password.getPassword().equals("")) {
                        passwordNew.setError("Please Create a Strong password");
                    }
                    if (password.getConfirmpassword().equals("")) {
                        passwordConfirm.setError("Please Confirm your password");
                    }
                }
                if(password.getPassword().equals(password.getConfirmpassword())){
                    {
                        createUser();
                    moveActivityForword();

                }
                }
                else{
                    myGlobal.toast("Password didn't match");
                }
            }
        });



    }


    public void createUser(){
         passwordProgressbar.setVisibility(View.VISIBLE);

       PersonalDetail personalDetail = PersonalDetailActivity.instanceCreate();
       Password password= PasswordActivity.instanceCreate();

        String email=personalDetail.getEmail();
        String pass=password.getConfirmpassword();


        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    String key = firebaseUser.getUid();


                    PersonalDetail personalDetail=PersonalDetailActivity.instanceCreate();
                    OtherDetail otherDetail = OtherDetailActivity.instanceCreate();
                    Description description=DescriptionActivity.instanceCreate();
                    ProfilePicture picture= ProfilePictureActivity.instanceCreate();
                    Password password= PasswordActivity.instanceCreate();

                    SearchData searchData = new SearchData(personalDetail.getName(),key);
                    searchData.setSearchName(personalDetail.getName());
                    searchData.setSearchId(key);

                    HashMap<String,HashMap<String,Object>> insertValues =new HashMap<String,HashMap<String,Object>>();


                    HashMap<String,Object> val1= new HashMap<>();
                    val1.put("Name",personalDetail.getName());
                    val1.put("DOB",personalDetail.getDob());
                    val1.put("City",otherDetail.getCity());



                    storageReference.child("images/"+key).putFile(picture.getPicture()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            myGlobal.toast("Images uploaded");


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        myGlobal.toast(e.getMessage().toString());
                        }
                    });






                    insertValues.put("PersonalDetails",val1);

                    HashMap<String,Object> val2= new HashMap<>();
                    val2.put("Email",personalDetail.getEmail());
                    val2.put("Mobile",personalDetail.getMobile());

                    insertValues.put("ContactDetails",val2);

                    HashMap<String,Object> val3= new HashMap<>();
                    val3.put("Country",otherDetail.getCountry());
                    val3.put("College",otherDetail.getCollege());
                    val3.put("Description",description.getDescription());
                    val3.put("Password",password.getConfirmpassword());

                    insertValues.put("OtherDetails",val3);

                    databaseReference.child(key).setValue(insertValues);

                    collectionReference.document().set(searchData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        //myGlobal.toast("Succeed");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });




                     passwordProgressbar.setVisibility(View.GONE);
                    moveActivityForword();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                passwordProgressbar.setVisibility(View.GONE);
                if(e instanceof FirebaseAuthUserCollisionException){
                    myGlobal.toast("Email Already exist");
                }

            }
        });


    }


    private  HashMap<String,HashMap<String,Object>> mapDetails(String key){
        return null;
    }


    private  void setImageUrl(String uri){
        ImageUri=uri;
    }
    private String getImageUri(){
        return ImageUri;
    }




    static Password instanceCreate() {
         String pasNew =passwordNew.getText().toString();
         String pasConfirm =passwordConfirm.getText().toString();

         Password password= new Password(pasNew,pasConfirm);
         password.setPassword(pasNew);
         password.setConfirmpassword(pasConfirm);

        return password;
    }


    private void initViews() {
        passwordNew=findViewById(R.id.passwordNew);
        passwordConfirm=findViewById(R.id.passwordConfirm);
        passwordRegister=findViewById(R.id.passwordRegister);
        passwordProgressbar=findViewById(R.id.passwordProgressBar);
        passwordProgressbar.setVisibility(View.GONE);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2,Color.BLUE);
        gd.setCornerRadius(10);
        passwordNew.setBackground(gd);
        passwordConfirm.setBackground(gd);

    }

    private void moveActivityForword(){

        PersonalDetail personalDetail= PersonalDetailActivity.instanceCreate();
        Password password= PasswordActivity.instanceCreate();

        Intent intent = new Intent(PasswordActivity.this, WelcomeActivity.class);
        intent.putExtra("Email",personalDetail.getEmail());
        intent.putExtra("Password",password.getConfirmpassword());
        intent.putExtra("Name",personalDetail.getName());
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);



    }




}
