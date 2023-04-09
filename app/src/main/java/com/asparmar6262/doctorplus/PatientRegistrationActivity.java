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

public class PatientRegistrationActivity extends AppCompatActivity {
    EditText email,password;
    Button register;
    TextView hasAccount;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        hasAccount = findViewById(R.id.hasAccount);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        String GivenEmail,GivenPassword;
        GivenEmail = email.getText().toString().trim();
        GivenPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(GivenEmail)){
            Toast.makeText(getApplicationContext(),"Please Enter Email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(GivenPassword)){
            Toast.makeText(getApplicationContext(),"Please enter Password",Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(GivenEmail,GivenPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PatientRegistrationActivity.this,MainActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Registration Failed ! Please try again later",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}