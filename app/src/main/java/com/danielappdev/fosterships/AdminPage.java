package com.danielappdev.fosterships;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

    Button btnPrepP1;
    Button btnPrepP2;
    Button btnPrepP3;
    Button btnPrepP4;

    Button btnAdmin;
    Button btnEndPrep;
    Button btnLeaderboards;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference
    Integer eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        //Main buttons
        btnPlayer1 = findViewById(R.id.btnPlayer1);
        btnPlayer2 = findViewById(R.id.btnPlayer2);
        btnPlayer3 = findViewById(R.id.btnPlayer3);
        btnPlayer4 = findViewById(R.id.btnPlayer4);
        //Prep buttons
        btnPrepP1 = findViewById(R.id.btnPrepP1);
        btnPrepP2 = findViewById(R.id.btnPrepP2);
        btnPrepP3 = findViewById(R.id.btnPrepP3);
        btnPrepP4 = findViewById(R.id.btnPrepP4);
        //Admin/misc buttons
        btnAdmin = findViewById(R.id.btnAdmin);
        btnEndPrep = findViewById(R.id.btnEndPrep);
        btnLeaderboards = findViewById(R.id.btnLeaderboards);

        //Event fields
        eventIDField = findViewById(R.id.eventIDField);
        eventNameField = findViewById(R.id.eventNameField);
        eventAdminField = findViewById(R.id.eventAdminField);
        eventPplField = findViewById(R.id.eventPplField);

        //Retrieve event from booking activity.
        Intent mIntent = getIntent();
        eventID = mIntent.getIntExtra("EventID", 0);
        //Log.d("eventid","eventid"+eventID);
        loadData(defReference, eventID);

        //Game phase buttons
        btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), gamephase.class);
                startActivity(intent);
            }
        });
        btnPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), gamephase2.class);
                startActivity(intent);
            }
        });
        btnPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), gamephase3.class);
                startActivity(intent);
            }
        });
        btnPlayer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), gamephase4.class);
                startActivity(intent);
            }
        });


        //Prep buttons
        btnPrepP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeamLeaderAuthentication.class);
                startActivity(intent);
            }
        });
        btnPrepP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), authentication.class);
                startActivity(intent);
            }
        });
        btnPrepP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), authentication.class);
                startActivity(intent);
            }
        });
        btnPrepP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), authentication.class);
                startActivity(intent);
            }
        });


        //Misc buttons
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), gamephaseAdmin.class);
                startActivity(intent);
            }
        });
//        btnLeaderboards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Leaderboards.class);
//                startActivity(intent);
//            }
//        });
        btnEndPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                            eventIDField.setText(Integer.toString(eventID));
                            eventNameField.setText(eventName);
                            eventPplField.setText(eventNoOfPpl);
                            eventAdminField.setText(eventAdminEmail);
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
