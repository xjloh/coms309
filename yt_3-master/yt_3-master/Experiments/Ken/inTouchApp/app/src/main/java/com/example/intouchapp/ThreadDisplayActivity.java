package com.example.intouchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intouchapp.net_utils.AppController;
import com.example.intouchapp.net_utils.Const;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadDisplayActivity extends AppCompatActivity {

    private ObjectMapper mapper;
    TextView titleBox;
    TextView contentBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_display);

        mapper = new ObjectMapper();

        Intent intent = getIntent();
        String threadId = intent.getStringExtra(TitleListActivity.EXTRA_ID);

        makeThreadRequest(threadId, getApplicationContext());
    }

    public void makeThreadRequest(String threadId, final Context context)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("threadId", threadId);
        JSONObject jsonBody = new JSONObject(bodyParams);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.URL_THREAD_REQ, jsonBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                // Parse JSONObject and display to screen
                try{
                    //Map<String, String> threadData = mapper.readValue(response.toString(), new TypeReference<Map<String, String>>(){});

                    // Display thread title
                    Log.i("TESK", (String)response.toString());
                    titleBox = (TextView)findViewById(R.id.titleBox);
                    titleBox.setText((String)response.get("title"));

                    // Display thread content
                    contentBox = (TextView)findViewById(R.id.contentBox);
                    contentBox.setText((String)response.get("content"));

                } catch  (JSONException e)
                {
                    Log.i("TESK", "BAD JSON");
                }
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
                Log.i("Volley Error", errorMessage);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                final Map<String, String> headers = new HashMap<>();

                // Add session key if exist
                String sessionKey = LocalStorage.getSessionKey(getApplicationContext());

                if (!sessionKey.isEmpty())
                {
                    headers.put("Authorization", "Basic " + sessionKey);
                }

                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
