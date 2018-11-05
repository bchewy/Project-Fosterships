package com.danielappdev.fosterships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingActivity extends AppCompatActivity {

    Button btnBookEvent;
    TextView txtAdminEmail;
    TextView txtEventName;
    TextView txtNoPpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        btnBookEvent = findViewById(R.id.btnBookEvent);
        txtAdminEmail = findViewById(R.id.txtadminEmail);
        txtEventName = findViewById(R.id.txtEventName);
        txtNoPpl = findViewById(R.id.txtNoPpl);

        btnBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                // saves data into database and prompts a text

                saveData(txtEventName.getText().toString(),txtAdminEmail.getText().toString(),txtNoPpl.getText().toString());


            }
        });

    }
    private void saveData(String eventName,String txtAdminEmail,String txtNoPpl){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference setRoomID = database.getReference("Events").child("eventID");
        setRoomID.setValue("013153"); //is unique id generated
        DatabaseReference setEventName = database.getReference("Events").child("eventName");
        setEventName.setValue(eventName);
        DatabaseReference setAdminEmail = database.getReference("Events").child("adminEmail");
        setAdminEmail.setValue(txtAdminEmail);
        DatabaseReference setNoPpl = database.getReference("Events").child("NoPpl");
        setNoPpl.setValue(txtNoPpl);

    }
}
