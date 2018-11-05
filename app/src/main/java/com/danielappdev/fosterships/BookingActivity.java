package com.danielappdev.fosterships;

import java.util.Random;

import android.app.usage.EventStats;
import android.content.DialogInterface;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends AppCompatActivity {

    Button btnBookEvent;
    TextView txtAdminEmail;
    TextView txtEventName;
    TextView txtNoPpl;
    Button btnCheckDetails;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //Look for the different ui stuff
        btnBookEvent = findViewById(R.id.btnBookEvent);
        btnCheckDetails = findViewById(R.id.btnCheckDetails);
        txtAdminEmail = findViewById(R.id.txtadminEmail);
        txtEventName = findViewById(R.id.txtEventName);
        txtNoPpl = findViewById(R.id.txtNoPpl);

        btnBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event newEvent = new Event(txtEventName.getText().toString(), txtAdminEmail.getText().toString(), txtNoPpl.getText().toString());
                saveData(newEvent,database);
                ShowDialog("Booking has been made. We will follow up with an email shortly!");

            }
        });
        btnCheckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(defReference);
            }
        });

    }
    private void loadData(final DatabaseReference reference){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) { //snapshot is the root reference
                for (DataSnapshot ds : snapshot.getChildren()){
                    //String eventName = snapshot.getValue(String.class);
                    String eventAdminEmail = ds.child("eventAdminEmail").getValue(String.class);
                    String eventNoOfPpl = ds.child("eventNoOfPpl").getValue(String.class);

                    //Log.d("tag1",eventName);
                    Log.d("tag1",eventAdminEmail);
                    Log.d("tag1",eventNoOfPpl);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void saveData(Event e, FirebaseDatabase database) {
        DatabaseReference refEventID = database.getReference("Events").push().child("eventID");
        DatabaseReference referenceNoOfPpl = database.getReference("Events").push().child("eventNoOfPpl");
        DatabaseReference referenceAdminEmail = database.getReference("Events").push().child("eventAdminEmail");
        DatabaseReference reference = database.getReference("Events").push().child("eventID");
        refEventID.setValue(e.getEventID());
        referenceNoOfPpl.setValue(e.getEventExpectedNoOfPpl());
        referenceAdminEmail.setValue(e.getEventAdminEmail());
    }

    private void ShowDialog(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
        alertDialog.setTitle("Alert!!");
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
