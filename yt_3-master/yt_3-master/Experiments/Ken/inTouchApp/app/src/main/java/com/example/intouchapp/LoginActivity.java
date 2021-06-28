package com.example.intouchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intouchapp.net_utils.AppController;
import com.example.intouchapp.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Send user login credential to server for verification.
    public void makeLoginRequest(String userId, String password)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("userId", userId);
        bodyParams.put("password", password);
        JSONObject jsonBody = new JSONObject(bodyParams);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    String loginStatus = (String)response.get("loginStatus");


                    if (loginStatus.equals("succeed"))
                    {
                        // Store session key
                        LocalStorage.setSessionKey(getApplicationContext(), (String)response.get("sessionKey"));
                        // Navigate to Map activity
                    }
                    else
                    {
                        // Display login failure function
                    }

                } catch (JSONException e) { }

                // Call function to notify login succeed and start map activity
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
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

                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
