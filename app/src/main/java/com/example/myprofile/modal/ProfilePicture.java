package com.example.myprofile.modal;

import android.net.Uri;

public class ProfilePicture {

    private Uri picture;

    public ProfilePicture(Uri picture) {
        this.picture = picture;
    }

    public Uri getPicture() {
        return picture;
    }
    public void setPicture(Uri picture) {
        this.picture = picture;
    }
}
