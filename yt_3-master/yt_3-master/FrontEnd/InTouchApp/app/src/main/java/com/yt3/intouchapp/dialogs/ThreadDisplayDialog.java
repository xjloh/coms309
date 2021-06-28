package com.yt3.intouchapp.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yt3.intouchapp.R;
import com.yt3.intouchapp.activities.PostCommentActivity;
import com.yt3.intouchapp.comment_view.Comment;
import com.yt3.intouchapp.comment_view.CommentAdapter;
import com.yt3.intouchapp.network_interface.CommentRequest;
import com.yt3.intouchapp.network_interface.GetThreadRequest;
import com.yt3.intouchapp.network_interface.data.Thread;

import java.util.ArrayList;

public class ThreadDisplayDialog extends BaseDialog
{
    public static final String THREAD_ID = "com.yt3.intouchapp.THREAD_ID";

    private TextView titleBox;
    private TextView contentBox;
    private TextView authorBox;
    private TextView distanceBox;

    private int active_thread_id;
    private Thread active_thread;
    private ArrayList<Comment> thread_comments;

    private GetThreadRequest threadRequest;
    private CommentRequest commentRequest;

    public ThreadDisplayDialog(@NonNull final Activity activity, int thread_id)
    {
        super(activity, R.layout.dialog_threaddisplay, 0.95f, 0.9f);

        this.active_thread_id = thread_id;

        this.threadRequest = new GetThreadRequest(this.activity.getApplicationContext());
        this.threadRequest.setListener(new GetThreadRequest.GetThreadListener()
        {
            @Override
            public void onThreadResponse(Thread response)
            {
                active_thread = response;
                displayThread(active_thread.getTitle(), active_thread.getContent(), active_thread.getAuthor());

                // Start PostCommentActivity when "Add a comment" button is clicked on
                Button addCommentButton = (Button) findViewById((R.id.addCommentButton));
                addCommentButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        selfReference.dismiss();

                        SubmitCommentDialog comment_dialog = new SubmitCommentDialog(activity, active_thread_id);
                        comment_dialog.show();
                    }
                });
            }

            @Override
            public void onCommentsResponse(ArrayList<Comment> commentList)
            {
                thread_comments = commentList;

                if (thread_comments.size() == 0)
                    setEmptyCommentLabel();
                else if (thread_comments.size() > 0)
                    displayComments();
                else
                    Log.i("Debug", "Comment list has a negative value as its size.");
            }
        }).getThread(this.active_thread_id);

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
    }

    /**
     * Display thread content on screen
     * @param title     title String to display
     * @param content   content String to display
     * @param author    author String to display
     */
    public void displayThread(String title, String content, String author)
    {
        // Display thread title
        titleBox = (TextView)findViewById(R.id.titleBox);
        titleBox.setText(title);

        // Display thread content
        contentBox = (TextView)findViewById(R.id.contentBox);
        contentBox.setText(content);

        // Display thread author
        authorBox = (TextView)findViewById(R.id.authorBox);
        authorBox.setText(author);

        // Display thread author
        distanceBox = (TextView)findViewById(R.id.distanceAwayBox);
        distanceBox.setText(author);
    }

    private void setEmptyCommentLabel()
    {
        TextView commentSectionLabel = (TextView) findViewById(R.id.commentSectionLabel);
        commentSectionLabel.setText("There are no comments yet. Be the first to join the conversation!");
    }

    /**
     * Display content comment list in RecyclerView
     */
    private void displayComments()
    {
        // Populate comment List
        final RecyclerView rvComment = (RecyclerView) findViewById(R.id.rvComment);

        // Create adapter to pass in comment data
        final CommentAdapter adapter = new CommentAdapter(getContext(), thread_comments);

        // Attach the adapter to RecyclerView to add data
        rvComment.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        rvComment.addItemDecoration(dividerItemDecoration);

        // Set up click listeners
        adapter.setOnCommentItemListener(new CommentAdapter.OnCommentItemListener()
        {
            @Override
            public void onUpvoteClick(int position)
            {
                Log.i("button click", "upvote");

                int comment_id = (int) adapter.getComment(position).getCommentID();

                commentRequest.upvoteComment(comment_id);
            }

            @Override
            public void onDownvoteClick(int position)
            {
                Log.i("button click", "downvote");

                int comment_id = (int) adapter.getComment(position).getCommentID();

                commentRequest.downvoteComment(comment_id);
            }

            @Override
            public void onMoreClick(int position)
            {
                Log.i("button click", "more");
            }
        });

        // Set layout manager to position the items
        rvComment.setLayoutManager(new LinearLayoutManager(this.activity));
    }

    @Override
    public void onClick(View view)
    {

    }
}
