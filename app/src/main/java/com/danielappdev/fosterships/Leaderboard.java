package com.danielappdev.fosterships;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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



public class Leaderboard extends AppCompatActivity {
    // TextView textView15;
    // TextView textView16;
    // TextView textView17;

    SharedPreferences mPref;

    LinearLayoutManager mLayoutManager;
    RecyclerView mLeaderboard;
    FirebaseDatabase mFirebase;
    DatabaseReference mRef;
    Query sort;
    String EventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        EventID = mPref.getString("EventID","Default");
        //Actionbar
        //ActionBar actionBar = getSupportActionBar();
        //setTitle
        //actionBar.setTitle("Leaderboards");

        //RecycleView
        mLeaderboard = findViewById(R.id.recyclerView);
        mLeaderboard.setHasFixedSize(true);

        //set layout as LinearLayout
        mLeaderboard.setLayoutManager(new LinearLayoutManager(this));

        mFirebase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Events").child("3518").child("Teams");
        sort = mRef.orderByChild("Score");

        //reverse order
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
    }

    //load data into recycler view when app starts
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Teams> options=
                new FirebaseRecyclerOptions.Builder<Teams>()
                        .setQuery(sort, Teams.class)
                        .build();

        FirebaseRecyclerAdapter<Teams, LeaderboardViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Teams, LeaderboardViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position, @NonNull Teams model) {
                        //holder.setDetails(getApplicationContext(), model.getTeamName(), model.getScore());
                        holder.Score.setText(String.valueOf(model.getScore()));
                        holder.TeamName.setText(model.getTeamName());
                    }

                    @NonNull
                    @Override
                    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_leaderboard_row, viewGroup, false);
                        LeaderboardViewHolder viewHolder = new LeaderboardViewHolder(view);
                        return viewHolder;
                    }
                };

        firebaseRecyclerAdapter.startListening();
        mLeaderboard.setAdapter(firebaseRecyclerAdapter);

    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        //View mView;

        TextView TeamName, Score;

        public LeaderboardViewHolder(View itemView) {
            super(itemView);

            TeamName = itemView.findViewById(R.id.TeamName);
            Score = itemView.findViewById(R.id.Score);

            //mView = itemView;
        }

        //set details to recycler view
       /*public void setDetails(Context ctx, String teamname, String score) {
            //Views
            TextView teamname = mView.findViewById(R.id.teamName);
            TextView mScore = mView.findViewById(R.id.score);
            // set data to views
            mTeamName.setText(teamname);
            mScore.setText(score);
        }*/
    }
}