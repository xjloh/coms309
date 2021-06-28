package com.yt3.intouchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yt3.intouchapp.R;

/**
 * Displays friend's profile
 */
public class FriendProfileActivity extends AppCompatActivity {

    private ImageButton fp_sendMsg;
    private TextView friend_displayName;
    private TextView friend_displayStatus;
    private View friend_statusIcon;
    private TextView friend_bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        friend_displayName =findViewById(R.id.friend_profile_displayName);
        friend_displayStatus =findViewById(R.id.friend_profile_display_status);
        fp_sendMsg=findViewById(R.id.profile_send_message_button);
        friend_statusIcon =findViewById(R.id.friend_online_icon);
        friend_bio=findViewById(R.id.friend_profile_bio);


        Bundle friendInfo=getIntent().getExtras();

        final String friend_id=friendInfo.getString("user_id");
        friend_displayName.setText(friend_id);

        String friend_status=friendInfo.getString("user_status");
        friend_displayStatus.setText(friend_status);
        if(friend_status.equals("offline")){
            friend_statusIcon.setVisibility(View.GONE);
        }

        /**
         * User can message his/her friend on the list
         * This function will launch the message page
         */
        fp_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messagePage=new Intent(FriendProfileActivity.this, MessageActivity.class);
                messagePage.putExtra("friend_id", friend_id);
                startActivity(messagePage);
            }
        });
    }
}
