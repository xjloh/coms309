package com.yt3.intouchapp.net_utils;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public class RequestUtil
{
    public static String parseVolleyError(VolleyError error)
    {
        NetworkResponse networkResponse = error.networkResponse;
        String errorMessage;

        if (networkResponse.statusCode == 404)
        {
            errorMessage = "404 Not Found";
        }
        else if (networkResponse.statusCode == 511)
        {
            errorMessage = "511 Network Authentication Needed";
        }
        else if (networkResponse.statusCode == 502)
        {
            errorMessage = "502 Bad Gateway";
        }
        else
        {
            errorMessage = "Undefined Error";
        }

        Log.i("Volley Error", errorMessage);
        return errorMessage;
    }
}
