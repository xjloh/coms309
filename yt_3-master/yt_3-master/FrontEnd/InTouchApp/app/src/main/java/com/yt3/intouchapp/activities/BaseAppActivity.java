package com.yt3.intouchapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.gps.LocationService;

public abstract class BaseAppActivity extends AppCompatActivity
{
    private final static int LOCATION_PERMISSIONS_REQUEST_CODE = 1;
    private final static int INTERNET_PERMISSIONS_REQUEST_CODE = 2;

    private static boolean initialized = false;

    private static Intent intent;

    protected static DrawerLayout mDrawerLayout;
    protected static NavigationView mNavigationView;

    private Activity activity;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(!initialized)
        {
            checkPermission("android.permission.ACCESS_FINE_LOCATION", LOCATION_PERMISSIONS_REQUEST_CODE);
            checkPermission("android.permission.INTERNET", INTERNET_PERMISSIONS_REQUEST_CODE);

            this.intent = new Intent(this, LocationService.class);
            startService(this.intent);

            initialized = true;
        }

        this.activity = this;
    }

    protected void onCreateDrawer()
    {
        this.mDrawerLayout   = findViewById(R.id.NavDrawer);
        this.mNavigationView = findViewById(R.id.DrawerNavigationView);

        if(mNavigationView != null)
            setupNavDrawerContent(mNavigationView);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    public abstract void onLocationUpdate(Location location);

    /*
        Basic Permissions checking functions. May be moved to different classes later
     */

    public void checkPermission(String permission, int request_code)
    {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{ permission }, request_code);
        }
        else
        {
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    // This function is called when the user accepts or declines the permission request.
    // Request Code is used to check which permission called this function.
    // This request code is provided when user is prompted for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSIONS_REQUEST_CODE)
        {
            //Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == INTERNET_PERMISSIONS_REQUEST_CODE)
        {
            //Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Internet Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Internet Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Creates Action Bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        return true;
    }

    //Handles action bar menu item presses
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case android.R.id.home:

                if(mDrawerLayout.isDrawerOpen(mNavigationView))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);

            break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void setupNavDrawerContent(NavigationView navView)
    {
        navView.setNavigationItemSelectedListener
        (
            new NavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                {
                    int id = menuItem.getItemId();
                    Intent myIntent;

                    switch(id)
                    {
                        case R.id.menuItem_MainScreen:
                            myIntent = new Intent(activity.getBaseContext(), MainActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menuItem_PostThread:
                            myIntent = new Intent(activity.getBaseContext(), PostThreadActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menuItem_ThreadDisplay:
                            myIntent = new Intent(activity.getBaseContext(), ThreadDisplayActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menuItem_TitleList:
                            myIntent = new Intent(activity.getBaseContext(), TitleListActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menuItem_RandomChat:
                            myIntent = new Intent(activity.getBaseContext(), RandomChatActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menuItem_Inbox:
                            myIntent=new Intent(activity.getBaseContext(), FriendListActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menuItem_Chatlist:
                            myIntent=new Intent(activity.getBaseContext(), ChatListActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        case R.id.menutItem_PersonalProfile:
                            myIntent=new Intent(activity.getBaseContext(), PersonalProfileActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;

                        default:
//                            menuItem.setChecked(true);
                            mDrawerLayout.closeDrawers();
                    }
                    return true;
                }
            }
        );
    }
}
