package com.example.talkie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.talkie.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyAcitivity extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrivacyPolicyAcitivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
