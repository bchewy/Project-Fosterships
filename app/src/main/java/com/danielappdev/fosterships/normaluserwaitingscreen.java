package com.danielappdev.fosterships;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.provider.Settings.Secure;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class normaluserwaitingscreen extends AppCompatActivity {

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    int eventID;
    ArrayList<String> PlayerList = new ArrayList<String>();
    ArrayList<String> NameList = new ArrayList<String>();
    ArrayList<Team> teamList = new ArrayList<Team>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events"); //Initial root reference
    //TextView tv_timer = findViewById(R.id.tv_timer);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normaluserwaitingscreen);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        ConstraintLayout rlayout = (ConstraintLayout) findViewById(R.id.normaluserswaitingscreenlayout);
        /*rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),authentication.class);
                startActivity(intent);
            }
        });*/

        Button nexttip = (Button) findViewById(R.id.btnnextfact);
        nexttip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvnextfact =" A ball of glass will bounce higher than a ball of rubber.";

                TextView tvnextfact1 = findViewById(R.id.tvfact);
                tvnextfact1.setText(tvnextfact.toString());

            }
        });



        new CountDownTimer(6, 1) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                eventID = mPref.getInt("EventID",0);
                defReference.child(String.valueOf(eventID)).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        TextView tvnextfact1 = findViewById(R.id.tvfact);
                        if(String.valueOf(snapshot.child("gameStatus").getValue()).equals("Ready")){
                            Log.d("???","worksss");
                            cancel();
                            Intent intent = new Intent(getApplicationContext(),authentication.class);
                            startActivity(intent);
                        }
                        else{
                            checkAndroid_ID();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                start();
                
            }
        }.start();
    }


    public void GetPlayers(){
        eventID = mPref.getInt("EventID",0);
        defReference.child(String.valueOf(eventID)).child("Players").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot s1 : snapshot.getChildren()) {
                   PlayerList.add(String.valueOf(s1.getValue()));
                }

                SplitTeams(PlayerList,teamList);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void SplitTeams(ArrayList<String> List,ArrayList<Team> TeamList ){
        Collections.shuffle(List);
        Team NewTeam = new Team();
        Log.d("SplitTeams: ", String.valueOf(List.size()));
        for (int i = 0; i < List.size(); i++){
            Log.d("SplitTeams: ", String.valueOf(List.get(i)));
            if(i == 0){ NewTeam.addMembers(List.get(i));NewTeam.AddSize();}
            if (i == 1){NewTeam.addMembers(List.get(i));NewTeam.AddSize();}
            if (i == 2){NewTeam.addMembers(List.get(i));NewTeam.AddSize();}
            if (i == 3){NewTeam.addMembers(List.get(i));NewTeam.AddSize();break;}
        }
        TeamList.add(NewTeam);
        if (NewTeam.getSize() == 4){
            for (int i = 1; i <= 4; i++){
                List.remove(0);
            }
            SplitTeams(List,TeamList);
        }

        else{
            List.clear();
            GetTeamName(TeamList);
        }
    }

    public void checkAndroid_ID(){
        final String Android_ID = mPref.getString("AndroidID","Default");
        eventID = mPref.getInt("EventID",0);
        defReference.child(String.valueOf(eventID)).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("gameStatus").getValue(String.class).equals("Not sorted")){
                    for (DataSnapshot s1 : snapshot.child("Players").getChildren()) {
                        if(s1.getValue(String.class).equals(Android_ID)){
                            defReference.child(String.valueOf(eventID)).child("gameStatus").setValue("Sorting");
                            GetPlayers();
                        }
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetTeamName(final ArrayList<Team> TeamList){
        eventID = mPref.getInt("EventID",0);
        defReference.child(String.valueOf(eventID)).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                {
                    for (DataSnapshot s1 : snapshot.child("Teams").getChildren()) {
                        NameList.add(s1.child("TeamName").getValue(String.class));

                    }
                }
                initialiseTeam(TeamList,NameList);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });


    }

    public void initialiseTeam(ArrayList<Team> TeamList,ArrayList<String> Names){
        eventID = mPref.getInt("EventID",0);
        for (int i = 0; i < TeamList.size(); i++){
            Team NewTeam = TeamList.get(i);
            NewTeam.setTeamname(Names.get(i));
            for(int x = 0; x < NewTeam.getSize(); x++){
                HashMap<String, Object> NewPlayer = new HashMap<>();
                NewPlayer.put("Role",x+1);
                defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).child("Members").child(String.valueOf(NewTeam.getMembers().get(x))).updateChildren(NewPlayer);
                //defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).child("Members").child(String.valueOf(NewTeam.getMembers().get(x))).updateChildren(NewPlayer);

            }
            NewTeam.GenerateCode();
            HashMap<String, Object> Round = new HashMap<>();
            HashMap<String, Object> Score = new HashMap<>();
            HashMap<String, Object> TeamName = new HashMap<>();
            HashMap<String, Object> TeamAuthCode = new HashMap<>();
            HashMap<String, Object> NoOfAuths = new HashMap<>();
            Round.put("Round",1);
            Score.put("Score",0);
            NoOfAuths.put("NoOfAuths",0);
            TeamAuthCode.put("TeamAuthCode",NewTeam.getAuthCode());
            TeamName.put("TeamName",NewTeam.getTeamname());
            defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).updateChildren(Round);
            defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).updateChildren(Score);
            defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).updateChildren(TeamName);
            defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).updateChildren(NoOfAuths);
            defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).updateChildren(TeamAuthCode);
        }
        defReference.child(String.valueOf(eventID)).child("gameStatus").setValue("Ready");


    }

}
