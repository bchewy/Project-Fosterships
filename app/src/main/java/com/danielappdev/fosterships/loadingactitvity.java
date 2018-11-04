package com.danielappdev.fosterships;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class loadingactitvity extends AppCompatActivity {
    private String android_id;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String TAG = "LoadingActvity";

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



        ConstraintLayout rlayout = (ConstraintLayout) findViewById(R.id.relativeloading);
        //getting ANDROID_ID
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //final String myfinal =android_id;
        //storing android_id in shared preferences
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        mEditor.putString("AndroidID",android_id);
        mEditor.commit();
        String getAndroidID = mPreferences.getString("AndroidID","default");
        Log.d(TAG, getAndroidID);

        //when onclick, move to the selectmenu layout.
        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),selectmenu.class);
                startActivity(intent);
            }
        });

    }




}





