package com.yt3.intouchapp.activities;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yt3.intouchapp.Adapters.Chat;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.dialogs.EditProfilePageDialog;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.utils.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class PersonalProfileActivity extends BaseAppActivity implements EditProfilePageDialog.ProfilePageDialogListener {

    private TextView pUserID;
    private TextView pUserEmail;
    private TextView pBio;
    private TextView pStatus;
    private ImageButton editProfile;
    private ImageView imageView;
    private String url = "http://coms-309-yt-3.misc.iastate.edu:8080/getImage";
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        super.onCreateDrawer();
        imageView = findViewById(R.id.profile_displayPic);

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.ic_email_black);
            }
        });
        updateUserInfo();
        AppController.getInstance().addToRequestQueue(imageRequest);
    }

    /**
     * Gets info from EditProfilePageDialog
     * @param name new name
     * @param email new email
     * @param bio new bio
     */
    @Override
    public void transferInfo(String name, String email, String bio) {
        pUserID.setText(name);
        pUserEmail.setText(email);
        pBio.setText(bio);
    }

    public void setUp(){
        pUserID=findViewById(R.id.profile_displayName);
        pUserEmail=findViewById(R.id.profile_displayEmail);
        pStatus=findViewById(R.id.profile_display_status);
        pBio=findViewById(R.id.personal_profile_bio);

        editProfile=findViewById(R.id.profile_settings_button);

        pUserID.setText(userInfo.getUsername());

        pStatus.setText("Online");

        pUserEmail.setText(userInfo.getEmail());
        pBio.setText(userInfo.getBio());

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void updateUserInfo(){
        JSONObject jsonObject = new JSONObject();
        try {
            // user_id, comment_id,status
            jsonObject.put("username", AppController.getInstance().getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_GET_USERINFO, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            int uid = (int)response.get("uid");
                            String username =(String)response.get("username");
                            String email = (String)response.get("email");
                            String ol = (String)response.get("online");

                            String bio = (String)response.get("bio");

                            userInfo = new UserInfo(uid, username, email,bio,ol);
                        }
                        catch(JSONException e){
                            Log.i("TESK","BAD JSON");
                            e.printStackTrace();
                        }
                        setUp();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage;

                if (networkResponse.statusCode == 404) {
                    errorMessage = "404 Not Found";
                } else if (networkResponse.statusCode == 511) {
                    errorMessage = "511 Network Authentication Needed";
                } else if (networkResponse.statusCode == 502) {
                    errorMessage = "502 Bad Gateway";
                } else {
                    errorMessage = "Undefined Error";
                }
                Log.i("Volley Error", errorMessage);
                error.printStackTrace();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void openDialog(){
        EditProfilePageDialog profileDialog=new EditProfilePageDialog();
        profileDialog.setUserInfo(userInfo);
        profileDialog.show(getSupportFragmentManager(), "open dialog");
    }

    public void onLocationUpdate(Location location) {

    }

}
