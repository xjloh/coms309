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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    // Send userId, emailAddress, and password for user registration
    public void makeRegisterRequest(String userId, String emailAddress, String password)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("userId", userId);
        bodyParams.put("password", password);
        bodyParams.put("emailAddress", emailAddress);
        JSONObject jsonBody = new JSONObject(bodyParams);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.URL_REGISTER, jsonBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    String registrationStatus = (String)response.get("registrationStatus");

                    if (registrationStatus.equals("succeed"))
                    {
                        // Store session key
                        LocalStorage.setSessionKey(getApplicationContext(), (String)response.get("sessionKey"));
                        // Navigate to Map activity
                    }
                    else
                    {
                        // Display registration failure function
                    }

                } catch (JSONException e) { }                // Call function to notify registration succeed and start map activity
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
