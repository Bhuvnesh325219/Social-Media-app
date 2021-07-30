package com.example.myprofile.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myprofile.R;
import com.example.myprofile.modal.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {


    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;
    ArrayList<News> arrayList,arr;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        //initViews();
        newsRecyclerView= findViewById(R.id.newsRecyclerView);
        arrayList =new ArrayList<News>();
        arr =new ArrayList<News>();
        newsData();
        newsRecyclerView= findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter=new NewsAdapter(NewsActivity.this,arr);
        newsRecyclerView.setAdapter(newsAdapter);





    }




    private void initViews() {
        newsRecyclerView= findViewById(R.id.newsRecyclerView);
    }

    private void newsData() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //String url ="https://www.google.com";
        String url ="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String s="";
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                News news = new News(
                                        jsonObject.getString("urlToImage"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("url")
                                );
                                arrayList.add(news); }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        newsAdapter.updateList(arrayList);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //textView.setText(error.getMessage().toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }




}