package com.example.myprofile.search;

public class SearchData {

    String searchName,searchId;

    public SearchData(String searchName, String searchId) {
        this.searchName = searchName;
        this.searchId = searchId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
