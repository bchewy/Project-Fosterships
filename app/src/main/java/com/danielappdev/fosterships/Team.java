package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class Team {
    private String TeamID;
    private String LeaderID;
    private String Player2ID;
    private String Player3ID;
    private String Player4ID;
    private String AuthCode;
    private String Teamname;

    public Team(/*String Name, String AuthCodes*/){
        /*TeamID = String.valueOf(eventID) + "-" + String.valueOf(numofTeams+1);
        LeaderID = leader;
        AuthCode = AuthCodes;
        Teamname = Name;*/
    }

    public String GenerateCode(){
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String random_string = sb1.toString();
        return random_string;
    }

    public String getLeaderID() {
        return LeaderID;
    }

    public String getTeamID() {
        return TeamID;
    }

    public void setAuthCode(String authCode) {
        AuthCode = authCode;
    }

    public void setTeamname(String teamname) {
        if (teamname != null){
            Teamname = teamname;
        }}

    public String getTeamname() {
        return Teamname;
    }

    public String getAuthCode() {
        return AuthCode;
    }
}
