package com.yt3.intouchapp.map;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.yt3.intouchapp.network_interface.data.MessagePoint;

import static android.graphics.Color.argb;

public class PostMapObject extends MapObject
{
    private Circle map_circle;

    private MessagePoint message_point;

    public PostMapObject(GoogleMap parent, MessagePoint message_point)
    {
        super(parent, new LatLng(message_point.getLatitude(), message_point.getLongitude()));

        this.type = "Post";

        CircleOptions options = new CircleOptions()
                                .strokeColor(argb(255, 14, 95, 6))
                                .fillColor(argb(128, 17, 115, 7))
                                .center(this.location)
                                .radius(3.5); // In meters

        this.map_circle = this.parentMap.addCircle(options);

        this.message_point = message_point;
    }

    @Override
    public boolean contains(LatLng coordinates)
    {
        float[] results = new float[1];
        Location.distanceBetween(this.getLatitude(), this.getLongitude(), coordinates.latitude, coordinates.longitude, results);
        float distance = results[0];

        return (distance) <= this.map_circle.getRadius();
    }

    public int getThreadId()
    {
        return this.message_point.getThreadId();
    }

    public String getThreadTitle()
    {
        return this.message_point.getTitle();
    }

}
