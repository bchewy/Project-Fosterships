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



        new CountDownTimer(2, 1) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                eventID = mPref.getInt("EventID",0);
                defReference.child(String.valueOf(eventID)).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        TextView tvnextfact1 = findViewById(R.id.tvfact);
                        if(String.valueOf(snapshot.child("Status").getValue()).equals("Ready")){
                            Log.d("???","worksss");
                            Intent intent = new Intent(getApplicationContext(),authentication.class);
                            startActivity(intent);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                /*String[] Roles = {"Player"};
                Random r = new Random();
                String playerrole = Roles[r.nextInt(Roles.length)];

                if (playerrole == "Player"){
                    mEditor = mPref.edit();


                    String android_id = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    mEditor.putString("AndroidID",android_id);
                    mEditor.commit();
                    eventID = mPref.getInt("EventID",0);
                    HashMap<String, Object> Member = new HashMap<>();
                    Member.put("Player1",android_id);
                    defReference.child(String.valueOf(eventID)).child("Teams").child("Team Banana").child("Members").updateChildren(Member);
                }

                Intent intent = new Intent(getApplicationContext(),authentication.class);
                startActivity(intent);*/
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
                Collections.shuffle(PlayerList);
                SplitTeams(PlayerList,teamList);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void SplitTeams(ArrayList<String> List,ArrayList<Team> TeamList ){
        Team NewTeam = new Team();
        for (int i = 0; i < List.size(); i++){
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
        else if(NewTeam.getSize() == 0){
            GetTeamName(TeamList);
        }
        else{
            List.clear();
        }
    }

    public void checkAndroid_ID(){
        final String Android_ID = mPref.getString("AndroidID","Default");
        eventID = mPref.getInt("EventID",0);
        defReference.child(String.valueOf(eventID)).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("Status").getValue(String.class).equals("Not sorted")){
                    for (DataSnapshot s1 : snapshot.child("Players").getChildren()) {
                        if(s1.getValue(String.class).equals(Android_ID)){
                            defReference.child(String.valueOf(eventID)).child("Status").setValue("Sorting");
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
    public void GetTeamName(ArrayList<Team> TeamList){
        NameList.add("apple");
        NameList.add("derp");
        initialiseTeam(TeamList,NameList);

    }

    public void initialiseTeam(ArrayList<Team> TeamList,ArrayList<String> Names){
        eventID = mPref.getInt("EventID",0);
        for (int i = 0; i < TeamList.size(); i++){
            Team NewTeam = TeamList.get(i);
            NewTeam.setTeamname("Team "+ Names.get(i));
            for(int x = 1; x == NewTeam.getSize(); x++){
                HashMap<String, Object> NewPlayer = new HashMap<>();
                NewPlayer.put("Role",x);
                defReference.child(String.valueOf(eventID)).child("Teams").child(NewTeam.getTeamname()).child("Members").child(NewTeam.getMembers().get(i)).updateChildren(NewPlayer);
            }
            NewTeam.GenerateCode();
            HashMap<String, Object> Round = new HashMap<>();
            HashMap<String, Object> Score = new HashMap<>();
            HashMap<String, Object> TeamName = new HashMap<>();
            HashMap<String, Object> TeamAuthCode = new HashMap<>();
            HashMap<String, Object> NoOfAuths = new HashMap<>();
            Round.put("Round",i);
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
        defReference.child(String.valueOf(eventID)).child("Status").setValue("Ready");

    }

}
