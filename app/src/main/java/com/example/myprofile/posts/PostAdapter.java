package com.example.myprofile.posts;

import android.content.Intent;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprofile.R;
import com.example.myprofile.Static.MyGlobal;
import com.example.myprofile.modal.PostData;
import com.example.myprofile.modal.PostUrl;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PostAdapter  extends FirestoreRecyclerAdapter<PostData,PostHolder> {





    public PostAdapter(@NonNull FirestoreRecyclerOptions<PostData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull PostData model) {

        long currTime=System.currentTimeMillis();
        long postCreatedTime=Long.valueOf(model.getPostCreatedTime());
        long diff=(currTime-postCreatedTime)/1000;

        String pt= MyGlobal.givePostTime(diff);

        holder.postsenderName.setText(model.getPostSenderName());
        holder.postTime.setText(pt);
        holder.postText.setText(model.getPostText());
        holder.postLinks.setText(model.getPostLinks());
        Linkify.addLinks(holder.postLinks,Linkify.WEB_URLS|Linkify.PHONE_NUMBERS);
        PostUrl postUrl=model.getPostUrl();
        if(!postUrl.getPostImageUrl().equals("")) {
            Glide.with(holder.itemView.getContext()).load(postUrl.getPostImageUrl()).into(holder.postImage);
        }

        holder.postContentShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Post Content Share by your friend: "+
                        model.getPostSenderName()+"\n"+model.getPostText()+"\n"
                        +model.getPostLinks()+"\n"+model.getPostUrl().getPostImageUrl());
                  intent.setType("text/plain");
                  holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        PostHolder postHolder = new PostHolder(view);
        return postHolder;
    }
}




class PostHolder extends RecyclerView.ViewHolder{
        TextView postsenderName,postText,postLinks,postTime;
        ImageView postImage;
        ImageButton postContentShare;



    public PostHolder(@NonNull View itemView) {
        super(itemView);
        postsenderName=itemView.findViewById(R.id.post_item_SenderName);
        postText=itemView.findViewById(R.id.post_item_Message);
        postLinks=itemView.findViewById(R.id.post_item_Links);
        postImage =itemView.findViewById(R.id.post_item_Image);
        postTime=itemView.findViewById(R.id.post_item_Time);
        postContentShare=itemView.findViewById(R.id.post_item_Share);
    }
}
