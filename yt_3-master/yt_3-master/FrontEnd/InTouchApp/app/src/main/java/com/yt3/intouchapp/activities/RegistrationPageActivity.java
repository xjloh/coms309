package com.yt3.intouchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yt3.intouchapp.LocalStorage;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Registration activity
 */
public class RegistrationPageActivity extends AppCompatActivity
{
    private Button regButton;
    private EditText regUsername;
    private EditText regEmail;
    private EditText regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);

        regButton    = findViewById(R.id.registerpage_Donebtn);
        regUsername =  findViewById((R.id.registerpage_username));
        regEmail     = findViewById(R.id.registerpage_email);
        regPassword  = findViewById(R.id.registerpage_password);

        /**
         * When user click on regButton, makeRegisterRequest function is called
         */
        regButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    makeRegisterRequest(regUsername.getText().toString(), regEmail.getText().toString(), regPassword.getText().toString());
                }
            }
        );
    }

    /**
     * Makes register request to server
     * @param userId User's username
     * @param emailAddress User's email address
     * @param password User's password
     */
    public void makeRegisterRequest(String userId, String emailAddress, String password)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("userId", userId);
        bodyParams.put("password", password);
        bodyParams.put("email", emailAddress);
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
                        // Store session key and userId
                        LocalStorage.setSessionKey(getApplicationContext(), (String)response.get("sessionKey"));
                        AppController.getInstance().setUserId((String)response.get("userId"));

                        // Navigate to Map activity
                        Toast.makeText(getApplicationContext(), "Registration Done!", Toast.LENGTH_LONG).show();

                        RegistrationSuccess();
                    }
                    else
                    {
                        // Display registration failure function
                        Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
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

    /**
     * When registration is successful, launch MainActivity
     */
    private void RegistrationSuccess()
    {
        Intent mainPage = new Intent(RegistrationPageActivity.this, MainActivity.class);
        startActivity(mainPage);
    }
}
