package com.example.talkie.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.talkie.R;
import com.example.talkie.adapters.UsersAdapter;
import com.example.talkie.databinding.FragmentChatsBinding;
import com.example.talkie.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }
    FragmentChatsBinding binding ;

    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentChatsBinding.inflate(inflater, container, false);
        database= FirebaseDatabase.getInstance();
        UsersAdapter adapter=new UsersAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot datasnapshot: snapshot.getChildren()){
                    Users users = datasnapshot.getValue(Users.class);
                    assert users != null;
                    users.setUserId(datasnapshot.getKey());
                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                    list.add(users);}
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}
