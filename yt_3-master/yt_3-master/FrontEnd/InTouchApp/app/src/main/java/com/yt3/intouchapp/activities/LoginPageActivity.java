package com.yt3.intouchapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
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
 * LoginButton activity
 */
public class LoginPageActivity extends AppCompatActivity
{
    private EditText    Name;
    private EditText    Password;
    private Button      LoginButton;
    private Button      RegisterButton;
    private ProgressBar progressBar;
    private TextView    Error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        //redirectActivity();

        Name        = findViewById(R.id.loginpage_username);
        Password    = findViewById(R.id.loginpage_password);
        LoginButton = findViewById(R.id.loginpage_loginbtn);
        progressBar = findViewById(R.id.progressbar_loading);
        Error       = findViewById(R.id.loginpage_errormsg);
        RegisterButton = findViewById(R.id.loginpage_register_btn);

        progressBar.setVisibility(View.GONE);
        Error.setVisibility(View.GONE);

        /**
         * When user clicks LoginButton button, information will be sent to makeLoginRequest for validation
         * Error msg will disappear
         * progressBar will be displayed
         * makeLoginRequest will be called
         */
        LoginButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Error.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    makeLoginRequest(Name.getText().toString(), Password.getText().toString());
                }
            }
        );

        /**
         * If user doesn't have an account,
         * User clicks on the RegisterButton button and it will bring user to the RegisterButton page
         */
        RegisterButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent registration = new Intent(LoginPageActivity.this, RegistrationPageActivity.class);
                    startActivity(registration);
                }
            }
        );
    }

    /**
     * Validates the username and password entered from user, sends request to server
     * If both username and password are correct, starts MainActivity
     * @param userId User's username
     * @param password User's password
     */
    public void makeLoginRequest(final String userId, String password)
    {
        Log.i("TESK", "makeLogin");
        Log.i("TESK", userId + password);

        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("userId", userId);
        bodyParams.put("password", password);
        JSONObject jsonBody = new JSONObject(bodyParams);

        Log.i("TESK", "url is " + Const.URL_LOGIN);
        Log.i("TESK", jsonBody.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Const.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                Log.i("TESK", "get response");
                Log.i("TESK", response.toString());

                try
                {
                    String loginStatus = (String)response.get("loginStatus");
                    if (loginStatus.equals("succeed"))
                    {
                        // Store session key and userId
                        LocalStorage.setSessionKey(getApplicationContext(), (String)response.get("sessionKey"));
                        AppController.getInstance().setUserId((String)response.get("userId"));

                        // Double-verify
                        AppController.getInstance().setUserId(userId);
                        //AppController.getInstance().updateUserInfo();

                        // Navigate to Map activity
                        Toast.makeText(getApplicationContext(), "Login Succeed!", Toast.LENGTH_LONG).show();

                        LoginSuccess();
                    }
                    else
                    {
                        // Display login failure function
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_LONG).show();
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

                String errorMessage = "";

                if(networkResponse != null)
                {
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
                }

                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * When login is successful, launch MainActivity
     */
    private void LoginSuccess()
    {
        Intent mainPage = new Intent(LoginPageActivity.this, MainActivity.class);
        startActivity(mainPage);
    }

    private void redirectActivity()
    {
        Intent redirectPage = new Intent(LoginPageActivity.this, ThreadDisplayActivity.class);
    }
}
