package com.yt3.intouchapp.Adapters;

/**
 * For receiving name and status data from the server
 */
public class Users {
    public String name;
    public String status;

//    public String image;

    public Users(){

    }

    public Users(String name, String status) {
        this.name = name;
        this.status = status;
//        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getImage() {
//        return image;
//    }

//    public void setImage(String image) {
//        this.image = image;
//    }
}
