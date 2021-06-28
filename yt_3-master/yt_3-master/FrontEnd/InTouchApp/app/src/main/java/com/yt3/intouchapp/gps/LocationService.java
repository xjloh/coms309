package com.yt3.intouchapp.gps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service implements LocationListener
{

    private static final String TAG = "LocationService";
    public static final String BROADCAST_ACTION = "com.yt3.intouchapp.locationevent";

    private static final int MIN_TIME_TO_UPDATE_MS = 1000;
    private static final int MIN_DISTANCE_TO_UPDATE_METERS = 2;

    private final Handler mHandler = new Handler();
    private Intent intent;

    boolean isGPSEnable     = false;
    boolean isNetworkEnable = false;

    private static LocationManager lm;
    private static Location currentLocation = null;
    private static Location prevLocation    = null;

    double latitude,longitude;
    private static Timer mTimer;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mTimer = new Timer();

        mTimer.schedule(new TimerTaskToGetLocation(), 1000, MIN_TIME_TO_UPDATE_MS);

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Service Starting", Toast.LENGTH_SHORT).show();

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Service Done", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public static Location getLocation()
    {
        return currentLocation;
    }

    @Override
    public void onLocationChanged(Location location)
    {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }

    private class TimerTaskToGetLocation extends TimerTask
    {
        @Override
        public void run()
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    fn_getlocation();
                }
            });
        }
    }

    private void fn_getlocation()
    {
        lm = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable)
        {

        }
        else
        {
            if (isGPSEnable)
            {

                currentLocation = null;

                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_TO_UPDATE_MS, MIN_DISTANCE_TO_UPDATE_METERS, this);

                if (lm != null)
                {

                    currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (currentLocation != null)
                    {
//                        Log.e("latitude", currentLocation.getLatitude()+"");
//                        Log.e("longitude", currentLocation.getLongitude()+"");

                        latitude = currentLocation.getLatitude();
                        longitude = currentLocation.getLongitude();
                        fn_update(currentLocation);
                    }
                }
            }
            else if (isNetworkEnable)
            {

                currentLocation = null;

                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_TO_UPDATE_MS,0,this);

                if (lm != null)
                {

                    currentLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (currentLocation != null)
                    {
//                        Log.e("latitude", currentLocation.getLatitude()+"");
//                        Log.e("longitude", currentLocation.getLongitude()+"");
                        latitude = currentLocation.getLatitude();
                        longitude = currentLocation.getLongitude();
                        fn_update(currentLocation);
                    }
                }
            }
        }
    }

    private void fn_update(Location location)
    {
        if(location != null)
        {
            intent.putExtra("location", location);
            sendBroadcast(intent);
            prevLocation = location;
        }
    }
}
