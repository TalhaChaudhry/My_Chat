package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.mychat.Adapter.GroupChatAdapter;
import com.example.mychat.Models.GroupChatModel;
import com.example.mychat.Models.User;
import com.example.mychat.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;
   // ArrayList<User> AllUsers=new ArrayList<>();
   User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<GroupChatModel> groupChatModels = new ArrayList<>();
        final String senderID = FirebaseAuth.getInstance().getUid();
        binding.groupName.setText("Announcements");
        final GroupChatAdapter adapter = new GroupChatAdapter(groupChatModels, this);
        binding.groupChatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.groupChatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User users = dataSnapshot.getValue(User.class);
                    users.setUserId(dataSnapshot.getKey());
                    if(senderID.equals(users.getUserId())){
                        user=users;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupChatModels.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    GroupChatModel msgModel=dataSnapshot.getValue(GroupChatModel.class);
                    groupChatModels.add(msgModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.groupSendMsgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = binding.groupMsgBox.getText().toString();
                /// use the concept of get data from database and set the data (name of user along the corresponding id )
               // final String senderName=
                final GroupChatModel model = new GroupChatModel(message, senderID);
                model.setTimeStamp(new Date().getTime());
                model.setSenderName(user.getName());
                Date date = new Date();
                //dd-MM-yyyy
                SimpleDateFormat sfd = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                sfd.format(new Date());
                model.setTime(String.valueOf(date));
                model.setSenderName(user.getName());
                binding.groupMsgBox.setText("");
                model.setSenderName(user.getName());
                database.getReference()
                        .child("Group Chat")
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
}