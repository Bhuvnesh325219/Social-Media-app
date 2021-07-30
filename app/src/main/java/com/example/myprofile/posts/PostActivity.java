package com.example.myprofile.posts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprofile.R;
import com.example.myprofile.modal.PostData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PostActivity extends AppCompatActivity {

         RecyclerView postRecyclerView;
         LinearLayoutManager linearLayoutManager;
         PostAdapter postAdapter;


         FirebaseDatabase firebaseDatabase;
         DatabaseReference databaseReference;
         FirebaseAppLifecycleListener appLifecycleListener;

         FirebaseFirestore firebaseFirestore;
         CollectionReference collectionReference;
         FirebaseRecyclerOptions<PostData> firebaseRecyclerOptions;
         //DatabaseReference query;


         FirestoreRecyclerOptions<PostData> firestoreRecyclerOptions;

         @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        postRecyclerView  =findViewById(R.id.postRecyclerView);


          firebaseDatabase =FirebaseDatabase.getInstance();
          databaseReference=firebaseDatabase.getReference("MyPosts");

             firebaseFirestore= FirebaseFirestore.getInstance();
             collectionReference=firebaseFirestore.collection("Posts");



             linearLayoutManager=new LinearLayoutManager(this);
        postRecyclerView.setLayoutManager(linearLayoutManager);



             Query query = collectionReference.orderBy("postCreatedTime", Query.Direction.DESCENDING);
             firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<PostData>()
                 .setQuery(query,PostData.class).build();

          postAdapter = new PostAdapter(firestoreRecyclerOptions);
         postRecyclerView.setAdapter(postAdapter);
         }

    @Override
    protected void onStart() {
        super.onStart();
       postAdapter.startListening();
         }

    @Override
    protected void onStop() {
        super.onStop();
        postAdapter.stopListening();
    }
}