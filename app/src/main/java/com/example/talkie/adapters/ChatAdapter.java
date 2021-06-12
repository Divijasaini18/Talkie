package com.example.talkie.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkie.R;
import com.example.talkie.models.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<MessageModel> messageModels ;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    private Context context;
    private int SENDER_VIEW_TYPE=1;
    private String recId;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MessageModel messageModel= messageModels.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid()+recId;
                                database.getReference().child("chats").child(senderRoom).child(messageModel.getMessageId()).setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).show();
                return false;
            }
        });
        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
        }
        else
        {
            ((ReceiverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());


        }

    }
    public int getItemViewType(int position)
    {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return 2;

        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMsg,receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiver_text);
            receiverTime = itemView.findViewById(R.id.receiver_time);

        }
    }
    public static class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg,senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.sender_text);
            senderTime = itemView.findViewById(R.id.sender_time);

        }
    }
}
