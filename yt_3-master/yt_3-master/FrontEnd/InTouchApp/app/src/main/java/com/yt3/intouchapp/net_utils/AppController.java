package com.yt3.intouchapp.net_utils;

import android.app.Application;
import android.location.Location;
import android.text.TextUtils;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.google.android.gms.maps.model.LatLng;
import com.yt3.intouchapp.network_interface.data.MessagePoint;

import java.util.List;

public class AppController extends Application
{
    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private String userId;
    private Location currentLocation;
    private LatLng currentLatLng;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        // set the default tag if empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(tag);
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getLatitude()
    {
        // Temp Solution
        double d = this.currentLatLng.latitude;
        if (d < 0)
            d = Math.abs(d);
        return String.format("%.6f", d);

        // return String.format("%.6f", this.currentLocation.getLatitude());
    }

    public String getLongitude()
    {        // Temp Solution
        double d = this.currentLatLng.longitude;
        if (d < 0)
            d = Math.abs(d);
        return String.format("%.6f", d);

        // return String.format("%.6f", this.currentLocation.getLongitude());
    }

    public Location getCurrentLocation()
    {
        return this.currentLocation;
    }

    public void setCurrentLocation(Location location)
    {
        // Log.i("AppController Location", this.currentLocation.toString());
        currentLocation = location;
        currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }

    public LatLng getCurrentLatLng()
    {
        return this.currentLatLng;
    }
}
