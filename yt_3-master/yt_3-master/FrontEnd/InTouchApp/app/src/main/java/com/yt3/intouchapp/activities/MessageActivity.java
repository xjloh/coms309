package com.yt3.intouchapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.yt3.intouchapp.Adapters.ChatAdapter;
import com.yt3.intouchapp.LocalStorage;
import com.yt3.intouchapp.Adapters.MessageAdapter;
import com.yt3.intouchapp.Adapters.Messages;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.net_utils.PostRequest;
import com.yt3.intouchapp.network_interface.MessageRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

/**
 * Private chat view
 */
public class MessageActivity extends AppCompatActivity
{
    private String myName= AppController.getInstance().getUserId();
    public static String recentMsg="";

    private ImageButton send_message_button;
    private EditText message;

    private RecyclerView mMessageList;
    private final List<Messages> messagesList=new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get Friend's name from ChatList activity or FriendProfileActivity
        String friend_name=getIntent().getStringExtra("friend_id");
        setTitle(friend_name);
        setStuff();
        //load messages history from server
        loadMessage();
        send_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        doTheAutoRefresh();
    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateChat(12,AppController.getInstance().getUserId(),getIntent().getStringExtra("friend_id"));
                mAdapter.notifyDataSetChanged();
                doTheAutoRefresh();
            }
        }, 5000);
    }

    void setStuff(){

        send_message_button=findViewById(R.id.sendButton);
        message=findViewById(R.id.text_send);
        mMessageList =findViewById(R.id.message_list);
        mLinearLayout=new LinearLayoutManager(this);

        mAdapter=new MessageAdapter(messagesList);
        mMessageList.setHasFixedSize(true);
        mMessageList.setLayoutManager(mLinearLayout);

        mMessageList.setAdapter(mAdapter);

        send_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    /**
     * function to get the converstation between two users,
     */
    public void getChat(String sender,String receiver)
    {
        new MessageRequest(getApplicationContext()).setListener(new MessageRequest.MessageListener()
        {
            @Override
            public void onResponse(String response)
            {
                MessageRequest.parseMessages(messagesList, response);
            }
        }).getChat(sender, receiver);
    }

    public void updateChat(int mid, String sender,String receiver)
    {
        new MessageRequest(getApplicationContext()).setListener(new MessageRequest.MessageListener()
        {
            @Override
            public void onResponse(String response)
            {
                MessageRequest.parseMessages(messagesList, response);
            }
        }).updateChat(String.valueOf(mid), sender, receiver);
    }

    public void loadMessage(){
        getChat(AppController.getInstance().getUserId(),getIntent().getStringExtra("friend_id"));
        mAdapter.notifyDataSetChanged();
    }

    //receives chat input, sends input to server
    private void sendMessage()
    {
        String messageText = message.getText().toString();
        sendMessageToServer(messageText);
        if(!TextUtils.isEmpty(messageText)){
            Messages temp=new Messages(myName, messageText, null);
            messagesList.add(temp);

            mAdapter.notifyDataSetChanged();
            message.getText().clear();
        }
    }

    public void sendMessageToServer(String message)
    {
        new MessageRequest(getApplicationContext()).setListener(new MessageRequest.MessageListener()
        {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(getApplicationContext(), "Message Submitted", Toast.LENGTH_LONG).show();
            }
        }).sendMessage(getIntent().getStringExtra("friend_id"), message);
    }
}
