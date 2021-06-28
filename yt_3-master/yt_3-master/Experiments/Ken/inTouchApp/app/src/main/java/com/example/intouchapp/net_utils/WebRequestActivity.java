package com.example.intouchapp.net_utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request.Method;
import com.example.intouchapp.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.location.Location;

import com.example.intouchapp.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class WebRequestActivity {

    public final static String URL_TEST = "http://www.google.com";

    public final static String ROOT_DOMAIN = "OUR_SERVER_IP";
    public final static String URL_REGISTER = ROOT_DOMAIN + "/register";
    public final static String URL_LOGIN = ROOT_DOMAIN + "/login";
    public final static String URL_LOC_UPDATE = ROOT_DOMAIN + "/postCoordinates";
    public final static String URL_THREAD_REQ = ROOT_DOMAIN + "/getThread";
    public final static String URL_GET_TITLES = ROOT_DOMAIN + "/getTitles";
    // Get homepage text
    public static void getHomepageString(final Context appContext)
    {
        StringRequest strReq = new StringRequest(Request.Method.GET, ROOT_DOMAIN, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(appContext, response.substring(0, 20), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(appContext, "Error", Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                return headers;
            }
        };

        String tag_string_req = "Ex1";
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




    /**
     * Attempt to login using session key
     * Called at the start of the application before displaying login page
     */
    public static boolean makeSessionKeyLogin(Context context)
    {
        LocalStorage storage = new LocalStorage();
        String sessionKey = storage.getSessionKey(context);

        if (sessionKey == null)
            return false;

        // login test
        // send request to server and check reply
        // return true is session key is valid, false otherwise
        return true;
    }

    public static void makeLocationUpdate(Location location)
    {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("latitude", latitude.toString());
        bodyParams.put("longitude", longitude.toString());
        JSONObject jsonBody = new JSONObject(bodyParams);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(URL_LOC_UPDATE, jsonBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                // process json response using ObjectMapper
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // handle volley error
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }







}