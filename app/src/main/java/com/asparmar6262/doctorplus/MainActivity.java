package com.asparmar6262.doctorplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    FirebaseUser currUser;
    ArrayList<UserInfo> userInfoArrayList;
    String value = "";
    DatabaseReference databaseReference;


    PatientHomeFragment patientHomeFragment = new PatientHomeFragment();
    PatientSearchFragment patientSearchFragment = new PatientSearchFragment();
    PatientNotificationFragment patientNotificationFragment = new PatientNotificationFragment();

    DoctorHomeFragment doctorHomeFragment = new DoctorHomeFragment();
    DoctorSearchFragment doctorSearchFragment = new DoctorSearchFragment();
    DoctorNotificationFragment doctorNotificationFragment = new DoctorNotificationFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();

        String userId = currUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = databaseReference.child(userId).child("role");
        //String parent = String.valueOf(userQuery.getRef());
        //Toast.makeText(this, "parent is :" + parent, Toast.LENGTH_LONG).show();
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = snapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, "value is : " + value + " sindgh=", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                if(value.equals("patient"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, patientHomeFragment).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, doctorHomeFragment).commit();
                return true;

            case R.id.search:
                if(value.equals("patient"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, patientSearchFragment).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, doctorSearchFragment).commit();
                return true;

            case R.id.notification:
                if(value.equals("patient"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, patientNotificationFragment).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, doctorNotificationFragment).commit();
                return true;
        }

        return false;
    }
}