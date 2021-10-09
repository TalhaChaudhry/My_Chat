package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.mychat.Adapter.ChatAdapter;
import com.example.mychat.Models.MessageModel;
import com.example.mychat.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId=auth.getUid();
        String name=getIntent().getStringExtra("userName");
        String dp=getIntent().getStringExtra("profilePic");
        String receiverId=getIntent().getStringExtra("userId");
        final String senderNode=senderId+receiverId;
        final String receiverNode=receiverId+senderId;
        Picasso.get().load(dp).placeholder(R.drawable.avatar).into(binding.profileImageChat);
        binding.userNameChat.setText(name);
        final ArrayList<MessageModel>messageModels=new ArrayList<>();
        final ChatAdapter adapter=new ChatAdapter(messageModels,this);
        binding.ChatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.ChatRecyclerView.setLayoutManager(layoutManager);

        database.getReference()
                .child("Chats")
                .child(senderNode)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            MessageModel model=snapshot1.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.sendMsgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message= binding.msgBox.getText().toString();
               final MessageModel model=new MessageModel(message,senderId);
                Date date=new Date();
                //dd-MM-yyyy
                SimpleDateFormat sfd = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                sfd.format(new Date());
                model.setTime(String.valueOf(date));
                model.setTimestamp(new Date().getTime());
               binding.msgBox.setText("");
               database.getReference()
                       .child("Chats")
                       .child(senderNode)
                       .push()
                       .setValue(model)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               database.getReference().child("Chats")
                                       .child(receiverNode)
                                       .push()
                                       .setValue(model)
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {

                                           }
                                       });
                           }
                       });
            }
        });


    }
}