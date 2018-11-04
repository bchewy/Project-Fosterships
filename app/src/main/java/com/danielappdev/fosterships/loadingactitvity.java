package com.danielappdev.fosterships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class loadingactitvity extends AppCompatActivity {
    private String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingactitvity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Instantiate
        DatabaseReference setEventID = database.getReference("Events").child("eventID");
        setEventID.setValue("Camp Tamara");
        DatabaseReference setEventCode = database.getReference("Events").child("eventCode");
        setEventCode.setValue("#CTamara");
        DatabaseReference setEventName = database.getReference("Events").child("eventName");
        setEventName.setValue("Camp Tamara");
        }
}





