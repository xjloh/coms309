package com.yt3.intouchapp.network_interface;

import android.content.Context;
import android.util.Log;

import com.yt3.intouchapp.Adapters.Messages;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRequest
{
    private Context context;
    private MessageRequest.MessageListener listener;

    public MessageRequest(Context context)
    {
        this.context = context;
    }

    public MessageRequest setListener(MessageRequest.MessageListener listener)
    {
        this.listener = listener;
        return this;
    }

    public MessageRequest getChat(String sender, String receiver)
    {
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_GET_CHAT).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response)
            {
                Log.i("Success", "MessageRequest : getChat()");
                listener.onResponse(response);
            }
            @Override
            public void onError(String error)
            {

            }
        }).send(makeGetChatRequestBody(sender, receiver));

        return this;
    }

    public MessageRequest updateChat(String mid, String sender, String receiver)
    {
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_UPDATE_CHAT).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response)
            {
                Log.i("Success", "MessageRequest : updateChat()");
                listener.onResponse(response);
            }
            @Override
            public void onError(String error)
            {

            }
        }).send(makeUpdateChatRequestBody(mid, sender, receiver));

        return this;
    }

    public MessageRequest sendMessage(String message, String friendId)
    {
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_POST_MESSAGE).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response)
            {
                Log.i("Success", "MessageRequest : sendMessage()");
                listener.onResponse(response);
            }
            @Override
            public void onError(String error)
            {

            }
        }).send(makeSendMessageRequestBody(message, friendId));

        return this;
    }

    public JSONObject makeGetChatRequestBody(String sender, String receiver)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("sender", sender);
        jsonMap.put("receiver", receiver);
        return new JSONObject(jsonMap);
    }

    public JSONObject makeUpdateChatRequestBody(String mid, String sender, String receiver)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("mid", mid);
        jsonMap.put("sender", sender);
        jsonMap.put("receiver", receiver);
        return new JSONObject(jsonMap);
    }

    private JSONObject makeSendMessageRequestBody(String message, String friendId)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("fromUser", AppController.getInstance().getUserId());
        jsonMap.put("toUser", friendId);
        jsonMap.put("message", message);
        return new JSONObject(jsonMap);
    }

    private JSONObject makeRequestBody(String title, String content, String latitude, String longitude)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("userId", AppController.getInstance().getUserId());
        jsonMap.put("title", title);
        jsonMap.put("content", content);
        jsonMap.put("latitude", latitude);
        jsonMap.put("longitude", longitude);
        return new JSONObject(jsonMap);
    }

    public interface MessageListener
    {
        public void onResponse(String response);
    }

    /**
     * Static Parse json array data into arraylist of Messages
     * @param messagesString messages string from server
     * @param mList arraylist to add message objects into
     */
    public static void parseMessages(List<Messages> mList, String messagesString)
    {
        try
        {
            JSONArray messageArray = new JSONArray(messagesString);
            JSONObject jsonObj;

            for (int i = 0; i < messageArray.length(); i++)
            {
                jsonObj = messageArray.getJSONObject(i);

                Messages m = new Messages();
                m.setMessages((String) jsonObj.get("message"));
                //m.setTime(null);//(Timestamp)jsonObj.get("time"));
                m.setName((String) jsonObj.get("name"));
                m.setMid((Integer)jsonObj.get("mid"));

                mList.add(m);
            }
        }
        catch (JSONException e)
        {
            Log.i("Error", "MessageRequest : parseMessages() : BAD JSON");
            e.printStackTrace();
        }
    }
}
