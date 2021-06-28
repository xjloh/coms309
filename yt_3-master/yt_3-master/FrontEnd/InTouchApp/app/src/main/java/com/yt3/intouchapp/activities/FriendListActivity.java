package com.yt3.intouchapp.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.yt3.intouchapp.Adapters.Users;
import com.yt3.intouchapp.Adapters.UsersAdapter;
import com.yt3.intouchapp.R;
import android.location.Location;
import android.util.Log;

import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.network_interface.FriendListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Friend's list
 * Creates a list of friends through recyclerView
 */
public class FriendListActivity extends BaseAppActivity {

    private RecyclerView userList;
    private List<Users> uList;
    private RecyclerView.Adapter uAdapter;

    private String url=Const.URL_GET_INBOX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        super.onCreateDrawer();
        setTitle("Friends");
        uList=new ArrayList<>();
        getInfo();
    }

    /**
     * function to set stuff i dont really understand
     * have to put it into a function so it can work with getInfo
     */
    private void setStuff()
    {
        userList=findViewById(R.id.users_list);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(this));
        uAdapter=new UsersAdapter(getApplicationContext(),uList);
        userList.setAdapter(uAdapter);
    }

    /**
     * Function to send a webrequest to get a JSONArray containing the inbox view and putting it into a list
     */
    private void getInfo()
    {
        new FriendListRequest(getApplicationContext()).setListener(new FriendListRequest.FriendListListener() {
            @Override
            public void onResponse(ArrayList<Users> userList)
            {
                uList = userList;
                setStuff();
            }
        }).getInfo();
    }

    public void onLocationUpdate(Location location) {
        
    }
}
