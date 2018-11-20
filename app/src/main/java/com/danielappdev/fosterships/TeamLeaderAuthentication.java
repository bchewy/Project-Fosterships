package com.danielappdev.fosterships;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
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
    String teamName = "Team Banana";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReferenceTeams = database.getReference("Teams"); //root for teams
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_leader_authentication);
        authCode = findViewById(R.id.AuthEditCode);

        authCodeString = "#"+getRandomString(5); //Generates the authentication code
        authCode.setText(authCodeString);
        Intent mIntent = getIntent();
        eventID = mIntent.getIntExtra("EventID", 0);//Zero means eventID is not parsed in from booking page!
        CreateTeams(teamName,authCodeString,eventID); //take eventid from admin page --> team leader authentication. and create team with authcode
    }



    private void CreateTeams(String teamName,String authCodeString,Integer eventID){
        DatabaseReference reference = database.getReference("Teams").child(teamName);//.push() was here
        //final String key = reference.getKey();

        //Database references
        DatabaseReference referenceCode = database.getReference("Teams").child(teamName).child("TeamAuthCode");
        DatabaseReference rName = database.getReference("Teams").child(teamName).child("TeamName");
        DatabaseReference rID = database.getReference("Teams").child(teamName).child("eventID");
        DatabaseReference rAnswer = database.getReference("Teams").child(teamName).child("phase1Answer");
        referenceCode.setValue(authCodeString); //Creates the authentication code for the team

        //Set values here...
        rID.setValue(eventID);
        rName.setValue("Team Banana");
        rAnswer.setValue("Three Red Mushrooms");

        //Xtra creation methods
        initUserCount(teamName);
    }
    private void initUserCount(String key){
        DatabaseReference rInit = database.getReference("Teams").child(key).child("NoOfAuths");//Initates the number of authenticated team members as 0.
        rInit.setValue(0);
    }


    //Other methods
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
