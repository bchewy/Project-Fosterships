package com.danielappdev.fosterships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loadingactitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingactitvity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Rooms").child("room1").child("room1code");
        myRef.setValue("1");
        DatabaseReference my2Ref = database.getReference("Rooms").child("room2").child("room2code");
        my2Ref.setValue("2");

        DatabaseReference my3Ref = database.getReference("Participants inRooms").child("1st person PID");
        my3Ref.setValue("2");

    }
}





