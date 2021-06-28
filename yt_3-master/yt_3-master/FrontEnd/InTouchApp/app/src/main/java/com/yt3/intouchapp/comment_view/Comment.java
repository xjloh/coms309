package com.yt3.intouchapp.comment_view;

import android.util.Log;

/**
 * Comment model class.
 * Used in RecyclerView to display comments in threads.
 */

public class Comment
{
    private String userId;
    private String comment;
    private String timeDate;
    private int points;
    private int comment_id;

    public Comment() { }

    public Comment(String userId, String comment, String timeDate, int points, int comment_id)
    {
        this.userId     = userId;
        this.comment    = comment;
        this.points     = points;
        this.comment_id = comment_id;
        setTimeDate(timeDate);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeDate() { return timeDate; }

    public void setTimeDate(String timeStamp)
    {
        this.timeDate = timeStamp.substring(0, 10) + " ";
        this.timeDate += timeStamp.substring(11, 16);
        this.timeDate += " UTC";
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCommentID(int comment_id)
    {
        this.comment_id = comment_id;
    }

    public int getCommentID() {
        return comment_id;
    }
}

