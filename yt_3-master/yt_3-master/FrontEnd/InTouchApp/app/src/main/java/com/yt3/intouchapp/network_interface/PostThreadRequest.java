package com.yt3.intouchapp.network_interface;

import android.content.Context;
import android.util.Log;

import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostThreadRequest
{
    private Context context;
    private PostThreadRequest.PostThreadListener listener;

    public PostThreadRequest(Context context)
    {
        this.context = context;
    }

    public PostThreadRequest setListener(PostThreadRequest.PostThreadListener listener)
    {
        this.listener = listener;
        return this;
    }


    public PostThreadRequest postThread(String title, String content, double latitude, double longitude)
    {
        // Post a new thread with given information
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_POST_THREAD).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response)
            {

                Log.i("Success", "Thread Posted!");
                listener.onPostResponse(response);
            }

            @Override
            public void onError(String error)
            {

            }
        }).send(makeRequestBody(title, content, Double.toString(latitude), Double.toString(longitude)));

        return this;
    }

    private JSONObject makeRequestBody(String title, String content, String latitude, String longitude)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("userId", AppController.getInstance().getUserId());
        jsonMap.put("title", title);
        jsonMap.put("content", content);
        jsonMap.put("latitude", latitude);
        jsonMap.put("longitude", longitude);
        return new JSONObject(jsonMap);
    }

    public interface PostThreadListener
    {
        public void onPostResponse(String response);
    }
}
