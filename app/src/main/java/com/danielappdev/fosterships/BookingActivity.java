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
                Event newEvent = new Event(txtEventName.getText().toString(),txtAdminEmail.getText().toString(),txtNoPpl.getText().toString());
                saveData(newEvent);


            }
        });

    }
    private void saveData(Event e){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Events");
        ref.setValue(e);
    }
}
