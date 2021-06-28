package com.yt3.intouchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yt3.intouchapp.LocalStorage;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.dialogs.ThreadDisplayDialog;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.network_interface.CommentRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostCommentActivity extends AppCompatActivity
{

    private Button postCommentButton;
    private EditText newCommentBox;

    private int threadId;
    private CommentRequest commentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        // Get thread id here
        Intent intent = getIntent();
        threadId = intent.getIntExtra(ThreadDisplayDialog.THREAD_ID, -1);

        commentRequest = new CommentRequest(this.getApplicationContext());
        commentRequest.setListener(new CommentRequest.CommentListener()
        {
            @Override
            public void onComment(String response)
            {
                Toast.makeText(getApplicationContext(), "Comment Submitted", Toast.LENGTH_SHORT);
            }

            @Override
            public void onVote(String response)
            {
                Toast.makeText(getApplicationContext(), "Vote Submitted", Toast.LENGTH_SHORT);
            }
        });

        newCommentBox = (EditText)findViewById(R.id.newCommentBox);

        // listen to button click
        postCommentButton = (Button)findViewById(R.id.postCommentButton);
        postCommentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String comment = newCommentBox.getText().toString();

                if(threadId != -1)
                    commentRequest.postComment(threadId, comment);
            }
        });
    }
}
