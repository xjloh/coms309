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
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.network_interface.CommentRequest;
import com.yt3.intouchapp.network_interface.PostThreadRequest;

public class SubmitCommentDialog extends BaseDialog
{
    private EditText editContent;
    private Button submitButton;
    private Button cancelDialogButton;

    private int thread_id;
    private CommentRequest commentRequest;

    public SubmitCommentDialog(@NonNull Activity activity, int thread_id)
    {
        super(activity, R.layout.dialog_submitcomment, 0.95f, 0.45f);

        this.thread_id = thread_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        editContent   = (EditText)findViewById(R.id.editContent);
        submitButton  = (Button)  findViewById(R.id.submitButton);

        commentRequest = new CommentRequest(getContext());
        commentRequest.setListener(new CommentRequest.CommentListener()
        {
            @Override
            public void onComment(String response)
            {
                Toast.makeText(getContext(), "Comment Submitted", Toast.LENGTH_SHORT);
            }

            @Override
            public void onVote(String response)
            {
                Toast.makeText(getContext(), "Vote Submitted", Toast.LENGTH_SHORT);
            }
        });

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String comment_contents = editContent.getText().toString();

                commentRequest.postComment(thread_id, comment_contents);

                selfReference.dismiss();

                ThreadDisplayDialog thread_dialog = new ThreadDisplayDialog(activity, thread_id);
                thread_dialog.show();
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
