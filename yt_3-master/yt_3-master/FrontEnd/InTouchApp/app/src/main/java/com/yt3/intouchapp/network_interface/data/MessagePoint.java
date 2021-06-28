package com.yt3.intouchapp.network_interface.data;

public class MessagePoint
{
    private double  latitude;
    private double  longitude;
    private int     threadId;
    private String  title;

    public MessagePoint() { }

    public MessagePoint(double latitude, double longitude, int threadId, String title)
    {
        this.latitude   = latitude;
        this.longitude  = longitude;
        this.threadId   = threadId;
        this.title      = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }
}
