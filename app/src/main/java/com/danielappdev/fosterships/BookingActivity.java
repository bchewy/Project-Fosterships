package com.danielappdev.fosterships;

import java.util.Random;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
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
                Event newEvent = new Event(txtEventName.getText().toString(), txtAdminEmail.getText().toString(), txtNoPpl.getText().toString());
                saveData(newEvent);
                ShowDialog();

            }
        });

    }

    private void saveData(Event e) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Random rand = new Random();

        int n = rand.nextInt(10000) + 1;

        DatabaseReference refadmin = database.getReference("Events").child(e.eventName).child("EventAdminEmail");
        refadmin.setValue(e.eventAdminEmail);
        DatabaseReference refnoppl = database.getReference("Events").child(e.eventName).child("EventNoOfPpl");
        refnoppl.setValue(e.eventExpectedNoOfPpl);
        DatabaseReference refEventID = database.getReference("Events").child(e.eventName).child("EventID");
        refEventID.setValue(n);

    }
    private void ShowDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Booking has been made... mother fucker!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
