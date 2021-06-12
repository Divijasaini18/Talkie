package com.example.talkie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.talkie.databinding.ActivitySignUpBinding;
import com.example.talkie.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if(binding.username.getText().toString().isEmpty()){
                    binding.username.setError("Enter correct e-mail.");
                    return;
                }
                if(binding.password.getText().toString().isEmpty()){
                    binding.password.setError("Enter correct password.");
                    return;
                }
                auth.createUserWithEmailAndPassword(binding.username.getText().toString(),binding.password.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Users user= new Users(binding.username.getText().toString(),binding.password.getText().toString());
                            String id= task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(SignUpActivity.this, "User created successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        if(auth.getCurrentUser()!= null)
        {
                Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
        }


    }
}
