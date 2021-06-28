package com.yt3.intouchapp.network_interface;

import android.content.Context;
import android.util.Log;

import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.network_interface.data.MessagePoint;
import com.yt3.intouchapp.net_utils.PostRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class for PostRequest to get message points from the server
 */
public class MessagePointsRequest
{
    /**
     *     Usage Example:
     *
     *         MessagePointsRequest test = new MessagePointsRequest(getApplicationContext());
     *         test.setListener(new MessagePointsRequest.MessagePointsListener()
     *         {
     *             @Override
     *             public void onResponse(ArrayList<MessagePoint> response)
     *             {
     *                 for (MessagePoint mPoint : response)
     *                 {
     *                     Log.i("Message Point Log", mPoint.getTitle());
     *                 }
     *             }
     *         }).getMessagePoints(93.63192, 42.030800);      // Using hardcoded coordinates
     */

    private Context context;
    private MessagePointsListener listener;

    public MessagePointsRequest(Context context)
    {
        this.context = context;
    }

    public MessagePointsRequest setListener(MessagePointsListener listener)
    {
        this.listener = listener;
        return this;
    }

    public MessagePointsRequest getMessagePoints(double latitude, double longitude)
    {
        PostRequest request = new PostRequest(context);
        request.setURL(Const.URL_GET_MPOINTS).onListener(new PostRequest.VolleyListener() {
            @Override
            public void onResponse(String response)
            {
                Log.i("Success", "Message Points Received");
                listener.onResponse(parseMessagePoints(response));
            }

            @Override
            public void onError(String error)
            {
                Log.i("Error Getting Messages", error);
            }
        }).send(makeJsonBody(latitude, longitude));

        return this;
    }

    private JSONObject makeJsonBody(double latitude, double longitude)
    {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("longitude", String.valueOf(longitude));
        jsonMap.put("latitude", String.valueOf(latitude));

        return new JSONObject(jsonMap);
    }

    private ArrayList<MessagePoint> parseMessagePoints(String data)
    {
        ArrayList<MessagePoint> mList = new ArrayList<MessagePoint>();

        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                MessagePoint mPoint = new MessagePoint();
                mPoint.setLatitude((double)jsonObject.get("latitude"));
                mPoint.setLongitude((double)jsonObject.get("longitude"));
                mPoint.setThreadId((int)jsonObject.get("tid"));
                mPoint.setTitle((String)jsonObject.get("title"));
                mList.add(mPoint);
            }
        } catch (Exception e)
        {
            Log.e("Error", "Faulty JSON : MessagePointsRequest : parseMessagePoints()");
        }

        return mList;
    }

    public interface MessagePointsListener
    {
        public void onResponse(ArrayList<MessagePoint> response);
    }
}
