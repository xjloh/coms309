package com.yt3.intouchapp.network_interface;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yt3.intouchapp.comment_view.Comment;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.network_interface.data.Thread;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class for PostRequest to get thread information and comments from the server
 */
public class GetThreadRequest
{
    private Context context;
    private GetThreadRequest.GetThreadListener listener;

    public GetThreadRequest(Context context)
    {
        this.context = context;
    }

    public GetThreadRequest setListener(GetThreadRequest.GetThreadListener listener)
    {
        this.listener = listener;
        return this;
    }

    public GetThreadRequest getThread(final int threadId)
    {
        // Get given thread information
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_GET_THREAD).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response) {

                Log.i("Success", "Thread Information Received");
                listener.onThreadResponse(parseThread(response));
            }

            @Override
            public void onError(String error)
            {
                Log.i("Thread Request Error", error);
            }
        }).send(makeRequestBody(threadId));

        // Get comments of given thread
        new PostRequest(context)
                .setURL(Const.URL_GET_COMMENT)
                .onListener(new PostRequest.VolleyListener() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Success", "Thread Comments Received");
                        listener.onCommentsResponse(parseComments(response));
                    }

                    @Override
                    public void onError(String error) {

                    }

                }).send(makeRequestBody(threadId));

        return this;
    }

    private JSONObject makeRequestBody(int threadId)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("threadId", Integer.toString(threadId));
        return new JSONObject(jsonMap);
    }

    private Thread parseThread(String data)
    {
        Thread thread = new Thread();

        try
        {
            JSONObject jsonObject = new JSONObject(data);
            thread.setTitle(jsonObject.getString("title"));
            thread.setContent(jsonObject.getString("content"));
            thread.setAuthor(jsonObject.getString("stringId"));
        }
        catch (Exception e)
        {
            Log.e("Error", "Faulty JSON : ThreadRequest : parseThread()");
        }

        return thread;
    }

    private ArrayList<Comment> parseComments(String data)
    {
        ArrayList<Comment> cList = new ArrayList<Comment>();

        try
        {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                Comment comment = new Comment();

                comment.setUserId(jsonObject.getString("author"));
                comment.setComment(jsonObject.getString("content"));
                comment.setTimeDate(String.valueOf(jsonObject.get("createdTime")));
                comment.setPoints(jsonObject.getInt("upvotes"));
                comment.setCommentID(jsonObject.getInt("cid"));

                cList.add(comment);
            }
        } catch (Exception e)
        {
            Log.e("Error", "Faulty JSON : ThreadRequest : parseComments()");
        }

        return cList;
    }

    public interface GetThreadListener
    {
        public void onThreadResponse(Thread response);
        public void onCommentsResponse(ArrayList<Comment> commentList);
    }
}
