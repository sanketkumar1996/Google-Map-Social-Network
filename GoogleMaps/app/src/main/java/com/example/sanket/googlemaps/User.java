package com.example.sanket.googlemaps;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by sanket on 5/3/17.
 */

public class User {

    String title,desc;
    LatLng latlang;
    ArrayList<String> urls;

    public User(){
        urls = new ArrayList<>();
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public LatLng getLatlang() {
        return latlang;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLatlang(LatLng latlang) {
        this.latlang = latlang;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrls(String urls) {
        this.urls.add(urls);
    }
}
