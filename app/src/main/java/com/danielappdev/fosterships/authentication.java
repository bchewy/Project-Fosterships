package com.danielappdev.fosterships;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class authentication extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("AuthCodes");
    DatabaseReference defReferenceTeams = database.getReference("Teams");
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    EditText authcode;
    Button btnStart;
    String teamName;
    String Android_ID;
    String AuthCode;
    int EventID;
    String TeamName;
    TextView texts;
    TextView Instructions;
    TextView AuthText;
    DatabaseReference EventRef;
    TextView test;
    ImageView img;
    Team CurTeam = new Team();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        EventRef = database.getReference(String.valueOf("Events"));
        //ConstraintLayout CS_Auth = findViewById(R.id.CS_Auth);
        authcode = findViewById(R.id.etAuthCode);
        Instructions = findViewById(R.id.textView11);
        AuthText =  findViewById(R.id.textView13);
        img = findViewById(R.id.imageView);
        btnStart = (Button)findViewById(R.id.btnStart);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        texts = (TextView)findViewById(R.id.textView10);
        //getteamDetails();
        PrepPage();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String authCodeToCheck;
                String pushKey;
                authCodeToCheck = authcode.getText().toString();
                //pushKey = TeamLeaderAuthentication.getActivityInstance().ReturnPushKey();
                CheckAuthKey(EventRef,authCodeToCheck);
            }
        });

    }

    private void CheckAuthKey(final DatabaseReference reference, final String authCodetoCheck) {
        final String Tname = String.valueOf(texts.getText()).replace("You are in","");
        reference.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) { //snapshot is the root reference
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("TeamAuthCode").equals(authCodetoCheck)){
                        //Authcode is the same..
                        int NumOfAuth = ds.child("NumOfAuth").getValue(Integer.class);
                        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("NumOfAuth").setValue(NumOfAuth+1);
                        btnStart.setVisibility(View.INVISIBLE);
                        AuthText.setText("Not everyone has logged in");
                        /*Intent intent = new Intent(getApplicationContext(), gamephase2.class);
                        startActivity(intent);*/
                        break;

                    }
                    else{

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getteamDetails(){

        EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot s1 : dataSnapshot.getChildren()) {

                    if (s1.child("Members").child(Android_ID).exists()) {
                        final String Teams = String.valueOf(s1.child("TeamName").getValue());
                        CurTeam.setTeamname(Teams);
                        //texts.setText("okokoko");

                        break;

                        //String role = String.valueOf(snapshot.child("Members").child(Android_ID).child("role").getValue());

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void PrepPage(){
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot s1 : dataSnapshot.getChildren()) {

                    if (s1.child("Members").child(Android_ID).exists()) {
                        String fruit = String.valueOf(s1.child("TeamName").getValue()).replace("Team ","");
                        int drawableId = getResources().getIdentifier(fruit, "drawable", getPackageName());
                        img.setImageResource(drawableId);
                        //test.setText(String.valueOf(s1.child("Members").child(Android_ID).child("role").getValue()));
                        //texts.setText("okokoko");
                        if(s1.child("Members").child(Android_ID).child("role").getValue(Integer.class) == 1){
                            authcode.setVisibility(View.INVISIBLE);
                            btnStart.setVisibility(View.INVISIBLE);
                            AuthText.setVisibility(View.VISIBLE);
                            AuthText.setText(String.valueOf(s1.child("TeamAuthCode").getValue()));
                            texts.setText("You are in Team " + fruit);
                            Instructions.setText("Leader, Find the rest of your Teammates!");

                        }
                        break;

                        //String role = String.valueOf(snapshot.child("Members").child(Android_ID).child("role").getValue());

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
