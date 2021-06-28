package com.yt3.intouchapp.utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yt3.intouchapp.activities.ThreadDisplayActivity;
import com.yt3.intouchapp.comment_view.Comment;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.net_utils.RequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThreadDisplayUtil
{
    public void makeThreadRequest(final ThreadDisplayActivity callback, String threadId)
    {
        Map<String, String> bodyParams = new HashMap<String, String>();

        String url = Const.URL_GET_THREAD + "/?" + "threadId=" + threadId;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                callback.threadDataListener(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                RequestUtil.parseVolleyError(error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getComments(final ThreadDisplayActivity callback, String threadId)
    {
        String url = Const.URL_GET_COMMENT + "/?" + "pid=" + threadId;
        JsonArrayRequest titleListReq = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                callback.commentDataListener(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                RequestUtil.parseVolleyError(error);
            }
        });

        AppController.getInstance().addToRequestQueue(titleListReq);
    }

    public String getTitleString(JSONObject data)
    {
        String title = null;
        try {
            title = (String) data.get("title");
        }
        catch (JSONException e)
        {}
        return title;
    }

    public String getContentString(JSONObject data)
    {
        String content = null;
        try {
            content = (String) data.get("content");
        }
        catch (JSONException e)
        {}
        return content;
    }

    public ArrayList<Comment> parseCommentData(JSONArray rawCommentData)
    {
        // Exit function if there is no comments in the thread
        if (rawCommentData.length() == 0)
        {
            Log.i("Debug", "This thread has no comments");
            return null;
        }

        // Parse JSONArray into object (Comment) list
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try{

            for (int i = 0; i < rawCommentData.length(); i++)
            {
                JSONObject cr   = rawCommentData.getJSONObject(i);

                String userId   = cr.getString("author");
                String content  = cr.getString("content");
                String timeDate = String.valueOf(cr.get("createdTime"));
                int upvotes     = cr.getInt("upvotes");
                int comment_id  = cr.getInt("cid");

                Comment comment = new Comment(userId, content, timeDate, upvotes, comment_id);
                comments.add(comment);
            }
        }
        catch  (Exception e)
        {
            Log.i("TESK", "BAD JSON");
        }

        return comments;
    }

}
