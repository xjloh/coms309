package com.yt3.intouchapp.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import static android.graphics.Color.argb;

public class UserMapObject extends MapObject
{

    private Circle user_marker;
    private Circle user_marker_radius;

    public UserMapObject(GoogleMap parent, LatLng location)
    {
        super(parent, location);

        this.type = "User";

        CircleOptions userMarkerOptions     = new CircleOptions()
                                            .strokeColor(argb(255, 49, 155, 212))
                                            .fillColor(argb(128, 49, 155, 212))
                                            .center(this.location)
                                            .radius(3.5); // In meters

        this.user_marker = this.parentMap.addCircle(userMarkerOptions);

        CircleOptions radiusMarkerOptions   = new CircleOptions()
                                            .strokeColor(argb(128, 49, 179, 212))
                                            .fillColor(argb(32, 49, 212, 212))
                                            .center(this.location)
                                            .radius(300); // In meters

        this.user_marker_radius = this.parentMap.addCircle(radiusMarkerOptions);
    }

    @Override
    public boolean contains(LatLng coordinates)
    {
        return false;
    }
}
