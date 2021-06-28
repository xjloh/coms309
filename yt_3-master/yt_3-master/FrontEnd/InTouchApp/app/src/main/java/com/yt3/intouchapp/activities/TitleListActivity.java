package com.yt3.intouchapp.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.network_interface.data.MessagePoint;

/**
 * Class is now deprecated
 */
public class TitleListActivity extends BaseAppActivity
{
    private ListView listView;
    private List<titleData> titleDataList;
    private ObjectMapper mapper;
    private List<String> titleList;
    private List<MessagePoint> messagePoints;

    public static final String EXTRA_ID = "com.yt3.intouchapp.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        super.onCreateDrawer();

        mapper = new ObjectMapper();
        titleList = new ArrayList<String>();
        titleDataList = new ArrayList<titleData>();
        messagePoints = new ArrayList<MessagePoint>();

        // Make request
        makeMessagePointsRequest();
    }

    public void parseMessagePoints(JSONArray jsonArray)
    {
        messagePoints.clear();

        try {
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                MessagePoint mPoint = new MessagePoint();
                mPoint.setLatitude((double)jsonObject.get("latitude"));
                mPoint.setLongitude((double)jsonObject.get("longitude"));
                mPoint.setThreadId((int)jsonObject.get("tid"));
                mPoint.setTitle((String)jsonObject.get("title"));
                messagePoints.add(mPoint);
            }
        } catch (Exception e)
        {
            Log.i("TESK", "BAD JSON");
        }

    }

    public void makeMessagePointsRequest()
    {
        // String url = Const.URL_GET_MPOINTS + "/?" + "latitude=" + AppController.getInstance().getLatitude() + "&longitude=" + AppController.getInstance().getLongitude();
        Log.i("Test", "latitude=" + AppController.getInstance().getLatitude() + "&longitude=" + AppController.getInstance().getLongitude());
        String url = Const.URL_GET_MPOINTS + "/?" + "latitude=42.030800&longitude=93.63192";
        JsonArrayRequest titleListReq = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try {

                    JSONObject jsonObject;

                    for (int i = 0; i < response.length(); i++)
                    {
                        jsonObject = response.getJSONObject(i);

                        titleData mData = new titleData();
                        mData.arrayId = i;
                        mData.threadTitle = (String)jsonObject.get("title");
                        mData.threadId = String.valueOf(jsonObject.get("tid"));

                        // Save data
                        titleList.add((String)jsonObject.get("title"));
                        titleDataList.add(mData);
                    }

                } catch (Exception e)
                {
                    Log.i("TESK", "BAD JSON");
                }

                parseMessagePoints(response);
                displayListView();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage;

                if(networkResponse == null)
                {
                    errorMessage = "Null Error Response";
                }
                else if (networkResponse.statusCode == 404)
                {
                    errorMessage = "404 Not Found";
                }
                else if (networkResponse.statusCode == 511)
                {
                    errorMessage = "511 Network Authentication Needed";
                }
                else if (networkResponse.statusCode == 502)
                {
                    errorMessage = "502 Bad Gateway";
                }
                else
                {
                    errorMessage = "Undefined Error";
                }
                Log.i("Volley Error", errorMessage);
            }
        });

        AppController.getInstance().addToRequestQueue(titleListReq);
    }

    @Override
    public void onLocationUpdate(Location location) {

    }

    /**
     * Legacy function
     */
    public void makeTitleListRequest()
    {
        JsonArrayRequest titleListReq = new JsonArrayRequest(Request.Method.GET, Const.URL_GET_TITLES, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                // Parse JSONArray into title list
                try{
                    List<Map<String, String>> data = mapper.readValue(response.toString(), new TypeReference<List<Map<String, String>>>(){});

                    for (int i = 0; i < data.size(); i++)
                    {
                        Map<String, String> title = data.get(i);

                        titleData mData = new titleData();
                        mData.threadTitle = title.get("title");
                        mData.threadId = title.get("threadId");
                        mData.arrayId = i;

                        // Save data
                        titleList.add(title.get("title"));
                        titleDataList.add(mData);
                    }
                } catch  (IOException e)
                {
                    Log.i("TESK", "BAD JSON");
                }

                displayListView();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage;

                if(networkResponse == null)
                {
                    errorMessage = "Null Error Response";
                }
                else if (networkResponse.statusCode == 404)
                {
                    errorMessage = "404 Not Found";
                }
                else if (networkResponse.statusCode == 511)
                {
                    errorMessage = "511 Network Authentication Needed";
                }
                else if (networkResponse.statusCode == 502)
                {
                    errorMessage = "502 Bad Gateway";
                }
                else
                {
                    errorMessage = "Undefined Error";
                }
                Log.i("Volley Error", errorMessage);
            }
        });

        AppController.getInstance().addToRequestQueue(titleListReq);
    }

    public void displayListView()
    {
        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.activity_list_item, android.R.id.text1, titleList);
        listView.setAdapter(adapter);

        // Listen for click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                            {
                                                // Start DisplayThreadActivity to GET thread content
                                                titleData data = titleDataList.get(position);
                                                displayThread(data.threadId);
                                            }
                                        }
        );
    }

    public void displayThread(String threadId)
    {
        Intent intent = new Intent(this, ThreadDisplayActivity.class);
        intent.putExtra(EXTRA_ID, threadId);
        startActivity(intent);
    }

    private class titleData
    {
        public int arrayId;
        public String threadTitle;
        public String threadId;
    }

}
