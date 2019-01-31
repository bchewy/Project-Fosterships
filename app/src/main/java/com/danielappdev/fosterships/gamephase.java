package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import android.widget.Toast;

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

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReferenceTeams = database.getReference("Teams");
    EditText answerBox;
    TextView CheatBox;
    Integer EventID;
    String Android_ID;
    TextView TxtViewname;
    TextView T_Round;
    Button btnTryGuess;
    ImageView imageView;
    DatabaseReference EventRef;
    String Tname;
    Integer Round;
    Integer Round2;
    boolean isImageFitToScreen;
    Integer runOnce = 0;
    Integer makeshiftRound;
    String Hints;
    Integer rx; //don't delete XD
    CountDownTimer Timerz = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        EventID = mPref.getInt("EventID",0);
        Tname = mPref.getString("TeamName", "Default");
        EventRef = database.getReference(String.valueOf("Events"));
        setContentView(R.layout.activity_gamephase);
        imageView = findViewById(R.id.imageViewgm2);
        answerBox = findViewById(R.id.answerBox);
        TxtViewname  = findViewById(R.id.txtTeamname);
        T_Round = findViewById(R.id.TV_Round);
        Log.d("name", getTeam());
        Android_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        btnTryGuess = findViewById(R.id.btnGuess);
        Intent mIntent = getIntent();

        Round1(getTeam());

        Timerz = new CountDownTimer(10, 1) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                cancel();
                Log.d("count", "onFinish: ");
                CheckStatus(getTeam());
                RefreshPage(getTeam());

            }
        }.start();

        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Answer = String.valueOf(answerBox.getText());
                //DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
                Integer gx = Integer.parseInt(String.valueOf(T_Round.getText()));
                Log.d(String.valueOf(gx), "onClick: ");
                CheckAnswer(Answer,gx );

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
/*
Toast.makeText(getActivity(), "This is my Toast message!",
    Toast.LENGTH_LONG).show();
*/


    public String getTeam()
    {
        Tname = mPref.getString("TeamName", "Default");
        return Tname;
    }

    public void getHints(final Integer Round)
    {
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("pictures").child("gamephase"+Round.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Hints =dataSnapshot.child("hints").getValue().toString();
                    ShowDialog("Wrong answer.. try again!", "Please try again... want a hint? "+ Hints);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }




    public void RefreshPage(String Tname){
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        final int Rd = Integer.parseInt(String.valueOf(T_Round.getText()));
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (Rd != snapshot.child("Round").getValue(Integer.class)) {

                    LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("Role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class));
                    T_Round.setText(String.valueOf(snapshot.child("Round").getValue(Integer.class)));
                }
                Timerz.start();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    //initializes round to one then loads image from firebase based on role. 
    //method to load first image from firebase based on android IDs role and round
    public void Round1(String Tname){
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        T_Round.setText("1");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(1);
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load

            @Override

            public void onDataChange(DataSnapshot snapshot) {
                LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("Role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }



    public void getRoundFB(String Tname) {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
      // Log.d("Round", "reached");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   rx = dataSnapshot.child("Round").getValue(Integer.class);
                   //T_Round.setText(Round);
                    Log.d("Round", rx.toString());
                    setRound(rx);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
   }

   public void setRound(final Integer rounnd){
        Round=rounnd;
   }
   public Integer getRound(){
        return Round;
   }

/*
   public void SetTVRound(String Tname)
   {
       Android_ID = mPref.getString("AndroidID","default");
       EventID = mPref.getInt("EventID",0);
       DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
       // Log.d("Round", "reached");
       EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   T_Round.setText(Round);
                //   Log.d("Round", Round.toString());
               }

           }
           @Override
           public void onCancelled(DatabaseError databaseError) {
           }
       });
   }
*/




    public void LoadImageFromFirebase() {
        Log.d("old", "?");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
            .load(storageReference)
                .into(imageView);
}


    public void LoadImageFromFirebase(Integer role, Integer round) {
//        String temprole = role.toString();
       // Log.d("pic", String.valueOf(round));
        //role = 1;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child(round.toString()+"/").child((role.toString())+".jpg");
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }


    // EventRef = database.getReference(String.valueOf("Events"));
//method to check if answer is equal to "gamephase + round" answer
    public void CheckAnswer(final String answer, final Integer Round) {

        EventRef.child(String.valueOf(EventID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean correct = false;

                   // int eventID = (ds.child("eventID").getValue(Integer.class));
                    //string round
                    String FirebaseAnswer = snapshot.child("pictures").child("gamephase"+Round.toString()).child("answers").getValue(String.class);
                    if  (FirebaseAnswer.equals(answer)) {
                        //increment score.
                       EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Score").setValue(snapshot.child("Teams").child(Tname).child("Score").getValue(Integer.class)+1);
                        correct = true;

                    }

                if (!correct) {
                    getHints(Integer.parseInt(String.valueOf(T_Round.getText())));
                } else {
                        //CheckStatus(Tname);
                       // RefreshPage(Tname);
                        //Timerz.start();
                    //ShowDialog("Correct!", "Now you're waiting for your teammates to also input the answer! - To move on to the next phase!");
                    answerBox.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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



    // if score is same as round then plus round by 1 and load next image based on the users android id
    public void CheckStatus(final String Tname){
        EventID = mPref.getInt("EventID",0);
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()){

                if(snapshot.child("Round").getValue(Integer.class).equals(snapshot.child("Score").getValue(Integer.class))) {
                    EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(snapshot.child("Round").getValue(Integer.class) + 1);
                    LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("Role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class)+1);
                }
                else if (snapshot.child("Round").getValue(Integer.class).equals(4)){
                    Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
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