package com.example.talkie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.talkie.adapters.ChatAdapter;
import com.example.talkie.databinding.ActivityNotificationBinding;
import com.example.talkie.models.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class NotificationActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "001";
    ActivityNotificationBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NotificationActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        binding.on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannel();

// Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.talkie)
                                .setContentTitle("Talkie")
                                .setContentText("One message awaiting.")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

            }
        });

    }
    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String userName = getIntent().getStringExtra("userName");
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, userName, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
