package com.yt3.intouchapp.utils;

public class UserInfo {
    private int uid;
    private String username;
    private String email;
    private String isOnline;
    private String imgPath;

    public UserInfo(){

    }
    public UserInfo(int uid, String username, String email, String bio,String ol) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.bio = bio;
        if(ol.equals("1")){
            isOnline = "online";
        }
        else{
            isOnline = "offline";
        }

    }

    public int getUid() {
        return uid;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    private String bio;
}
