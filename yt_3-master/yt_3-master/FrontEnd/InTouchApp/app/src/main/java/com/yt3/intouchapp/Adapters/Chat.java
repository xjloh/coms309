package com.yt3.intouchapp.Adapters;

public class Chat {
    public String name;
    public String latestMsg;
//    public String image;

    public Chat(){

    }

    public Chat(String name, String latestMsg) {
        this.name = name;
        this.latestMsg = latestMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatestMsg() {
        return latestMsg;
    }

    public void setLatestMsg(String status) {
        this.latestMsg = status;
    }
}
