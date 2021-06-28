package com.yt3.intouchapp.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.activities.BaseAppActivity;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.network_interface.PostThreadRequest;

public class SubmitPostDialog extends BaseDialog
{
    private EditText editTitle;
    private EditText editContent;
    private Button postButton;
    private Button cancelDialogButton;
    private PostThreadRequest submitPostRequest;

    public SubmitPostDialog(@NonNull Activity activity)
    {
        super(activity, R.layout.dialog_submitpost, 0.95f, 0.5f);

        this.selfReference = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        editTitle   = (EditText)findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);
        postButton  = (Button)  findViewById(R.id.postButton);

        submitPostRequest = new PostThreadRequest(this.activity.getApplicationContext());
        submitPostRequest.setListener(new PostThreadRequest.PostThreadListener()
        {
            @Override
            public void onPostResponse(String response)
            {
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                // Post thread to server
                String title    = editTitle.getText().toString();
                String content  = editContent.getText().toString();

                LatLng current_location = AppController.getInstance().getCurrentLatLng();

                submitPostRequest.postThread(title, content, current_location.latitude, current_location.longitude);

                selfReference.dismiss();

                ((BaseAppActivity) activity).onLocationUpdate(AppController.getInstance().getCurrentLocation());
            }
        });

        cancelDialogButton = (Button) findViewById(R.id.cancelDialogButton);
        cancelDialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selfReference.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view)
    {

    }
}
