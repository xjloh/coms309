package com.yt3.intouchapp.Adapters;

import java.sql.Timestamp;

public class Messages {
    private String name, messages;
    private int mid;
    //private Timestamp time;

    public Messages(String name, String messages, Timestamp time) {
        this.name = name;
        this.messages = messages;
        //this.time = time;
    }

    public Messages() {

    }

    public String getName() {
        return name;
    }

//    public Timestamp getTime() {
//        return time;
//    }
//
//    public void setTime(Timestamp time) {
//        this.time = time;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Messages){
            Messages ptr = (Messages) v;
            retVal = ptr.mid == this.mid;
        }

        return retVal;
    }
}
