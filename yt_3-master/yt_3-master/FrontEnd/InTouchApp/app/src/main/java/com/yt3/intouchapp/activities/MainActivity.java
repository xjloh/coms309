package com.yt3.intouchapp.activities;

import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.dialogs.SubmitPostDialog;
import com.yt3.intouchapp.dialogs.ThreadDisplayDialog;
import com.yt3.intouchapp.gps.LocationReceiver;
import com.yt3.intouchapp.gps.LocationService;
import com.yt3.intouchapp.map.PostMapObject;
import com.yt3.intouchapp.map.UserMapObject;
import com.yt3.intouchapp.net_utils.AppController;

import com.yt3.intouchapp.map.MapObject;
import com.yt3.intouchapp.network_interface.data.MessagePoint;
import com.yt3.intouchapp.network_interface.MessagePointsRequest;

import java.util.ArrayList;

public class MainActivity extends BaseAppActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener
{
    private FloatingActionButton submitPostButton;

    private GoogleMap googleMap;

    private LocationReceiver locationReceiver;

    private UserMapObject   userObject;
    private MapObject[]     mapObjects;

    private MessagePointsRequest pointsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Activity view set up and navigation drawer set up from base activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        // Instantiate the locationReceiver for this class, which receives broadcasts of location updates from the LocationService
        this.locationReceiver = new LocationReceiver(this);

        // Bind the map fragment's onMapReadyCallback to this activity's onMapReady function
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.GoogleMap);
        mapFragment.getMapAsync(this);

        // Set click listener to pop-up submit post dialog when button is pressed
        submitPostButton = findViewById(R.id.Map_AddPostButton);
        submitPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SubmitPostDialog dialog = new SubmitPostDialog(MainActivity.this);

                dialog.show();
            }
        });

        pointsRequest = new MessagePointsRequest(getApplicationContext());
        pointsRequest.setListener(new MessagePointsRequest.MessagePointsListener()
        {
            @Override
            public void onResponse(ArrayList<MessagePoint> response)
            {
                mapObjects = new MapObject[response.size()];
                for(int i = 0; i < response.size(); i++)
                {
                    mapObjects[i] = new PostMapObject(googleMap, response.get(i));
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        registerReceiver(this.locationReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
    }

    @Override
    public void onLocationUpdate(Location location)
    {
        googleMap.clear();

        LatLng current_location = new LatLng(location.getLatitude(), location.getLongitude());

        AppController.getInstance().setCurrentLocation(location);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));

        this.userObject = new UserMapObject(this.googleMap, current_location);

//        this.mapObjects = new MapObject[1];
//        this.mapObjects[0] = new PostMapObject(this.googleMap, new MessagePoint(current_location.latitude, current_location.longitude, "", "Title"));

        this.pointsRequest.getMessagePoints(current_location.latitude, current_location.longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        this.googleMap  = googleMap;
        this.userObject = new UserMapObject(this.googleMap, new LatLng(0.0, 0.0));

        try
        {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = this.googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.google_map_styling));

            if (!success)
            {
                Log.e("MapStyleParsingError", "Style parsing failed.");
            }
        }
        catch (Resources.NotFoundException e)
        {
            Log.e("MapStyleNotFoundError", "Can't find style. Error: ", e);
        }

        this.googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng)
    {
        if(this.mapObjects != null)
        {
            for(MapObject m : this.mapObjects)
            {
                if(m.contains(latLng))
                {
                    if(m.getType() == "Post")
                    {
                        ThreadDisplayDialog dialog = new ThreadDisplayDialog(MainActivity.this, ((PostMapObject) m).getThreadId());
                        dialog.show();
                    }

                    break;
                }
            }
        }
        return;
    }
}
