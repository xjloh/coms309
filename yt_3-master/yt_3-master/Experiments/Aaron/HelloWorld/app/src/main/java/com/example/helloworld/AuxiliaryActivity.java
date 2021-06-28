package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuxiliaryActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxiliary);

        final Button button = findViewById(R.id.switch_screens_button);
        button.setOnClickListener
        (
            new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    // Code here executes on main thread after user presses button
                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        );
    }
}