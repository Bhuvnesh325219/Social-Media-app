package com.example.myprofile.modal;

public class OtherDetail {

    private String country,college,city;

    public OtherDetail(String country, String college,String city) {
        this.country = country;
        this.college = college;
        this.city=city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }

    public String getCity(){ return city;}
    public void setCity(String city) {
        this.city=city;
    }
}
