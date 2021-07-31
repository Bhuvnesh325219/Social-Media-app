package com.example.myprofile.search;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    EditText searchName;
    ImageButton searchButton;

    RecyclerView searchRecyclerView;
    LinearLayoutManager linearLayoutManager;
    SearchAdapter searchAdapter;

    ArrayList<String> searchList;
    ArrayList<String> arrList;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;

    MyGlobal myGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

          initViews();

          arrList=new ArrayList<>();
          searchList=new ArrayList<>();


          linearLayoutManager=new LinearLayoutManager(this);
          searchRecyclerView.setLayoutManager(linearLayoutManager);

          searchAdapter=new SearchAdapter(arrList,SearchActivity.this);
          searchRecyclerView.setAdapter(searchAdapter);

          myGlobal=new MyGlobal(this);

          firebaseFirestore=FirebaseFirestore.getInstance();
          collectionReference=firebaseFirestore.collection("MySearchData");


        firebaseAuth=FirebaseAuth.getInstance();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name=searchName.getText().toString();

             searchPeople(name);
            }
        });




    }

    private void searchPeople(String name) {


        collectionReference.whereEqualTo("searchName",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                String s="";
                for (QueryDocumentSnapshot document : task.getResult()) {
                    s=document.getData().get("searchId").toString();
                    searchList.add(s);
                }
                searchAdapter.updateSearchList(searchList);
            }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }


    private void initViews() {
        searchName=findViewById(R.id.searchName);
        searchButton=findViewById(R.id.searchButton);
        searchRecyclerView=findViewById(R.id.searchRecyclerView);
    }
}