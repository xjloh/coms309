package com.yt3.intouchapp.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.yt3.intouchapp.activities.BaseAppActivity;

public class LocationReceiver extends BroadcastReceiver
{
    private Location currentLocation = null;
    private BaseAppActivity parentActivity;

    public LocationReceiver(BaseAppActivity parentActivity)
    {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(LocationService.BROADCAST_ACTION))
        {
            Location newLocation = (Location) intent.getParcelableExtra("location");

            if(newLocation != null)
            {
                double newLat = newLocation.getLatitude();
                double newLon = newLocation.getLongitude();

                if(this.currentLocation == null || !(newLat == this.currentLocation.getLatitude() && newLon == this.currentLocation.getLongitude()))
                {
                    this.currentLocation = newLocation;

                    Log.e("latitude", this.currentLocation.getLatitude()+"");
                    Log.e("longitude", this.currentLocation.getLongitude()+"");

                    this.parentActivity.onLocationUpdate(this.currentLocation);
                }
            }
        }
    }

    public Location getCurrentLocation()
    {
        return currentLocation;
    }
}
