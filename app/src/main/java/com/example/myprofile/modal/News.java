package com.example.myprofile.modal;

public class News {

    String imageUrl,author,title,url;


    public News(String imageUrl, String author, String title,String url) {
        this.imageUrl = imageUrl;
        this.author = author;
        this.title = title;
        this.url=url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }








}
