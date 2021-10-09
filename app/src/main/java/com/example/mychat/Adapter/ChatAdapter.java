package com.example.mychat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychat.Models.MessageModel;
import com.example.mychat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<MessageModel> messageModels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
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
            View view= LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
       // return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).sender_msg.setText(messageModel.getMessage());
            ((SenderViewHolder)holder).sender_msg_time.setText(messageModel.getTime());
        }
        else {
            ((ReceiverViewHolder)holder).receiver_msg.setText(messageModel.getMessage());
            ((ReceiverViewHolder)holder).receiver_Msg_time.setText(messageModel.getTime());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiver_msg, receiver_Msg_time;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiver_msg = itemView.findViewById(R.id.receiver_msg);
            receiver_Msg_time = itemView.findViewById(R.id.receiver_msg_time);
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
