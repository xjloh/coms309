package com.yt3.intouchapp.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public abstract class MapObject
{
    protected LatLng location;
    protected GoogleMap parentMap;

    protected String type = "Generic";

    public MapObject(GoogleMap parent, LatLng location)
    {
        this.parentMap = parent;
        this.location = location;
    }

    public abstract boolean contains(LatLng coordinates);

    public LatLng getLocation()
    {
        return this.location;
    }

    public double getLatitude()
    {
        return this.location.latitude;
    }

    public double getLongitude()
    {
        return this.location.longitude;
    }

    public String getType()
    {
        return this.type;
    }
}
