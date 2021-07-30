package com.example.myprofile.modal;

public class PostData {
    String postSenderName,postText,postLinks,postCreatedTime;
    PostUrl postUrl;


    public PostData() {}

    public String getPostSenderName() {
        return postSenderName;
    }
    public void setPostSenderName(String postSenderName) {
        this.postSenderName = postSenderName;
    }

    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostLinks() {
        return postLinks;
    }
    public void setPostLinks(String postLinks) {
        this.postLinks = postLinks;
    }

    public String getPostCreatedTime() {
        return postCreatedTime;
    }
    public void setPostCreatedTime(String postCreatedTime) { this.postCreatedTime = postCreatedTime; }

    public PostUrl getPostUrl() {
        return postUrl;
    }
    public void setPostUrl(PostUrl postUrl) {
        this.postUrl = postUrl;
    }
}



