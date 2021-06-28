package com.yt3.intouchapp.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.yt3.intouchapp.Adapters.Chat;
import com.yt3.intouchapp.Adapters.ChatAdapter;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.dialogs.OpenProfilePageDialog;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends BaseAppActivity {

    private List<Chat> clist;
    private RecyclerView chat_list;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        super.onCreateDrawer();
        setTitle("Chat");
        clist=new ArrayList<>();
        getInfo(AppController.getInstance().getUserId() );
    }

    private void setStuff(){
        chat_list=findViewById(R.id.chat_list);
        chat_list.setHasFixedSize(true);
        chat_list.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ChatAdapter(ChatListActivity.this, clist);
        chat_list.setAdapter(adapter);
    }

    private void getInfo(String username){


        //NOT SURE HOW TO GET THE USER ID, someone in frontend should know better than me, please change
        String url = Const.URL_GET_INBOX + "/?" + "username=" +AppController.getInstance().getUserId();

        JsonArrayRequest userListReq = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{
                    JSONObject jsonObj;

                    for(int i =0; i < response.length();i++){
                        jsonObj = response.getJSONObject(i);
                        Chat u = new Chat();
                        u.setName((String)jsonObj.get("name"));
                        u.setLatestMsg((String)jsonObj.get("message"));
                        clist.add(u);
                    }
                }
                catch(JSONException e){
                    Log.i("TESK","BAD JSON");
                    e.printStackTrace();
                }

                setStuff();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage;

                if (networkResponse.statusCode == 404) {
                    errorMessage = "404 Not Found";
                } else if (networkResponse.statusCode == 511) {
                    errorMessage = "511 Network Authentication Needed";
                } else if (networkResponse.statusCode == 502) {
                    errorMessage = "502 Bad Gateway";
                } else {
                    errorMessage = "Undefined Error";
                }
                Log.i("Volley Error", errorMessage);
            }
        });

        AppController.getInstance().addToRequestQueue(userListReq);
    }

    public void onLocationUpdate(Location location) {

    }
}

