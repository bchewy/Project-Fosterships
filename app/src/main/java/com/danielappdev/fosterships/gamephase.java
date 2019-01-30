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
    Integer EventID;
    String Android_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    TextView TxtViewname;
    Button btnTryGuess;
    ImageView imageView;
    DatabaseReference EventRef;
    String Tname;
    Integer Round;
    boolean isImageFitToScreen;
    Integer runOnce = 0;
    Integer makeshiftRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        EventRef = database.getReference(String.valueOf("Events"));
        setContentView(R.layout.activity_gamephase);
        imageView = findViewById(R.id.imageViewgm2);
        answerBox = findViewById(R.id.answerBox);
        TxtViewname  = findViewById(R.id.txtTeamname);

        //Prep merge
        btnTryGuess = findViewById(R.id.btnGuess);
        //Get EventID
        Intent mIntent = getIntent();
        //EventID = 3518;
        EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot s1 : snapshot.getChildren()) {
                    if (s1.child("Members").child(Android_ID).exists()) {
                        Round1(String.valueOf(s1.child("TeamName").getValue()));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //getRole();
        // checks frequently
        new CountDownTimer(5, 1) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot s1 : snapshot.getChildren()) {
                            if (s1.child("Members").child(Android_ID).exists()) {
                                CheckStatus(String.valueOf(s1.child("TeamName").getValue()));
                                RefreshPage(String.valueOf(s1.child("TeamName").getValue()));


                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                start();
            }
        }.start();





        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Answer = String.valueOf(answerBox.getText());
                //DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
                CheckAnswer(Answer, getRound());





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
    //displays image based on round.
    //to make sure the other 3 members load the actual current round image


    public String getTeam() {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot s1 : snapshot.getChildren()) {
                    if (s1.child("Members").child(Android_ID).exists()) {

                        Tname = TxtViewname.toString();
                        Log.d("Tname", Tname);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return ("Team banana");
    }

    public void RefreshPage(String Tname){
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class));


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //method to load first image from firebase based on android IDs role and round
    //
    public void Round1(String Tname){
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(1);
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override

            public void onDataChange(DataSnapshot snapshot) {
                LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

  /*  mEditor = mPreferences.edit();
    String T = "Team banana";
        mEditor.putString("AndroidID",android_id);
        mEditor.putString("TeamName",T);
        mEditor.commit();*/
/*

    public int getRole() {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Members").child(Android_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d("exist", "exist ");

                    Integer role2 = dataSnapshot.child("Role").getValue(Integer.class);
                    Log.d("role", role2.toString());
                }
                //int makeshiftrole = dataSnapshot.child("role").getValue(Integer.class);
                mEditor = mPref.edit();
                //mEditor.putInt("Role",makeshiftrole);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //Log.d("role", (role = mPref.getInt("Role",0)).toString());
       // return role = mPref.getInt("Role",0);

    }
*/





   public int getRound() {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Log.d("exist", "exist ");

                }
                int makeshiftRound = dataSnapshot.child("Round").getValue(Integer.class);
                mEditor = mPref.edit();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //Log.d("role", (role = mPref.getInt("Role",0)).toString());
       return  makeshiftRound;
   }








    public void LoadImageFromFirebase() {
        Log.d("old", "?");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
            .load(storageReference)
                .into(imageView);
}


    public void LoadImageFromFirebase(Integer role, Integer round) {
        String temprole = role.toString();
        Log.d("pic", String.valueOf(round));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child((String.valueOf(((round-1)*4)+role)+".jpg").replace("0",""));
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }



    // EventRef = database.getReference(String.valueOf("Events"));
//method to check if answer is equal to "gamephase + round" answer
    public void CheckAnswer(final String answer, final Integer Round) {

        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean correct = false;
                for (DataSnapshot ds : snapshot.getChildren()) {
                   // int eventID = (ds.child("eventID").getValue(Integer.class));
                    //string round
                    String FirebaseAnswer = ds.child("phase1Answer").getValue(String.class);
                    if  (FirebaseAnswer.equals(answer)) {
                        //increment score.
                       EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Score").setValue(snapshot.child("Score").getValue(Integer.class)+1);

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



    public void fullScreen() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("tagimg", "Turning immersive mode mode off. ");
        } else {
            Log.i("tagimg", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }


    // if score is same as round then plus round by 1 and load next image based on the users android id
    public void CheckStatus(final String Tname){
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("Round").getValue(Integer.class).equals(snapshot.child("Score").getValue(Integer.class))){
                    EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(snapshot.child("Round").getValue(Integer.class)+1);
                    //ventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Members").child(Android_ID).child("Round").setValue(snapshot.child("Round").getValue(Integer.class)+1);
                    //LoadImageFromFirebase();

                    LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class)+1);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }





}