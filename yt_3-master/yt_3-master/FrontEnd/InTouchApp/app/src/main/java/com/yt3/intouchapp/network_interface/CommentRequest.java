package com.yt3.intouchapp.network_interface;

import android.content.Context;
import android.util.Log;

import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommentRequest
{
    private Context context;
    private CommentRequest.CommentListener listener;

    public CommentRequest(Context context)
    {
        this.context = context;
    }

    public CommentRequest setListener(CommentRequest.CommentListener listener)
    {
        this.listener = listener;
        return this;
    }

    public CommentRequest postComment(int threadId, String comment)
    {
        // Post a comment to the given thread
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_POST_COMMENT).onListener(new PostRequest.VolleyListener()
        {
            @Override
            public void onResponse(String response)
            {
                Log.i("Success", "Comment Posted");
                listener.onComment(response);
            }

            @Override
            public void onError(String error)
            {

            }
        }).send(makeRequestBody(Integer.toString(threadId), comment));

        return this;
    }

    /**
     * Upvote a given comment
     * @param commentId Id of comment to upvote
     * @return this object
     */
    public CommentRequest upvoteComment(int commentId)
    {
        voteComment(commentId, Const.URL_UP_COMMENT);
        return this;
    }

    /**
     * Downvote a given comment
     * @param commentId Id of comment to downvote
     * @return this object
     */
    public CommentRequest downvoteComment(int commentId)
    {
        voteComment(commentId, Const.URL_DOWN_COMMENT);
        return this;
    }

    private void voteComment(int commentId, String url)
    {
        // Vote a given comment
        PostRequest request = new PostRequest(context);
        request.setURL(url).onListener(new PostRequest.VolleyListener()
        {
            @Override
            public void onResponse(String response)
            {

                Log.i("Success", "Comment Vote Updated");
                listener.onVote(response);
            }

            @Override
            public void onError(String error)
            {

            }
        }).send(makeVoteBody(Integer.toString(commentId)));
    }

    private JSONObject makeRequestBody(String threadId, String comment)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("author", AppController.getInstance().getUserId());
        jsonMap.put("pid", threadId);
        jsonMap.put("comment", comment);
        return new JSONObject(jsonMap);
    }

    private JSONObject makeVoteBody(String commentId)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("author", AppController.getInstance().getUserId());
        jsonMap.put("commentId", commentId);
        return new JSONObject(jsonMap);
    }

    public interface CommentListener
    {
        public void onComment(String response);
        public void onVote(String response);
    }
}
