package com.asparmar6262.doctorplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView noAccount;
    EditText email,loginPassword;
    ProgressBar progressBar;
    Button loginBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        noAccount = findViewById(R.id.noAccount);
        email = findViewById(R.id.email);
        loginPassword = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.progressBar);
        loginBtn = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);

        String GivenEmail,GivenPassword;

        GivenEmail = email.getText().toString().trim();
        GivenPassword = loginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(GivenEmail)){
            Toast.makeText(getApplicationContext(),"please enter Email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(GivenPassword)){
            Toast.makeText(getApplicationContext(),"please enter Password",Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(GivenEmail,GivenPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Failed! Please try later",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}