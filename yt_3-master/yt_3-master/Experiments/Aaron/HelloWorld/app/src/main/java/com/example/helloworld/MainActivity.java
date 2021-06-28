package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText latitudeBox  = (EditText) findViewById(R.id.latitude_box);
        final EditText longitudeBox = (EditText) findViewById(R.id.longitude_box);

        LocationManager locationManager = (LocationManager)
        getSystemService(Context.LOCATION_SERVICE);

        LocationListener myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                latitudeBox.setText(String.format("%f", latitude));
                longitudeBox.setText(String.format("%f", longitude));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, myLocationListener);

        final Button button = findViewById(R.id.switch_screens_button);
        button.setOnClickListener
        (
            new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    // Code here executes on main thread after user presses button
                    Intent myIntent = new Intent(v.getContext(), AuxiliaryActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        );
    }
}
