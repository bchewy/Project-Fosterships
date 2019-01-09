package com.danielappdev.fosterships;


import android.app.usage.EventStats;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.Dataset;
import android.service.notification.NotificationListenerService;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.app.ActionBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;



public class Leaderboards extends AppCompatActivity {
   // TextView textView15;
   // TextView textView16;
   // TextView textView17;

    RecyclerView mLeaderboard;
    FirebaseDatabase mFirebase;
    DatabaseReference mRef;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Leaderboards"); //Initial root reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        //setTitle
        actionBar.setTitle("Leaderboards");

        //RecycleView
        mLeaderboard = findViewById(R.id.recyclerView);
        mLeaderboard.setHasFixedSize(true);

        //set layout as LinearLayout
        mLeaderboard.setLayoutManager(new LinearLayoutManager(this));

        mFirebase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Teams");
    }

    //load data into recycler view when app starts
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Teams> options=
                new FirebaseRecyclerOptions.Builder<Teams>()
                    .setQuery(mRef, Teams.class)
                    .build();

        FirebaseRecyclerAdapter<Teams, LeaderboardViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Teams, LeaderboardViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position, @NonNull Teams model) {
                        holder.setDetails(getApplicationContext(), model.getTeamName(), model.getScore());
                    }

                    @NonNull
                    @Override
                    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_leaderboard_row, viewGroup, false);
                        LeaderboardViewHolder viewHolder = new LeaderboardViewHolder(view);
                        return viewHolder;
                    }
                };

        mLeaderboard.setAdapter(firebaseRecyclerAdapter);
    }

    /*private void SendData() {
        DatabaseReference reference = database.getReference("Leaderboards").push();
        String key = reference.getKey();
        //setup
        DatabaseReference referenceRanking = database.getReference("Leaderboards").child(key).child("Ranking");
        DatabaseReference referenceGroupName = database.getReference("Leaderboards").child(key).child("GroupName");
        DatabaseReference referenceScore = database.getReference("Leaderboards").child(key).child("Score");
        referenceRanking.setValue("1");
        referenceGroupName.setValue("Team banana");
        referenceScore.setValue("400");

    }

    private void LoadData(final DatabaseReference reference){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String GroupName = ds.child("GroupName").getValue(String.class);

                        String ranking = ds.child("Ranking").getValue(String.class);
                        String groupName = ds.child("GroupName").getValue(String.class);
                        String score = ds.child("Score").getValue(String.class);
                        Log.d("1", ranking);
                        Log.d("1", groupName);
                        Log.d("1", score);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/
}




