package com.asparmar6262.doctorplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegistrationActivity extends AppCompatActivity {
    EditText email,password,doctorName,doctorAge;
    CircleImageView profilePic;
    Button register;
    TextView hasAccount;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    Uri imageUri;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        doctorName = findViewById(R.id.DoctorName);
        doctorAge = findViewById(R.id.DoctorAge);
        hasAccount = findViewById(R.id.hasAccount);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        profilePic = findViewById(R.id.profilePic);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        //real_time database setup
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctor");

        //profile pic work
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this,LoginActivity.class);
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

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"select Picture"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK && requestCode == 1){
            imageUri = data.getData();
            if(imageUri != null){
                profilePic.setImageURI(imageUri);
            }
        }

    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        String GivenEmail,GivenPassword,GivenName,GivenAge,image;
        GivenEmail = email.getText().toString().trim();
        GivenPassword = password.getText().toString().trim();
        GivenName = doctorName.getText().toString().trim();
        GivenAge = doctorAge.getText().toString().trim();
        image = imageUri.toString();


        if(TextUtils.isEmpty(GivenEmail)){
            Toast.makeText(getApplicationContext(),"Please Enter Email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(GivenPassword)){
            Toast.makeText(getApplicationContext(),"Please enter Password",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(GivenName)){
            Toast.makeText(getApplicationContext(),"Please enter your Name",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(GivenAge)){
            Toast.makeText(getApplicationContext(),"Please enter your Age",Toast.LENGTH_SHORT).show();
        }


        mAuth.createUserWithEmailAndPassword(GivenEmail,GivenPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DoctorRegistrationActivity.this,MainActivity.class);
                    startActivity(intent);
                    addDatatoFirebase(GivenName,GivenAge,GivenEmail,GivenPassword,image);
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Registration Failed ! Please try again later",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addDatatoFirebase(String givenName, String givenAge, String givenEmail, String givenPassword, String image) {
        doctor = new Doctor(givenName,givenAge,givenEmail,givenPassword,image);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(doctor);
                Toast.makeText(getApplicationContext(), "data added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "failed to add Data !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}