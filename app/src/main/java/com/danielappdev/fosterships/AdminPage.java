package com.danielappdev.fosterships;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference
    Integer eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        btnPlayer1 = findViewById(R.id.btnPlayer1);
//        btnPlayer1 = findViewById(R.id.btnPlayer2);
//        btnPlayer1 = findViewById(R.id.btnPlayer3);
//        btnPlayer1 = findViewById(R.id.btnPlayer4);
//        eventIDField = findViewById(R.id.eventIDField);
//        eventNameField = findViewById(R.id.eventNameField);
//        eventAdminField = findViewById(R.id.eventAdminField);
//        eventPplField = findViewById(R.id.eventPplField);

        //Button onclick listeners
        btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gamephase.class);
                startActivity(intent);
            }
        });
    }
}
