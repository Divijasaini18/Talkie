package com.example.talkie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.talkie.adapters.ChatAdapter;
import com.example.talkie.databinding.ActivityChatDetailBinding;
import com.example.talkie.databinding.ActivityGroupChatBinding;
import com.example.talkie.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;
    FirebaseDatabase database ;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId=auth.getUid();
        binding.userName.setText("Group");

        setContentView(binding.getRoot());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<MessageModel> messageModels=new ArrayList<>();
        ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        database.getReference().child("Chat Room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= binding.message.getText().toString();
                final MessageModel model= new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.message.setText("");

                database.getReference().child("Chat Room").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                });
            }
        });
    }
}
