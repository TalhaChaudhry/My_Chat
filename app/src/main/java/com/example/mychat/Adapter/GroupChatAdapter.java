package com.example.mychat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychat.Models.GroupChatModel;
import com.example.mychat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GroupChatAdapter extends RecyclerView.Adapter {
    ArrayList<GroupChatModel> chatModels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;


    public GroupChatAdapter(ArrayList<GroupChatModel> chatModels, Context context) {
        this.chatModels = chatModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==SENDER_VIEW_TYPE){
           View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
           return new SenderViewHolder(view);
       }
       else {
           View view=LayoutInflater.from(context).inflate(R.layout.group_chat_sample_receiver,parent,false);
           return new ReceiverViewholder(view);
       }
    }

    @Override
    public int getItemViewType(int position) {
        if(chatModels.get(position).getId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupChatModel groupChatModel=chatModels.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).sender_msg.setText(groupChatModel.getMessage());
            ((SenderViewHolder)holder).sender_msg_time.setText(groupChatModel.getTime());

        }
        else {
            ((ReceiverViewholder)holder).chat_message.setText(groupChatModel.getMessage());
            ((ReceiverViewholder)holder).chat_message_time.setText(groupChatModel.getTime());
            ((ReceiverViewholder)holder).sender_name.setText(groupChatModel.getSenderName());
        }

    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ReceiverViewholder extends RecyclerView.ViewHolder {
        TextView chat_message, chat_message_time, sender_name;

        public ReceiverViewholder(@NonNull View itemView) {
            super(itemView);
            sender_name = itemView.findViewById(R.id.sender_name);
            chat_message = itemView.findViewById(R.id.group_received_msg);
            chat_message_time=itemView.findViewById(R.id.group_received_msg_time);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sender_msg, sender_msg_time;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sender_msg = itemView.findViewById(R.id.sender_msg);
            sender_msg_time = itemView.findViewById(R.id.sender_msg_time);

        }
    }

}
