package com.danielappdev.fosterships;

import java.util.Random;

import android.app.usage.EventStats;
import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        //Load up buttons
        btnBookEvent = findViewById(R.id.btnBookEvent);
        txtAdminEmail = findViewById(R.id.txtadminEmail);
        txtEventName = findViewById(R.id.txtEventName);
        txtNoPpl = findViewById(R.id.txtNoPpl);

        btnBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event newEvent = new Event(txtEventName.getText().toString(), txtAdminEmail.getText().toString(), txtNoPpl.getText().toString());
                int eventID = saveData(newEvent);
                ShowDialog(eventID);
                readData();

            }
        });

    }
    private void readData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    //do something here...
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private int saveData(Event e) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int n =generateEventID();
        DatabaseReference refadmin = database.getReference("Events").child(e.eventName).child("eventAdminEmail");
        refadmin.setValue(e.eventAdminEmail);
        DatabaseReference refnoppl = database.getReference("Events").child(e.eventName).child("eventNoOfPpl");
        refnoppl.setValue(e.eventExpectedNoOfPpl);
        DatabaseReference refEventID = database.getReference("Events").child(e.eventName).child("eventID");
        refEventID.setValue(n);
        return n;

    }
    private void ShowDialog(int text){
        AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Booking has been made!.. We will follow up with an email."+" EventID:"+text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private void ShowDialogRead(String text){
        AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Event name:"+text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private int generateEventID(){
        Random rand = new Random();
        int n = rand.nextInt(10000) + 1;
        return n;
    }
}
