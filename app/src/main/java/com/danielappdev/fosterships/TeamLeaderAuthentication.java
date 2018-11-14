package com.danielappdev.fosterships;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class TeamLeaderAuthentication extends AppCompatActivity {

    static TeamLeaderAuthentication INSTANCE;
    TextView authCode;
    String authCodeString;
    String pushKey;
    Integer eventID;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReferenceTeams = database.getReference("Teams");
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_leader_authentication);
        authCode = findViewById(R.id.AuthEditCode);
        authCodeString = "#"+getRandomString(5);
        authCode.setText(authCodeString);
        saveData(defReference,authCodeString);
        Intent mIntent = getIntent();
        eventID = mIntent.getIntExtra("EventID", 0);
        CreateTeams(defReferenceTeams,eventID); //take eventid from admin page --> team leader authentication.


        /*
        Try to move the pushkey to gamephase activity so you can check via firebase under same class
        Shared preferneces
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
        editor.commit();
        */

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
    private void saveData(DatabaseReference reference,String authCodeString) {
        reference.push();
        String key = reference.getKey();
        DatabaseReference referenceCode = database.getReference("AuthCodes").child(key).child("Code");
        DatabaseReference referenceName = database.getReference("AuthCodes").child(key).child("Name");
        referenceCode.setValue(authCodeString);
        referenceName.setValue("Team Banana");
    }
    private void CreateTeams(DatabaseReference reference,Integer eventID){
        reference.push();//repeated code
        String key = reference.getKey();
        DatabaseReference referenceName = database.getReference(key).child("Name");
        DatabaseReference referenceEventName = database.getReference(key).child("eventName");
        referenceEventName.setValue(eventID);
        referenceName.setValue("Team Banana");
    }
}
