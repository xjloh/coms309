package com.yt3.intouchapp.network_interface;

import android.content.Context;
import android.util.Log;

import com.yt3.intouchapp.Adapters.Users;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendListRequest
{
    private Context context;
    private FriendListRequest.FriendListListener listener;

    public FriendListRequest(Context context)
    {
        this.context = context;
    }

    public FriendListRequest setListener(FriendListRequest.FriendListListener listener)
    {
        this.listener = listener;
        return this;
    }

    public FriendListRequest getInfo()
    {
        // Post a new thread with given information
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_GET_FRIEND).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response)
            {
                Log.i("Success", "Received Friend List Info");
                listener.onResponse(parseUsers(response));
            }

            @Override
            public void onError(String error)
            {

            }
        }).send(makeRequestBody());

        return this;
    }

    private JSONObject makeRequestBody()
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("username", AppController.getInstance().getUserId());
        return new JSONObject(jsonMap);
    }

    private ArrayList<Users> parseUsers(String data)
    {
        ArrayList<Users> uList = new ArrayList<Users>();

        try{
            JSONArray userArray = new JSONArray(data);
            JSONObject jsonObj;

            for(int i = 0 ; i < data.length(); i++)
            {
                jsonObj = userArray.getJSONObject(i);
                Users u = new Users();
                u.setName(jsonObj.getString("name"));
                boolean temp = jsonObj.getBoolean("online");

                if(temp)
                    u.setStatus("online");
                else
                    u.setStatus("offline");

                uList.add(u);
            }
        }
        catch(JSONException e)
        {
            Log.i("Error","FriendListRequest : parseUsers() JSON Exception");
            e.printStackTrace();
        }

        return uList;
    }

    public interface FriendListListener
    {
        public void onResponse(ArrayList<Users> userList);
    }
}
