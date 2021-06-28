package com.yt3.intouchapp.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * For displaying messages
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> mMessageList;

    public MessageAdapter(List<Messages> mMessageList){
        this.mMessageList=mMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageBody;
        public TextView name_bubble;
        public CircleImageView profileImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            //message body, name and profile pic
            messageBody=itemView.findViewById(R.id.message_body_bubble);
            name_bubble=itemView.findViewById(R.id.name_chat_bubble);
            profileImage=itemView.findViewById(R.id.avatar_chat_bubble);

        }
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message_bubble, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Messages text=mMessageList.get(position);
        holder.name_bubble.setText(text.getName());
        if(text.getName().equals(AppController.getInstance().getUserId())){
            holder.messageBody.setBackgroundResource(R.drawable.my_message);
            holder.messageBody.setTextColor(Color.BLACK);
        }
        else{
            holder.messageBody.setBackgroundResource(R.drawable.their_message);
            holder.messageBody.setTextColor(Color.BLACK);
        }
        holder.messageBody.setText(text.getMessages());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
