package com.danielappdev.fosterships;

import java.util.Random;

import android.app.usage.EventStats;
import android.content.DialogInterface;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        btnCheckDetails.setVisibility(View.GONE);
        btnBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event newEvent = new Event(txtEventName.getText().toString(), txtAdminEmail.getText().toString(), txtNoPpl.getText().toString());
                saveData(newEvent, database);
                if (ValidateData()) {
                    ShowDialog("Booking has been made. We will follow up with an email shortly!");
                    //btnCheckDetails.setVisibility(View.VISIBLE);
                } else {
                    txtEventName.setError("Type in something for all fields...");
                    txtAdminEmail.setError("Type in something for all fields...");
                    txtNoPpl.setError("Is this more than 0?");
                }

            }
        });
        btnCheckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(defReference);
            }
        });

    }

    public static boolean IsNullOrEmpty(String value) {
        if (value != null)
            return value.length() == 0;
        else
            return true;
    }
    public boolean isDigits(String number){
        if(!TextUtils.isEmpty(number)){
            return TextUtils.isDigitsOnly(number);
        }else{
            return false;
        }
    }
    private boolean ValidateData() {
        String eName = txtEventName.getText().toString();
        String ePpl = txtNoPpl.getText().toString();
        String eAdminEmail = txtAdminEmail.getText().toString();
        boolean validated = false;
        boolean validatedNo = false;
        if (!IsNullOrEmpty(eName) && !IsNullOrEmpty(ePpl) && !IsNullOrEmpty(eAdminEmail)) {
            validated = true;
        }
        if (isDigits(ePpl)&&Integer.valueOf(ePpl)>0) {
            validatedNo = true;
        }
        return validated&&validatedNo;
    }

    private void loadData(final DatabaseReference reference) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) { //snapshot is the root reference
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String eventName = ds.child("eventName").getValue(String.class);
                    String eventAdminEmail = ds.child("eventAdminEmail").getValue(String.class);
                    String eventNoOfPpl = ds.child("eventNoOfPpl").getValue(String.class);
                    int eventID = (ds.child("eventID").getValue(Integer.class));

                    Log.d("tag1", Integer.toString(eventID));
                    Log.d("tag1", eventName);
                    Log.d("tag1", eventAdminEmail);
                    Log.d("tag1", eventNoOfPpl);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveData(Event e, FirebaseDatabase database) {
        DatabaseReference reference = database.getReference("Events").push();
        String key = reference.getKey();
        //setup
        DatabaseReference referenceName = database.getReference("Events").child(key).child("eventName");
        DatabaseReference referenceEventID = database.getReference("Events").child(key).child("eventID");
        DatabaseReference referenceNoOfPpl = database.getReference("Events").child(key).child("eventNoOfPpl");
        DatabaseReference referenceAdminEmail = database.getReference("Events").child(key).child("eventAdminEmail");
        referenceName.setValue(e.getEventName());
        referenceEventID.setValue(e.getEventID());
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
