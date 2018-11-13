package com.danielappdev.fosterships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class TeamLeaderAuthentication extends AppCompatActivity {

    TextView authCode;
    String authCodeString;
    String pushKey;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_leader_authentication);
        authCode = findViewById(R.id.AuthEditCode);
        authCodeString = "Auth Code: #"+getRandomString(5);
        authCode.setText(authCodeString);
        pushKey = saveData(authCodeString);
    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
    private String saveData(String authCodeString) {
        DatabaseReference reference = database.getReference("AuthCodes").push();
        String key = reference.getKey();
        DatabaseReference referenceName = database.getReference("AuthCodes").child(key).child("Code");
        referenceName.setValue(authCodeString.toString());
        return key;
    }
}
