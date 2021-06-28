package com.yt3.intouchapp.comment_view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yt3.intouchapp.R;
import com.yt3.intouchapp.dialogs.OpenProfilePageDialog;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>
{
    private List<Comment> CommentList;
    private OnCommentItemListener mListener;
    private Context context;

    public interface OnCommentItemListener
    {
        void onUpvoteClick(int position);
        void onDownvoteClick(int position);
        void onMoreClick(int position);
    }

    public void setOnCommentItemListener(OnCommentItemListener listener)
    {
        mListener = listener;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userIdBox;
        public TextView commentBox;
        public TextView timeDateBox;
        public TextView pointBox;
        public ImageButton upvoteButton;
        public ImageButton downvoteButton;
        public ImageButton moreButton;

        OnCommentItemListener onClickListener;

        public CommentViewHolder(View itemView, final OnCommentItemListener listener)
        {
            super(itemView);

            // Find widget ids in item_comment.xml
            userIdBox       = (TextView)    itemView.findViewById(R.id.userIdBox);
            commentBox      = (TextView)    itemView.findViewById(R.id.commentBox);
            timeDateBox     = (TextView)    itemView.findViewById(R.id.timeDateBox);
            pointBox        = (TextView)    itemView.findViewById(R.id.pointBox);
            upvoteButton    = (ImageButton) itemView.findViewById(R.id.upvoteButton);
            downvoteButton  = (ImageButton) itemView.findViewById(R.id.downvoteButton);
            moreButton      = (ImageButton) itemView.findViewById((R.id.moreButton));

            upvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION)
                            listener.onUpvoteClick(position);
                    }
                }
            });

            downvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION)
                            listener.onDownvoteClick(position);
                    }
                }
            });

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION)
                            listener.onMoreClick(position);
                    }
                }
            });
        }
    }


    public CommentAdapter(Context context, List<Comment> comments)
    {
        this.context=context;
        CommentList = comments;
    }

    /**
     * Inflates a layout from XML and returns the holder
     */
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View commentView = inflater.inflate(R.layout.item_comment, parent, false);

        // Return a new holder instance
        final CommentViewHolder viewHolder = new CommentViewHolder(commentView, mListener);
        return viewHolder;
    }

    /**
     * Add data into the item using holder
     */
    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder viewHolder, int position)
    {
        // Get the data model based on position
        Comment comment = CommentList.get(position);

        // Set item views - fill in date from list to widgets on screen
        TextView userIdView = viewHolder.userIdBox;
        userIdView.setText(comment.getUserId());

        TextView commentView = viewHolder.commentBox;
        commentView.setText(comment.getComment());

        TextView pointView = viewHolder.pointBox;
        pointView.setText(String.valueOf(comment.getPoints()));

        TextView timeDateView = viewHolder.timeDateBox;
        timeDateView.setText(comment.getTimeDate());

        //opens profile page of unfriend user by clicking on the name
        final String userid=comment.getUserId();
        viewHolder.userIdBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileDialog(userid);
            }
        });
    }

    private void openProfileDialog(String name){
        OpenProfilePageDialog profileDialog=new OpenProfilePageDialog(name);
        profileDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "open profile dialog");
    }

    /**
     * Return the number of comments in the list
     */
    @Override
    public int getItemCount()
    {
        return CommentList.size();
    }

    public Comment getComment(int position)
    {
        return CommentList.get(position);
    }
}
