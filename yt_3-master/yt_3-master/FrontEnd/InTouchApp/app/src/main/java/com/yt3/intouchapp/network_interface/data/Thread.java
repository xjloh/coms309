package com.yt3.intouchapp.network_interface.data;

import com.google.android.gms.maps.model.LatLng;

public class Thread
{
    private String title;
    private String content;
    private String author;
    private LatLng location;

    public Thread() {    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
