package com.example.intouchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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

public class PostThreadActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editContent;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_thread);

        editTitle = (EditText)findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);
        postButton = (Button)findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Post thread to server
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                makeThreadRequest(getApplicationContext(), title, content);
            }
        });

    }

    public void makeThreadRequest(final Context context, String title, String content)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("title", title);
        bodyParams.put("content", content);

        JSONObject jsonBody = new JSONObject(bodyParams);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.URL_THREAD_REQ, jsonBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                // Parse success
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
