package com.example.myprofile.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprofile.R;
import com.example.myprofile.modal.News;

import java.util.ArrayList;

public class NewsAdapter  extends RecyclerView.Adapter<NewsViewHolder> {


    public Context context;
    public ArrayList<News> arrayList;


    public NewsAdapter(Context context, ArrayList<News> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        NewsViewHolder newsViewHolder =new NewsViewHolder(view);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news =arrayList.get(position);
        Glide.with(holder.itemView.getContext()).load(news.getImageUrl()).into(holder.newsImage);
        holder.newsAuthor.setText(news.getAuthor());
        holder.newsTitle.setText(news.getTitle());

        holder.newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent =builder.build();
                customTabsIntent.launchUrl(holder.itemView.getContext(), Uri.parse(news.getUrl()));

            }
        });

       holder.newsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Intent.ACTION_SEND));
                intent.setType("text/plane");
                intent.putExtra(Intent.EXTRA_TEXT,news.getUrl());
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

      public void updateList(ArrayList<News> newList){
        arrayList.clear();
        arrayList.addAll(newList);
        notifyDataSetChanged();
      }



}



class NewsViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView newsImage;
    public TextView newsAuthor,newsTitle;
    public ImageButton newsShare;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        newsAuthor=itemView.findViewById(R.id.newsAuthor);
        newsTitle=itemView.findViewById(R.id.newsTitle);
        newsImage=itemView.findViewById(R.id.newsImage);
        newsShare =itemView.findViewById(R.id.newsShare);

        newsImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


    }
}
