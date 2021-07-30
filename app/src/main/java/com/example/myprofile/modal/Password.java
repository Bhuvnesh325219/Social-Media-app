package com.example.myprofile.modal;

public class Password {

    private String password,confirmpassword;

    public Password(String password, String confirmpassword) {
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }
    public void setConfirmpassword(String confirmpassword) { this.confirmpassword = confirmpassword; }
}
