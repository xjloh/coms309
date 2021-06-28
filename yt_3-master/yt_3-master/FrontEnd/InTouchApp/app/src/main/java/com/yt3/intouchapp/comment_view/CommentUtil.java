package com.yt3.intouchapp.comment_view;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * Helper Class
 * Parse comment data from a json string into ArrayList of Comment objects
 */
public class CommentUtil
{
    /**
     * Parse comment data from json string to objects
     * @param jsonComments String of comment data in json format
     * @return ArrayList of Comment objects to pass into adapter
     */
    public static ArrayList<Comment> createCommentList(String jsonComments)
    {
        // Use mockito here
        // To pass fake data in jsonString param

        ArrayList<Comment> commentList = new ArrayList<Comment>();

        // Parse json array
        try
        {
            JSONArray commentJsonArray = new JSONArray(jsonComments);
            JSONObject commentJsonObject;

            for (int i = 0; i < commentJsonArray.length(); i++)
            {
                commentJsonObject = commentJsonArray.getJSONObject(i);
                Comment comment = new Comment((String) commentJsonObject.get("userId"),
                                              (String) commentJsonObject.get("comment"),
                                              (String) commentJsonObject.get("dateTime"),
                                              (int) commentJsonObject.get("points"),
                                              (int) commentJsonObject.get("cid"));

                commentList.add(comment);
            }
        }
        catch (Exception e)
        {
            //Log.i("JSON Handling", "Error: class CommentUtil > createCommentList() > Loop to parse jsonComments");
        }

        return commentList;
    }

    public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffMinutes;
    }

    public static String buildCommentTimeStamp(long diffMinutes)
    {
        // Comment posted within an hour ago
        if (diffMinutes < 60)
            return diffMinutes + "m";

        // Comment posted within a day ago
        else if (diffMinutes < 60 * 24)
            return (diffMinutes/60) + "h";

        // Comment posted more than a day ago
        else
            return (diffMinutes/60/24) + "d";
    }


}
