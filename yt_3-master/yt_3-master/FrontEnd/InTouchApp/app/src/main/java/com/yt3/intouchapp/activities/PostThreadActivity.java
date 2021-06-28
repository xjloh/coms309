package com.yt3.intouchapp.activities;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.LocalStorage;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostThreadActivity extends BaseAppActivity
{
    private EditText editTitle;
    private EditText editContent;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_thread);
        super.onCreateDrawer();

        editTitle   = (EditText)findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);
        postButton  = (Button)findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Post thread to server
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                makeThreadRequestTest();
                //makeThreadRequest(title, content);git sgit
            }
        });

    }

    @Override
    public void onLocationUpdate(Location location) {

    }

    public void makeThreadRequestTest()
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("title", "exampleTitle");
        bodyParams.put("content", "exampleContent");
        bodyParams.put("userId", "admin");
        bodyParams.put("latitude", AppController.getInstance().getLatitude());
        bodyParams.put("longitude", AppController.getInstance().getLongitude());

        final JSONObject jsonBody = new JSONObject(bodyParams);

        Log.i("TEST", "In makeThreadRequestTest");


            PostRequest request = new PostRequest(getApplicationContext());
            request.setURL(Const.URL_POST_THREAD)
                    .onListener(new PostRequest.VolleyListener() {
                @Override
                public void onResponse(String response) {
                    onSuccess();
                    Log.i("TEST", "Thread submitted");
                }

                @Override
                public void onError(String error) {

                }
            }).send(jsonBody);

    }

    public void onSuccess()
    {
        Toast.makeText(getApplicationContext(), "Thread submitted!!", Toast.LENGTH_LONG).show();
    }

    public void makeThreadRequest(String title, String content)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("title", title);
        bodyParams.put("content", content);
        bodyParams.put("userId", AppController.getInstance().getUserId());
        bodyParams.put("latitude", AppController.getInstance().getLatitude());
        bodyParams.put("longitude", AppController.getInstance().getLongitude());

        final JSONObject jsonBody = new JSONObject(bodyParams);
        Log.i("TESK", jsonBody.toString());

        StringRequest strRequest = new StringRequest(Request.Method.POST, Const.URL_POST_THREAD, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                // Parse success
                Toast.makeText(getApplicationContext(), "Thread submitted!!", Toast.LENGTH_LONG).show();
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
                    headers.put("Authorization", sessionKey);
                }

                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError
            {
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }
}
