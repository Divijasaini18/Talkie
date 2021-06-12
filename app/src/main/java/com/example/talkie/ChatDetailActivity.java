package com.example.talkie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.LimitExceededException;
import android.view.View;

import com.example.talkie.adapters.ChatAdapter;
import com.example.talkie.databinding.ActivityChatDetailBinding;
import com.example.talkie.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database ;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        String senderId=auth.getUid();
        String receiverId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");
        binding.userName.setText(userName);
        database.getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("profilePic").get();
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileImage);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<MessageModel> messageModels=new ArrayList<>();
        ChatAdapter chatAdapter=new ChatAdapter(messageModels,this,receiverId);
        binding.chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        final String senderRoom=senderId+receiverId;
        final String receiverRoom=receiverId+senderId;
        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    assert model != null;
                    model.setMessageId(snapshot1.getKey());
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
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Timestamp");
                binding.message.setText("");
                database.getReference().child("chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    if(binding.message.getText().toString().isEmpty()){
                        binding.message.setError("Enter some text.");
                        return;
                    }
                        database.getReference().child("chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
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
