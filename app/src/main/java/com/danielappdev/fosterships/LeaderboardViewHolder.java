package com.danielappdev.fosterships;

import android.app.usage.EventStats;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.Dataset;
import android.service.notification.NotificationListenerService;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public LeaderboardViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
    }

    //set details to recycler view
    public void setDetails(Context ctx, String teamname, String score) {
        //Views
        TextView mTeamName = mView.findViewById(R.id.teamName);
        TextView mScore = mView.findViewById(R.id.score);
        // set data to views
        mTeamName.setText(teamname);
        mScore.setText(score);
    }
}
