package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
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
import android.widget.TextView;

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

public class gamephase extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReferenceTeams = database.getReference("Teams");
    DatabaseReference defReference = database.getReference("Events");
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    EditText answerBox;
    Integer eventID;
    Button btnTryGuess;
    ImageView imageView;
    boolean isImageFitToScreen;
    Integer runOnce = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamephase);
        imageView = findViewById(R.id.imageViewgm2);
        answerBox = findViewById(R.id.answerBox);
        //Prep merge
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        btnTryGuess = findViewById(R.id.btnGuess);
        //Get EventID
        Intent mIntent = getIntent();
        eventID = mIntent.getIntExtra("EventID", 0);
        LoadImageFromFirebase();
        new CountDownTimer(2, 1) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                final String TeamName = mPref.getString("TeamName","default");
                defReference.child(String.valueOf(eventID)).child("Teams").child(TeamName).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.child("").getValue(Integer.class).equals(snapshot.child("").getValue(Integer.class))){
                            defReference.child(String.valueOf(eventID)).child("Teams").child(TeamName).child("Round").setValue(snapshot.getValue(Integer.class)+1);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }.start();
     /*  while(runOnce<1){//Only ever runs once because variable is 0 on creation
           if(runOnce<0){
               break;
           }
           else{
               runOnce = LoadImageFromFirebase(runOnce);//Variable +=1 in loadImagefromFirebase method
           }
       }*/


        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckAnswer(defReferenceTeams, answerBox.getText().toString(), eventID);
                //Load with Glide
                LoadImageFromFirebase(); //Secretly only for testing right now!
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                fullScreen();
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }

            }
        });


    }




    // DatabaseReference defReferenceTeams = database.getReference("Teams");
    public void LoadImageFromFirebase() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }


    public Integer LoadImageFromFirebase(Integer runOnce) {
        runOnce+=1;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
        return runOnce;
    }
    //public void CheckAnswer(final DatabaseReference defReference,final String answer,int score)
    {

    }

    //  DatabaseReference defReferenceTeams = database.getReference("Teams");
    //public void CheckAnswer(final DatabaseReference reference, final String answer, final Integer eventIDCurrent)
 /*  public void CheckAnswer(final DatabaseReference defReference,final String answer,int score) {
       defReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot snapshot) {
               Boolean correct = false;
               for (DataSnapshot ds : snapshot.getChildren()) {
                   //int eventID = (ds.child("eventID").getValue(Integer.class));
                   String answerFire = ds.child("phase1Answer").getValue(String.class);
                   if (eventID == (eventIDCurrent) && answerFire.equals(answer)) {
                       correct = true;
                       break;
                   }
               }
               if (!correct) {
                   ShowDialog("Wrong answer.. try again!", "Please try again... want a hint? A hint pops up on your leader's screen every few minutes!");
               } else {
                   ShowDialog("Correct!", "Now you're waiting for your teammates to also input the answer! - To move on to the next phase!");
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }*/






    //Starts a Show Dialog prompt on the screen with title/text!
    private void ShowDialog(String title, String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(gamephase.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }



}