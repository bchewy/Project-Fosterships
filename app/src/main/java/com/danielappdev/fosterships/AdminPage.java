package com.danielappdev.fosterships;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPage extends AppCompatActivity {

    //Instantiate the user interface
    TextView eventNameField;
    TextView eventPplField;
    TextView eventAdminField;
    TextView eventIDField;
    Button btnPlayer1;
    Button btnPlayer2;
    Button btnPlayer3;
    Button btnPlayer4;
    Button btnAdmin;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference
    Integer eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        btnPlayer1 = findViewById(R.id.btnPlayer1);
        btnPlayer2 = findViewById(R.id.btnPlayer2);
        btnPlayer3 = findViewById(R.id.btnPlayer3);
        btnPlayer4 = findViewById(R.id.btnPlayer4);
        btnAdmin = findViewById(R.id.btnAdmin);
        eventIDField = findViewById(R.id.eventIDField);
        eventNameField = findViewById(R.id.eventNameField);
        eventAdminField = findViewById(R.id.eventAdminField);
        eventPplField = findViewById(R.id.eventPplField);
        //eventID=Integer.valueOf(getIntent().getStringExtra("EventID"));
        eventID=getIntent().getIntExtra("<EventID>",0);
        //Log.d("eventid","eventid"+eventID);
        loadData(defReference,eventID);
        btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gamephase.class);
                startActivity(intent);
            }
        });
        btnPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gamephase2.class);
                startActivity(intent);
            }
        });
        btnPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gamephase3.class);
                startActivity(intent);
            }
        });
        btnPlayer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gamephase4.class);
                startActivity(intent);
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gamephaseAdmin.class);
                startActivity(intent);
            }
        });
    }

    private void loadData(final DatabaseReference reference, final Integer eventIDCurrent) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) { //snapshot is the root reference
                for (DataSnapshot ds : snapshot.getChildren()) {
                    int eventID = (ds.child("eventID").getValue(Integer.class));
                    if (eventID == (eventIDCurrent)) {
                        String eventName = ds.child("eventName").getValue(String.class);
                        String eventAdminEmail = ds.child("eventAdminEmail").getValue(String.class);
                        String eventNoOfPpl = ds.child("eventNoOfPpl").getValue(String.class);


Log.d("tag2", Integer.toString(eventID));
Log.d("tag2", eventName);
Log.d("tag2", eventAdminEmail);
Log.d("tag2", eventNoOfPpl);
String text = "EventName:" + eventName + "\n"
+ "Event Code:" + eventID + "\n" +
"Event Admin email" + eventAdminEmail + "\n"
+ "Event Expected Number:" + eventNoOfPpl;

//                    eventIDField.setText(eventID);
//                    eventNameField.setText(eventName);
//                    eventPplField.setText(eventNoOfPpl);
//                    eventAdminField.setText(eventAdminEmail);
                        break;
                    } else {
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
