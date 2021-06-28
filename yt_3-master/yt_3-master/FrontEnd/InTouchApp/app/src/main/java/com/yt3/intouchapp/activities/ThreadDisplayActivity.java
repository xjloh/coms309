package com.yt3.intouchapp.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yt3.intouchapp.comment_view.CommentAdapter;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.comment_view.Comment;
import com.yt3.intouchapp.utils.ThreadDisplayUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ThreadDisplayActivity extends BaseAppActivity
{
    TextView titleBox;
    TextView contentBox;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    private ThreadDisplayUtil tUtil;

    public static final String THREAD_ID = "com.yt3.intouchapp.THREAD_ID";
    private String threadId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_display);
        super.onCreateDrawer();

        Intent intent = getIntent();
        threadId = intent.getStringExtra(TitleListActivity.EXTRA_ID);

        tUtil = new ThreadDisplayUtil();

        makeNetworkRequests();

        // Start PostCommentActivity when "Add a comment" button is clicked on
        Button addCommentButton = (Button) findViewById((R.id.addCommentButton));
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startPostCommentActivity();
            }
        });
    }

    public void setThreadId(String threadId)
    {
        this.threadId = threadId;
    }

    public void makeNetworkRequests()
    {
        tUtil.makeThreadRequest(this, threadId);
        tUtil.getComments(this, threadId);
    }

    public void setUtilObject(ThreadDisplayUtil util)
    {
        this.tUtil = util;
    }

    @Override
    public void onLocationUpdate(Location location) {

    }

    public void startPostCommentActivity()
    {
        Intent intent = new Intent(this, PostCommentActivity.class);
        intent.putExtra(THREAD_ID, threadId);
        startActivity(intent);
    }

    public void threadDataListener(JSONObject response)
    {
        // Parse thread data
        String title = tUtil.getTitleString(response);
        String content = tUtil.getContentString(response);

        // Display data on GUI
        displayThread(title, content);
    }

    public void commentDataListener(JSONArray response)
    {
        comments = tUtil.parseCommentData(response);

        if (comments.size() == 0)
            setEmptyCommentLabel();
        else if (comments.size() > 0)
            displayComments();
        else
            Log.i("Debug", "Comment list has a negative value as its size.");
    }

    /**
     * Display thread content on screen
     * @param title title String to display
     * @param content content String to display
     */
    public void displayThread(String title, String content)
    {
        // Display thread title
        titleBox = (TextView)findViewById(R.id.titleBox);
        titleBox.setText(title);

        // Display thread content
        contentBox = (TextView)findViewById(R.id.contentBox);
        contentBox.setText(content);
    }

    private void setEmptyCommentLabel()
    {
        TextView commentSectionLabel = (TextView) findViewById(R.id.commentSectionLabel);
        commentSectionLabel.setText("There are no comments yet. Be the first to comment");
    }

    /**
     * Display content comment list in RecyclerView
     */
    private void displayComments()
    {
        // Populate comment List
        RecyclerView rvComment = (RecyclerView) findViewById(R.id.rvComment);

        // Initialize comments
        // comments = CommentUtil.createCommentList(jsonString);

        // Create adapter to pass in comment data
        CommentAdapter adapter = new CommentAdapter(ThreadDisplayActivity.this, comments);

        // Attach the adapter to RecyclerView to add data
        rvComment.setAdapter(adapter);

        // Set up click listeners
        adapter.setOnCommentItemListener(new CommentAdapter.OnCommentItemListener()
        {
            @Override
            public void onUpvoteClick(int position)
            {
                // send volley
                Log.i("button click", "upvote");
            }

            @Override
            public void onDownvoteClick(int position)
            {
                Log.i("button click", "downvote");
            }

            @Override
            public void onMoreClick(int position)
            {
                Log.i("button click", "more");
            }
        });

        // Set layout manager to position the items
        rvComment.setLayoutManager(new LinearLayoutManager(this));
    }
}
