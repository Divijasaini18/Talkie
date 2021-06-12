package com.example.talkie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.talkie.databinding.ActivityHomeBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import static androidx.core.os.HandlerCompat.postDelayed;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Intent intent;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000 );
    }
}
