package com.danielappdev.fosterships;

//import java.util.Random;

//import android.app.usage.EventStats;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.app.AlertDialog;
//import android.widget.Button;
import android.widget.TextView;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;


public class Leaderboards extends AppCompatActivity {


    TextView textView15;
    TextView textView16;
    TextView textView17;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference LB = database.getReference("Leaderboards"); //Initial root reference
    Integer LeaderboardID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
    }

    private Integer LeaderboardsData(Event e, FirebaseDatabase database) {
        DatabaseReference reference = database.getReference("Leaderboards").push();
        String key = reference.getKey();
        //setup
        DatabaseReference referenceRanking = database.getReference("Leaderboards").child(key).child("Ranking");
        DatabaseReference referenceGroupName = database.getReference("Leaderboards").child(key).child("GroupName");
        DatabaseReference referenceScore = database.getReference("Leaderboards").child(key).child("Score");
        referenceRanking.setValue(e.getRanking());
        referenceGroupName.setValue(e.getGroupName());
        referenceScore.setValue(e.getScore());
        return e.LeaderboardID();
    }
}
