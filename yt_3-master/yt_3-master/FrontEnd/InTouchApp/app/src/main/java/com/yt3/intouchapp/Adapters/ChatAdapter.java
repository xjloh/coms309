package com.yt3.intouchapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yt3.intouchapp.R;
import com.yt3.intouchapp.activities.MessageActivity;
import com.yt3.intouchapp.dialogs.OpenProfilePageDialog;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Chat> chat_list;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView friendID;
        private TextView latestMsg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            friendID=(TextView)itemView.findViewById(R.id.chat_single_name);
            latestMsg=(TextView)itemView.findViewById(R.id.chat_single_message);

        }
    }

    public ChatAdapter(Context context, List<Chat> chatList){
        this.context=context;
        chat_list=chatList;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_chat_layout, parent, false);

        final ChatAdapter.MyViewHolder vh=new ChatAdapter.MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatAdapter.MyViewHolder holder, int position) {
        Chat friend=chat_list.get(position);
        holder.friendID.setText(friend.getName());
        holder.latestMsg.setText(MessageActivity.recentMsg);
        final String friend_name=friend.getName();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatRoomIntent=new Intent();
                chatRoomIntent.setClass(context, MessageActivity.class);
                chatRoomIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                chatRoomIntent.putExtra("friend_id", friend_name);
                context.startActivity(chatRoomIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chat_list.size();
    }

}
