package com.danielappdev.fosterships;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.AndroidException;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class loadingactitvity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingactitvity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Rooms table").child("room1").child("room1code");
        myref.setValue("1");

        DatabaseReference my2nd = database.getReference("participants in rooms table").child("1st person pid");
        my2nd.setValue("000001");

        String id = Settings.Secure.getString(getContentResolver(),Secure.ANDROID_ID);
        Toast.makeText(getApplicationContext(), "id"+id, Toast.LENGTH_SHORT).show();
    }
}







