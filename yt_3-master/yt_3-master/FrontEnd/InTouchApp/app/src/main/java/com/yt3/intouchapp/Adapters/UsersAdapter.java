package com.yt3.intouchapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yt3.intouchapp.R;
import com.yt3.intouchapp.activities.FriendProfileActivity;

import java.util.List;

/**
 * For displaying friend's userId, and status in RecyclerView
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    private List<Users> ulist;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
//        public View image;
        public TextView status;
        public ImageView online_status_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.user_single_name);
            status=(TextView)itemView.findViewById(R.id.user_single_status);
            online_status_icon=itemView.findViewById(R.id.user_single_online_icon);
//            image=itemView.findViewById(R.id.user_single_image);

        }
    }

    public UsersAdapter(Context context, List<Users> mylist){
        this.context=context;
        ulist=mylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);

        MyViewHolder vh=new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users user=ulist.get(position);

        holder.name.setText(user.getName());
        holder.status.setText(user.getStatus());
        if(user.getStatus().equals("offline")){
            holder.online_status_icon.setVisibility(View.GONE);
        }
        else if(user.getStatus().equals("online")){
            holder.online_status_icon.setVisibility(View.VISIBLE);
        }

        final String userId=user.getName();
        final String friend_status=user.getStatus();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent=new Intent();
                Bundle friends_info=new Bundle();
                profileIntent.setClass(context, FriendProfileActivity.class);
                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                friends_info.putString("user_id", userId);
                friends_info.putString("user_status", friend_status);
                profileIntent.putExtras(friends_info);
                context.startActivity(profileIntent);
            }
        });

//        holder.image.setTe(user.getImage());

    }

    @Override
    public int getItemCount() {
        return ulist.size();
    }

}
