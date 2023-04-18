package com.asparmar6262.doctorplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientRegistrationActivity extends AppCompatActivity {
    CircleImageView profilePic;
    EditText email,password,patientName,patientAge;
    Button register;
    TextView hasAccount;
    ProgressBar progressBar;
    Uri imageUri;

    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        hasAccount = findViewById(R.id.hasAccount);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        profilePic = findViewById(R.id.profilePic);
        patientName = findViewById(R.id.patientName);
        patientAge = findViewById(R.id.patientAge);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        //real_time database setup
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Patient");

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
        GivenName = patientName.getText().toString().trim();
        GivenAge = patientAge.getText().toString().trim();
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
                    Intent intent = new Intent(PatientRegistrationActivity.this,MainActivity.class);
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
        patient = new Patient(givenName,givenAge,givenEmail,givenPassword,image);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(patient);
                Toast.makeText(getApplicationContext(), "data added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PatientRegistrationActivity.this, "failed to add Data !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}