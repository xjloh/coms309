package com.yt3.intouchapp.activities;

import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Random;

public class RandomChatActivity extends BaseAppActivity
{

    private WebSocketClient ws;

    private TextView chatView;
    private EditText userMessage;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_chat);
        super.onCreateDrawer();

        chatView = (TextView)findViewById(R.id.chatView);
        userMessage = (EditText)findViewById(R.id.userMessage);
        sendButton = (Button)findViewById(R.id.sendButton);

        connectToChat();
    }

    private void connectToChat()
    {
        String username = AppController.getInstance().getUserId();

        // Temporary hard-coded values
        float latitude = 1.3f;
        float longitude = 1.3f;
        String latStr = "/" + String.valueOf(latitude);
        String longStr = "/" + String.valueOf(longitude);

        Draft drafts[] = {new Draft_6455()};
        String url = Const.URL_CHAT + username + latStr + longStr;

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        ws = new WebSocketClient(uri, (Draft) drafts[0]) {
            @Override
            public void onOpen(ServerHandshake handshake)
            {
                Log.d("Websocket", "Socket connection opened");
            }

            @Override
            public void onMessage(String message)
            {
                Log.d("Websocket", "Server sent -> " + message);
                chatView.append(message + "\n");
            }

            @Override
            public void onClose(int i, String s, boolean b)
            {
                Log.d("Websocket", "Closed " + s);
                chatView.append("Connection lost!");
            }

            @Override
            public void onError(Exception e) {
                Log.d("Websocket", "Error " + e.getMessage());
            }
        };
        ws.connect();

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Get text from userMessage and clear it
                String message = userMessage.getText().toString();
                userMessage.getText().clear();

                // Send message to server
                ws.send(message);
            }
        });
    }

    @Override
    public void onLocationUpdate(Location location) {

    }
}
