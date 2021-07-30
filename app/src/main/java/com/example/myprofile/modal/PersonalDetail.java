package com.example.myprofile.modal;

public class PersonalDetail {

   private String name,email,mobile,dob;

    public PersonalDetail(String name, String email, String mobile, String dob) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
}
