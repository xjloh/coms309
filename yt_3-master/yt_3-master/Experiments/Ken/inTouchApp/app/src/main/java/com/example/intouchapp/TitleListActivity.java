package com.example.intouchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.intouchapp.net_utils.AppController;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class TitleListActivity extends AppCompatActivity {

    private ListView listView;
    private List<titleData> titleDataList;
    private ObjectMapper mapper;
    private List<String> titleList;

    public static final String EXTRA_ID = "com.example.intouchapp.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);

        mapper = new ObjectMapper();
        titleList = new ArrayList<String>();
        titleDataList = new ArrayList<titleData>();

        // Make request
        makeTitleListRequest();
    }

    public void makeTitleListRequest()
    {
        JsonArrayRequest titleListReq = new JsonArrayRequest("http://pastebin.com/raw/Q0x632MB", new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                // Parse JSONArray into
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

                if (networkResponse.statusCode == 404)
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
