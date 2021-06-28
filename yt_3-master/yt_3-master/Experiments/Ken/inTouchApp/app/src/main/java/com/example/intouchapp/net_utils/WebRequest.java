package com.example.intouchapp.net_utils;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.*;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class WebRequest {

    private String url;
    private int method;
    private String sessionKey;
    private VolleyListener volleyListener;

    public WebRequest setURL(String url)
    {
        this.url = url;
        return this;
    }

    public WebRequest setMethod(int method)
    {
        this.method = method;
        return this;
    }

    public WebRequest setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
        return this;
    }

    public WebRequest post(JSONObject jsonBody) {

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                String d = response.toString();
                Log.i("Test", d);
                volleyListener.onReceive(null);
            }
        }, new Response.ErrorListener()
        {
            //Log.i("Test", "zz");
            @Override
            public void onErrorResponse(VolleyError error)
            {
                NetworkResponse networkResponse = error.networkResponse;
                String errorResponse;

                if (networkResponse.statusCode == 404)
                {
                    errorResponse = "404 Not Found";
                }
                else if (networkResponse.statusCode == 511)
                {
                    errorResponse = "511 Network Authentication Required";
                }
                else if (networkResponse.statusCode == 502)
                {
                    errorResponse = "502 Bad Gateway";
                }
                else
                {
                    // networkResponse == null
                    errorResponse = "N/A";
                }
                volleyListener.onFail(errorResponse);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                final Map<String, String> headers = new HashMap<>();

                // Add session key if exist
                if (!sessionKey.isEmpty())
                {
                    headers.put("Authorization", "Basic " + sessionKey);
                }

                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return this;
    }

    public WebRequest get()
    {
        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Map<String, String> bodyParams = new HashMap<String, String>();
                bodyParams.put("userId", s);
                JSONObject jsonBody = new JSONObject(bodyParams);
                volleyListener.onReceive(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyListener.onFail("Failed");
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest);
        return this;
    }


    public WebRequest onListener(VolleyListener volleyListener)
    {
        this.volleyListener = volleyListener;
        return this;
    }

    public interface VolleyListener
    {
        public void onReceive(String data);
        public void onFail(String errorText);
    }
}
