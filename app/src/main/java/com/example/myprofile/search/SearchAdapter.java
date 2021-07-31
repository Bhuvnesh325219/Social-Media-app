package com.example.myprofile.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprofile.R;
import com.example.myprofile.insideactivity.ProfileActivity;
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

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    public ArrayList<String> searchList;
    public Context context;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    public SearchAdapter(ArrayList<String> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("MyProfileUsers");

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("MyProfileImages");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
       SearchViewHolder searchViewHolder = new SearchViewHolder(view);
       return  searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

                  String key=searchList.get(position);
                  storageReference.child("images/" + key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).circleCrop().into(holder.searchImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });


         databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
               String name=snapshot.child("PersonalDetails").child("Name").getValue().toString();
               String city=snapshot.child("PersonalDetails").child("City").getValue().toString();
               holder.searchedName.setText(name);
               holder.searchedCity.setText(city);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(firebaseUser.getUid().equals(key)){
                     Intent intent = new Intent(context, ProfileActivity.class);
                     holder.itemView.getContext().startActivity(intent);
                 }else{

                 Intent intent = new Intent(context,SearchPeopleActivity.class);
                 intent.putExtra("Key",key);
                 holder.itemView.getContext().startActivity(intent);}
             }
         });


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void updateSearchList(ArrayList<String> newsearchList){
        searchList.clear();
        searchList.addAll(newsearchList);
        notifyDataSetChanged();
    }




}

class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView searchedName,searchedCity;
        ImageView searchImage;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        searchedName=itemView.findViewById(R.id.search_item_Name);
        searchedCity=itemView.findViewById(R.id.search_item_City);
        searchImage=itemView.findViewById(R.id.search_item_Image);
    }
}
