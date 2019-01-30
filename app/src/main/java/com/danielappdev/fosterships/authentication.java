package com.danielappdev.fosterships;

import android.annotation.SuppressLint;
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
    String Tname;
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
        test = findViewById(R.id.textView5);
        texts = (TextView)findViewById(R.id.textView10);


        PrepPage();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tname = test.getText().toString();
                mEditor = mPref.edit();
                mEditor.putString("TeamName", Tname);
                mEditor.commit();
                String authCodeToCheck;
                String pushKey;
                authCodeToCheck = authcode.getText().toString();
                //pushKey = TeamLeaderAuthentication.getActivityInstance().ReturnPushKey();
                CheckAuthKey(EventRef,authCodeToCheck);
            }
        });
        new CountDownTimer(5, 1) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                CheckTeamAuthNO();

                start();
            }
        }.start();

        }


    private void CheckAuthKey(final DatabaseReference reference, final String authCodetoCheck) {
        final String Teamname = mPref.getString("TeamName", "Default");
        reference.child(String.valueOf(EventID)).child("Teams").child(Teamname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("TeamAuthCode").exists()){
                    if(String.valueOf(snapshot.child("TeamAuthCode").getValue()).equals(authCodetoCheck)){
                        int NumOfAuth =  snapshot.child("NoOfAuths").getValue(Integer.class);
                        EventRef.child(String.valueOf(EventID)).child("Teams").child(Teamname).child("NoOfAuths").setValue(NumOfAuth+1);
                        CheckTeamAuthNO();
                        authcode.setVisibility(View.INVISIBLE);
                        btnStart.setVisibility(View.INVISIBLE);
                        AuthText.setVisibility(View.VISIBLE);
                        AuthText.setText("Not everyone has logged in");
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
                        texts.setText("You are in Team " + fruit);
                        img.setImageResource(drawableId);
                        test.setText("Team " + fruit);

                        //test.setText(String.valueOf(s1.child("Members").child(Android_ID).child("Role").getValue()));

                        if(s1.child("Members").child(Android_ID).child("Role").getValue(Integer.class) == 1){
                            authcode.setVisibility(View.INVISIBLE);
                            btnStart.setVisibility(View.INVISIBLE);
                            AuthText.setVisibility(View.VISIBLE);
                            AuthText.setText(String.valueOf(s1.child("TeamAuthCode").getValue()));
                            Instructions.setText("Leader, Find the rest of your Teammates!");

                        }
                        else{
                            authcode.setVisibility(View.VISIBLE);
                            btnStart.setVisibility(View.VISIBLE);
                            AuthText.setVisibility(View.INVISIBLE);
                            Instructions.setText("Key in your team leaders authentication code");
                        }
                        break;

                        //String role = String.valueOf(snapshot.child("Members").child(Android_ID).child("Role").getValue());

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //test.setText(mPref.getString("TeamName","Default"));
    }
    public void CheckTeamAuthNO(){

        String Teamname = mPref.getString("TeamName", "Default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Teamname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("NoOfAuths").exists()){
                    //test.setText("works");
                    if(snapshot.child("NoOfAuths").getValue(Integer.class).equals(3)){

                       Intent intent = new Intent(getApplicationContext(), gamephase.class);
                       startActivity(intent);
                    }

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
