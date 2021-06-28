package com.yt3.intouchapp.net_utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yt3.intouchapp.LocalStorage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostRequest
{
    private Context context;
    private String url;
    private VolleyListener volleyListener;
    private JSONObject body = new JSONObject();

    public PostRequest(Context context)
    {
        this.context = context;
    }

    public PostRequest setURL(String url)
    {
        this.url = url;
        return this;
    }

    public PostRequest onListener(VolleyListener volleyListener)
    {
        this.volleyListener = volleyListener;
        return this;
    }

    public PostRequest send(final JSONObject requestBody)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                volleyListener.onResponse(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                volleyListener.onError(RequestUtil.parseVolleyError(error));
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                final Map<String, String> headers = new HashMap<>();

                // Add session key if exist
                String sessionKey = LocalStorage.getSessionKey(context);
                if (!sessionKey.isEmpty())
                    headers.put("Authorization", sessionKey);
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError
            {
                return requestBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };

        AppController.getInstance().addToRequestQueue(postRequest);
        return this;
    }

    public interface VolleyListener
    {
        public void onResponse(String response);
        public void onError(String error);
    }
}