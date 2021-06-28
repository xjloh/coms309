package com.example.intouchapp;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intouchapp.net_utils.AppController;
import com.example.intouchapp.net_utils.WebRequestActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intouchapp.LocalStorage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Initialize layout objects
    Button postButton;
    Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postButton = (Button)findViewById(R.id.postThread);
        getButton = (Button)findViewById(R.id.getTitles);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPostActivity();
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTitleActivity();
            }
        });
    }

    public void openPostActivity()
    {
        Intent intent = new Intent(this, PostThreadActivity.class);
        startActivity(intent);
    }

    public void openTitleActivity()
    {
        Intent intent = new Intent(this, TitleListActivity.class);
        startActivity(intent);
    }
}
